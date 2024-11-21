package com.veron.services.services;

import com.veron.dto.MvtCashDto;
import com.veron.entity.*;
import com.veron.enums.CashType;
import com.veron.enums.StatutMissing;
import com.veron.enums.StatutPurchase;
import com.veron.exceptions.AgencyNotFountException;
import com.veron.exceptions.CustomerNoFoundException;
import com.veron.exceptions.MvtCashNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.MvtCashInterfaces;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class MvtCashServices implements MvtCashInterfaces {
    private final MvtCashRepository mvtCashRepository;
    private final CashRepository cashRepository;
    private final AgencyRepository agencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final SpentRepository spentRepository;
    private final MissingCashRepository missingCashRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CrCashRepository crCashRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Map<String, String> createMvtCash(MvtCashDto mvtCashDto) {
        Map<String,String> response=new HashMap<>();
        List<CrCash> crCashes = crCashRepository.findAll().stream()
                .filter(mvt -> mvt.getCash().equals(mvtCashDto.getCashRef()))
                .toList();
        boolean state=false;
        for(CrCash crCash:crCashes){
            if(crCash.getDateCrCash().equals(LocalDate.now())){
                state=true;
            }
        }

        if(!state){
        if(!mvtCashDto.getType().isEmpty()){
            if(!mvtCashDto.getMotif().isEmpty()){
                if(!mvtCashDto.getSens().isEmpty()){
                    if(mvtCashDto.getAmount()!=0){
                        if (!mvtCashDto.getCashRef().isEmpty()) {
                        Cash cash=cashRepository.findByRefCash(mvtCashDto.getCashRef()).orElse(null);
                        if(cash!=null){
                            MvtCash mvtCash=new MvtCash();
                            if(mvtCashDto.getType().equals(CashType.APPRO_CAISSE_EN_SORTIE.name())){
                                if(cash.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    Agency agency=agencyRepository.findByName(cash.getAgency()).orElse(null);
                                    if(agency!=null){
                                        if(agency.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setValidated(false);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            agency.setBalanceCredit(agency.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            agency.setBalance(agency.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            agencyRepository.save(agency);
                                            response.put("message","Opération effectuée avec succès");
                                        }else{
                                            response.put("message","Crédit insuffisant");
                                        }
                                    }else{
                                        response.put("message","Agence introuvable");
                                    }
                                }else{
                                    response.put("message","Solde caisse insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.APPRO_CAISSE_EN_ENTREE.name())) {
                                if(cash.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    Agency agency=agencyRepository.findByName(cash.getAgency()).orElse(null);
                                    if(agency!=null){
                                        if(agency.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                            List<MvtCash> mvtCashes=new ArrayList<>();
                                            mvtCashes.add(mvtCash);

                                            MvtCash mvtCash1=mvtCashRepository.findByrefOperationCash(mvtCashDto.getMotif()).orElseThrow(()->new MvtCashNotFoundException("Mouvement introuvable"));
                                            mvtCash1.setValidated(true);
                                            mvtCashes.add(mvtCash1);

                                            mvtCashRepository.saveAll(mvtCashes);



                                            cash.setBalance(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            agency.setBalanceCredit(agency.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            agency.setBalance(agency.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            agencyRepository.save(agency);
                                            response.put("message","Opération effectuée avec succès");
                                        }else{
                                            response.put("message","solde caisse insuffisant");
                                        }
                                    }else{
                                        response.put("message","Agence introuvable");
                                    }
                                }else{
                                    response.put("message","Crédit insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.APPRO_CAISSE_VIA_LA_BANQUE.name())) {
                                if(cash.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    BankAccount bankAccount=bankAccountRepository.findByBankAccountNumber(mvtCashDto.getMotif()).orElse(null);
                                    if(bankAccount!=null){
                                        if(bankAccount.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            bankAccount.setBalanceCredit(bankAccount.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            bankAccount.setBalance(bankAccount.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            bankAccountRepository.save(bankAccount);
                                            response.put("message","Opération effectuée avec succès");
                                        }else{
                                            response.put("message","Solde banque insuffisant");
                                        }
                                    }else{
                                        response.put("message","compte bancaire introuvable");
                                    }
                                }else{
                                    response.put("message","Crédit insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.RETRAIT_POUR_VERSEMENT_BANQUE.name())) {
                                if (cash.getBalance() >= (mvtCashDto.getAmount() + mvtCashDto.getFee())) {
                                    BankAccount bankAccount = bankAccountRepository.findByBankAccountNumber(mvtCashDto.getMotif()).orElse(null);
                                    if (bankAccount != null) {
                                        if (bankAccount.getBalanceCredit() >= (mvtCashDto.getAmount() + mvtCashDto.getFee())) {
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "0" + idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance() - mvtCashDto.getAmount() - mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance() - mvtCashDto.getAmount() - mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit() + mvtCashDto.getAmount() + mvtCashDto.getFee());
                                            bankAccount.setBalanceCredit(bankAccount.getBalanceCredit() - mvtCashDto.getAmount() - mvtCashDto.getFee());
                                            bankAccount.setBalance(bankAccount.getBalance() + mvtCashDto.getAmount() + mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            bankAccountRepository.save(bankAccount);
                                            response.put("message","Opération effectuée avec succès");
                                        } else {
                                            response.put("message", "Crédit insuffisant");
                                        }
                                    } else {
                                        response.put("message", "Mobile money introuvable");
                                    }
                                } else {
                                    response.put("message", "Solde caisse insuffisant");
                                }
                            }else if (mvtCashDto.getType().equals(CashType.DEPENSES.name())) {
                                if(cash.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    Spent spent=spentRepository.findByName(mvtCashDto.getMotif()).orElse(null);
                                    if(spent!=null){
                                        if(spent.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                            if(spent.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                           mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            spent.setBalanceCredit(spent.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                                spent.setBalance(spent.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            spentRepository.save(spent);
                                                response.put("message","Opération effectuée avec succès");
                                        }else{
                                            response.put("message","Budget insuffisant");
                                        }
                                        }else{
                                            response.put("message","Crédit insuffisant");
                                        }
                                    }else{
                                        response.put("message","Mobile money introuvable");
                                    }
                                }else{
                                    response.put("message","Solde caisse insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.AUTRES_VERSEMENTS.name())) {
                                if(cash.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    Agency agency=agencyRepository.findByName(cash.getAgency()).orElse(null);
                                    if(agency!=null){
                                             List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                        mvtCash.setIdDay(idDay);
                                        mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        agency.setBalanceCredit(agency.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            agencyRepository.save(agency);
                                        response.put("message","Opération effectuée avec succès");

                                    }else{
                                        response.put("message","Mobile money introuvable");
                                    }
                                }else{
                                    response.put("message","Crédit insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.AVANCE_PERCUE.name())) {
                                if(cash.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                      Customer customer=customerRepository.findByFullName(mvtCashDto.getMotif()).orElseThrow(()->new CustomerNoFoundException("Client introuvable"));
                                        List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                .toList();
                                        int idDay = 0;
                                        if (listMvtCash.isEmpty()) {
                                            idDay = 1;
                                        } else {
                                            idDay = listMvtCash.get(0).getIdDay() + 1;
                                        }
                                        mvtCash.setDateMvtCash(LocalDate.now());
                                        mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                        mvtCash.setType(mvtCashDto.getType());
                                        mvtCash.setMotif(mvtCashDto.getMotif());
                                        mvtCash.setSens(mvtCashDto.getSens());
                                        mvtCash.setReference(mvtCashDto.getReference());
                                        mvtCash.setBalanceBefore(cash.getBalance());
                                        mvtCash.setAmount(mvtCashDto.getAmount());
                                        mvtCash.setFee(mvtCashDto.getFee());
                                        mvtCash.setBalanceAfter(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        mvtCash.setCash(mvtCashDto.getCashRef());
                                        mvtCash.setAgency(cash.getAgency());
                                        mvtCash.setValidated(false);
                                        mvtCash.setIdDay(idDay);
                                    mvtCash.setUserCreated("admin-veron@gmail.com");
                                        mvtCashRepository.save(mvtCash);

                                        cash.setBalance(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        cash.setBalanceCredit(cash.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                        customer.setBalance(customer.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        customer.setBalanceCredit(customer.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                       customer.setDepot(customer.getDepot()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        customerRepository.save(customer);

                                        cashRepository.save(cash);
                                        response.put("message","Opération effectuée avec succès");


                                }else{
                                    response.put("message","Crédit insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.AUTRES_RETRAITS.name())) {
                                if(cash.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                    Agency agency=agencyRepository.findByName(cash.getAgency()).orElse(null);
                                    if(agency!=null){
                                        if(agency.getBalanceCredit()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){
                                        List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                .toList();
                                        int idDay = 0;
                                        if (listMvtCash.isEmpty()) {
                                            idDay = 1;
                                        } else {
                                            idDay = listMvtCash.get(0).getIdDay() + 1;
                                        }
                                        mvtCash.setDateMvtCash(LocalDate.now());
                                        mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                        mvtCash.setType(mvtCashDto.getType());
                                        mvtCash.setMotif(mvtCashDto.getMotif());
                                        mvtCash.setSens(mvtCashDto.getSens());
                                        mvtCash.setReference(mvtCashDto.getReference());
                                        mvtCash.setBalanceBefore(cash.getBalance());
                                        mvtCash.setAmount(mvtCashDto.getAmount());
                                        mvtCash.setFee(mvtCashDto.getFee());
                                        mvtCash.setBalanceAfter(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        mvtCash.setCash(mvtCashDto.getCashRef());
                                        mvtCash.setAgency(cash.getAgency());
                                        mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                        mvtCashRepository.save(mvtCash);

                                        cash.setBalance(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        cash.setBalanceCredit(cash.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        agency.setBalanceCredit(agency.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                        cashRepository.save(cash);
                                        agencyRepository.save(agency);
                                            response.put("message","Opération effectuée avec succès");
                                    }else{
                                        response.put("message","Credit insuffisant");
                                    }
                                    }else{
                                        response.put("message","Agence introuvable");
                                    }
                                }else{
                                    response.put("message","Solde caisse insuffisant");
                                }
                            } else if (mvtCashDto.getType().equals(CashType.REGULARISATIONS.name())) {
                                if(mvtCashDto.getSens().equals("ENCAISSEMENT")){
                                    if (cash.getBalanceCredit() >= (mvtCashDto.getAmount() + mvtCashDto.getFee())) {
                                        Agency agency = agencyRepository.findByName(cash.getAgency()).orElse(null);
                                        if (agency != null) {
                                               List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                        .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                        .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                        .toList();
                                                int idDay = 0;
                                                if (listMvtCash.isEmpty()) {
                                                    idDay = 1;
                                                } else {
                                                    idDay = listMvtCash.get(0).getIdDay() + 1;
                                                }
                                                mvtCash.setDateMvtCash(LocalDate.now());
                                                mvtCash.setRefOperationCash("CASH" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "0" + idDay);
                                                mvtCash.setType(mvtCashDto.getType());
                                                mvtCash.setMotif(mvtCashDto.getMotif());
                                                mvtCash.setSens(mvtCashDto.getSens());
                                                mvtCash.setReference(mvtCashDto.getReference());
                                                mvtCash.setBalanceBefore(cash.getBalance());
                                                mvtCash.setAmount(mvtCashDto.getAmount());
                                                mvtCash.setFee(mvtCashDto.getFee());
                                                mvtCash.setBalanceAfter(cash.getBalance() + mvtCashDto.getAmount() + mvtCashDto.getFee());
                                                mvtCash.setCash(mvtCashDto.getCashRef());
                                                mvtCash.setAgency(cash.getAgency());
                                                mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                                mvtCashRepository.save(mvtCash);

                                                cash.setBalance(cash.getBalance() + mvtCashDto.getAmount() + mvtCashDto.getFee());
                                                cash.setBalanceCredit(cash.getBalanceCredit() - mvtCashDto.getAmount() - mvtCashDto.getFee());
                                                agency.setBalanceCredit(agency.getBalanceCredit() + mvtCashDto.getAmount() + mvtCashDto.getFee());

                                                cashRepository.save(cash);
                                                agencyRepository.save(agency);
                                            response.put("message","Opération effectuée avec succès");

                                        }else {
                                                response.put("message", "Agence introuvable");
                                            }
                                    } else {
                                        response.put("message", "Crédit insuffisant");
                                    }
                                } else if(mvtCashDto.getSens().equals("DECAISSSEMENT")){
                                    if (cash.getBalance() >= (mvtCashDto.getAmount() + mvtCashDto.getFee())) {
                                        Agency agency = agencyRepository.findByName(cash.getAgency()).orElse(null);
                                        if (agency != null) {
                                            if(agency.getBalanceCredit()>=(mvtCashDto.getAmount() + mvtCashDto.getFee())){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "0" + idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance() + mvtCashDto.getAmount() + mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                                mvtCash.setIdDay(idDay);
                                                mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance() - mvtCashDto.getAmount() - mvtCashDto.getFee());
                                            cash.setBalanceCredit(cash.getBalanceCredit() + mvtCashDto.getAmount() + mvtCashDto.getFee());
                                            agency.setBalanceCredit(agency.getBalanceCredit() - mvtCashDto.getAmount() - mvtCashDto.getFee());

                                            cashRepository.save(cash);
                                            agencyRepository.save(agency);
                                                response.put("message","Opération effectuée avec succès");
                                        }else {
                                            response.put("message", "Crédit insuffisant");
                                        }
                                        }else {
                                            response.put("message", "Agence introuvable");
                                        }
                                    } else {
                                        response.put("message", "Solde caisse insuffisant");
                                    }

                                }
                            }else if (mvtCashDto.getType().equals(CashType.VERSEMENT_MANQUANTS.name())) {
                                    MissingCash missingCash=missingCashRepository.findByRefMissingCash(mvtCashDto.getMotif()).orElse(null);
                                    if(missingCash!=null){
                                        if((mvtCashDto.getAmount()+mvtCashDto.getFee())<=missingCash.getRest()){
                                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                    .toList();
                                            int idDay = 0;
                                            if (listMvtCash.isEmpty()) {
                                                idDay = 1;
                                            } else {
                                                idDay = listMvtCash.get(0).getIdDay() + 1;
                                            }
                                            mvtCash.setDateMvtCash(LocalDate.now());
                                            mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                            mvtCash.setType(mvtCashDto.getType());
                                            mvtCash.setMotif(mvtCashDto.getMotif());
                                            mvtCash.setSens(mvtCashDto.getSens());
                                            mvtCash.setReference(mvtCashDto.getReference());
                                            mvtCash.setBalanceBefore(cash.getBalance());
                                            mvtCash.setAmount(mvtCashDto.getAmount());
                                            mvtCash.setFee(mvtCashDto.getFee());
                                            mvtCash.setBalanceAfter(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            mvtCash.setCash(mvtCashDto.getCashRef());
                                            mvtCash.setAgency(cash.getAgency());
                                            mvtCash.setValidated(false);
                                            mvtCash.setIdDay(idDay);
                                            mvtCash.setUserCreated("admin-veron@gmail.com");
                                            mvtCashRepository.save(mvtCash);

                                            cash.setBalance(cash.getBalance()+mvtCashDto.getAmount()+mvtCashDto.getFee());


                                            missingCash.setBalanceCredit(missingCash.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                            missingCash.setAdvance(missingCash.getAdvance()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                            missingCash.setRest(missingCash.getRest()-mvtCashDto.getAmount()-mvtCashDto.getFee());

                                            if(missingCash.getRest()==0){
                                                missingCash.setStatut(StatutMissing.VERSE);
                                            }
                                            cashRepository.save(cash);
                                            missingCashRepository.save(missingCash);
                                            response.put("message","Opération effectuée avec succès");

                                        }else{
                                            response.put("message","Le montant versé ne doit pas être supérieur au montant restant");
                                        }

                                    }else{
                                        response.put("message","Manquant introuvable");
                                    }
                            }else if (mvtCashDto.getType().equals(CashType.BON_DE_COMANDE.name())) {
                                if(cash.getBalance()>=(mvtCashDto.getAmount()+mvtCashDto.getFee())){

                                    PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder(mvtCashDto.getMotif()).orElse(null);
                                    if(purchaseOrder!=null){
                                        List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                                .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                                .toList();
                                        int idDay = 0;
                                        if (listMvtCash.isEmpty()) {
                                            idDay = 1;
                                        } else {
                                            idDay = listMvtCash.get(0).getIdDay() + 1;
                                        }
                                        mvtCash.setDateMvtCash(LocalDate.now());
                                        mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                        mvtCash.setType(mvtCashDto.getType());
                                        mvtCash.setMotif(mvtCashDto.getMotif());
                                        mvtCash.setSens(mvtCashDto.getSens());
                                        mvtCash.setReference(mvtCashDto.getReference());
                                        mvtCash.setBalanceBefore(cash.getBalance());
                                        mvtCash.setAmount(mvtCashDto.getAmount());
                                        mvtCash.setFee(mvtCashDto.getFee());
                                        mvtCash.setBalanceAfter(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        mvtCash.setCash(mvtCashDto.getCashRef());
                                        mvtCash.setAgency(cash.getAgency());
                                        mvtCash.setValidated(false);
                                        mvtCash.setIdDay(idDay);
                                        mvtCash.setUserCreated("admin-veron@gmail.com");
                                        mvtCashRepository.save(mvtCash);

                                        cash.setBalance(cash.getBalance()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        cash.setBalanceCredit(cash.getBalanceCredit()+mvtCashDto.getAmount()+mvtCashDto.getFee());
                                        purchaseOrder.setBalanceCredit(purchaseOrder.getBalanceCredit()-mvtCashDto.getAmount()-mvtCashDto.getFee());
                                        purchaseOrder.setStatutPurchase(StatutPurchase.PAYE);

                                        purchaseOrderRepository.save(purchaseOrder);
                                        cashRepository.save(cash);
                                        response.put("message","Opération effectuée avec succès");

                                    }else{
                                       throw new MvtCashNotFoundException("Bon de commande introuvable");
                                    }
                                }else{
                                    response.put("message", "Solde caisse insuffisant");
                                }

                            }
                        }else{
                          response.put("message","Caisse non trouvée");
                      }
                    }else{
                        response.put("message","Caisse non trouvée"); }
                    } else{
                        response.put("message","Principal non renseigné");
                    }
                }else{
                    response.put("message","Sens de l'opération non sélectionné");
                }
            }else{
                response.put("message","Motif de l'opération non sélectionné");
            }
        }else{
            response.put("message","Type d'opération non sélectionné");
        }
        }else{
            response.put("message","Impossible d'effectuer cette opération, car vous avez déjà clôturer votre caisse");
        }


        return response;
    }

    @Override
    public List<MvtCash> getAllByMvtCash() {
        return mvtCashRepository.findAll();
    }

    @Override
    public List<MvtCash> getAllMvtCashByDate(LocalDate startDate, LocalDate endDate) {
        return mvtCashRepository.findByDateMvtCashBetween(startDate, endDate);
    }

    @Override
    public MvtCash getMvtByRef(String refOperationCash) {
        return mvtCashRepository.findByrefOperationCash(refOperationCash).orElseThrow(()->new MvtCashNotFoundException("Mouvement introuvable"));
    }


    public void exportMvtCash(List<MvtCash> mvtCashes, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mouvements");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Agence");
        header.createCell(3).setCellValue("idOperation");
        header.createCell(4).setCellValue("Type");
        header.createCell(5).setCellValue("Référence");
        header.createCell(6).setCellValue("Motif");
        header.createCell(7).setCellValue("Sens");
        header.createCell(8).setCellValue("Solde avant");
        header.createCell(9).setCellValue("Principal");
        header.createCell(10).setCellValue("Frais");
        header.createCell(11).setCellValue("Solde après");
        header.createCell(12).setCellValue("Créé par:");


        // Remplissage des données
        int rowIndex = 1;
        for (MvtCash mvtCash : mvtCashes) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(mvtCash.getIdMvtCash());
            row.createCell(1).setCellValue(mvtCash.getDateMvtCash());
            row.createCell(2).setCellValue(mvtCash.getAgency());
            row.createCell(3).setCellValue(mvtCash.getRefOperationCash());
            row.createCell(4).setCellValue(mvtCash.getType());
            row.createCell(5).setCellValue(mvtCash.getReference());
            row.createCell(6).setCellValue(mvtCash.getMotif());
            row.createCell(7).setCellValue(mvtCash.getSens());
            row.createCell(8).setCellValue(mvtCash.getBalanceBefore());
            row.createCell(9).setCellValue(mvtCash.getAmount());
            row.createCell(10).setCellValue(mvtCash.getFee());
            row.createCell(11).setCellValue(mvtCash.getBalanceAfter());
            row.createCell(12).setCellValue(mvtCash.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"mouvement_caisse.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
