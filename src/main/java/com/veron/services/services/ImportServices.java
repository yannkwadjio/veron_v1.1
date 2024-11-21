package com.veron.services.services;

import com.veron.entity.*;
import com.veron.enums.*;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportServices {

    public List<Agency> readExcelFile(MultipartFile file) throws IOException {
        List<Agency> agencies = new ArrayList<>();

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
                Agency agency = new Agency();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> agency.setRefAgency(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> agency.setName(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> agency.setBalanceCredit(currentCell.getNumericCellValue());
                        case 3 -> agency.setBalance(currentCell.getNumericCellValue());
                        case 4 -> agency.setNumeroAgency((int)currentCell.getNumericCellValue());
                        case 5 -> agency.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 6 -> agency.setUserCreated(currentCell.getStringCellValue());
                        case 7 -> agency.setEntity(currentCell.getStringCellValue());
                        case 8 -> agency.setCity(currentCell.getStringCellValue().toUpperCase());
                        case 9 -> agency.setRegion(currentCell.getStringCellValue().toUpperCase());
                        case 10 -> agency.setEnterprise(currentCell.getStringCellValue().toUpperCase());
                        case 11 -> agency.setCountry(currentCell.getStringCellValue().toUpperCase());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                agencies.add(agency);
            }
        }

        return agencies;
    }

    public List<BankAccount> readExcelFileBankAccount(MultipartFile file) throws IOException {
        List<BankAccount> bankAccounts = new ArrayList<>();

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
                BankAccount bankAccount = new BankAccount();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> bankAccount.setBankAccountNumber(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> bankAccount.setBalanceCredit(currentCell.getNumericCellValue());
                        case 2 -> bankAccount.setBalance(currentCell.getNumericCellValue());
                        case 3 -> bankAccount.setBank(currentCell.getStringCellValue().toUpperCase());
                        case 4 -> bankAccount.setEntity(currentCell.getStringCellValue());
                        case 5 -> bankAccount.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                bankAccounts.add(bankAccount);
            }
        }

        return bankAccounts;
    }


    public List<Cash> readExcelFileCash(MultipartFile file) throws IOException {
        List<Cash> cashes = new ArrayList<>();

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
                Cash cash = new Cash();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> cash.setRefCash(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> cash.setBalance(currentCell.getNumericCellValue());
                        case 2 -> cash.setBalanceCredit(currentCell.getNumericCellValue());
                        case 3 -> cash.setNumeroCash((int)currentCell.getNumericCellValue());
                        case 4 -> cash.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 5 -> cash.setUserCreated(currentCell.getStringCellValue());
                        case 6 -> cash.setCasherRole(CasherRole.valueOf(currentCell.getStringCellValue()));
                        case 7 -> cash.setEnterprise(currentCell.getStringCellValue());
                        case 8 -> cash.setEntity(currentCell.getStringCellValue());
                        case 9 -> cash.setUsers(currentCell.getStringCellValue());
                        case 10 -> cash.setAgency(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                cashes.add(cash);
            }
        }

        return cashes;
    }

    public List<CountLot> readExcelFileLot(MultipartFile file) throws IOException {
        List<CountLot> countLots = new ArrayList<>();

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
                CountLot countLot = new CountLot();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> countLot.setLot(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> countLot.setIdDay((int)currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                countLots.add(countLot);
            }
        }

        return countLots;
    }


    public List<CrCash> readExcelFileCrCash(MultipartFile file) throws IOException {
        List<CrCash> crCashes = new ArrayList<>();

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
                CrCash crCash = new CrCash();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> crCash.setDateCrCash(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> crCash.setNb10000((int)currentCell.getNumericCellValue());
                        case 2 -> crCash.setNb5000((int)currentCell.getNumericCellValue());
                        case 3 -> crCash.setNb2000((int)currentCell.getNumericCellValue());
                        case 4 -> crCash.setNb1000((int)currentCell.getNumericCellValue());
                        case 5 -> crCash.setNb500((int)currentCell.getNumericCellValue());
                        case 6 -> crCash.setNb100((int)currentCell.getNumericCellValue());
                        case 7 -> crCash.setNb50((int)currentCell.getNumericCellValue());
                        case 8 -> crCash.setNb25((int)currentCell.getNumericCellValue());
                        case 9 -> crCash.setNb10((int)currentCell.getNumericCellValue());
                        case 10 -> crCash.setNb5((int)currentCell.getNumericCellValue());
                        case 11 -> crCash.setNb2((int)currentCell.getNumericCellValue());
                        case 12 -> crCash.setNb1((int)currentCell.getNumericCellValue());
                        case 13 -> crCash.setTotalCash((int)currentCell.getNumericCellValue());
                        case 14 -> crCash.setSoldeInitial((int)currentCell.getNumericCellValue());
                        case 15 -> crCash.setEncaissement((int)currentCell.getNumericCellValue());
                        case 16 -> crCash.setDecaissement((int)currentCell.getNumericCellValue());
                        case 17 -> crCash.setSoldeFinal((int)currentCell.getNumericCellValue());
                        case 18 -> crCash.setDifference((int)currentCell.getNumericCellValue());
                        case 19 -> crCash.setCash(currentCell.getStringCellValue());
                        case 20 -> crCash.setAgency(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                crCashes.add(crCash);
            }
        }

        return crCashes;
    }


    public List<Customer> readExcelFileCustomer(MultipartFile file) throws IOException {
        List<Customer> customers = new ArrayList<>();

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
                Customer customer = new Customer();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> customer.setRefCustomer(currentCell.getStringCellValue());
                        case 1 -> customer.setFullName(currentCell.getStringCellValue());
                        case 2 -> customer.setAdresse(currentCell.getStringCellValue());
                        case 3 -> customer.setPhoneNumber(currentCell.getStringCellValue());
                        case 4 -> customer.setEmail(currentCell.getStringCellValue());
                        case 5 -> customer.setDepot(currentCell.getNumericCellValue());
                        case 6 -> customer.setRetrait(currentCell.getNumericCellValue());
                        case 7 -> customer.setBalance(currentCell.getNumericCellValue());
                        case 8 -> customer.setBalanceCredit(currentCell.getNumericCellValue());
                        case 9 -> customer.setUserCreated(currentCell.getStringCellValue());
                        case 10 -> customer.setIdDay((int)currentCell.getNumericCellValue());
                        case 11 -> customer.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 12 -> customer.setEnterprise(currentCell.getStringCellValue());
                        case 13 -> customer.setCountry(currentCell.getStringCellValue());
                        case 14 -> customer.setType("CLIENT");
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                customers.add(customer);
            }
        }

        return customers;
    }



    public List<Invoice> readExcelFileInvoice(MultipartFile file) throws IOException {
        List<Invoice> invoices = new ArrayList<>();

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
                Invoice invoice = new Invoice();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> invoice.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> invoice.setRefInvoice(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> invoice.setCustomer(currentCell.getStringCellValue());
                        case 3 -> invoice.setMotif(currentCell.getStringCellValue());
                        case 4 -> invoice.setProducts(List.of(currentCell.getStringCellValue()));
                        case 5 -> invoice.setAmount(currentCell.getNumericCellValue());
                        case 6 -> invoice.setAdvance(currentCell.getNumericCellValue());
                        case 7 -> invoice.setRest(currentCell.getNumericCellValue());
                        case 8 -> invoice.setBalanceCredit(currentCell.getNumericCellValue());
                        case 9 -> invoice.setAgency(currentCell.getStringCellValue());
                        case 10 -> invoice.setUserCreated(currentCell.getStringCellValue());
                        case 11 -> invoice.setProfit(currentCell.getNumericCellValue());
                        case 12 -> invoice.setInvoiceType(InvoiceType.valueOf(currentCell.getStringCellValue()));
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                invoices.add(invoice);
            }
        }

        return invoices;
    }


    public List<MissingCash> readExcelFileMissingCash(MultipartFile file) throws IOException {
        List<MissingCash> missingCashes = new ArrayList<>();

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
                MissingCash missingCash = new MissingCash();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> missingCash.setRefMissingCash(currentCell.getStringCellValue());
                        case 1 -> missingCash.setAgency(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> missingCash.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 3 -> missingCash.setResponsible(currentCell.getStringCellValue());
                        case 4 -> missingCash.setAmount(currentCell.getNumericCellValue());
                        case 5 -> missingCash.setAdvance(currentCell.getNumericCellValue());
                        case 6 -> missingCash.setRest(currentCell.getNumericCellValue());
                        case 7 -> missingCash.setBalanceCredit(currentCell.getNumericCellValue());
                        case 8 -> missingCash.setStatut(StatutMissing.valueOf(currentCell.getStringCellValue()));
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                missingCashes.add(missingCash);
            }
        }

        return missingCashes;
    }

    public List<MvtCash> readExcelFileMvtCash(MultipartFile file) throws IOException {
        List<MvtCash> mvtCashes = new ArrayList<>();

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
                MvtCash mvtCash = new MvtCash();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> mvtCash.setDateMvtCash(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> mvtCash.setRefOperationCash(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> mvtCash.setType(currentCell.getStringCellValue().toUpperCase());
                        case 3 -> mvtCash.setReference(currentCell.getStringCellValue());
                        case 4 -> mvtCash.setMotif(currentCell.getStringCellValue());
                        case 5 -> mvtCash.setSens(currentCell.getStringCellValue());
                        case 6 -> mvtCash.setBalanceBefore(currentCell.getNumericCellValue());
                        case 7 -> mvtCash.setAmount(currentCell.getNumericCellValue());
                        case 8 -> mvtCash.setFee(currentCell.getNumericCellValue());
                        case 9 -> mvtCash.setBalanceAfter(currentCell.getNumericCellValue());
                        case 10 -> mvtCash.setCash(currentCell.getStringCellValue());
                        case 11 -> mvtCash.setAgency(currentCell.getStringCellValue());
                        case 12 -> mvtCash.setIdDay((int)currentCell.getNumericCellValue());
                        case 13 -> mvtCash.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                mvtCashes.add(mvtCash);
            }
        }

        return mvtCashes;
    }


    public List<MvtCredit> readExcelFileMvtCredit(MultipartFile file) throws IOException {
        List<MvtCredit> mvtCredits = new ArrayList<>();

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
                MvtCredit mvtCredit = new MvtCredit();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> mvtCredit.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> mvtCredit.setSource(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> mvtCredit.setDestination(currentCell.getStringCellValue().toUpperCase());
                        case 3 -> mvtCredit.setAmount(currentCell.getNumericCellValue());
                        case 4 -> mvtCredit.setStatutCredit(StatutCredit.valueOf(currentCell.getStringCellValue()));
                        case 5 -> mvtCredit.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                mvtCredits.add(mvtCredit);
            }
        }

        return mvtCredits;
    }


    public List<MvtStock> readExcelFileMvtStock(MultipartFile file) throws IOException {
        List<MvtStock> mvtStocks = new ArrayList<>();

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
                MvtStock mvtStock = new MvtStock();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> mvtStock.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> mvtStock.setIdOperation(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> mvtStock.setStore01(currentCell.getStringCellValue().toUpperCase());
                        case 3 -> mvtStock.setStore02(currentCell.getStringCellValue().toUpperCase());
                        case 4 -> mvtStock.setCategory(Category.valueOf(currentCell.getStringCellValue()));
                        case 5 -> mvtStock.setProduct(currentCell.getStringCellValue());
                        case 6 -> mvtStock.setInitialStock(currentCell.getNumericCellValue());
                        case 7 -> mvtStock.setIncomingQuantity(currentCell.getNumericCellValue());
                        case 8 -> mvtStock.setOutgoingQuantity(currentCell.getNumericCellValue());
                        case 9 -> mvtStock.setFinalStock(currentCell.getNumericCellValue());
                        case 10 -> mvtStock.setValueStock(currentCell.getNumericCellValue());
                        case 11 -> mvtStock.setUserCreated(currentCell.getStringCellValue());
                        case 12 -> mvtStock.setIdDay((int)currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                mvtStocks.add(mvtStock);
            }
        }

        return mvtStocks;
    }

    public List<Payment> readExcelFilePayment(MultipartFile file) throws IOException {
        List<Payment> payments = new ArrayList<>();

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
                Payment payment = new Payment();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> payment.setCustomer(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> payment.setInvoice(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> payment.setAmountInvoice(currentCell.getNumericCellValue());
                        case 3 -> payment.setAmount(currentCell.getNumericCellValue());
                        case 4 -> payment.setRest(currentCell.getNumericCellValue());
                        case 5 -> payment.setPaymentMethod(PaymentMethod.valueOf(currentCell.getStringCellValue()));
                        case 6 -> payment.setBalanceCredit(currentCell.getNumericCellValue());
                        case 7 -> payment.setUserCreated(currentCell.getStringCellValue());
                        case 8 -> payment.setAgency(currentCell.getStringCellValue());
                        case 9 -> payment.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                payments.add(payment);
            }
        }

        return payments;
    }


    public List<Product> readExcelFileProducts(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();

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
                Product product = new Product();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> product.setRefProduct(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> product.setName(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> product.setCategory(Category.valueOf(currentCell.getStringCellValue().toUpperCase()));
                        case 3 -> product.setPurchasePrice(currentCell.getNumericCellValue());
                        case 4 -> product.setSellingPrice(currentCell.getNumericCellValue());
                        case 5 -> product.setUnitCost(currentCell.getNumericCellValue());
                        case 6 -> product.setFinalStock(currentCell.getNumericCellValue());
                        case 7 -> product.setFinalValue(currentCell.getNumericCellValue());
                        case 8 -> product.setNumeroSerie((int)currentCell.getNumericCellValue());
                        case 9 -> product.setEnterprise(currentCell.getStringCellValue());
                        case 10 -> product.setStore(currentCell.getStringCellValue());
                        case 11 -> product.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                products.add(product);
            }
        }

        return products;
    }


    public List<ProductStock> readExcelFileProductStock(MultipartFile file) throws IOException {
        List<ProductStock> productStocks = new ArrayList<>();

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
                ProductStock productStock = new ProductStock();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> productStock.setAgency(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> productStock.setRefProduct(currentCell.getStringCellValue().toUpperCase());
                        case 2 -> productStock.setCategory(Category.valueOf(currentCell.getStringCellValue().toUpperCase()));
                        case 3 -> productStock.setName(currentCell.getStringCellValue());
                        case 4 -> productStock.setPurchasePrice(currentCell.getNumericCellValue());
                        case 5 -> productStock.setFinalStock(currentCell.getNumericCellValue());
                        case 6 -> productStock.setStore(currentCell.getStringCellValue());
                        case 7 -> productStock.setLot(currentCell.getStringCellValue());
                        case 8 -> productStock.setDateExpiration(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                productStocks.add(productStock);
            }
        }

        return productStocks;
    }


    public List<Profit> readExcelFileProfit(MultipartFile file) throws IOException {
        List<Profit> profits = new ArrayList<>();

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
                Profit profit = new Profit();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> profit.setProfitDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 1 -> profit.setMontant(currentCell.getNumericCellValue());
                        case 2 -> profit.setAgency(currentCell.getStringCellValue());
                        case 3 -> profit.setEnterprise(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                profits.add(profit);
            }
        }

        return profits;
    }

    public List<PurchaseOrder> readExcelFilePurchaseOrder(MultipartFile file) throws IOException {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();

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
                PurchaseOrder purchaseOrder = new PurchaseOrder();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> purchaseOrder.setRefPurchaseOrder(currentCell.getStringCellValue());
                        case 1 -> purchaseOrder.setEnterprise(currentCell.getStringCellValue());
                        case 2 -> purchaseOrder.setAgency(currentCell.getStringCellValue());
                        case 3 -> purchaseOrder.setProducts(List.of(currentCell.getStringCellValue()));
                        case 4 -> purchaseOrder.setPriceHT(currentCell.getNumericCellValue());
                        case 5 -> purchaseOrder.setTva(currentCell.getNumericCellValue());
                        case 6 -> purchaseOrder.setPriceTTC(currentCell.getNumericCellValue());
                        case 7 -> purchaseOrder.setRemise(currentCell.getNumericCellValue());
                        case 8 -> purchaseOrder.setPaymentMethod(PaymentMethod.valueOf(currentCell.getStringCellValue()));
                        case 9 -> purchaseOrder.setStatutPurchase(StatutPurchase.valueOf(currentCell.getStringCellValue()));
                        case 10 -> purchaseOrder.setUserCreated(currentCell.getStringCellValue());
                        case 11 -> purchaseOrder.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 12 -> purchaseOrder.setBalanceCredit(currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                purchaseOrders.add(purchaseOrder);
            }
        }

        return purchaseOrders;
    }

    public List<Sale> readExcelFileSale(MultipartFile file) throws IOException {
        List<Sale> sales = new ArrayList<>();

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
                Sale sale = new Sale();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> sale.setRefSale(currentCell.getStringCellValue());
                        case 1 -> sale.setSaleDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 2 -> sale.setCustomer(currentCell.getStringCellValue());
                        case 3 -> sale.setService(currentCell.getStringCellValue());
                        case 4 -> sale.setProducts(List.of(currentCell.getStringCellValue()));
                        case 5 -> sale.setPaymentMethod(PaymentMethod.valueOf(currentCell.getStringCellValue()));
                        case 6 -> sale.setProfit(currentCell.getNumericCellValue());
                        case 7 -> sale.setPriceHT(currentCell.getNumericCellValue());
                        case 8 -> sale.setRemise(currentCell.getNumericCellValue());
                        case 9 -> sale.setTva(currentCell.getNumericCellValue());
                        case 10 -> sale.setPriceTTC(currentCell.getNumericCellValue());
                        case 11 -> sale.setLotSale(currentCell.getStringCellValue());
                        case 12 -> sale.setUserCreated(currentCell.getStringCellValue());
                        case 13 -> sale.setAgency(currentCell.getStringCellValue());
                        case 14 -> sale.setIdDay((int)currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                sales.add(sale);
            }
        }

        return sales;
    }

    public List<SellingService> readExcelFileSellingService(MultipartFile file) throws IOException {
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
                        case 0 -> sellingService.setName(currentCell.getStringCellValue());
                        case 1 -> sellingService.setDescription(currentCell.getStringCellValue());
                        case 2 -> sellingService.setPrice(currentCell.getNumericCellValue());
                        case 3 -> sellingService.setBalanceCredit(currentCell.getNumericCellValue());
                        case 4 -> sellingService.setUserCreated(currentCell.getStringCellValue());
                        case 5 -> sellingService.setEnterprise(currentCell.getStringCellValue());
                        case 6 -> sellingService.setCategory(currentCell.getStringCellValue());
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


    public List<SellingServiceCategory> readExcelFileSellingServiceCategory(MultipartFile file) throws IOException {
        List<SellingServiceCategory> sellingServiceCategories = new ArrayList<>();

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
                SellingServiceCategory sellingServiceCategory = new SellingServiceCategory();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> sellingServiceCategory.setName(currentCell.getStringCellValue());
                        case 1 -> sellingServiceCategory.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                sellingServiceCategories.add(sellingServiceCategory);
            }
        }

        return sellingServiceCategories;
    }


    public List<Spent> readExcelFileSpent(MultipartFile file) throws IOException {
        List<Spent> spents = new ArrayList<>();

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
                Spent spent = new Spent();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> spent.setRefSpent(currentCell.getStringCellValue());
                        case 1 -> spent.setName(currentCell.getStringCellValue());
                        case 2 -> spent.setBalance(currentCell.getNumericCellValue());
                        case 3 -> spent.setBalanceCredit(currentCell.getNumericCellValue());
                        case 4 -> spent.setSpendingFamily(currentCell.getStringCellValue());
                        case 5 -> spent.setUserCreated(currentCell.getStringCellValue());
                        case 6 -> spent.setNumeroSerie((int)currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                spents.add(spent);
            }
        }

        return spents;
    }


    public List<Store> readExcelFileStore(MultipartFile file) throws IOException {
        List<Store> stores = new ArrayList<>();

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
                Store store = new Store();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> store.setRefStore(currentCell.getStringCellValue());
                        case 1 -> store.setName(currentCell.getStringCellValue());
                        case 2 -> store.setAgencies(currentCell.getStringCellValue());
                        case 3 -> store.setUserStores(Set.of(currentCell.getStringCellValue()));
                        case 4 -> store.setUserCreated(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                stores.add(store);
            }
        }

        return stores;
    }


    public List<Supplier> readExcelFileSupplier(MultipartFile file) throws IOException {
        List<Supplier> suppliers = new ArrayList<>();

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
                Supplier supplier = new Supplier();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> supplier.setRefSupplier(currentCell.getStringCellValue());
                        case 1 -> supplier.setCountry(currentCell.getStringCellValue());
                        case 2 -> supplier.setEnterprise(currentCell.getStringCellValue());
                        case 3 -> supplier.setNumberPhone(currentCell.getStringCellValue());
                        case 4 -> supplier.setEmail(currentCell.getStringCellValue());
                        case 5 -> supplier.setAdresse(currentCell.getStringCellValue());
                        case 6 -> supplier.setPointFocal(currentCell.getStringCellValue());
                        case 7 -> supplier.setBalanceCredit(currentCell.getNumericCellValue());
                        case 8 -> supplier.setDateCreation(currentCell.getLocalDateTimeCellValue().toLocalDate());
                        case 9 -> supplier.setUserCreated(currentCell.getStringCellValue());
                        case 10 -> supplier.setIdDay((int)currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                suppliers.add(supplier);
            }
        }

        return suppliers;
    }


    public List<Users> readExcelFileUser(MultipartFile file) throws IOException {
        List<Users> userss = new ArrayList<>();

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
                Users users = new Users();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> users.setUsername(currentCell.getStringCellValue());
                        case 1 -> users.setFullName(currentCell.getStringCellValue());
                        case 2 -> users.setPhoneNumber(currentCell.getStringCellValue());
                        case 3 -> users.setPassword(currentCell.getStringCellValue());
                        case 8 -> users.setUserCreated(currentCell.getStringCellValue());
                        case 9 -> users.setCountry(currentCell.getStringCellValue());
                        case 10 -> users.setEnterprise(currentCell.getStringCellValue());
                        case 11 -> users.setAgency(currentCell.getStringCellValue());
                        case 12 -> users.setCash(currentCell.getStringCellValue());
                        case 13 -> users.setStore(currentCell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                userss.add(users);
            }
        }

        return userss;
    }



}
