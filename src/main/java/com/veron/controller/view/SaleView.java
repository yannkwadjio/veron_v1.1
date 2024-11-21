package com.veron.controller.view;

import com.veron.dto.ProductSaleDto;
import com.veron.dto.SaleDto;
import com.veron.dto.TiersDto;
import com.veron.entity.*;
import com.veron.enums.Category;
import com.veron.enums.PaymentMethod;
import com.veron.enums.Role;
import com.veron.enums.Tiers;
import com.veron.repository.*;
import com.veron.services.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class SaleView {
    private final UsersRepository usersRepository;
    private final CashServices cashServices;
    private final SaleServices saleServices;
    private final SupplierRepository supplierRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final CashRepository cashRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final CustomerServices customerServices;
    private final SupplierServices supplierServices;
    private final AgencyRepository agencyRepository;
    private final MvtCashRepository mvtCashRepository;
    private final MvtStockRepository mvtStockRepository;
    private final MvtSalesRepository mvtSalesRepository;

    @GetMapping("/sale")
    private String viewSale(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){

        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }

        List<Sale> sales=saleRepository.findAll().stream()
                .filter(sale->sale.getSaleDate().equals(LocalDate.now()) && sale.getUserCreated().equals(userDetails.getUsername()))
                .toList();
        String nomUtil=userDetails.getUsername();

        Users users=usersRepository.findByUsername(nomUtil).orElse(null);

        model.addAttribute("modes", PaymentMethod.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("nomUtil",nomUtil);
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("customers",customerRepository.findAll());
        assert users != null;
        model.addAttribute("store",users.getStore());
        model.addAttribute("saleDto",new SaleDto());
        model.addAttribute("tiersDto",new TiersDto());
        model.addAttribute("sales",sales);
        model.addAttribute("tiers", Tiers.values());

        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            Cash cash=null;
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
            model.addAttribute("cash",cash);
        }else{
            cashier=existingUser.getCash();
            Cash cash=cashServices.getByCash(cashier);
            model.addAttribute("cash",cash);
            model.addAttribute("cashier",cashier);
        }

        int mt_sale=0;
        int mt_facture=0;

        for(Sale sale:saleRepository.findAll().stream().filter(sale -> sale.getSaleDate().equals(LocalDate.now()) && sale.getUserCreated().equals(userDetails.getUsername())).toList()){
            mt_sale+=(int)sale.getPriceTTC();
        }

        for(Invoice invoice:invoiceRepository.findAll().stream().filter(invoice->invoice.getRest()>0 && invoice.getUserCreated().equals(userDetails.getUsername())).toList()){
            mt_facture+=(int)invoice.getRest();
        }


        model.addAttribute("mt_sale",mt_sale);
        model.addAttribute("mt_facture",mt_facture);

        return "sale";
    }

    @PostMapping("/sale-validation")
    public String saleValidation(Model model,@ModelAttribute SaleDto saleDto, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
  Map<String,String> response=saleServices.createSale(saleDto);
        Cash cash=cashRepository.findByRefCash(saleDto.getRefCashValue()).orElse(null);

        assert cash != null;
        enterpriseRepository.findByName(cash.getEnterprise()).ifPresent(enterprise -> redirectAttributes.addFlashAttribute("enterprise", enterprise));


        Product product=new Product();

        List<ProductSaleDto> products=new ArrayList<>();
        if(saleDto.getProducts()!=null){


                for(String productIItem:saleDto.getProducts()){
                    ProductSaleDto productSaleDto=new ProductSaleDto();
                    String[] item=productIItem.split(":");
                    product=productRepository.findByName(item[0]).orElse(null);
                    if(product!=null){
                        productSaleDto.setPrice(product.getSellingPrice());
                    }else{
                        productSaleDto.setPrice(Double.parseDouble(item[2]));
                    }
                    productSaleDto.setQuantity(Double.parseDouble(item[1]));
                    productSaleDto.setName(item[0]);
                    products.add(productSaleDto);
                }



            double priceHT=0;
            double remise=0;
            if(!saleDto.getRemise().isEmpty()){
                priceHT=Double.parseDouble(saleDto.getTotalPrice())+saleDto.getTvaValues()+Double.parseDouble(saleDto.getRemise());
            }else{
                priceHT=Double.parseDouble(saleDto.getTotalPrice())+saleDto.getTvaValues();
                remise=0;
            }
            redirectAttributes.addFlashAttribute("products",products);
            redirectAttributes.addFlashAttribute("remise",remise);
            redirectAttributes.addFlashAttribute("tva",saleDto.getTvaValues());
            redirectAttributes.addFlashAttribute("priceHT",priceHT);
            redirectAttributes.addFlashAttribute("priceTTC",Double.parseDouble(saleDto.getTotalPrice()));

        }else{
            response.put("message","Produits non sélectionnés");
        }


        if(response.get("message").equals("Vente effectuée avec succès") && saleDto.getPaymentMethod().equals(PaymentMethod.ESPECES.name())){
            List<Sale> listSale=saleRepository.findAll().stream()
                    .sorted(Comparator.comparing(Sale::getIdSale).reversed())
                    .toList();

            if (!listSale.isEmpty()){
                Sale sale=listSale.get(0);
                sale.setUserCreated(userDetails.getUsername());
                redirectAttributes.addFlashAttribute("sale",sale);
                saleRepository.save(sale);
            }

            List<MvtCash> mvtCashes=mvtCashRepository.findAll().stream()
                    .sorted(Comparator.comparing(MvtCash::getIdMvtCash).reversed())
                    .toList();

            if(!mvtCashes.isEmpty()){
                MvtCash mvtCash=mvtCashes.get(0);
                mvtCash.setUserCreated(userDetails.getUsername());
                mvtCashRepository.save(mvtCash);
            }

            List<MvtStock> mvtStocks=mvtStockRepository.findAll().stream()
                    .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                    .toList();

            if(!mvtStocks.isEmpty()){
                MvtStock mvtStock=mvtStocks.get(0);
                mvtStock.setUserCreated(userDetails.getUsername());
                mvtStockRepository.save(mvtStock);
            }

            List<MvtSales> mvtSales=mvtSalesRepository.findAll().stream()
                    .sorted(Comparator.comparing(MvtSales::getIdMvtSale).reversed())
                    .toList();
            if(!mvtSales.isEmpty()){
                MvtSales mvtSale=mvtSales.get(0);
                mvtSale.setUserCreated(userDetails.getUsername());
                mvtSalesRepository.save(mvtSale);
            }

    return "redirect:/recu-sale";
}else if(response.get("message").equals("Vente effectuée avec succès") && saleDto.getPaymentMethod().equals(PaymentMethod.A_CREDIT.name())){
            List<Invoice> listInvoice=invoiceRepository.findAll().stream()
                    .sorted(Comparator.comparing(Invoice::getIdInvoice).reversed())
                    .toList();
if(!listInvoice.isEmpty()){
    Invoice invoice=listInvoice.get(0);
    invoice.setUserCreated(userDetails.getUsername());
    redirectAttributes.addFlashAttribute("invoice",invoice);
    invoiceRepository.save(invoice);
}

List<Sale> listSales=saleRepository.findAll().stream()
        .sorted(Comparator.comparing(Sale::getIdSale).reversed())
        .toList();

            if(!listSales.isEmpty()){
                Sale sale =listSales.get(0);
                sale.setUserCreated(userDetails.getUsername());
                saleRepository.save(sale);
            }

Enterprise enterprise=enterpriseRepository.findByName(cash.getEnterprise()).orElse(null);
model.addAttribute("enterprise",enterprise);
 return "redirect:/invoice-sale";
        }else if(response.get("message").equals("Impossible d'effectuer cette opération, car vous avez déjà clôturer votre caisse")){
            redirectAttributes.addFlashAttribute("response", response.get("message"));
            return "redirect:/sale";
        }
        else{
            redirectAttributes.addFlashAttribute("response", response.get("message"));
    return "redirect:/sale";
}

    }

    @GetMapping("/recu-sale")
    public String saleRecu(Model model, RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails) {
        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("date", date);
        Users users = usersRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (users != null) {
            Agency agency = agencyRepository.findByName(users.getAgency()).orElse(null);
            if (agency != null) {
                model.addAttribute("agency", agency);
            }

        }
        return "recu_sale";
    }

    @GetMapping("/invoice-sale")
    public String invoiceRecu(Model model, RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails){
        LocalDateTime date=LocalDateTime.now();
        model.addAttribute("date",date);

        Users users=usersRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if(users!=null){
            Agency agency=agencyRepository.findByName(users.getAgency()).orElse(null);
            if(agency!=null){
                model.addAttribute("agency",agency);
            }
        }

        return "invoice_sale";
    }


    @PostMapping("/customer_sale")
    public String postAgency(@ModelAttribute TiersDto tiersDto, RedirectAttributes redirectAttributes, @RequestParam String type, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=new HashMap<>();
        if(type.equals("CLIENT")){
            response=customerServices.createCustomer(tiersDto);
           List<Customer> customers=customerRepository.findAll().stream()
                   .sorted(Comparator.comparing(Customer::getIdCustomer).reversed())
                   .toList();


            if(!customers.isEmpty()){
                Customer custom=customers.get(0);
                custom.setUserCreated(userDetails.getUsername());
                customerRepository.save(custom);
            }

        }else{
            response=supplierServices.createSupplier(tiersDto);
            List<Supplier> suppliers=supplierRepository.findAll().stream()
                    .sorted(Comparator.comparing(Supplier::getIdSupplier).reversed())
                    .toList();
            if(!suppliers.isEmpty()){
                Supplier supplier=suppliers.get(0);
                supplier.setUserCreated(userDetails.getUsername());
                supplierRepository.save(supplier);
            }

        }

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        return "redirect:/sale";
    }

    @GetMapping("/state-sales")
    public String viewStateSale(Model model,@AuthenticationPrincipal UserDetails userDetails){
        String nomUtil=userDetails.getUsername();
        Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            Cash cash=null;
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
            model.addAttribute("cash",cash);
        }else{
            cashier=existingUser.getCash();
            Cash cash=cashServices.getByCash(cashier);
            model.addAttribute("cash",cash);
            model.addAttribute("cashier",cashier);
        }

        int mt_sale=0;

        for(Sale sale:saleRepository.findAll().stream().filter(sale -> sale.getSaleDate().equals(LocalDate.now()) && sale.getUserCreated().equals(userDetails.getUsername())).toList()){
            mt_sale+=(int)sale.getPriceTTC();
        }
        model.addAttribute("mt_sale",mt_sale);
        return "state_sales";
    }


}
