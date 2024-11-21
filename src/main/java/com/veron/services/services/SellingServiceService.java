package com.veron.services.services;

import com.veron.dto.SellingServiceDto;
import com.veron.entity.SellingService;
import com.veron.repository.SellingServiceRepository;
import com.veron.services.interfaces.SellingServiceInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SellingServiceService implements SellingServiceInterfaces {
    private final SellingServiceRepository sellingServiceRepository;

    @Override
    public Map<String, String> createService(SellingServiceDto sellingServiceDto) {
        Map<String,String> response=new HashMap<>();
        Optional<SellingService> existingService=sellingServiceRepository.findByName(sellingServiceDto.getName().toUpperCase());
        if(existingService.isEmpty()){
           if(!sellingServiceDto.getName().isEmpty()) {
               if(!sellingServiceDto.getEnterprise().isEmpty()) {
                   SellingService sellingService = new SellingService();
                   sellingService.setPrice(sellingServiceDto.getPrice());
                   sellingService.setDescription(sellingServiceDto.getDescription());
                   sellingService.setName(sellingServiceDto.getName().toUpperCase());
                   sellingService.setEnterprise(sellingServiceDto.getEnterprise());
                   sellingService.setCategory(sellingServiceDto.getCategory());
                   sellingService.setUserCreated("admin-veron@gmail.com");
                   sellingServiceRepository.save(sellingService);
                   response.put("message","Service créé avec succès");
               }else{
                   response.put("message","Entreprise non sélectionnée");
               }
           }else{
               response.put("message","Service non renseigné");
           }
        }else{
            response.put("message","Ce service existe déjà");
        }
        return response;
    }

    @Override
    public List<SellingService> getAllServices() {
        return sellingServiceRepository.findAll();
    }

    @Override
    public Map<String, String> deleteServiceSelling(int idService) {
        Map<String,String> response=new HashMap<>();
        boolean existingService=sellingServiceRepository.existsById((long)idService);
        if(existingService){
            sellingServiceRepository.deleteById((long)idService);
            response.put("message","Service supprimé avec succès");
        }else{
            response.put("message","Ce service n'existe pas");
        }
        return response;
    }

    @Override
    public SellingService getSellingServiceByName(String name) {
        return sellingServiceRepository.findByName(name).orElse(null);
    }

    @Override
    public Map<String, String> updateSellingServiceByName(String name,double price,String description) {
        Map<String,String> response=new HashMap<>();
        SellingService sellingService=sellingServiceRepository.findByName(name).orElse(null);
        if(sellingService!=null){
            if(price!=sellingService.getPrice()){
                sellingService.setPrice(price);
                sellingService.setDescription(description);
                sellingServiceRepository.save(sellingService);
                response.put("message","Mise à jour effectuée avec succès");
            }else{
                response.put("message","Ce prix est déjà présent");
            }

        }else{
            response.put("message","Ce service n'existe pas");
        }
        return response;
    }



    public void exportSellingService(List<SellingService> sellingServices, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Service");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Catégorie");
        header.createCell(2).setCellValue("Service");
        header.createCell(3).setCellValue("Prix");
        header.createCell(4).setCellValue("Description");
        header.createCell(5).setCellValue("Créé par:");


        // Remplissage des données
        int rowIndex = 1;
        for (SellingService sellingService : sellingServices) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(sellingService.getIdService());
            row.createCell(1).setCellValue(sellingService.getCategory());
            row.createCell(2).setCellValue(sellingService.getName());
            row.createCell(3).setCellValue(sellingService.getPrice());
            row.createCell(4).setCellValue(sellingService.getDescription());
            row.createCell(5).setCellValue(sellingService.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"services.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }



    public List<SellingService> readExcelFile(MultipartFile file) throws IOException {
        List<SellingService> sellingServices = new ArrayList<>();

        // Ouvrir le fichier Excel
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Sauter l'en-tête
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                // Lire les cellules de chaque ligne
                Iterator<Cell> cellsInRow = currentRow.iterator();
                SellingService sellingService = new SellingService();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> sellingService.setCategory(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> sellingService.setName(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> sellingService.setPrice(currentCell.getNumericCellValue());
                        case 3 -> sellingService.setDescription(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                sellingServices.add(sellingService);
            }
        }

        return sellingServices;
    }



}
