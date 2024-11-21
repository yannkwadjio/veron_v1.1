package com.veron.services.services;

import com.veron.dto.TiersDto;
import com.veron.entity.*;
import com.veron.repository.*;
import com.veron.services.interfaces.CustomerInterfaces;
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
public class CustomerServices implements CustomerInterfaces {
    private final CustomerRepository customerRepository;
    @Override
    public Map<String, String> createCustomer(TiersDto tiersDto) {
        Map<String,String> response=new HashMap<>();
        if(!tiersDto.getFullName().isEmpty()){
            Optional<Customer> customer2=customerRepository.findByFullName(tiersDto.getFullName());
            if(customer2.isEmpty()){

                if(!tiersDto.getCountry().isEmpty()){
                    if(!tiersDto.getEnterprise().isEmpty()){
                        if(!tiersDto.getPhoneNumber().isEmpty()){
                                if (!tiersDto.getType().isEmpty()){
                                        Customer customer=new Customer();
                                        List<Customer> listCustomer=customerRepository.findAll().stream()
                                                .filter(customer1->customer1.getDateCreation().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(Customer::getIdDay).reversed())
                                                .toList();
                                        int numeroSerie=0;
                                        if(!listCustomer.isEmpty()){
                                            numeroSerie=listCustomer.get(0).getIdDay()+1;
                                        }else{
                                            numeroSerie=1;
                                        }
                                        customer.setCountry(tiersDto.getCountry());
                                        customer.setEnterprise(tiersDto.getEnterprise());
                                        customer.setRefCustomer("CL"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                                        customer.setFullName(tiersDto.getFullName());
                                        customer.setAdresse(tiersDto.getAdresse().toUpperCase());
                                        customer.setPhoneNumber(tiersDto.getPhoneNumber());
                                        customer.setEmail(tiersDto.getEmail());
                                        customer.setDepot(0);
                                        customer.setRetrait(0);
                                        customer.setBalance(0);
                                        customer.setType(tiersDto.getType());
                                        customer.setBalanceCredit(0);
                                        customer.setIdDay(numeroSerie);
                                        customer.setDateCreation(LocalDate.now());
                                        customer.setUserCreated("admin-veron@gmail.com");
                                        customerRepository.save(customer);
                                        response.put("message","Client/Fournisseur créé avec succès");

                                }else{
                                    response.put("message","Type de tiers non sélectionné");}


                        }else{
                            response.put("message","Nº téléphone non renseigné");}
                    }else{
                        response.put("message","Entreprise non sélectionnée");}
                }else{
                    response.put("message","Pays non sélectionné");}
            }else{
                response.put("message","Un client porté déjà ce nom");}
                }else{
                    response.put("message","Nom client non renseigné");}

        return response;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Map<String, String> updateCustomByRef(String refCustomer,String fullName,String email,String phoneNumber,String adresse) {
       Map<String,String> response=new HashMap<>();
boolean state=false;
        Customer customer=customerRepository.findByRefCustomer(refCustomer).orElse(null);


        assert customer != null;
        if(!customer.getFullName().equals(fullName) && !fullName.isEmpty()){
             customer.setFullName(fullName.toUpperCase());
        }

        if(!customer.getAdresse().equals(adresse) && !adresse.isEmpty()){
            customer.setAdresse(adresse);
        }

        if(!customer.getPhoneNumber().equals(phoneNumber) && !phoneNumber.isEmpty()){
            Optional<Customer> customer1=customerRepository.findByPhoneNumber(phoneNumber);
           if(customer1.isEmpty()){
               customer.setPhoneNumber(phoneNumber);
               }else{
               state=true;
              response.put("message","Ce téléphone est déjà utilisé par un autre client");
           }
        }

            if(!customer.getEmail().equals(email) && !email.isEmpty()){
                Optional<Customer> customer2=customerRepository.findByEmail(email);
                if(customer2.isEmpty()){
                    customer.setEmail(email);
                }else{
                    state=true;
                    response.put("message","Cette adresse e-mail est déjà utilisé par un autre client");
                }
            }
            if(!state){
                customerRepository.save(customer);
            }
            response.put("message","Mise à jour effectuée avec succès");



        return response;
    }

    @Override
    public Customer getByRefCustomer(String refCustomer) {
        return customerRepository.findByRefCustomer(refCustomer).orElse(null);
    }


    public void exportCustomers(List<Customer> customers, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Pays");
        header.createCell(2).setCellValue("Entreprise");
        header.createCell(3).setCellValue("Client");
        header.createCell(4).setCellValue("Dépôt");
        header.createCell(5).setCellValue("Retrait");
        header.createCell(6).setCellValue("Solde");
        header.createCell(7).setCellValue("Crédit");

        // Remplissage des données
        int rowIndex = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(customer.getRefCustomer());
            row.createCell(1).setCellValue(customer.getCountry());
            row.createCell(2).setCellValue(customer.getEnterprise());
            row.createCell(3).setCellValue(customer.getFullName());
            row.createCell(4).setCellValue(customer.getDepot());
            row.createCell(5).setCellValue(customer.getRetrait());
            row.createCell(6).setCellValue(customer.getBalance());
            row.createCell(7).setCellValue(customer.getBalanceCredit());
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
