package com.veron.services.services;

import com.veron.entity.MvtStock;
import com.veron.repository.MvtStockRepository;
import com.veron.services.interfaces.MvtStockInterfaces;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MvtStockServices implements MvtStockInterfaces {
    @SuppressWarnings("unused")
    private final MvtStockRepository mvtStockRepository;

    @Override
    public List<MvtStock> getAllMvtStockByDate(LocalDate startDate,LocalDate endDate) {
        return mvtStockRepository.findByDateCreationBetween(startDate,endDate);
    }

    public void exportMvtStock(List<MvtStock> mvtStocks, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mouvements stock");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("idOperation");
        header.createCell(2).setCellValue("Magasin 1");
        header.createCell(3).setCellValue("Magasin 2");
        header.createCell(4).setCellValue("Produits/Forunitures");
        header.createCell(5).setCellValue("Stock initial");
        header.createCell(6).setCellValue("Entrées");
        header.createCell(7).setCellValue("Sorties");
        header.createCell(8).setCellValue("Stock final");



        // Remplissage des données
        int rowIndex = 1;
        for (MvtStock mvtStock : mvtStocks) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(mvtStock.getDateCreation());
            row.createCell(1).setCellValue(mvtStock.getIdMvtStock());
            row.createCell(2).setCellValue(mvtStock.getStore01());
            row.createCell(3).setCellValue(mvtStock.getStore02());
            row.createCell(4).setCellValue(mvtStock.getProduct());
            row.createCell(5).setCellValue(mvtStock.getInitialStock());
            row.createCell(6).setCellValue(mvtStock.getIncomingQuantity());
            row.createCell(7).setCellValue(mvtStock.getOutgoingQuantity());
            row.createCell(8).setCellValue(mvtStock.getFinalStock());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"mouvement_stocks.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
