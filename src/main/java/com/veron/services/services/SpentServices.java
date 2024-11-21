package com.veron.services.services;

import com.veron.dto.SpentDto;
import com.veron.entity.SpendingFamily;
import com.veron.entity.Spent;
import com.veron.repository.SpendingFamilyRepository;
import com.veron.repository.SpentRepository;
import com.veron.services.interfaces.SpentInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SpentServices implements SpentInterfaces {
    private final SpentRepository spentRepository;
    private final SpendingFamilyRepository spendingFamilyRepository;


    @Override
    public List<Spent> getAllSpents() {
        return spentRepository.findAll();
    }

    @Override
    public Map<String, String> createSpent(SpentDto spentDto) {
        Map<String,String> response=new HashMap<>();
        Optional<Spent> existingSpent=spentRepository.findByName(spentDto.getName().toUpperCase());
        if(existingSpent.isEmpty()){
               if(!spentDto.getName().isEmpty()){
                   if(!spentDto.getSpendingFamily().isEmpty()){
                       SpendingFamily spendingFamily=spendingFamilyRepository.findByName(spentDto.getSpendingFamily().toUpperCase()).orElse(null);
                      List<Spent> listSpents=spentRepository.findAll().stream()
                              .sorted(Comparator.comparing(Spent::getNumeroSerie).reversed())
                              .toList();
                      int numeroSerie=0;
                      if(listSpents.isEmpty()){
                          numeroSerie=1;
                      }else{
                          numeroSerie=listSpents.get(0).getNumeroSerie()+1;
                      }
                       Spent spent=new Spent();
                      spent.setRefSpent(spendingFamily.getRefFamily()+"DEP0"+numeroSerie);
                      spent.setName(spentDto.getName().toUpperCase());
                      spent.setBalance(0);
                      spent.setBalanceCredit(0);
                      spent.setNumeroSerie(numeroSerie);
                      spent.setSpendingFamily(spendingFamily.getName());
                      spent.setUserCreated("admin-veron@gmail.com");
                      spentRepository.save(spent);
                      spendingFamilyRepository.save(spendingFamily);
                      response.put("message","Dépense créée avec succès");

                   }else{
                       response.put("message","Famille de dépenses non sélectionnée");
                   }
               }else{
                   response.put("message","Nom de la dépense non renseignée");
               }
        }else{
            response.put("message","Cette dépense existe déjà");
        }
        return response;
    }

    public void exportSpent(List<Spent> spents, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Depense");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Famille");
        header.createCell(2).setCellValue("Dépense");
        header.createCell(3).setCellValue("Budget");
        header.createCell(4).setCellValue("Crédit");


        // Remplissage des données
        int rowIndex = 1;
        for (Spent spent : spents) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(spent.getRefSpent());
            row.createCell(1).setCellValue(spent.getSpendingFamily());
            row.createCell(2).setCellValue(spent.getName());
            row.createCell(3).setCellValue(spent.getBalance());
            row.createCell(4).setCellValue(spent.getBalanceCredit());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"depense.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
