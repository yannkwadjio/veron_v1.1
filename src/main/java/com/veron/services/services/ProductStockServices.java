package com.veron.services.services;

import com.veron.dto.ApproStoreDto;
import com.veron.dto.FounitureOutDto;
import com.veron.dto.ProductStockDto;
import com.veron.entity.*;
import com.veron.enums.InvoiceType;
import com.veron.enums.PaymentMethod;
import com.veron.enums.StatutPurchase;
import com.veron.exceptions.EnterpriseNotFoundException;
import com.veron.exceptions.ProductStockNotFoundException;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.ProductStockInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductStockServices implements ProductStockInterfaces{
    private final ProductStockRepository productStockRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final CountLotRepository countLotRepository;
    private final MvtStockRepository mvtStockRepository;
    private final StoreRepository storeRepository;
    private final InvoiceRepository invoiceRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Map<String, String> createProductStock(ProductStockDto productStockDto) throws ArrayIndexOutOfBoundsException {
        Map<String,String> response=new HashMap<>();
        double totalPrice=0;
        List<ProductStock> productStocks=new ArrayList<>();
        if(productStockDto.getProducts() !=null){
           for(String product:productStockDto.getProducts()){
            try{
                String[] items=product.split(":");
                if(items[6].equals("on")){
                    Product product1=productRepository.findByName(items[1]).orElse(null);
                    if(product1!=null){
                        ProductStock productStock=new ProductStock();
                        totalPrice+=Double.parseDouble(items[2])*Double.parseDouble(items[3]);
                        productStock.setFinalStock(Double.parseDouble(items[2]));
                        productStock.setRefProduct(items[0]);
                        productStock.setLot(items[5]);
                        List<CountLot> listLot=countLotRepository.findAll().stream()
                                .sorted(Comparator.comparing(CountLot::getIdDay).reversed())
                                .toList();
                        int idDay=0;
                        if(!listLot.isEmpty()){
                            idDay=(listLot.get(0).getIdDay());
                            idDay=idDay+1;
                            Optional<CountLot> lot=countLotRepository.findByLot(items[5]);
                            if(lot.isPresent()){
                                idDay=idDay+1;
                            }
                        }else{
                            idDay=1;
                        }
                        CountLot countLot=new CountLot();
                        countLot.setLot(items[5]);
                        countLot.setIdDay(idDay);
                        countLotRepository.save(countLot);
                        if(!items[4].isEmpty()){
                            int annee=Integer.parseInt(items[4].substring(0,4));
                            int mois=Integer.parseInt(items[4].substring(5,7));
                            int jour=Integer.parseInt(items[4].substring(8,10));
                            productStock.setDateExpiration(LocalDate.of(annee,mois,jour));
                        }else{
                            productStock.setDateExpiration(LocalDate.now());
                        }

                        productStock.setName(items[1]);
                        productStock.setCategory(product1.getCategory());
                        productStock.setStore("STORE01");
                        productStocks.add(productStock);
                        productStock.setAgency("MAGASIN PRINCIPAL");

                        MvtStock mvtStock=new MvtStock();
                        mvtStock.setDateCreation(LocalDate.now());
                        List<MvtStock> mvtStocks=mvtStockRepository.findAll().stream()
                                .filter(mvt->mvt.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!mvtStocks.isEmpty()){
                            numeroSerie=(mvtStocks.get(0).getIdDay()+1);
                        }else{
                            numeroSerie=1;
                        }
                        mvtStock.setIdOperation("MS"+LocalDate.now()+"O"+numeroSerie);
                        mvtStock.setStore01("FOURNISSEUR");
                        mvtStock.setStore02("STORE-01");
                        mvtStock.setCategory(product1.getCategory());
                        mvtStock.setProduct(product1.getName());
                        mvtStock.setInitialStock(product1.getFinalStock());
                        mvtStock.setIncomingQuantity(Double.parseDouble(items[2]));
                        mvtStock.setOutgoingQuantity(0);
                        mvtStock.setFinalStock(product1.getFinalStock()+Double.parseDouble(items[2]));
                        mvtStock.setValueStock(product1.getFinalValue()+(Double.parseDouble(items[2])*Double.parseDouble(items[3])));
                        mvtStock.setIdDay(numeroSerie);
                        mvtStock.setUserCreated("admin-veron@gmail.com");
                        mvtStockRepository.save(mvtStock);

                        product1.setFinalStock(product1.getFinalStock()+Double.parseDouble(items[2]));
                        product1.setFinalValue(product1.getFinalStock()+(Double.parseDouble(items[2])*Double.parseDouble(items[3])));
                        productRepository.save(product1);
                    }
                }
            }catch(ProductStockNotFoundException p){
            throw new ArrayIndexOutOfBoundsException(p.getMessage());
            }


           }


            productStockRepository.saveAll(productStocks);
            PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder(productStockDto.getRefPurchaseOrder()).orElse(null);
            if(purchaseOrder!=null){
                purchaseOrder.setPriceHT(totalPrice);
                purchaseOrder.setStatutPurchase(StatutPurchase.CONFIRME);
                purchaseOrderRepository.save(purchaseOrder);



            }

            response.put("message","Commande validée avec succès");
        }else{
            throw new ProductStockNotFoundException("Liste des produits introuvable");
        }




        return response;
    }

    @Override
    public List<ProductStock> getAllProductStock() {
        return productStockRepository.findAll();
    }

    @Override
    public Map<String, String> createApproStock(ApproStoreDto approStoreDto) {
        Map<String,String> response=new HashMap<>();
        if(!approStoreDto.getAgency().isEmpty()){
            if(!approStoreDto.getStore01().isEmpty()){
                if(!approStoreDto.getStore02().isEmpty()){
                    if(approStoreDto.getProducts()!=null){
                       Store store1=storeRepository.findByRefStore(approStoreDto.getStore01()).orElse(null);
                       if(store1!=null){
                           Store store2=storeRepository.findByRefStore(approStoreDto.getStore02()).orElse(null);
                        if(store2!=null){
                            for(String product:approStoreDto.getProducts()){
                                String[] items=product.split(":");
                                ProductStock productStock1=productStockRepository.findByStoreAndLotAndAgency(approStoreDto.getStore01(),items[3],items[4]).orElse(null);
                                if(productStock1!=null){
                                    Product product1=productRepository.findByName(productStock1.getName()).orElse(null);
                                    productStock1.setFinalStock(productStock1.getFinalStock()-Double.parseDouble(items[2]));
                                  productStockRepository.save(productStock1);

                                    ProductStock productStock=new ProductStock();
                                    productStock.setRefProduct(items[0]);
                                    productStock.setName(items[1]);
                                    productStock.setPurchasePrice(productStock1.getPurchasePrice());
                                    productStock.setFinalStock(Double.parseDouble(items[2]));
                                    productStock.setStore(approStoreDto.getStore02());
                                    productStock.setLot(items[3]);
                                    if(approStoreDto.getStore02().equals("STORE01")){
                                        productStock.setAgency("MAGASIN PRINCIPAL");
                                    }else{
                                        productStock.setAgency(approStoreDto.getAgency());
                                    }

                                    if(product1!=null){
                                     productStock.setCategory(product1.getCategory());
                                    }
                                    productStock.setDateExpiration(productStock1.getDateExpiration());
                                    productStockRepository.save(productStock);


                                    MvtStock mvtStock=new MvtStock();
                                    mvtStock.setDateCreation(LocalDate.now());
                                    List<MvtStock> mvtStocks=mvtStockRepository.findAll().stream()
                                            .filter(mvt->mvt.getDateCreation().equals(LocalDate.now()))
                                            .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                                            .toList();
                                    int numeroSerie=0;
                                    if(!mvtStocks.isEmpty()){
                                        numeroSerie=(mvtStocks.get(0).getIdDay()+1);
                                    }else{
                                        numeroSerie=1;
                                    }
                                    mvtStock.setIdOperation("MS"+LocalDate.now()+"O"+numeroSerie);
                                    mvtStock.setStore01(approStoreDto.getStore01());
                                    mvtStock.setStore02(approStoreDto.getStore02());
                                    if(product1 !=null){
                                        mvtStock.setCategory(product1.getCategory());
                                        mvtStock.setProduct(product1.getName());
                                        mvtStock.setInitialStock(product1.getFinalStock());
                                        mvtStock.setFinalStock(product1.getFinalStock());
                                        mvtStock.setValueStock(product1.getFinalValue());
                                    }
                                    mvtStock.setIncomingQuantity(Double.parseDouble(items[2]));
                                    mvtStock.setOutgoingQuantity(Double.parseDouble(items[2]));
                                    mvtStock.setIdDay(numeroSerie);
                                    mvtStockRepository.save(mvtStock);
                                    response.put("message","Approvisionnement effectué avec succès");
                                } else{
                                    response.put("message","Lot/Magasin inexistant");
                                }
                                }

                        }else{
                            throw new ProductStockNotFoundException("Magasin d'arrivée introuvable");
                        }
                       }else{
                           throw new ProductStockNotFoundException("Magasin de départ introuvable");
                       }
                    }else{
                        throw new ProductStockNotFoundException("Produit non sélectionné");
                    }
                }else{
                    response.put("message","Magasin d'arrivée non sélectionné");
                }
            }else{
                response.put("message","Magasin de départ non sélectionné");
            }
        }else{
            response.put("message","Agence non sélectionnée");
        }

        return response;
    }

    @Override
    public Map<String, String> createOutStock(FounitureOutDto founitureOutDto) {
        Map<String,String> response=new HashMap<>();
        boolean state=false;
        if(founitureOutDto.getAgency()!=null){
            if(founitureOutDto.getStore()!=null){
                if(founitureOutDto.getProducts()!=null){
                    for(String products:founitureOutDto.getProducts()){
                        String[] items=products.split(":");
                        Product product=productRepository.findByName(items[2]).orElse(null);
                       for(ProductStock productStock2:productStockRepository.findAll()) {
                        if(productStock2.getStore().equals(items[1])){
                           if (productStock2.getName().equals(items[2]) && productStock2.getFinalStock() >= Double.parseDouble(items[3])) {
                             state=true;
                               productStock2.setFinalStock(productStock2.getFinalStock() - Double.parseDouble(items[3]));

                               MvtStock mvtStock = new MvtStock();
                               mvtStock.setDateCreation(LocalDate.now());
                               List<MvtStock> mvtStocks = mvtStockRepository.findAll().stream()
                                       .filter(mvt -> mvt.getDateCreation().equals(LocalDate.now()))
                                       .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                                       .toList();
                               int numeroSerie = 0;
                               if (!mvtStocks.isEmpty()) {
                                   numeroSerie = (mvtStocks.get(0).getIdDay() + 1);
                               } else {
                                   numeroSerie = 1;
                               }
                               mvtStock.setIdOperation("MS" + LocalDate.now() + "O" + numeroSerie);
                               mvtStock.setStore01(items[1]);
                               mvtStock.setStore02(founitureOutDto.getAgency());
                               if (product != null) {
                                   mvtStock.setCategory(product.getCategory());
                                   mvtStock.setProduct(product.getName());
                                   mvtStock.setInitialStock(product.getFinalStock());
                                   mvtStock.setFinalStock(product.getFinalStock());
                                   mvtStock.setValueStock(product.getFinalValue());
                               }
                               mvtStock.setIncomingQuantity(0);
                               mvtStock.setOutgoingQuantity(Double.parseDouble(items[3]));
                               mvtStock.setIdDay(numeroSerie);
                               mvtStockRepository.save(mvtStock);

                               productStockRepository.save(productStock2);
                               assert product != null;
                               product.setFinalStock(product.getFinalStock() - Double.parseDouble(items[3]));
                               break;
                           }
                       }
                       }

                    }
                    if(state){
                        response.put("message","Fourniture commandée avec succès");
                    }else{
                        response.put("message","Stock insuffisant, bv approvisionner un de vos magasin");
                    }
                }else{
                    throw new ProductStockNotFoundException("Aucune fourniture trouvé");
                }
            }else{
                throw new ProductStockNotFoundException("Magasin introuvable");
            }
        }else{
            throw new ProductStockNotFoundException("Agence introuvable");
        }
        return response;
    }
}
