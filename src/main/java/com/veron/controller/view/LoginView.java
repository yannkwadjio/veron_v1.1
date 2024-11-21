package com.veron.controller.view;


import com.veron.dto.*;
import com.veron.dto.update.BankAccountDto;
import com.veron.dto.update.CashUpdateDto;
import com.veron.entity.*;
import com.veron.enums.*;
import com.veron.exceptions.CustomerNoFoundException;
import com.veron.exceptions.EnterpriseNotFoundException;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.*;

import com.veron.services.services.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class LoginView {
private final UsersRepository usersRepository;
private final EnterpriseRepository enterpriseRepository;
private final CountryRepository countryRepository;
private final BankAccountServices bankAccountServices;
private final BankAccountRepository bankAccountRepository;
private final EnterpriseServices enterpriseServices;
private final CustomerServices customerServices;
private final CustomerRepository customerRepository;
private final SupplierRepository supplierRepository;
private final SupplierServices supplierServices;
private final MvtCreditServices mvtCreditServices;
private final MvtCreditRepository mvtCreditRepository;
private final SpentRepository spentRepository;
private final SpentServices spentServices;
private final SpendingFamilyRepository spendingFamilyRepository;
private final ProductRepository productRepository;
private final ProductServices productServices;
private final SellingServiceCategoryRepository sellingServiceCategoryRepository;
private final SellingServiceCategoryServices sellingServiceCategoryServices;
private final SellingServiceService sellingServiceService;
private final SellingServiceRepository sellingServiceRepository;
private final AgencyRepository agencyRepository;
private final AgencyServices agencyServices;
private final CashRepository cashRepository;
private final CashServices cashServices;
private final StoreServices storeServices;
private final StoreRepository storeRepository;
private final UsersServices usersServices;
private final InvoiceRepository invoiceRepository;
private final PaymentRepository paymentRepository;
private final ProductStockRepository productStockRepository;
private final SaleRepository saleRepository;
private final MvtCashServices mvtCashServices;
private final MvtCashRepository mvtCashRepository;
private final CrCashServices crCashServices;
private final ResetServices resetServices;
private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/login")
    public String loginPage(Model model){
           return "login";
        }


        @PostMapping("/login")
        public String PostLoginPage(RedirectAttributes redirectAttributes,Model model,@AuthenticationPrincipal UserDetails userDetails,@RequestParam String email,@RequestParam String password){
            Users users=usersRepository.findByUsername(email).orElse(null);
           Map<String,String> response=new HashMap<>();
            if(users!=null){
                if(users.getPassword().equals(password)){
                    return "redirect:/home";
                }else{

                    response.put("message","Mot de passe incorrect");
                    redirectAttributes.addFlashAttribute("msg", response.get("message"));
                    return "redirect:/login";
                }
            }else{

                response.put("message","Utilisateur introuvable");
                redirectAttributes.addFlashAttribute("msg", response.get("message"));
                return "redirect:/login";
            }

        }


    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails){

        String nomUtil=userDetails.getUsername();

       Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
          role= new StringBuilder(existingRole.name());  
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);
        Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
        }else{
            cashier=existingUser.getCash();
            model.addAttribute("cashier",cashier);
        }

        List<Users> userss=usersRepository.findAll().stream()
                        .filter(users1 -> !users1.getRole().contains(Role.ADMIN) && !users1.getRole().contains(Role.SUPERADMIN))                                .toList();

        List<SellingServiceCategory> categories=sellingServiceCategoryRepository.findAll().stream()
                        .filter(category->!category.getName().equals("VENTES"))
                                .toList();

  model.addAttribute("countries",countryRepository.findAll());
        model.addAttribute("enterpriseDto",new EnterpriseDto());
        model.addAttribute("mvtCreditDto",new MvtCreditDto());
        model.addAttribute("bankAccountDto",new BankAccountDto());
        model.addAttribute("tiersDto", new TiersDto());
        model.addAttribute("spentDto", new SpentDto());
        model.addAttribute("productDto",new ProductDto());
        model.addAttribute("agencyDto",new AgencyDto());
        model.addAttribute("categorys", Category.values());
        model.addAttribute("sellingServiceCategoryDto",new SellingServiceCategoryDto());
        model.addAttribute("sellingServiceDto",new SellingServiceDto());
        model.addAttribute("cashUpdateDto",new CashUpdateDto());
        model.addAttribute("cashDto",new CashDto());
        model.addAttribute("storeDto",new StoreDto());
        model.addAttribute("userDto",new UserDto());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("enterprises",enterpriseRepository.findAll());
        model.addAttribute("families",spendingFamilyRepository.findAll());
        model.addAttribute("bankAccounts",bankAccountRepository.findAll());
        model.addAttribute("customers",customerRepository.findAll());
        model.addAttribute("spents",spentRepository.findAll());
        model.addAttribute("products",productRepository.findAll());
        model.addAttribute("categories",categories);
        model.addAttribute("services",sellingServiceRepository.findAll());
        model.addAttribute("agencies",agencyRepository.findAll());
        model.addAttribute("cashes",cashRepository.findAll());
        model.addAttribute("stores",storeRepository.findAll());
        model.addAttribute("userss",userss);
        model.addAttribute("tiers", Tiers.values());
        model.addAttribute("casherRoles", CasherRole.values());
        model.addAttribute("roles", Role.values());

        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }

        double nbSale=0;
        double sales=0;
        double profits=0;
        double depenses=0;
        double marges=0;
        double soldeCaisse=0;
        double soldeBanque=0;
        double factures=0;

        List<Sale> mvtSales=saleRepository.findAll().stream()
                .filter(sale -> sale.getSaleDate().equals(LocalDate.now()) && sale.getPaymentMethod().equals(PaymentMethod.ESPECES))
                .toList();

        for(Sale sale:mvtSales){
            nbSale+=1;
            sales+=sale.getPriceTTC();
            profits+=sale.getProfit();
            marges+=sale.getProfit();
        }
        model.addAttribute("nbSale",nbSale);
        model.addAttribute("sales",sales);
        model.addAttribute("profits",profits);


        List<MvtCash> mvtCashes=mvtCashRepository.findAll().stream()
                .filter(mvtCash->mvtCash.getType().equals("DEPENSES") && mvtCash.getDateMvtCash().equals(LocalDate.now()))
                .toList();

        for(MvtCash mvtCash:mvtCashes){
            depenses+=mvtCash.getAmount()+mvtCash.getFee();
            marges-=mvtCash.getAmount()+mvtCash.getFee();
        }
        model.addAttribute("depenses",depenses);

        model.addAttribute("marges",marges);

        List<Cash> cashList=cashRepository.findAll().stream().toList();
        for(Cash cash:cashList){
            soldeCaisse+=cash.getBalance();
        }
        model.addAttribute("soldeCaisse",soldeCaisse);

        List<BankAccount> bankAccounts=bankAccountRepository.findAll().stream().toList();
        for(BankAccount bankAccount:bankAccounts){
            soldeBanque+=bankAccount.getBalance();
        }
        model.addAttribute("soldeBanque",soldeBanque);

        List<Invoice> invoices=invoiceRepository.findAll().stream().toList();
        for(Invoice invoice:invoices){
            factures+=invoice.getRest();
        }
        model.addAttribute("factures",factures);

        int nb_entreprise=0;
        int nb_agence=0;
        int nb_utilisateur=0;
        int nb_bank=0;
        int nb_compensation=0;
        int nb_client=0;
        int nb_fournisseur=0;
        int mt_sale=0;
        int mt_cash=0;
        int mt_cashes=0;
        int mt_stores=0;
        int nb_product=0;
        int nb_service=0;


        for(Enterprise enterpriseItem:enterpriseRepository.findAll()){
            nb_entreprise+=1;
        }

        for(Agency agencyItem:agencyRepository.findAll()){
            nb_agence+=1;
        }

        for(Users users1:usersRepository.findAll()){
            if(!users1.getRole().contains(Role.ADMIN) && !users1.getRole().contains(Role.SUPERADMIN)){
                nb_utilisateur+=1;
            }
        }

        for(BankAccount bankAccount:bankAccountRepository.findAll()){
            nb_bank+=1;
        }

        for(MvtCredit mvtCredit:mvtCreditRepository.findAll().stream().filter(mvt->mvt.getDateCreation().equals(LocalDate.now())).toList()){
            nb_compensation+=1;
        }

        for(Customer customer:customerRepository.findAll()){
            nb_client+=1;
        }

        for(Supplier supplier:supplierRepository.findAll()){
            nb_fournisseur+=1;
        }

        for(Sale sale:saleRepository.findAll().stream().filter(sale -> sale.getSaleDate().equals(LocalDate.now()) && sale.getUserCreated().equals(userDetails.getUsername())).toList()){
            mt_sale+=(int)sale.getPriceTTC();
        }

        Cash cash=cashRepository.findByRefCash(users.getCash()).orElse(null);
        if(cash!=null){
            mt_cash=(int)cash.getBalance();
        }

        List<Cash> cashes=cashRepository.findAll();
        for(Cash cashItem:cashes){
            mt_cashes+=(int)cashItem.getBalance();
        }

        List<Store> stores=storeRepository.findAll();
        for(Store store:stores){
            mt_stores+=1;
        }

        List<Product> products=productRepository.findAll();
        for(Product product:products){
            nb_product+=1;
        }


        List<SellingService> sellingServices=sellingServiceRepository.findAll();
        for(SellingService sellingService:sellingServices){
            nb_service+=1;
        }


        model.addAttribute("nb_entreprise",nb_entreprise);
        model.addAttribute("nb_agence",nb_agence);
        model.addAttribute("nb_utilisateur",nb_utilisateur);
        model.addAttribute("nb_bank",nb_bank);
        model.addAttribute("nb_compensation",nb_compensation);
        model.addAttribute("nb_client",nb_client);
        model.addAttribute("nb_fournisseur",nb_fournisseur);
        model.addAttribute("mt_sale",mt_sale);
        model.addAttribute("mt_cash",mt_cash);
        model.addAttribute("mt_cashes",mt_cashes);
        model.addAttribute("mt_stores",mt_stores);
        model.addAttribute("nb_product",nb_product);
        model.addAttribute("nb_service",nb_service);

        System.out.println(LocalDate.now().toString());
        return "home";
    }



    @PostMapping("/logout")
    public String logoutPage(Model model){
        return "redirect:/login";
    }


    @PostMapping("/bank-account")
    public String createBankAccountView(@ModelAttribute BankAccountDto bankAccountDto, RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=bankAccountServices.createBankAccount(bankAccountDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<BankAccount> listBankAccount=bankAccountRepository.findAll().stream()
                .sorted(Comparator.comparing(BankAccount::getBankAccountNumber).reversed())
                .toList();
        if(!listBankAccount.isEmpty()){
           BankAccount bankAccount=listBankAccount.get(0);
           bankAccount.setUserCreated(userDetails.getUsername());
           bankAccountRepository.save(bankAccount);
        }
        return "redirect:/home";
    }

    @PostMapping("/enterprise")
    public String postEnterprise(Model model, EnterpriseDto enterpriseDto, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=enterpriseServices.createEnterprise(enterpriseDto,file);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<Enterprise> listEnterprise=enterpriseRepository.findAll().stream()
                .sorted(Comparator.comparing(Enterprise::getIdEnterprise).reversed())
                .toList();
        if(!listEnterprise.isEmpty()){
            Enterprise enterprise=listEnterprise.get(0);
            enterprise.setUserCreated(userDetails.getUsername());
            enterpriseRepository.save(enterprise);
        }

        List<Customer> listCustomer=customerRepository.findAll().stream()
                .sorted(Comparator.comparing(Customer::getIdCustomer).reversed())
                .toList();

        if(!listCustomer.isEmpty()){
            Customer customer=listCustomer.get(0);
            customer.setUserCreated(userDetails.getUsername());
            customerRepository.save(customer);
        }

        List<Supplier> listSupplier=supplierRepository.findAll().stream()
                .sorted(Comparator.comparing(Supplier::getIdSupplier).reversed())
                .toList();

        if(!listSupplier.isEmpty()){
            Supplier supplier=listSupplier.get(0);
            supplier.setUserCreated(userDetails.getUsername());
            supplierRepository.save(supplier);
        }

        List<SellingService> listServices=sellingServiceRepository.findAll().stream()
                .sorted(Comparator.comparing(SellingService::getIdService).reversed())
                .toList();

        if(!listServices.isEmpty()){
            SellingService sellingService=listServices.get(0);
            sellingService.setUserCreated(userDetails.getUsername());
            sellingServiceRepository.save(sellingService);
        }

        return "redirect:/home";
    }

    @PostMapping("/customer")
    public String postAgency(@ModelAttribute TiersDto tiersDto, RedirectAttributes redirectAttributes, @RequestParam String type, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if(type.equals("CLIENT")){
            response=customerServices.createCustomer(tiersDto);
            Customer custom=customerRepository.findByPhoneNumber(tiersDto.getPhoneNumber()).orElse(null);
            assert custom != null;
            custom.setUserCreated(userDetails.getUsername());
            customerRepository.save(custom);
        }else{
            response=supplierServices.createSupplier(tiersDto);
            List<Supplier> supplierList=supplierRepository.findAll().stream()
                    .sorted(Comparator.comparing(Supplier::getIdSupplier).reversed())
                    .toList();
            Supplier supplier = supplierList.get(0);
            supplier.setUserCreated(userDetails.getUsername());
            supplierRepository.save(supplier);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @PostMapping("/update-by-ref")
    public String updateByRefCustomerView(@RequestParam String refCustomer,@RequestParam String fullName,@RequestParam String email,@RequestParam String phoneNumber,@RequestParam String adresse,Model model,RedirectAttributes redirectAttributes){
        Map<String,String> response=customerServices.updateCustomByRef(refCustomer,fullName,email,phoneNumber,adresse);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @GetMapping("/export-clients-excel")
    public void exportClientsToExcel(HttpServletResponse response) throws IOException {
        List<Customer> customers = customerRepository.findAll();

        customerServices.exportCustomers(customers, response);
    }

    @PostMapping("/credit")
    public String createMvtCreditView(@ModelAttribute MvtCreditDto mvtCreditDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=mvtCreditServices.createMvtCredit(mvtCreditDto);
        List<MvtCredit> listMvtCredit=mvtCreditRepository.findAll().stream()
                .sorted(Comparator.comparing(MvtCredit::getIdMvtCredit).reversed())
                .toList();
        if(!listMvtCredit.isEmpty()){
            MvtCredit mvtCredit=listMvtCredit.get(0);
            mvtCredit.setUserCreated(userDetails.getUsername());
            mvtCreditRepository.save(mvtCredit);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @GetMapping("/export-mvts-credit-excel")
    public void exportMvtCreditToExcel(HttpServletResponse response) throws IOException {
        List<MvtCredit> mvtCredits = mvtCreditRepository.findAll();

        mvtCreditServices.exportMvtCredit(mvtCredits, response);
    }

    @PostMapping("/spent")
    public String spentCreate(@ModelAttribute SpentDto spentDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=spentServices.createSpent(spentDto);
        List<Spent> listSpent=spentServices.getAllSpents().stream()
                .sorted(Comparator.comparing(Spent::getIdSpent).reversed())
                .toList();
        if(!listSpent.isEmpty()){
            Spent spent=listSpent.get(0);
            spent.setUserCreated(userDetails.getUsername());
            spentRepository.save(spent);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @GetMapping("/export-excel-spent")
    public void exportSpentsToExcel(HttpServletResponse response) throws IOException {
        List<Spent> spents = spentRepository.findAll();

        spentServices.exportSpent(spents, response);
    }


    @PostMapping("/product")
    public String postProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=productServices.createProducts(productDto);

        List<Product> listProduct=productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getIdProduct).reversed())
                .toList();
        if(!listProduct.isEmpty()){
            Product product=listProduct.get(0);
            product.setUserCreated(userDetails.getUsername());
            productRepository.save(product);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @GetMapping("/export-excel-product")
    public void exportproductsToExcel(HttpServletResponse response) throws IOException {
        List<Product> products = productRepository.findAll();

        productServices.exportProduct(products, response);
    }

    @PostMapping("/import-excel-product")
    public String importExcelFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/home";
        }

        try {
            Enterprise enterprise=enterpriseRepository.findAll().get(0);
            List<Product> products = productServices.readExcelFile(file);
            int numeroSerie=0;
            int count=0;
            for(Product product:products){
                List<Product> listProduct=productRepository.findAll().stream()
                        .sorted(Comparator.comparing(Product::getNumeroSerie).reversed())
                        .toList();
                if(!listProduct.isEmpty() && count==0){
                    numeroSerie=listProduct.get(0).getNumeroSerie();
                }
                    numeroSerie+=+1;

                product.setUnitCost(product.getPurchasePrice());
                product.setRefProduct("ART0"+numeroSerie);
                product.setEnterprise(enterprise.getName());
                product.setUserCreated(userDetails.getUsername());
                product.setNumeroSerie(numeroSerie);
                product.setStore("STORE01");
                product.setFinalValue(product.getFinalStock()*product.getPurchasePrice());
                count++;
            }

            productRepository.saveAll(products);

            model.addAttribute("products", products);
            response.put("message","Le fichier a été importé avec succès !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));

        } catch (IOException e) {
            response.put("message","Erreur lors de l'importation du fichier !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));   }

        return "redirect:/home";
    }



    @PostMapping("/selling-service-category")
    public String postSellingServiceCategory(@ModelAttribute SellingServiceCategoryDto sellingServiceCategoryDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=sellingServiceCategoryServices.createSellingServiceCategory(sellingServiceCategoryDto);
        List<SellingServiceCategory> listServiceCategory=sellingServiceCategoryRepository.findAll().stream()
                .sorted(Comparator.comparing(SellingServiceCategory::getIdCategoryService).reversed())
                .toList();
        if(!listServiceCategory.isEmpty()){
            SellingServiceCategory sellingServiceCategory=listServiceCategory.get(0);
            sellingServiceCategory.setUserCreated(userDetails.getUsername());
            sellingServiceCategoryRepository.save(sellingServiceCategory);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @PostMapping("/services")
    public String postSellingService(@ModelAttribute SellingServiceDto sellingServiceDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=sellingServiceService.createService(sellingServiceDto);
        List<SellingService> listService=sellingServiceService.getAllServices().stream()
                .sorted(Comparator.comparing(SellingService::getIdService).reversed())
                .toList();
        if(!listService.isEmpty()){
            SellingService sellingService=listService.get(0);
            sellingService.setUserCreated(userDetails.getUsername());
            sellingServiceRepository.save(sellingService);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }



    @PostMapping("/import-excel-service")
    public String importExcelServiceFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model,@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Map<String,String> response=new HashMap<>();
        if (file.isEmpty()) {
            response.put("message","Veuillez sélectionner un fichier Excel !");
            redirectAttributes.addFlashAttribute("msg", response.get("message"));
            return "redirect:/home";

        }else{
            try {
                List<SellingService> sellingServices = sellingServiceService.readExcelFile(file);
                 sellingServiceRepository.saveAll(sellingServices);
                 List<SellingServiceCategory> sellingServiceCategories=new ArrayList<>();
                 for(SellingService sellingService:sellingServices){
                     Optional<SellingServiceCategory> eSellingServiceCategory=sellingServiceCategoryRepository.findByName(sellingService.getCategory());
                     if(eSellingServiceCategory.isEmpty()){
                         SellingServiceCategory sellingServiceCategory=new SellingServiceCategory();
                         sellingServiceCategory.setName(sellingService.getCategory().toUpperCase());
                         sellingServiceCategories.add(sellingServiceCategory);
                     }

                 }
                 sellingServiceCategoryRepository.saveAll(sellingServiceCategories);
                response.put("message","Le fichier a été importé avec succès !");
                redirectAttributes.addFlashAttribute("msg", response.get("message"));
                 } catch (IOException e) {
                response.put("message","Erreur lors de l'importation du fichier !");
                redirectAttributes.addFlashAttribute("msg", response.get("message"));
                  }
        }


        return "redirect:/home";
    }


    @PostMapping("/update-service-by-ref")
    public String updateByRefServiceView(@RequestParam String name,@RequestParam double price,@RequestParam String description,Model model,RedirectAttributes redirectAttributes){
        Map<String,String> response=sellingServiceService.updateSellingServiceByName(name,price,description);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @GetMapping("/export-excel-service")
    public void exportSellingServiceToExcel(HttpServletResponse response) throws IOException {
        List<SellingService> sellingServices = sellingServiceRepository.findAll();

        sellingServiceService.exportSellingService(sellingServices, response);
    }

    @PostMapping("/agency")
    public String postAgency(@ModelAttribute AgencyDto agencyDto, RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=agencyServices.createAgency(agencyDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<Agency> listAgency=agencyRepository.findAll().stream()
                .sorted(Comparator.comparing(Agency::getIdAgency).reversed())
                .toList();
        Agency agency=listAgency.get(0);
        agency.setUserCreated(userDetails.getUsername());
        agencyRepository.save(agency);
        return "redirect:/home";
    }

    @PostMapping("/deleteAgency/{idAgency}")
    public String deleteAgency(@PathVariable("idAgency") int idAgency, RedirectAttributes redirectAttributes){
        Map<String,String> response=agencyServices.deleteAgencyById(idAgency);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @GetMapping("/export-cash-excel")
    public void exportCashToExcel(HttpServletResponse response) throws IOException {
        List<Cash> cashes = cashRepository.findAll();

        cashServices.exportCashes(cashes, response);
    }


    @PostMapping("/update-casher/{refCash}")
    public String updateCashier(@ModelAttribute CashUpdateDto cashUpdateDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails, @PathVariable("refCash") String refCash){
        Map<String,String> response=cashServices.updateByRefCash(cashUpdateDto,refCash);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @PostMapping("/casher")
    public String postAgency(@ModelAttribute CashDto cashDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=cashServices.createCasher(cashDto);
        List<Cash> listCash=cashRepository.findAll().stream()
                .sorted(Comparator.comparing(Cash::getIdCash).reversed())
                .toList();
        Cash cash=listCash.get(0);
        cash.setUserCreated(userDetails.getUsername());
        cashRepository.save(cash);

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @PostMapping("/store")
    public String createStoreView(@ModelAttribute StoreDto storeDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=storeServices.createStores(storeDto);
        List<Store> listStore=storeRepository.findAll().stream()
                .sorted(Comparator.comparing(Store::getIdStore).reversed())
                .toList();
        if(!listStore.isEmpty()){
            Store store=listStore.get(0);
            store.setUserCreated(userDetails.getUsername());
            storeRepository.save(store);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @GetMapping("/export-store-excel")
    public void exportStoreToExcel(HttpServletResponse response) throws IOException {
        List<Store> stores = storeRepository.findAll();

        storeServices.exportStores(stores, response);
    }

    @PostMapping("/update-store")
    public String updateStore(RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails, @RequestParam String enterprise,@RequestParam String agency,@RequestParam String refStore,@RequestParam String name,@RequestParam String usersStores){
        Map<String,String> response=storeServices.updateStore(enterprise,agency,refStore,name,usersStores);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @PostMapping("/update-store-store")
    public String updateStoreStore(RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails,@RequestParam String agency,@RequestParam String usersStores,@RequestParam String store1,@RequestParam String store2){
        Map<String,String> response=storeServices.updateStoreToStore(agency,usersStores,store1,store2);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @PostMapping("/user")
    public String postStore(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        response=usersServices.createUser(userDto);
        List<Users> listUser=usersServices.getAllUsers().stream()
                .sorted(Comparator.comparing(Users::getIdUser).reversed())
                .toList();

        if(!listUser.isEmpty()){
            Users users=listUser.get(0);
            users.setUserCreated(userDetails.getUsername());
            usersRepository.save(users);
        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));

        return "redirect:/home";
    }

    @PostMapping("/user-config")
    public String postUserModif(RedirectAttributes redirectAttributes,Model model,@RequestParam String username,@RequestParam String fullName,@RequestParam String phoneNumber,@RequestParam String role,@RequestParam boolean isEnabled){
        Map<String,String> response=usersServices.updateByUsername(username,fullName,phoneNumber,role,isEnabled);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @PostMapping("/update-password")
    public String postPasswordModif(RedirectAttributes redirectAttributes,Model model,@RequestParam String username,@RequestParam String password,@RequestParam String repassword,@AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if(userDetails.getUsername().equals(username)){
            response=usersServices.updatePassword(username,password,repassword);
        }else{
            response.put("message","Impossible de modifier le mot de passe d'un autre utilisateur");
        }
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }


    @GetMapping("/reporting")
    public String viewReporting(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        String nomUtil=userDetails.getUsername();
        Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);
        Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
        }else{
            cashier=existingUser.getCash();
            model.addAttribute("cashier",cashier);
        }
        model.addAttribute("invoices",invoiceRepository.findAll());
        model.addAttribute("payments",paymentRepository.findAll());
        model.addAttribute("statuts", StatutPurchase.values());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("productStocks",productStockRepository.findAll());
        model.addAttribute("stores",storeRepository.findAll());


        List<MvtCreditsDto> listMvtCreditDto=new ArrayList<>();
        double totalUV=0;

        for(Country country:countryRepository.findAll()){
            MvtCreditsDto mvtCreditsDto =new MvtCreditsDto();
            mvtCreditsDto.setEntity(country.getName());
            mvtCreditsDto.setEntite(country.getEntity());
            mvtCreditsDto.setBalance(country.getBalanceCredit());
            totalUV+=country.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto);
        }


        for(Enterprise enterprise:enterpriseRepository.findAll()){
            MvtCreditsDto mvtCreditsDto1 =new MvtCreditsDto();
            mvtCreditsDto1.setEntity(enterprise.getName());
            mvtCreditsDto1.setEntite(enterprise.getEntity());
            mvtCreditsDto1.setBalance(enterprise.getBalanceCredit());
            totalUV+=enterprise.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto1);
        }


        for(Agency agency:agencyRepository.findAll()){
            MvtCreditsDto mvtCreditsDto2 =new MvtCreditsDto();
            mvtCreditsDto2.setEntity(agency.getName());
            mvtCreditsDto2.setEntite(agency.getEntity());
            mvtCreditsDto2.setBalance(agency.getBalanceCredit());
            totalUV+=agency.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto2);
        }


        for(Cash cash:cashRepository.findAll()){
            MvtCreditsDto mvtCreditsDto3 =new MvtCreditsDto();
            mvtCreditsDto3.setEntity(cash.getRefCash());
            mvtCreditsDto3.setEntite(cash.getEntity());
            mvtCreditsDto3.setBalance(cash.getBalanceCredit());
            totalUV+=cash.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto3);
        }

        for(BankAccount bankAccount:bankAccountRepository.findAll()){
            MvtCreditsDto mvtCreditsDto4 =new MvtCreditsDto();
            mvtCreditsDto4.setEntity(bankAccount.getBankAccountNumber());
            mvtCreditsDto4.setEntite(bankAccount.getEntity());
            mvtCreditsDto4.setBalance(bankAccount.getBalanceCredit());
            totalUV+=bankAccount.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto4);
        }


        for(Customer customer:customerRepository.findAll()){
            MvtCreditsDto mvtCreditsDto5 =new MvtCreditsDto();
            mvtCreditsDto5.setEntity("CLIENTS");
            mvtCreditsDto5.setEntite("VENTES & AUTRES");
            mvtCreditsDto5.setBalance(customer.getBalanceCredit());
            totalUV+=customer.getBalanceCredit();
            listMvtCreditDto.add(mvtCreditsDto5);
        }


        PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000").orElseThrow(()->new PurchaseNotFoundException("Entité des achats introuvable"));
        MvtCreditsDto mvtCreditsDto6 =new MvtCreditsDto();
        mvtCreditsDto6.setEntity("ACHATS");
        mvtCreditsDto6.setEntite("ACHATS");
        mvtCreditsDto6.setBalance(purchaseOrder.getBalanceCredit());
        totalUV+=purchaseOrder.getBalanceCredit();
        listMvtCreditDto.add(mvtCreditsDto6);

        model.addAttribute("listBalance", listMvtCreditDto);
        model.addAttribute("totalUV", totalUV);

        model.addAttribute("customers",customerRepository.findAll());

        return "reporting";
    }

    @PostMapping("/update-product-by-ref")
    public String updateProductByName(RedirectAttributes redirectAttributes,@RequestParam String refProduct,@RequestParam double purchasePrice,@RequestParam double sellingPrice){
        Map<String,String> response=productServices.updateProductByRef(refProduct,purchasePrice,sellingPrice);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @PostMapping("/export-cashs-excel-date")
    public void exportPurchaseToExcel(HttpServletResponse response, @RequestParam LocalDate startDate,@RequestParam LocalDate endDate) throws IOException {
        List<MvtCash> mvtCashes = mvtCashRepository.findByDateMvtCashBetween(startDate,endDate);
        mvtCashServices.exportMvtCash(mvtCashes, response);
    }

    @PostMapping("/delete-cr-cash")
    public String deleteCrCash(@RequestParam int idCrCash,RedirectAttributes redirectAttributes){
        Map<String,String> response=crCashServices.deleteCrashById(idCrCash);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/home";
    }

    @PostMapping("/delete-all")
    public String deleteAll(){
        resetServices.deleteAllBdd();
        return "redirect:/login";
    }

    @GetMapping("/reset")
    public String viewReset(){
        return "reset";
    }

    @PostMapping("/update-mdp")
    public String editPassword(@RequestParam String username,Model model,@AuthenticationPrincipal UserDetails userDetails,RedirectAttributes redirectAttributes){
Map<String,String> response=usersServices.reinitialzePassword(username);
try {
    if(response.get("message").contains(":")){
        String[] items=response.get("message").split(":");
        String code=items[1];
        redirectAttributes.addFlashAttribute("code",code);
        redirectAttributes.addFlashAttribute("msg",items[0]);
    }else{
        redirectAttributes.addFlashAttribute("msg",response.get("message"));
    }

} catch (UsernameNotFoundException e) {
    throw new UsernameNotFoundException("Erreur lors de la réinitailisation");
}
        return "redirect:/raz-mdp";
    }


    @GetMapping("/raz-mdp")
    public String viewRazMdp(){

        return "raz_mdp";
    }


    @PostMapping("/delete-product/{refProduct}")
    public String deleteProduct(@PathVariable String refProduct,RedirectAttributes redirectAttributes){
        productServices.deleteProductByRef(refProduct);
        Map<String,String> response=Map.of("message","Produit supprimé avec succès");
        redirectAttributes.addFlashAttribute("msg",response.get("message"));
        return "redirect:/home";

    }

    @PostMapping("/edit-balance")
    public String editBalance(RedirectAttributes redirectAttributes,@RequestParam String agency,String cash,double balance,double newBalance){
        Map<String,String> response=cashServices.updateBalance(agency,cash,balance,newBalance);
        redirectAttributes.addFlashAttribute("msg",response.get("message"));
        return "redirect:/home";
    }

    @PostMapping("/viewHistory")
    public String viewHistory(Model model,RedirectAttributes redirectAttributes,@RequestParam LocalDate startDate,@RequestParam LocalDate endDate,@RequestParam String customer){
       List<HistoryCustomDto> listCustomer=new ArrayList<>();

        List<Sale> listSales=saleRepository.findAllByCustomerAndSaleDateBetween(customer,startDate,endDate);
        if(!listSales.isEmpty()){
       for(Sale sale:listSales){
           if(sale.getPriceTTC()!=0){
               HistoryCustomDto historyCustomDto=new HistoryCustomDto();
               historyCustomDto.setDateCreation(sale.getSaleDate());
               if(sale.getPaymentMethod().equals(PaymentMethod.ESPECES)){
                   historyCustomDto.setType("VENTES");
               }else if(sale.getPaymentMethod().equals(PaymentMethod.A_CREDIT)){
                   historyCustomDto.setType("FACTURES");
               }
               historyCustomDto.setAmount(sale.getPriceTTC());
               historyCustomDto.setPaymentMethod(sale.getPaymentMethod().name());
               listCustomer.add(historyCustomDto);
           }

           }

       }

        List<Payment> listPayments=paymentRepository.findAllByCustomerAndDateCreationBetween(customer,startDate, endDate);
        if(!listPayments.isEmpty()){
        for(Payment payment:listPayments){
           if(payment.getAmount()!=0) {
               HistoryCustomDto historyCustomDto1=new HistoryCustomDto();

               historyCustomDto1.setDateCreation(payment.getDateCreation());
               historyCustomDto1.setType("REGLEMENT");
               historyCustomDto1.setAmount(payment.getAmount());
               historyCustomDto1.setPaymentMethod(payment.getPaymentMethod().name());
               listCustomer.add(historyCustomDto1);
           }

            }

        }



Customer existingCustomer=customerRepository.findByFullName(customer).orElseThrow(()->new CustomerNoFoundException("Client introuvable"));

       Enterprise enterprise=enterpriseRepository.findByName(existingCustomer.getEnterprise()).orElseThrow(()->new EnterpriseNotFoundException("Entreprise introiuvable"));
        model.addAttribute("enterprise",enterprise);
        model.addAttribute("history",listCustomer);
        model.addAttribute("customer",customer);
        model.addAttribute("date",LocalDate.now());


        return "viewHistory";
    }
}
