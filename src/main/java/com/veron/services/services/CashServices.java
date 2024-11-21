package com.veron.services.services;

import com.veron.dto.CashDto;
import com.veron.dto.update.CashUpdateDto;
import com.veron.entity.*;
import com.veron.enums.CasherRole;
import com.veron.enums.Role;
import com.veron.exceptions.AgencyNotFountException;
import com.veron.exceptions.CashNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.CashInterfaces;
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
public class CashServices implements CashInterfaces {
    private final CashRepository cashRepository;
    private final AgencyRepository agencyRepository;
    private final UsersRepository usersRepository;

    @Override
    public Map<String, String> createCasher(CashDto cashDto) {
        Map<String, String> response = new HashMap<>();
        if (cashDto.getAgency()!=null) {
                       List<Cash> listCasher=cashRepository.findAll().stream()
                            .filter(casher->casher.getAgency().equals(cashDto.getAgency()))
                              .sorted(Comparator.comparing(Cash::getNumeroCash).reversed())
                              .toList();
                            int numeroSerie=0;
                            if(!listCasher.isEmpty()){
                                numeroSerie=listCasher.get(0).getNumeroCash()+1;
                            }else{
                                numeroSerie=1;
                            }
                            Agency agency=agencyRepository.findByName(cashDto.getAgency().toUpperCase()).orElse(null);
                            Cash cash=new Cash();

                            cash.setAgency(cashDto.getAgency());
                        assert agency != null;
                        cash.setEnterprise(agency.getEnterprise());
                        cash.setRefCash(agency.getRefAgency()+"-CASH"+0+numeroSerie);
                            String namCash=cash.getRefCash();
                            cash.setNumeroCash(numeroSerie);
                            cash.setBalance(0);
                            cash.setBalanceCredit(0);
                            if(numeroSerie==1){
                                cash.setCasherRole(CasherRole.PRINCIPAL);
                            }else{
                                cash.setCasherRole(CasherRole.SECONDAIRE);
                            }
                            cash.setDateCreation(LocalDate.now());
                            cash.setEntity("CAISSE");
                            cash.setUserCreated("admin-veron@gmail.com");
                            cashRepository.save(cash);
                            agencyRepository.save(agency);
                            response.put("message","Caisse créée avec succès");

                    } else {
                        throw new CashNotFoundException("Agence introuvable");
                    }


        return response;
    }

    @Override
    public List<Cash> getAllCashers() {
        return cashRepository.findAll();
    }


    @Override
    public Map<String, String> updateByRefCash(CashUpdateDto cashUpdateDto, String refCash) {
        Map<String,String> response=new HashMap<>();
        if(cashUpdateDto.getAgency()!=null){
if(cashUpdateDto.getRefCash()!=null){
    if(cashUpdateDto.getUsers()!=null){
        if(cashUpdateDto.getCasherRole()!=null){
            Cash cash=cashRepository.findByRefCash(cashUpdateDto.getRefCash()).orElse(null);
           if(cash!=null){
               Optional<Users> userFound= Optional.empty();
               if(cash.getUsers()!=null){
                   if(cash.getUsers().equals(cashUpdateDto.getUsers())){
                       userFound=usersRepository.findByUsername(cash.getUsers().toUpperCase());
                   }

               }
               if(userFound.isPresent()){
                   userFound.get().setCash(null);
                   usersRepository.save(userFound.get());
               }
               Users users=usersRepository.findByUsername(cashUpdateDto.getUsers()).orElse(null);
               assert users != null;
               if(users.getRole().contains(Role.CAISSIER)){
                   cash.setAgency(cashUpdateDto.getAgency());
                   cash.setUsers(cashUpdateDto.getUsers());
                   List<Cash> listCash=cashRepository.findAll().stream()
                           .filter(cash1 -> cash1.getCasherRole().equals(CasherRole.PRINCIPAL))
                           .toList();
                   if(listCash.isEmpty()){
                       cash.setCasherRole(CasherRole.valueOf(cashUpdateDto.getCasherRole()));
                   }else {
                       Cash cash2=cashRepository.findByRefCash(listCash.get(0).getRefCash()).orElse(null);
                       assert cash2 != null;
                       cash2.setCasherRole(CasherRole.SECONDAIRE);
                       if(cash2.getUsers()!=null){
                           if(cash2.getUsers().equals(cash.getUsers())){
                               cash2.setUsers(null);
                           }
                       }

                       cashRepository.save(cash2);
                       cash.setUsers(users.getFullName());
                       cash.setCasherRole(CasherRole.valueOf(cashUpdateDto.getCasherRole()));

                   }
                   users.setCash(cashUpdateDto.getRefCash());
                   cashRepository.save(cash);
                   usersRepository.save(users);

                   response.put("message","Utilisateur assigné avec succès");
           }else{

                   response.put("message","Seul un caissier peut avoir une caisse");
               }

       }else{
               throw new CashNotFoundException("Caisse introuvable");
           }
             }else{
            response.put("message","Rôle non assigné à ce caissier");
        }
    }else{
        throw new CashNotFoundException("Utilisateur introuvable");
    }
}else{
    response.put("message","Caisse non sélectionnée");
}
        }else{
            throw new CashNotFoundException("Agence introuvable");
        }
        return response;
    }

    @Override
    public Cash getByCash(String refCash) {
        return cashRepository.findByRefCash(refCash).orElse(null);
    }

    @Override
    public Map<String, String> updateBalance(String agency, String cash, double balance, double newBalance) {
       Map<String,String> response=new HashMap<>();
       Agency agency1=agencyRepository.findByName(agency).orElseThrow(()->new AgencyNotFountException("Agence introuvable"));
       Cash cash1=cashRepository.findByRefCash(cash).orElseThrow(()->new CashNotFoundException("Caisse introuvable"));
       if(newBalance!=balance){
           cash1.setBalance(newBalance);
           if(newBalance<balance){
               cash1.setBalanceCredit(cash1.getBalanceCredit()+(balance-newBalance));
           }else if(balance<newBalance){
               cash1.setBalanceCredit(cash1.getBalanceCredit()-(newBalance-balance));
           }
           cashRepository.save(cash1);
           response.put("message","Solde mis à jour");

       }else{
           response.put("message","Impossible d'allouer le même solde");
       }
        return response;
    }


    public void exportCashes(List<Cash> cashes, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Caisse");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Agence");
        header.createCell(2).setCellValue("Caisse");
        header.createCell(3).setCellValue("Rôle");
        header.createCell(4).setCellValue("Responsable");
        header.createCell(5).setCellValue("Solde");
        header.createCell(6).setCellValue("Crédit");

        // Remplissage des données
        int rowIndex = 1;
        for (Cash cash : cashes) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(cash.getIdCash());
            row.createCell(1).setCellValue(cash.getAgency());
            row.createCell(2).setCellValue(cash.getRefCash());
            row.createCell(3).setCellValue(cash.getCasherRole().toString());
            row.createCell(4).setCellValue(cash.getUsers());
            row.createCell(5).setCellValue(cash.getBalance());
            row.createCell(6).setCellValue(cash.getBalanceCredit());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"caisse.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
