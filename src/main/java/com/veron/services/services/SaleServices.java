package com.veron.services.services;

import com.veron.dto.SaleDto;
import com.veron.entity.*;
import com.veron.enums.InvoiceType;
import com.veron.enums.PaymentMethod;
import com.veron.exceptions.SaleNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.SaleInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaleServices implements SaleInterface {
    private final SaleRepository saleRepository;
    private final CashRepository cashRepository;
    private final ProductRepository productRepository;
    private final ProfitRepository profitRepository;
    private final MvtCashRepository mvtCashRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductStockRepository productStockRepository;
    private final MvtStockRepository mvtStockRepository;
    private final CrCashRepository crCashRepository;
    private final MvtSalesRepository mvtSalesRepository;
    @Override
    public Map<String, String> createSale(SaleDto saleDto) {
        Map<String, String> response = new HashMap<>();

        List<CrCash> crCashes = crCashRepository.findAll().stream()
                .filter(mvt -> mvt.getCash().equals(saleDto.getRefCashValue()))
                .toList();
        boolean state=false;
        for(CrCash crCash:crCashes){
            if(crCash.getDateCrCash().equals(LocalDate.now())){
                state=true;
            }
        }

        if(!state){
            if(saleDto.getProducts()!=null || !saleDto.getServiceValue().equals("VENTES")){
                double tva = 0;
                double profit = 0;
                double profits=0;
                if (saleDto.getPaymentMethod() != null && !saleDto.getPaymentMethod().isEmpty()) {
                    if (saleDto.getPaymentMethod().equals(PaymentMethod.ESPECES.name())) {
                        Cash cash = cashRepository.findByRefCash(saleDto.getRefCashValue()).orElse(null);
                        if (cash != null) {
                            double remise = 0;
                            if (saleDto.getRemise() != null && !saleDto.getRemise().isEmpty()) {
                                remise = Double.parseDouble(saleDto.getRemise());
                            }
                            double price;
                            if (saleDto.getTotalPrice().isEmpty()) {
                                price = 0;
                            } else{
                                price= Double.parseDouble(saleDto.getTotalPrice());
                                price=price+remise-saleDto.getTvaValues();
                            }

                            double priceTTC = price + saleDto.getTvaValues() - remise;
                            double priceHT = Double.parseDouble(saleDto.getTotalPrice());

                            if (cash.getBalanceCredit() >= priceHT) {
                                Sale sale = new Sale();
                                sale.setSaleDate(LocalDate.now());
                                sale.setAgency(cash.getAgency());
                                sale.setPaymentMethod(PaymentMethod.ESPECES);
                                sale.setCustomer(saleDto.getCustomerValue());
                                tva = saleDto.getTvaValues();
                                sale.setRemise(remise);
                                sale.setTva(saleDto.getTvaValues());
                                sale.setPriceHT(priceHT);
                                sale.setPriceTTC(priceTTC);
                                List<String> productList = new ArrayList<>();
                                for (String product : saleDto.getProducts()) {
                                    String[] item = product.split(":");
                                    productList.add(item[0] + ":" + item[1]);
                                    ProductStock productStock=productStockRepository.findByStoreAndLotAndAgency(saleDto.getStore(),item[3],cash.getAgency()).orElse(null);
                                    Product product1 = productRepository.findByName(item[0]).orElse(null);
                                    if(product1!=null){
                                        if(productStock != null){
                                            if (productStock.getFinalStock() >= Double.parseDouble(item[1])) {
                                                productStock.setFinalStock(productStock.getFinalStock()-Double.parseDouble(item[1]));
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
                                                mvtStock.setStore01(saleDto.getStore());
                                                mvtStock.setStore02("CLIENT");
                                                mvtStock.setCategory(product1.getCategory());
                                                mvtStock.setProduct(product1.getName());
                                                mvtStock.setInitialStock(product1.getFinalStock());
                                                mvtStock.setIncomingQuantity(0);
                                                mvtStock.setOutgoingQuantity(Double.parseDouble(item[1]));
                                                mvtStock.setFinalStock(product1.getFinalStock()-Double.parseDouble(item[1]));
                                                mvtStock.setValueStock(product1.getFinalValue()-(Double.parseDouble(item[1])*product1.getPurchasePrice()));
                                                mvtStock.setIdDay(numeroSerie);
                                                mvtStock.setUserCreated("admin-veron@gmail.com");
                                                mvtStockRepository.save(mvtStock);

                                                List<Sale> listSales = saleRepository.findAll().stream()
                                                        .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                                        .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                                        .toList();

                                                int idDays = listSales.isEmpty() ? 1 : (listSales.get(0).getIdDay() + 1);
                                                sale.setIdDay(idDays);
                                                String references= "";
                                                if(saleDto.getServiceValue().equals("VENTES")){
                                                    references = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;
                                                }else{
                                                    references = "SCE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;

                                                }
                                                profits= (profits + Double.parseDouble(item[1]) * (product1.getSellingPrice() - product1.getPurchasePrice()));

                                                MvtSales mvtSales=new MvtSales();
                                                mvtSales.setRefSale(references);
                                                mvtSales.setSaleDate(LocalDate.now());
                                                mvtSales.setCustomer(saleDto.getCustomerValue());
                                                mvtSales.setService(saleDto.getServiceValue());
                                                mvtSales.setProducts(product1.getName());
                                                mvtSales.setPaymentMethod(PaymentMethod.valueOf(saleDto.getPaymentMethod()));
                                                mvtSales.setProfit(profits);
                                                mvtSales.setPriceHT(Double.parseDouble(item[2]));
                                                mvtSales.setAgency(cash.getAgency());
                                                mvtSales.setUserCreated("admin-veron@gmail.com");
                                                mvtSalesRepository.save(mvtSales);



                                            }

                                            if (product1 != null) {
                                                product1.setFinalStock(product1.getFinalStock() - Double.parseDouble(item[1]));
                                                profit = (profit + Double.parseDouble(item[1]) * (product1.getSellingPrice() - product1.getPurchasePrice()));
                                                product1.setFinalValue(product1.getFinalValue() - Double.parseDouble(saleDto.getTotalPrice()));
                                                productRepository.save(product1);
                                            }
                                            productStockRepository.save(productStock);
                                        }


                                    }else{
                                        profit = (profit + Double.parseDouble(item[2]));

                                        List<Sale> listSales = saleRepository.findAll().stream()
                                                .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                                .toList();

                                        int idDays = listSales.isEmpty() ? 1 : (listSales.get(0).getIdDay() + 1);
                                        sale.setIdDay(idDays);
                                        String references= "";
                                        if(saleDto.getServiceValue().equals("VENTES")){
                                            references = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;
                                        }else{
                                            references = "SCE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;

                                        }
                                        profits= (profits +Double.parseDouble(item[2]));

                                        MvtSales mvtSales=new MvtSales();
                                        mvtSales.setRefSale(references);
                                        mvtSales.setSaleDate(LocalDate.now());
                                        mvtSales.setCustomer(saleDto.getCustomerValue());
                                        mvtSales.setService(saleDto.getServiceValue());
                                        mvtSales.setProducts(item[0]);
                                        mvtSales.setPaymentMethod(PaymentMethod.valueOf(saleDto.getPaymentMethod()));
                                        mvtSales.setProfit(profits);
                                        mvtSales.setPriceHT(Double.parseDouble(item[2]));
                                        mvtSales.setAgency(cash.getAgency());
                                        mvtSales.setUserCreated("admin-veron@gmail.com");
                                        mvtSalesRepository.save(mvtSales);

                                    }
                                }


                                Profit newProfit = new Profit();
                                newProfit.setAgency(cash.getAgency());
                                newProfit.setEnterprise(cash.getEnterprise());
                                newProfit.setProfitDate(LocalDate.now());
                                newProfit.setMontant(profit);
                                profitRepository.save(newProfit);

                                sale.setProducts(productList);
                                sale.setProfit(profit);
                                List<Sale> listSale = saleRepository.findAll().stream()
                                        .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                        .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                        .toList();

                                int idDay = listSale.isEmpty() ? 1 : (listSale.get(0).getIdDay() + 1);
                                sale.setIdDay(idDay);
                                String reference= "";
                                if(saleDto.getServiceValue().equals("VENTES")){
                                    reference = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDay;
                                }else{
                                    reference = "SCE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDay;

                                }

                                sale.setRefSale(reference);
                                sale.setService(saleDto.getServiceValue());
                                sale.setUserCreated("admin-veron@gmail.com");
                                saleRepository.save(sale);

                                Customer customer=customerRepository.findByFullName(saleDto.getCustomerValue()).orElseThrow(()->new SaleNotFoundException("Client introuvable"));
                              customer.setBalanceCredit(customer.getBalanceCredit()+priceTTC);

                                MvtCash mvtCash = new MvtCash();
                                mvtCash.setValidated(false);
                                mvtCash.setType("VENTES");
                                mvtCash.setMotif("VENTES PRODUITS");
                                mvtCash.setAgency(cash.getAgency());
                                mvtCash.setReference(reference);
                                mvtCash.setCash(cash.getRefCash());
                                mvtCash.setAmount(priceTTC);
                                mvtCash.setFee(0);
                                mvtCash.setBalanceAfter(cash.getBalance() + priceTTC);
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
                                mvtCash.setSens("ENCAISSEMENT");
                                mvtCash.setIdDay(idDay);
                                mvtCash.setUserCreated("admin-veron@gmail.com");
                                mvtCashRepository.save(mvtCash);


                                cash.setBalance(cash.getBalance() + priceTTC);
                                cash.setBalanceCredit(cash.getBalanceCredit() - priceTTC);
                                cashRepository.save(cash);

                                response.put("message", "Vente effectuée avec succès");
                            } else {
                                response.put("message", "Crédit insuffisant");
                            }
                        } else {
                            response.put("message", "Caisse non trouvée");
                        }
                    } else if (saleDto.getPaymentMethod().equals(PaymentMethod.A_CREDIT.name())) {
                        Cash cash = cashRepository.findByRefCash(saleDto.getRefCashValue()).orElse(null);
                        assert cash != null;
                        double remise = 0;
                        if (saleDto.getRemise() != null && !saleDto.getRemise().isEmpty()) {
                            remise = Double.parseDouble(saleDto.getRemise());
                        }
                        double priceTTC = Double.parseDouble(saleDto.getTotalPrice()) - saleDto.getTvaValues() - remise;
                        double priceHT = Double.parseDouble(saleDto.getTotalPrice());

                        Sale sale = new Sale();
                        sale.setSaleDate(LocalDate.now());
                        sale.setAgency(cash.getAgency());
                        sale.setPaymentMethod(PaymentMethod.A_CREDIT);
                        sale.setCustomer(saleDto.getCustomerValue());
                        tva = saleDto.getTvaValues();
                        sale.setRemise(remise);
                        sale.setTva(saleDto.getTvaValues());
                        sale.setPriceHT(priceHT);
                        sale.setPriceTTC(priceTTC);
                        List<String> productList = new ArrayList<>();
                        for (String product : saleDto.getProducts()) {
                            String[] item = product.split(":");
                            productList.add(item[0] + ":" + item[1]);
                            ProductStock productStock=productStockRepository.findByStoreAndLotAndAgency(saleDto.getStore(),item[3],cash.getAgency()).orElse(null);
                            Product product1 = productRepository.findByName(item[0]).orElse(null);
                            if(product1!=null){
                                if(productStock != null){
                                    if (productStock.getFinalStock() >= Double.parseDouble(item[1])) {
                                        productStock.setFinalStock(productStock.getFinalStock()-Double.parseDouble(item[1]));
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
                                        mvtStock.setStore01(saleDto.getStore());
                                        mvtStock.setStore02("CLIENT");
                                        mvtStock.setCategory(product1.getCategory());
                                        mvtStock.setProduct(product1.getName());
                                        mvtStock.setInitialStock(product1.getFinalStock());
                                        mvtStock.setIncomingQuantity(0);
                                        mvtStock.setOutgoingQuantity(Double.parseDouble(item[1]));
                                        mvtStock.setFinalStock(product1.getFinalStock()-Double.parseDouble(item[1]));
                                        mvtStock.setValueStock(product1.getFinalValue()-(Double.parseDouble(item[1])*product1.getPurchasePrice()));
                                        mvtStock.setIdDay(numeroSerie);
                                        mvtStock.setUserCreated("admin-veron@gmail.com");
                                        mvtStockRepository.save(mvtStock);

                                        List<Sale> listSales = saleRepository.findAll().stream()
                                                .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                                .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                                .toList();

                                        int idDays = listSales.isEmpty() ? 1 : (listSales.get(0).getIdDay() + 1);
                                        sale.setIdDay(idDays);
                                        String references= "";
                                        if(saleDto.getServiceValue().equals("VENTES")){
                                            references = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;
                                        }else{
                                            references = "SCE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;

                                        }
                                        profits= (profits + Double.parseDouble(item[1]) * (product1.getSellingPrice() - product1.getPurchasePrice()));

                                        MvtSales mvtSales=new MvtSales();
                                        mvtSales.setRefSale(references);
                                        mvtSales.setSaleDate(LocalDate.now());
                                        mvtSales.setCustomer(saleDto.getCustomerValue());
                                        mvtSales.setService(saleDto.getServiceValue());
                                        mvtSales.setProducts(product1.getName());
                                        mvtSales.setPaymentMethod(PaymentMethod.valueOf(saleDto.getPaymentMethod()));
                                        mvtSales.setProfit(profits);
                                        mvtSales.setPriceHT(Double.parseDouble(item[2]));
                                        mvtSales.setAgency(cash.getAgency());
                                        mvtSales.setUserCreated("admin-veron@gmail.com");
                                        mvtSalesRepository.save(mvtSales);



                                    }

                                    if (product1 != null) {
                                        product1.setFinalStock(product1.getFinalStock() - Double.parseDouble(item[1]));
                                        profit = (profit + Double.parseDouble(item[1]) * (product1.getSellingPrice() - product1.getPurchasePrice()));
                                        product1.setFinalValue(product1.getFinalValue() - Double.parseDouble(saleDto.getTotalPrice()));
                                        productRepository.save(product1);
                                    }
                                    productStockRepository.save(productStock);
                                }
                            }else{
                                profit = (profit + Double.parseDouble(item[2]));

                                List<Sale> listSales = saleRepository.findAll().stream()
                                        .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                        .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                        .toList();

                                int idDays = listSales.isEmpty() ? 1 : (listSales.get(0).getIdDay() + 1);
                                sale.setIdDay(idDays);
                                String references= "";
                                if(saleDto.getServiceValue().equals("VENTES")){
                                    references = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;
                                }else{
                                    references = "SCE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDays;

                                }
                                profits= (profits +Double.parseDouble(item[2]));

                                MvtSales mvtSales=new MvtSales();
                                mvtSales.setRefSale(references);
                                mvtSales.setSaleDate(LocalDate.now());
                                mvtSales.setCustomer(saleDto.getCustomerValue());
                                mvtSales.setService(saleDto.getServiceValue());
                                mvtSales.setProducts(item[0]);
                                mvtSales.setPaymentMethod(PaymentMethod.valueOf(saleDto.getPaymentMethod()));
                                mvtSales.setProfit(profits);
                                mvtSales.setPriceHT(Double.parseDouble(item[2]));
                                mvtSales.setAgency(cash.getAgency());
                                mvtSales.setUserCreated("admin-veron@gmail.com");
                                mvtSalesRepository.save(mvtSales);

                            }
                        }
                        sale.setProducts(productList);
                        Profit newProfit = new Profit();
                        newProfit.setAgency(cash.getAgency());
                        newProfit.setEnterprise(cash.getEnterprise());
                        newProfit.setProfitDate(LocalDate.now());
                        newProfit.setMontant(profit - tva - remise);
                        profitRepository.save(newProfit);



                        List<Sale> listSale = saleRepository.findAll().stream()
                                .filter(date -> date.getSaleDate().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Sale::getIdDay).reversed())
                                .toList();

                        int idDay = listSale.isEmpty() ? 1 : (listSale.get(0).getIdDay() + 1);
                        sale.setIdDay(idDay);
                        String reference = "VTE" + LocalDate.now().getDayOfYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "0" + idDay;
                        sale.setRefSale(reference);
                        sale.setService(saleDto.getServiceValue());
                        sale.setUserCreated("admin-veron@gmail.com");
                        saleRepository.save(sale);

                        Customer customer=customerRepository.findByFullName(saleDto.getCustomerValue()).orElse(null);
                        assert customer!=null;

                        Invoice invoice = new Invoice();
                        List<Invoice> listInvoice = invoiceRepository.findAll().stream()
                                .sorted(Comparator.comparing(Invoice::getIdInvoice).reversed())
                                .toList();

                        if (!listInvoice.isEmpty()) {
                            invoice.setRefInvoice("FACT0" + (listInvoice.get(0).getIdInvoice() + 1));
                        } else {
                            invoice.setRefInvoice("FACT01");
                        }
                        invoice.setCustomer(saleDto.getCustomerValue());
                        StringBuilder productNames = new StringBuilder();
                        if (productList.isEmpty()) {
                            invoice.setMotif(saleDto.getServiceValue());
                        } else {
                            for (String product : productList) {
                                if ((productNames.isEmpty()) && productList.size() == 1) {
                                    productNames = new StringBuilder("(" + product + ")");
                                } else if (productNames.isEmpty()) {
                                    productNames = new StringBuilder("(" + product);
                                } else {
                                    productNames.append(",").append(product);
                                }
                            }
                            invoice.setMotif("VENTES " + productNames);
                        }
                        List<String> listProductInvoice;
                        if (!productList.isEmpty()) {

                            listProductInvoice = new ArrayList<>(productList);
                        } else {
                            listProductInvoice = null;
                        }
                        invoice.setProducts(listProductInvoice);
                        invoice.setAmount(Double.parseDouble(saleDto.getTotalPrice()));
                        invoice.setBalanceCredit(Double.parseDouble(saleDto.getTotalPrice()));
                        invoice.setAgency(cash.getAgency());
                        invoice.setAdvance(0);
                        invoice.setRest(invoice.getAmount());
                        invoice.setDateCreation(LocalDate.now());
                        invoice.setProfit(profit);
                        invoice.setInvoiceType(InvoiceType.EMISE);
                        invoice.setUserCreated("admin-veron@gmail.com");
                        invoiceRepository.save(invoice);

                        customer.setRetrait(Double.parseDouble(saleDto.getTotalPrice()));
                        customer.setBalance(customer.getBalance()-Double.parseDouble(saleDto.getTotalPrice()));
                        customerRepository.save(customer);


                        response.put("message", "Vente effectuée avec succès");

                    } else {

                    }

                } else {
                    response.put("message", "Méthode de paiement non sélectionnée");
                }
            }else {
                response.put("message", "Produits non sélectionnés");
            }

        }else {
            response.put("message", "Impossible d'effectuer cette opération, car vous avez déjà clôturer votre caisse");
        }



        return response;
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> getAllSaleByDate(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findBySaleDateBetween(startDate,endDate);
    }


}
