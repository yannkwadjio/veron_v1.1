package com.veron.controller.view;

import com.veron.entity.*;
import com.veron.repository.*;
import com.veron.services.services.ImportServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ImportView {

    private final ImportServices importServices;
    private final AgencyRepository agencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CashRepository cashRepository;
    private final CountLotRepository countLotRepository;
    private final CrCashRepository crCashRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;
    private final MissingCashRepository missingCashRepository;
    private final MvtCashRepository mvtCashRepository;
    private final MvtCreditRepository mvtCreditRepository;
    private final MvtStockRepository mvtStockRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final ProfitRepository profitRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleRepository saleRepository;
    private final SellingServiceRepository sellingServiceRepository;
    private final SellingServiceCategoryRepository sellingServiceCategoryRepository;
    private final SpentRepository spentRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;
    private final UsersRepository usersRepository;

    @GetMapping("/import-bdd")
    public String viewImportBdd(Model model){
        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }
        return "import_bdd";
    }

    @PostMapping("/import-agency")
    public String importAgency(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Agency> agencies =importServices.readExcelFile(file);
List<Agency> agencyList=new ArrayList<>();
            for(Agency agency:agencies){
                Optional<Agency> existingAgency=agencyRepository.findByName(agency.getName());
                if(existingAgency.isEmpty()){
                    agencyList.add(agency);
                }
            }
            agencyRepository.saveAll(agencyList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-bank")
    public String importBank(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<BankAccount> bankAccounts =importServices.readExcelFileBankAccount(file);
            List<BankAccount> bankAccountList=new ArrayList<>();
            for(BankAccount bankAccount:bankAccounts){
                Optional<BankAccount> existingBankAccount=bankAccountRepository.findByBankAccountNumber(bankAccount.getBankAccountNumber());
                if(existingBankAccount.isEmpty()){
                    bankAccountList.add(bankAccount);
                }
            }
            bankAccountRepository.saveAll(bankAccountList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-cash")
    public String importCash(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Cash> cashes =importServices.readExcelFileCash(file);
            List<Cash> cashList=new ArrayList<>();
            for(Cash cash:cashes){
                Optional<Cash> existingCash=cashRepository.findByRefCash(cash.getRefCash());
                if(existingCash.isEmpty()){
                    cashList.add(cash);
                }
            }
            cashRepository.saveAll(cashList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-lot")
    public String importLot(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<CountLot> countLots =importServices.readExcelFileLot(file);
            List<CountLot> countLotList=new ArrayList<>();
            for(CountLot countLot:countLots){
                Optional<CountLot> existingLot=countLotRepository.findByLot(countLot.getLot());
                if(existingLot.isEmpty()){
                    countLotList.add(countLot);
                }
            }
            countLotRepository.saveAll(countLotList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-cr-cash")
    public String importCrCash(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<CrCash> crCashes =importServices.readExcelFileCrCash(file);
            List<CrCash> crCashList=new ArrayList<>();
            for(CrCash crCash:crCashes){
                Optional<CrCash> existingCrCash=crCashRepository.findByDateCrCashAndCashAndAgency(crCash.getDateCrCash(),crCash.getCash(),crCash.getAgency());
                if(existingCrCash.isEmpty()){
                    crCashList.add(crCash);
                }
            }
            crCashRepository.saveAll(crCashList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-customer")
    public String importCustomer(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Customer> Customers =importServices.readExcelFileCustomer(file);
            List<Customer> customerList=new ArrayList<>();
            for(Customer customer:Customers){
                Optional<Customer> existingCustomer=customerRepository.findByRefCustomer(customer.getRefCustomer());
                if(existingCustomer.isEmpty()){
                    Optional<Customer> customer1=customerRepository.findByFullName(customer.getFullName());
                    if(customer1.isEmpty()){
                        customerList.add(customer);
                    }

                }
            }
            customerRepository.saveAll(customerList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-invoice")
    public String importInvoice(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Invoice> invoices =importServices.readExcelFileInvoice(file);
            List<Invoice> invoiceList=new ArrayList<>();
            for(Invoice invoice:invoices){
                Optional<Invoice> existingInvoice=invoiceRepository.findByRefInvoice(invoice.getRefInvoice());
                if(existingInvoice.isEmpty()){
                    invoiceList.add(invoice);
                }
            }
            invoiceRepository.saveAll(invoiceList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-missing")
    public String importMissing(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<MissingCash> missingCashes =importServices.readExcelFileMissingCash(file);
            List<MissingCash> missingCashList=new ArrayList<>();
            for(MissingCash missingCash:missingCashes){
                Optional<MissingCash> existingMissing=missingCashRepository.findByRefMissingCash(missingCash.getRefMissingCash());
                if(existingMissing.isEmpty()){
                    missingCashList.add(missingCash);
                }
            }
            missingCashRepository.saveAll(missingCashList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-mvt-cash")
    public String importMvtCash(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<MvtCash> mvtCashes =importServices.readExcelFileMvtCash(file);
            mvtCashRepository.saveAll(mvtCashes);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-mvt-credit")
    public String importMvtCredit(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<MvtCredit> mvtCredits =importServices.readExcelFileMvtCredit(file);
            mvtCreditRepository.saveAll(mvtCredits);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-mvt-stock")
    public String importMvtStock(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<MvtStock> mvtStocks =importServices.readExcelFileMvtStock(file);
            mvtStockRepository.saveAll(mvtStocks);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-payment")
    public String importPayment(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Payment> payments =importServices.readExcelFilePayment(file);
            paymentRepository.saveAll(payments);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-product")
    public String importProduct(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Product> products =importServices.readExcelFileProducts(file);
            List<Product> productList=new ArrayList<>();
            for(Product product:products){
                Optional<Product> existingProduct=productRepository.findByRefProduct(product.getRefProduct());
                if(existingProduct.isEmpty()){
                    productList.add(product);
                }
            }
            productRepository.saveAll(productList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-product-stock")
    public String importProductStock(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<ProductStock> productStocks =importServices.readExcelFileProductStock(file);
            List<ProductStock> productStockList=new ArrayList<>();
            for(ProductStock productStock:productStocks){
                Optional<ProductStock> existingProductStock=productStockRepository.findByStoreAndLotAndAgency(productStock.getStore(),productStock.getLot(),productStock.getAgency());
                if(existingProductStock.isEmpty()){
                    productStockList.add(productStock);
                }
            }
            productStockRepository.saveAll(productStockList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-profit")
    public String importProfit(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Profit> profits =importServices.readExcelFileProfit(file);
            profitRepository.saveAll(profits);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-purchase-order")
    public String importPurchaseOrder(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<PurchaseOrder> purchaseOrders =importServices.readExcelFilePurchaseOrder(file);
           List<PurchaseOrder> purchaseOrderList=new ArrayList<>();
           for(PurchaseOrder purchaseOrder:purchaseOrders){
               Optional<PurchaseOrder> purchaseOrderExisting=purchaseOrderRepository.findByRefPurchaseOrder(purchaseOrder.getRefPurchaseOrder());
               if(purchaseOrderExisting.isEmpty()){
                   purchaseOrderList.add(purchaseOrder);
               }
           }
            purchaseOrderRepository.saveAll(purchaseOrderList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-sale")
    public String importSale(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Sale> sales =importServices.readExcelFileSale(file);
            saleRepository.saveAll(sales);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-selling-service")
    public String importSellingService(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<SellingService> sellingServices =importServices.readExcelFileSellingService(file);
            sellingServiceRepository.saveAll(sellingServices);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-selling-service-category")
    public String importSellingServiceCategory(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<SellingServiceCategory> sellingServiceCategories =importServices.readExcelFileSellingServiceCategory(file);
            sellingServiceCategoryRepository.saveAll(sellingServiceCategories);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-spent")
    public String importSpent(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Spent> spends =importServices.readExcelFileSpent(file);
            spentRepository.saveAll(spends);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-store")
    public String importStore(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Store> stores =importServices.readExcelFileStore(file);
            List<Store> storeList=new ArrayList<>();
            for(Store store:stores){
                Optional<Store> storeExisting=storeRepository.findByRefStore(store.getRefStore());
                if(storeExisting.isEmpty()){
                    storeList.add(store);
                }
            }
            storeRepository.saveAll(storeList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }


    @PostMapping("/import-supplier")
    public String importSupplier(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Supplier> suppliers =importServices.readExcelFileSupplier(file);
            supplierRepository.saveAll(suppliers);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }

    @PostMapping("/import-user")
    public String importUser(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/import-bdd";
        }

        try {
            List<Users> userss =importServices.readExcelFileUser(file);
            List<Users> usersList=new ArrayList<>();
            for(Users users:userss){
                Optional<Users> usersExisting=usersRepository.findByUsername(users.getUsername());
                if(usersExisting.isEmpty()){
                    usersList.add(users);
                }
            }
            usersRepository.saveAll(usersList);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/import-bdd";
    }



}
