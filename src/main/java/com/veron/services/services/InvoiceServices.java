package com.veron.services.services;

import com.veron.entity.Invoice;
import com.veron.repository.InvoiceRepository;
import com.veron.services.interfaces.InvoiceInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServices implements InvoiceInterfaces {
    @SuppressWarnings("unused")
    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice getByRefInvoice(String refInvoice) {
        return invoiceRepository.findByRefInvoice(refInvoice).orElse(null);
    }

    @Override
    public void deleteInvoice(String refInvoice) {
        Invoice invoice=invoiceRepository.findByRefInvoice(refInvoice).orElseThrow(()->new RuntimeException("Facture introuvable"));
        invoiceRepository.delete(invoice);
    }

    public void exportInvoice(List<Invoice> products, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produit");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Agence");
        header.createCell(3).setCellValue("Ref.");
        header.createCell(4).setCellValue("Client");
        header.createCell(5).setCellValue("Motif");
        header.createCell(6).setCellValue("Produits");
        header.createCell(7).setCellValue("Montant");
        header.createCell(8).setCellValue("Avence");
        header.createCell(9).setCellValue("Reste");
        header.createCell(10).setCellValue("Créé par:");


        // Remplissage des données
        int rowIndex = 1;
        for (Invoice invoice : products) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(invoice.getIdInvoice());
            row.createCell(1).setCellValue(invoice.getDateCreation());
            row.createCell(2).setCellValue(invoice.getAgency());
            row.createCell(3).setCellValue(invoice.getRefInvoice());
            row.createCell(4).setCellValue(invoice.getCustomer());
            row.createCell(5).setCellValue(invoice.getMotif());
            row.createCell(6).setCellValue(invoice.getProducts().toString());
            row.createCell(7).setCellValue(invoice.getAmount());
            row.createCell(8).setCellValue(invoice.getAdvance());
            row.createCell(9).setCellValue(invoice.getRest());
            row.createCell(10).setCellValue(invoice.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"facture.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
