package com.veron.services.services;


import com.veron.entity.*;
import com.veron.enums.PaymentMethod;
import com.veron.repository.*;
import com.veron.services.interfaces.PurchaseInterfaces;
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
public class PurchaseSevices implements PurchaseInterfaces {
    private final PurchaseRepository purchaseRepository;
    private final CashRepository cashRepository;
    private final ProductRepository productRepository;
    private final MvtCashRepository mvtCashRepository;

    @Override
    public Map<String, String> createPurchase(String refCashValue, Map<String, String> listProduct, String paymentMethodValue, String supplierValue, String totalPriceValue, String remiseValue) {
        Map<String, String> response = new HashMap<>();
        if (paymentMethodValue != null && !paymentMethodValue.isEmpty()) {
            if (paymentMethodValue.equals(PaymentMethod.ESPECES.name())) {
            Cash cash = cashRepository.findByRefCash(refCashValue).orElse(null);
            if (cash != null) {
                        double prixHT = Double.parseDouble(totalPriceValue);
                        double remise = 0;

                        if (remiseValue != null && !remiseValue.isEmpty()) {
                            remise = Double.parseDouble(remiseValue);
                        }

                        if (cash.getBalance() >= prixHT) {
                            Purchase purchase = new Purchase();
                            purchase.setPurchaseDate(LocalDate.now());
                            purchase.setAgency(cash.getAgency());
                            purchase.setPaymentMethod(PaymentMethod.ESPECES);
                            purchase.setSupplier(supplierValue);
                            purchase.setRemise(remise);
                            purchase.setPriceHT(prixHT);

                            List<String> productList = new ArrayList<>();
                            for (String product : listProduct.keySet()) {
                                productList.add(product + ":" + listProduct.get(product));
                                Product product1 = productRepository.findByName(product).orElse(null);

                                if (product1 != null) {
                                    double num = (double) Integer.parseInt(listProduct.get(product)) / product1.getPurchasePrice();

                                    product1.setFinalStock(product1.getFinalStock() + num);

                                    product1.setFinalValue(product1.getFinalValue() + (double) Integer.parseInt(listProduct.get(product)));
                                    productRepository.save(product1);
                                }
                            }

                            purchase.setProducts(productList);

                            List<Purchase> listPurchase = purchaseRepository.findAll().stream()
                                    .filter(date -> date.getPurchaseDate().equals(LocalDate.now()))
                                    .sorted(Comparator.comparing(Purchase::getIdDay).reversed())
                                    .toList();

                            int idDay = listPurchase.isEmpty() ? 1 : listPurchase.get(0).getIdDay() + 1;
                            String reference = "ACH" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDay;
                            purchase.setRefPurchase(reference);
                            purchaseRepository.save(purchase);


                            MvtCash mvtCash=new MvtCash();
                            mvtCash.setValidated(false);
                            mvtCash.setAgency(cash.getAgency());
                            mvtCash.setReference(reference);
                            mvtCash.setCash(cash.getRefCash());
                            mvtCash.setAmount(prixHT);
                            mvtCash.setFee(0);
                            mvtCash.setBalanceAfter(cash.getBalance()-prixHT);
                            mvtCash.setBalanceBefore(cash.getBalance());
                            mvtCash.setDateMvtCash(LocalDate.now());
                            List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                    .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                    .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                    .toList();
                            if (listMvtCash.isEmpty()) {
                                idDay = 1;
                            } else {
                                idDay = listMvtCash.get(0).getIdDay() + 1;
                            }
                            mvtCash.setRefOperationCash("CASH" + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()) + "0" + idDay);
                           mvtCash.setSens("DECAISSEMENT");
                           mvtCash.setMotif("ACHAT PRODUITS");
                            mvtCash.setType("ACHATS");
                            mvtCash.setIdDay(idDay);
                           mvtCashRepository.save(mvtCash);


                            cash.setBalance(cash.getBalance() - prixHT + remise);
                            cash.setBalanceCredit(cash.getBalanceCredit() + prixHT - remise);
                            cashRepository.save(cash);


                            response.put("message", "Achat effectué avec succès");
                        } else {
                            response.put("message", "Solde caisse insuffisant pour effectuer cette opération");
                        }
                     }else {
                            response.put("message", "Caisse non trouvée");
                    }
                    }else {

                    }

               } else {
                 response.put("message", "Méthode de paiement non sélectionnée");
              }

        return response;

    }

    @Override
    public List<Purchase> getAllPurchaseByDate(LocalDate startDate, LocalDate endDate) {
        return purchaseRepository.findByPurchaseDateBetween(startDate,endDate);
    }

    public void exportPurchase(List<Purchase> purchases, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("achat");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("Agence");
        header.createCell(2).setCellValue("idProduit");
        header.createCell(3).setCellValue("Fournisseur");
        header.createCell(4).setCellValue("Produit");
        header.createCell(5).setCellValue("Mode");
        header.createCell(6).setCellValue("Prix HT");
        header.createCell(7).setCellValue("Remise");
        header.createCell(8).setCellValue("Prix TTC");
        header.createCell(9).setCellValue("Crée par");


        // Remplissage des données
        int rowIndex = 1;
        for (Purchase purchase : purchases) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(purchase.getPurchaseDate());
            row.createCell(1).setCellValue(purchase.getAgency());
            row.createCell(2).setCellValue(purchase.getRefPurchase());
            row.createCell(3).setCellValue(purchase.getSupplier());
            row.createCell(4).setCellValue(purchase.getProducts().toString());
            row.createCell(5).setCellValue(purchase.getPaymentMethod().name());
            row.createCell(6).setCellValue(purchase.getPriceHT());
            row.createCell(7).setCellValue(purchase.getRemise());
            row.createCell(8).setCellValue(purchase.getTotalPrice());
            row.createCell(9).setCellValue(purchase.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"achats.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
