package com.veron.services.services;

import com.veron.dto.PurchaseOrderDto;
import com.veron.entity.PurchaseOrder;
import com.veron.enums.PaymentMethod;
import com.veron.enums.StatutPurchase;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.PurchaseOrderRepository;
import com.veron.services.interfaces.PurchaseOrderInterfaces;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServices implements PurchaseOrderInterfaces {

    private final PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public Map<String, String> createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        Map<String,String> response=new HashMap<>();
        PurchaseOrder purchaseOrder0=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000").orElseThrow(()->new PurchaseNotFoundException("Entité des achats introuvables"));
            if(purchaseOrderDto.getEnterprise()!=null){
                if(purchaseOrderDto.getAgency()!=null){
                    if(purchaseOrderDto.getProducts()!=null){
                        if(purchaseOrderDto.getPaymentMethod()!=null){

                            if(purchaseOrderDto.getPaymentMethod().equals(PaymentMethod.ESPECES)){
                                if(purchaseOrder0.getBalanceCredit()>=purchaseOrderDto.getPriceTTC()){
                                    PurchaseOrder purchaseOrder=new PurchaseOrder();
                                    purchaseOrder.setAgency(purchaseOrderDto.getAgency());
                                    purchaseOrder.setEnterprise(purchaseOrderDto.getEnterprise());
                                    purchaseOrder.setPaymentMethod(purchaseOrderDto.getPaymentMethod());
                                    purchaseOrder.setPriceHT(purchaseOrderDto.getPriceHT());
                                    purchaseOrder.setTva(purchaseOrderDto.getTva());
                                    purchaseOrder.setRemise(purchaseOrderDto.getRemise());
                                    purchaseOrder.setPriceTTC(purchaseOrderDto.getPriceTTC());
                                    purchaseOrder.setDateCreation(LocalDate.now());
                                    purchaseOrder.setUserCreated("admin-veron@gmail.com");
                                    try{
                                        List<String> triList=purchaseOrderDto.getProducts().stream()
                                                .toList();
                                        purchaseOrder.setProducts(triList);
                                        List<PurchaseOrder> listPurchaseOrder=purchaseOrderRepository.findAll().stream()
                                                .sorted(Comparator.comparing(PurchaseOrder::getIdPurchaseOrder).reversed())
                                                .toList();
                                        if(!listPurchaseOrder.isEmpty()){
                                            purchaseOrder.setRefPurchaseOrder("BC"+ LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())+"0"+(listPurchaseOrder.get(0).getIdPurchaseOrder()+1));

                                        }else{
                                            purchaseOrder.setRefPurchaseOrder("BC"+ LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())+"01");

                                        }
                                        purchaseOrder.setStatutPurchase(StatutPurchase.EN_INSTANCE);
                                        purchaseOrder.setBalanceCredit(purchaseOrderDto.getPriceTTC());
                                        purchaseOrder0.setBalanceCredit(purchaseOrder0.getBalanceCredit()-purchaseOrderDto.getPriceTTC());
                                        List<PurchaseOrder> listPurchase=List.of(purchaseOrder,purchaseOrder0);
                                        purchaseOrderRepository.saveAll(listPurchase);
                                        response.put("message","Bon de commande créé avec succès");
                                    }catch(PurchaseNotFoundException e){
                                        throw new PurchaseNotFoundException("Liste de produit introuvable");
                                    }
                                }else{
                                    response.put("message","Crédit insuffisant");
                                }
                            }else if(purchaseOrderDto.getPaymentMethod().equals(PaymentMethod.A_CREDIT)){
                                PurchaseOrder purchaseOrder=new PurchaseOrder();
                                purchaseOrder.setAgency(purchaseOrderDto.getAgency());
                                purchaseOrder.setEnterprise(purchaseOrderDto.getEnterprise());
                                purchaseOrder.setPaymentMethod(purchaseOrderDto.getPaymentMethod());
                                purchaseOrder.setPriceHT(purchaseOrderDto.getPriceHT());
                                purchaseOrder.setTva(purchaseOrderDto.getTva());
                                purchaseOrder.setRemise(purchaseOrderDto.getRemise());
                                purchaseOrder.setPriceTTC(purchaseOrderDto.getPriceTTC());
                                purchaseOrder.setUserCreated("admin-veron@gmail.com");
                                purchaseOrder.setDateCreation(LocalDate.now());
                                try{
                                    List<String> triList=purchaseOrderDto.getProducts().stream()
                                            .toList();
                                    purchaseOrder.setProducts(triList);
                                    List<PurchaseOrder> listPurchaseOrder=purchaseOrderRepository.findAll().stream()
                                            .sorted(Comparator.comparing(PurchaseOrder::getIdPurchaseOrder).reversed())
                                            .toList();
                                    if(!listPurchaseOrder.isEmpty()){
                                        purchaseOrder.setRefPurchaseOrder("BC"+ LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())+"0"+(listPurchaseOrder.get(0).getIdPurchaseOrder()+1));

                                    }else{
                                        purchaseOrder.setRefPurchaseOrder("BC"+ LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth())+"01");

                                    }
                                    purchaseOrder.setStatutPurchase(StatutPurchase.EN_INSTANCE);
                                    purchaseOrder.setBalanceCredit(purchaseOrderDto.getPriceTTC());
                                    purchaseOrder0.setBalanceCredit(purchaseOrder0.getBalanceCredit()-purchaseOrderDto.getPriceTTC());
                                    List<PurchaseOrder> listPurchase=List.of(purchaseOrder,purchaseOrder0);
                                    purchaseOrderRepository.saveAll(listPurchase);
                                    response.put("message","Bon de commande créé avec succès");
                                }catch(PurchaseNotFoundException e){
                                    throw new PurchaseNotFoundException("Liste de produit introuvable");
                                }
                            }
                        }else{
                            throw new PurchaseNotFoundException("Mode de règlement non sélectionné");
                        }

                    }else{
                        throw new PurchaseNotFoundException("Liste des produits introuvables");
                    }
                }else{
                    throw new PurchaseNotFoundException("Agence introuvable");
                }
            }else{
                throw new PurchaseNotFoundException("Entreprise introuvable");
            }


        return response;
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrder() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public PurchaseOrder getPurchaseOrderByRef(String refPurchase) {
        return purchaseOrderRepository.findByRefPurchaseOrder(refPurchase).orElse(null);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderByDate(LocalDate startDate, LocalDate endDate) {
        return purchaseOrderRepository.findByDateCreationBetween(startDate,endDate);
    }

    public void exportPurchaseOrder(List<PurchaseOrder> purchaseOrders, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bon de commnde");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Date");
        header.createCell(2).setCellValue("Référence");
        header.createCell(3).setCellValue("Entreprise");
        header.createCell(4).setCellValue("Agence");
        header.createCell(5).setCellValue("Produits");
        header.createCell(6).setCellValue("Prix HT");
        header.createCell(7).setCellValue("T.V.A.");
        header.createCell(8).setCellValue("Prix T.T.C.");
        header.createCell(9).setCellValue("Remise");
        header.createCell(10).setCellValue("Mode");
        header.createCell(11).setCellValue("Statut");
        header.createCell(12).setCellValue("Créé par:");


        // Remplissage des données
        int rowIndex = 1;
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(purchaseOrder.getIdPurchaseOrder());
            row.createCell(1).setCellValue(purchaseOrder.getDateCreation());
            row.createCell(2).setCellValue(purchaseOrder.getRefPurchaseOrder());
            row.createCell(3).setCellValue(purchaseOrder.getEnterprise());
            row.createCell(4).setCellValue(purchaseOrder.getAgency());
            row.createCell(5).setCellValue(purchaseOrder.getProducts().toString());
            row.createCell(6).setCellValue(purchaseOrder.getPriceHT());
            row.createCell(7).setCellValue(purchaseOrder.getTva());
            row.createCell(8).setCellValue(purchaseOrder.getPriceTTC());
            row.createCell(9).setCellValue(purchaseOrder.getRemise());
            row.createCell(10).setCellValue(purchaseOrder.getPaymentMethod().name());
            row.createCell(11).setCellValue(purchaseOrder.getStatutPurchase().name());
            row.createCell(12).setCellValue(purchaseOrder.getUserCreated());


        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"bon de commande.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
