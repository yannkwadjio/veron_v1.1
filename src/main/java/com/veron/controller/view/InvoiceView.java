package com.veron.controller.view;

import com.veron.dto.PaymentDto;
import com.veron.entity.*;
import com.veron.enums.Role;
import com.veron.exceptions.CustomerNoFoundException;
import com.veron.exceptions.InvoiceNotFoundException;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.*;
import com.veron.services.services.CashServices;
import com.veron.services.services.InvoiceServices;
import com.veron.services.services.PaymentServices;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class InvoiceView {

    private final UsersRepository usersRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final InvoiceServices invoiceServices;
    private final CashServices cashServices;
    private final PaymentServices paymentServices;
    private final CashRepository cashRepository;
    private final AgencyRepository agencyRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final SaleRepository saleRepository;
    private final MvtCashRepository mvtCashRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/invoice")
    public String viewInvoice(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }
        String nomUtil=userDetails.getUsername();
        model.addAttribute("nomUtil",nomUtil);

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
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
        }else{
            cashier=existingUser.getCash();
            model.addAttribute("cashier",cashier);
        }

        Users users1=usersRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
        List<Invoice> invoices=new ArrayList<>();
        if(users1.getRole().contains(Role.COMPTABLE_MATIERE)){
            for(Invoice invoice:invoiceRepository.findAll()){
                if(invoice.getMotif().contains("BON DE COMMANDE Ref.")){
                    invoices.add(invoice);
                }
            }
        }else{
            invoices.addAll(invoiceRepository.findAll());
        }

        model.addAttribute("invoices",invoices);

        int mt_sale=0;

        for(Sale sale:saleRepository.findAll().stream().filter(sale -> sale.getSaleDate().equals(LocalDate.now()) && sale.getUserCreated().equals(userDetails.getUsername())).toList()){
            mt_sale+=(int)sale.getPriceTTC();
        }
        model.addAttribute("mt_sale",mt_sale);

        return "invoice";
    }

    @GetMapping("/payment/{refInvoice}")
    public String viewPayment(Model model, @PathVariable("refInvoice") String refInvoice,@AuthenticationPrincipal UserDetails userDetails){

            model.addAttribute("payments",paymentRepository.findAll());


         model.addAttribute("invoice",invoiceServices.getByRefInvoice(refInvoice));
        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }
        String nomUtil=userDetails.getUsername();
        model.addAttribute("nomUtil",nomUtil);

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


         model.addAttribute("paymentDto",new PaymentDto());
        return "payment";
    }

    @PostMapping("/payment")
    public String createPayment(Model model,@RequestParam String refInvoice, @RequestParam String customer, @RequestParam double payment, @RequestParam String paymentMethod, @RequestParam String receiveAccount, @RequestParam String refCash, @RequestParam String motif, @RequestParam double amount, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        Map<String,String> response=paymentServices.createPayment(refInvoice,customer,payment,paymentMethod,receiveAccount,refCash,motif,amount);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<Payment> listPayment=paymentRepository.findAll().stream()
                .sorted(Comparator.comparing(Payment::getIdPayment).reversed())
                .toList();

        List<Sale> mvtSales=saleRepository.findAll().stream()
                .sorted(Comparator.comparing(Sale::getIdSale).reversed())
                .toList();

        List<MvtCash> mvtCashes=mvtCashRepository.findAll().stream()
                .sorted(Comparator.comparing(MvtCash::getIdMvtCash).reversed())
                .toList();

Cash cash=cashRepository.findByRefCash(refCash).orElse(null);
assert cash !=null;
model.addAttribute("enterprise",cash.getEnterprise());
model.addAttribute("date",LocalDate.now());
        if(!listPayment.isEmpty()){
            Payment newPayment=listPayment.get(0);
            newPayment.setUserCreated(userDetails.getUsername());
            model.addAttribute("payment",newPayment);
            paymentRepository.save(newPayment);
        }

        if(!mvtSales.isEmpty()){
            Sale sale=mvtSales.get(0);
            sale.setUserCreated(userDetails.getUsername());
            saleRepository.save(sale);
        }

        if(!mvtCashes.isEmpty()){
            MvtCash mvtCash=mvtCashes.get(0);
            mvtCash.setUserCreated(userDetails.getUsername());
            mvtCashRepository.save(mvtCash);
        }

       Agency agency=agencyRepository.findByName(cash.getAgency()).orElse(null);
        if(agency!=null){
            Enterprise enterprise=enterpriseRepository.findByName(agency.getEnterprise()).orElse(null);
            model.addAttribute("enterprise",enterprise);
            model.addAttribute("agency",agency);
        }


if(response.get("message").equals("Versement effectué avec succès")){
    return "recu_payment";
}else{
    return "redirect:/invoice";
}

    }


    @GetMapping("/export-facture-excel")
    public void exportFactureToExcel(HttpServletResponse response) throws IOException {
        List<Invoice> invoice = invoiceRepository.findAll();
        invoiceServices.exportInvoice(invoice, response);
    }

    @PostMapping("/delete-invoice/{refInvoice}")
    public String deleteInvoice(@PathVariable String refInvoice){
        Invoice invoice=invoiceRepository.findByRefInvoice(refInvoice).orElseThrow(()-> new InvoiceNotFoundException("Facture introuvable"));
        Customer customer=customerRepository.findByFullName(invoice.getCustomer()).orElseThrow(()->new CustomerNoFoundException("Client introuvable"));
       customer.setBalanceCredit(customer.getBalanceCredit()-invoice.getAmount());
       customerRepository.save(customer);

       PurchaseOrder purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000").orElseThrow(()->new PurchaseNotFoundException("Bon de commande introuvable"));
       purchaseOrder.setBalanceCredit(purchaseOrder.getBalanceCredit()+invoice.getAmount());
       purchaseOrderRepository.save(purchaseOrder);

       invoiceServices.deleteInvoice(refInvoice);
        return "redirect:/invoice";
    }

}
