package com.veron.services.services;

import com.veron.dto.MvtCreditDto;
import com.veron.entity.*;
import com.veron.enums.StatutCredit;
import com.veron.exceptions.MvtCreditNotFoundException;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.MvtCreditInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MvtCreditServices implements MvtCreditInterfaces {
    private final MvtCreditRepository mvtCreditRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final CountryRepository countryRepository;
    private final AgencyRepository agencyRepository;
    private final CashRepository cashRepository;
    private final BankAccountRepository bankAccountRepository;
    private final SpentRepository spentRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public Map<String, String> createMvtCredit(MvtCreditDto mvtCreditDto) {
        Map<String, String> response = new HashMap<>();
        if (mvtCreditDto.getSource()!=null) {
            if (mvtCreditDto.getDestination()!=null) {
                if (mvtCreditDto.getAmount() != 0) {
                    MvtCredit mvtCredit = new MvtCredit();
                    mvtCredit.setAmount(mvtCreditDto.getAmount());
                    mvtCredit.setDateCreation(LocalDate.now());
                    mvtCredit.setSource(mvtCreditDto.getSource());
                    mvtCredit.setDestination(mvtCreditDto.getDestination());
                    mvtCredit.setStatutCredit(StatutCredit.NON_COMPTABILISE);
                    if (mvtCreditDto.getEntiteSource().equals("PAYS") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        Country country = countryRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        assert country != null;
                        if (country.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            country.setBalanceCredit(country.getBalanceCredit() - mvtCreditDto.getAmount());
                            enterpriseRepository.save(enterprise);
                            countryRepository.save(country);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("PAYS") && mvtCreditDto.getEntiteDestination().equals("PAYS")) {
                        Country country = countryRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert country != null;
                        country.setBalanceCredit(country.getBalanceCredit() + mvtCreditDto.getAmount());
                        countryRepository.save(country);
                        mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                    } else if (mvtCreditDto.getEntiteSource().equals("COMPAGNIE") && mvtCreditDto.getEntiteDestination().equals("PAYS")) {
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        Country country = countryRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert enterprise != null;
                        if (enterprise.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() - mvtCreditDto.getAmount());
                            assert country != null;
                            country.setBalanceCredit(country.getBalanceCredit() + mvtCreditDto.getAmount());
                            enterpriseRepository.save(enterprise);
                            countryRepository.save(country);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }else if (mvtCreditDto.getEntiteSource().equals("VENTES") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {
                      double saleBalanceCredit=0;
                      List<Customer> customers=customerRepository.findAll();
                      for(Customer customer:customerRepository.findAll()){
                          saleBalanceCredit+=customer.getBalanceCredit();
                      }
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);

                        if (saleBalanceCredit >= mvtCreditDto.getAmount()) {
                            for(Customer customer:customerRepository.findAll()){
                                double amount=mvtCreditDto.getAmount();
                               if(amount>=customer.getBalanceCredit()){
                                   customer.setBalanceCredit(0);
                                   amount-=customer.getBalanceCredit();
                               }else if(amount<customer.getBalanceCredit()){
                                   customer.setBalanceCredit(customer.getBalanceCredit()-amount);
                                   amount=0;
                               }
                                customers.add(customer);
                            }


                              assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            customerRepository.saveAll(customers);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }else if (mvtCreditDto.getEntiteSource().equals("ACHATS") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {

                        PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000").orElseThrow(()->new PurchaseNotFoundException("Entité des achats introuvables"));
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);

                        if (purchaseOrder.getBalanceCredit() >= mvtCreditDto.getAmount()) {

                            assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            purchaseOrder.setBalanceCredit(purchaseOrder.getBalanceCredit()-mvtCreditDto.getAmount());
                            purchaseOrderRepository.save(purchaseOrder);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }else if (mvtCreditDto.getEntiteSource().equals("COMPAGNIE") && mvtCreditDto.getEntiteDestination().equals("ACHATS")) {

                        PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000").orElseThrow(()->new PurchaseNotFoundException("Entité des achats introuvables"));
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        assert enterprise != null;
                        if (enterprise.getBalanceCredit() >= mvtCreditDto.getAmount()) {

                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() - mvtCreditDto.getAmount());
                            purchaseOrder.setBalanceCredit(purchaseOrder.getBalanceCredit()+mvtCreditDto.getAmount());
                            purchaseOrderRepository.save(purchaseOrder);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }else if (mvtCreditDto.getEntiteSource().equals("AUTRES VERSEMENTS") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {
                        double paymentBalanceCredit=0;
                        List<Customer> customers=customerRepository.findAll();
                        for(Customer customer:customerRepository.findAll()){
                            paymentBalanceCredit+=customer.getBalanceCredit();
                        }
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);

                        if (paymentBalanceCredit >= mvtCreditDto.getAmount()) {
                            for(Customer customer:customerRepository.findAll()){
                                double amount=mvtCreditDto.getAmount();
                                if(amount>=customer.getBalanceCredit()){
                                    customer.setBalanceCredit(0);
                                    amount-=customer.getBalanceCredit();
                                }else if(amount<customer.getBalanceCredit()){
                                    customer.setBalanceCredit(customer.getBalanceCredit()-amount);
                                    amount=0;
                                }
                                customers.add(customer);
                            }


                            assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            customerRepository.saveAll(customers);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("COMPAGNIE") && mvtCreditDto.getEntiteDestination().equals("AGENCE")) {
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        Agency agency = agencyRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert enterprise != null;
                        if (enterprise.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() - mvtCreditDto.getAmount());
                            assert agency != null;
                            agency.setBalanceCredit(agency.getBalanceCredit() + mvtCreditDto.getAmount());
                            enterpriseRepository.save(enterprise);
                            agencyRepository.save(agency);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);

                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }else if (mvtCreditDto.getEntiteSource().equals("AGENCE") && mvtCreditDto.getEntiteDestination().equals("DEPENSE")) {
                        Agency agency = agencyRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        Spent spent = spentRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert agency!=null;
                        if (agency.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            agency.setBalanceCredit(agency.getBalanceCredit() - mvtCreditDto.getAmount());
                            assert spent != null;
                            spent.setBalanceCredit(spent.getBalanceCredit() + mvtCreditDto.getAmount());
                            spent.setBalance(spent.getBalance() + mvtCreditDto.getAmount());
                            agencyRepository.save(agency);
                            spentRepository.save(spent);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    }  else if (mvtCreditDto.getEntiteSource().equals("AGENCE") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        Agency agency = agencyRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        assert agency != null;
                        if (agency.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            agency.setBalanceCredit(agency.getBalanceCredit() - mvtCreditDto.getAmount());
                            enterpriseRepository.save(enterprise);
                            agencyRepository.save(agency);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("AGENCE") && mvtCreditDto.getEntiteDestination().equals("CAISSE")) {
                        Cash cash = cashRepository.findByRefCash(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        Agency agency = agencyRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        assert agency != null;
                        if (agency.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            assert cash != null;
                            cash.setBalanceCredit(cash.getBalanceCredit() + mvtCreditDto.getAmount());
                            agency.setBalanceCredit(agency.getBalanceCredit() - mvtCreditDto.getAmount());
                            cashRepository.save(cash);
                            agencyRepository.save(agency);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("CAISSE") && mvtCreditDto.getEntiteDestination().equals("AGENCE")) {
                        Cash cash = cashRepository.findByRefCash(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        Agency agency = agencyRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert cash != null;
                        if (cash.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            cash.setBalanceCredit(cash.getBalanceCredit() - mvtCreditDto.getAmount());
                            assert agency != null;
                            agency.setBalanceCredit(agency.getBalanceCredit() + mvtCreditDto.getAmount());
                            cashRepository.save(cash);
                            agencyRepository.save(agency);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("COMPAGNIE") && mvtCreditDto.getEntiteDestination().equals("BANQUE")) {
                        BankAccount bankAccount = bankAccountRepository.findByBankAccountNumber(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        assert bankAccount != null;
                        assert enterprise != null;
                        if (enterprise.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            bankAccount.setBalanceCredit(bankAccount.getBalanceCredit() + mvtCreditDto.getAmount());
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() - mvtCreditDto.getAmount());
                            bankAccountRepository.save(bankAccount);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }

                    } else if (mvtCreditDto.getEntiteSource().equals("BANQUE") && mvtCreditDto.getEntiteDestination().equals("COMPAGNIE")) {
                        BankAccount bankAccount = bankAccountRepository.findByBankAccountNumber(mvtCreditDto.getSource().toUpperCase()).orElse(null);
                        Enterprise enterprise = enterpriseRepository.findByName(mvtCreditDto.getDestination().toUpperCase()).orElse(null);
                        assert bankAccount != null;
                        if (bankAccount.getBalanceCredit() >= mvtCreditDto.getAmount()) {
                            bankAccount.setBalanceCredit(bankAccount.getBalanceCredit() - mvtCreditDto.getAmount());
                            assert enterprise != null;
                            enterprise.setBalanceCredit(enterprise.getBalanceCredit() + mvtCreditDto.getAmount());
                            bankAccountRepository.save(bankAccount);
                            enterpriseRepository.save(enterprise);
                            mvtCredit.setStatutCredit(StatutCredit.COMPTABILISE);
                            mvtCredit.setUserCreated("admin-veron@gmail.com");
                            mvtCreditRepository.save(mvtCredit);
                            response.put("message", "Compense créée avec succès");
                        } else {
                            response.put("message", "Solde insuffisant");
                        }


                    }else {
                        response.put("message", "Entité destination non sélectionnée");
                    }
                } else {
                    response.put("message", "Montant non renseigné");
                }
            } else{
                throw new MvtCreditNotFoundException("Entité destination introuvable");
            }


        }else{
            throw new MvtCreditNotFoundException("Entité source introuvable");
        }
        return response;
    }

    @Override
    public List<MvtCredit> getAllMvtCreditByDate(LocalDate startDateCredit, LocalDate endDateCredit) {
        return mvtCreditRepository.findByDateCreationBetween(startDateCredit,endDateCredit);
    }


    public void exportMvtCredit(List<MvtCredit> mvtCredits, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mvt_Credits");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Source");
        header.createCell(3).setCellValue("Destination");
        header.createCell(4).setCellValue("Montant");
        header.createCell(5).setCellValue("Statut");
        header.createCell(6).setCellValue("Créé par");

        // Remplissage des données
        int rowIndex = 1;
        for (MvtCredit mvtCredit : mvtCredits) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(mvtCredit.getIdMvtCredit());
            row.createCell(1).setCellValue(mvtCredit.getDateCreation());
            row.createCell(2).setCellValue(mvtCredit.getSource());
            row.createCell(3).setCellValue(mvtCredit.getDestination());
            row.createCell(4).setCellValue(mvtCredit.getAmount());
            row.createCell(5).setCellValue(mvtCredit.getStatutCredit().name());
            row.createCell(6).setCellValue(mvtCredit.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
