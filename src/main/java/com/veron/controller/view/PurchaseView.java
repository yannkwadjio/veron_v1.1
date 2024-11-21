package com.veron.controller.view;

import com.veron.dto.*;
import com.veron.entity.*;
import com.veron.enums.PaymentMethod;
import com.veron.enums.Role;
import com.veron.enums.StatutPurchase;
import com.veron.exceptions.PurchaseNotFoundException;
import com.veron.repository.*;
import com.veron.services.services.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PurchaseView {
    private final UsersRepository usersRepository;
    private final CashServices cashServices;
    private final PurchaseSevices purchaseSevices;
    private final BankAccountRepository bankAccountRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderServices purchaseOrderServices;
    private final ProductRepository productRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final ProductStockServices productStockServices;
    private final AgencyRepository agencyRepository;
    private final MvtStockRepository mvtStockRepository;
    private final StoreRepository storeRepository;
    private final ProductStockRepository productStockRepository;
    private final CountLotRepository countLotRepository;

    @GetMapping("/purchase")
    private String viewPurchase(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        if (model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }

        String nomUtil = userDetails.getUsername();
        Users users = usersRepository.findByUsername(nomUtil).orElse(null);
        List<PurchaseOrder> listPurchaseOrder = purchaseOrderRepository.findAll();

        if (!listPurchaseOrder.isEmpty()) {
            List<PurchaseOrder> listFilterPurchase = new ArrayList<>();
            for (PurchaseOrder purchase : listPurchaseOrder) {
                if (purchase.getStatutPurchase().equals(StatutPurchase.EN_INSTANCE)) {
                    assert users != null;
                    if (purchase.getAgency().equals(users.getAgency())) {
                        listFilterPurchase.add(purchase);
                    }
                }
            }
            model.addAttribute("purchaseOrder", listFilterPurchase);
        }


        model.addAttribute("modes", PaymentMethod.values());
        model.addAttribute("nomUtil", nomUtil);
        model.addAttribute("purchaseOrderDto", new PurchaseOrderDto());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("purchaseOrders", purchaseOrderRepository.findAll().stream().filter(purchase -> purchase.getDateCreation().equals(LocalDate.now())).toList());
        model.addAttribute("modes", PaymentMethod.values());
        model.addAttribute("agencies", agencyRepository.findAll());

        assert users != null;
        StringBuilder role = null;
        for (Role existingRole : users.getRole()) {
            role = new StringBuilder(existingRole.name());
        }
        model.addAttribute("role", role);
        model.addAttribute("nomUtil", nomUtil);
        Users existingUser = usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if (existingUser.getCash() == null) {
            Cash cash = null;
            cashier = "pas de caisse";
            model.addAttribute("cashier", cashier);
            model.addAttribute("cash", cash);
        } else {
            cashier = existingUser.getCash();
            Cash cash = cashServices.getByCash(cashier);
            model.addAttribute("cash", cash);
            model.addAttribute("cashier", cashier);
        }


        double totalBank = 0;
        List<BankAccount> listBankAccount = bankAccountRepository.findAll().stream()
                .toList();
        for (BankAccount bank : listBankAccount) {
            totalBank += bank.getBalance();
        }
        model.addAttribute("totalBank", totalBank);


        List<Purchase> purchases = purchaseRepository.findAll().stream()
                .filter(achat -> achat.getPurchaseDate().equals(LocalDate.now()))
                .toList();

        model.addAttribute("purchases", purchases);


        int nb_bc = 0;
        int nb_bc_cf = 0;

        for (PurchaseOrder purchaseOrder : purchaseOrderRepository.findAll()) {
            if (purchaseOrder.getStatutPurchase().equals(StatutPurchase.EN_INSTANCE)) {
                nb_bc += 1;
            } else if (purchaseOrder.getStatutPurchase().equals(StatutPurchase.CONFIRME) && purchaseOrder.getDateCreation().equals(LocalDate.now())) {
                nb_bc_cf += 1;
            }

        }
        model.addAttribute("nb_bc_cf", nb_bc_cf);
        model.addAttribute("nb_bc", nb_bc);

        return "purchase";
    }

    @GetMapping("purchase-validation")
    public String puchaseValidation(Model model, RedirectAttributes redirectAttributes, @RequestParam String refCashValue, @RequestParam Map<String, String> listProduct, @RequestParam("paymentMethodValue") String paymentMethodValue, @RequestParam("supplierValue") String supplierValue, @RequestParam("totalPriceValue") String totalPriceValue, @RequestParam("remiseValue") String remiseValue, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = purchaseSevices.createPurchase(refCashValue, listProduct, paymentMethodValue, supplierValue, totalPriceValue, remiseValue);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<Purchase> listPurchase = purchaseRepository.findAll().stream()
                .sorted(Comparator.comparing(Purchase::getIdPurchase).reversed())
                .toList();

        Purchase purchase = listPurchase.get(0);
        purchase.setUserCreated(userDetails.getUsername());
        purchaseRepository.save(purchase);

        redirectAttributes.addFlashAttribute("response", response.get("message"));

        return "redirect:/purchase";
    }

    @GetMapping("/export-purchaseOrder-excel-by-date-present")
    public void exportPurchaseOrderToExcel(HttpServletResponse response) throws IOException {
        List<PurchaseOrder> pruchasesOrder = purchaseOrderRepository.findAll().stream().filter(order -> order.getDateCreation().equals(LocalDate.now())).toList();
        purchaseOrderServices.exportPurchaseOrder(pruchasesOrder, response);
    }


    @GetMapping("/store-config")
    public String viewStoreConfig(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("modes", PaymentMethod.values());
        model.addAttribute("purchaseOrderDto", new PurchaseOrderDto());

        String nomUtil = userDetails.getUsername();
        model.addAttribute("nomUtil", nomUtil);

        Users users = usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for (Role existingRole : users.getRole()) {
            role = new StringBuilder(existingRole.name());
        }
        model.addAttribute("role", role);
        model.addAttribute("nomUtil", nomUtil);
        Users existingUser = usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if (existingUser.getCash() == null) {
            cashier = "pas de caisse";
            model.addAttribute("cashier", cashier);
        } else {
            cashier = existingUser.getCash();
            model.addAttribute("cashier", cashier);
        }


        return "purchase_order";
    }


    @PostMapping("/purchase-order")
    public String createPurchaseOrderView(Model model, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute PurchaseOrderDto purchaseOrderDto, RedirectAttributes redirectAttributes) {
        Map<String, String> response = purchaseOrderServices.createPurchaseOrder(purchaseOrderDto);
        List<PurchaseOrder> listPurchaseOrder = purchaseOrderRepository.findAll().stream()
                .sorted(Comparator.comparing(PurchaseOrder::getIdPurchaseOrder).reversed())
                .toList();
        if (!listPurchaseOrder.isEmpty()) {
            Enterprise enterprise = enterpriseRepository.findByName(listPurchaseOrder.get(0).getEnterprise()).orElse(null);
            Agency agency = agencyRepository.findByName(listPurchaseOrder.get(0).getAgency()).orElse(null);
            model.addAttribute("agency", agency);
            model.addAttribute("enterprise", enterprise);
            model.addAttribute("purchaseOrder", listPurchaseOrder.get(0));
            model.addAttribute("date", LocalDateTime.now());
            Product product;
            List<PurchaseOrderListDto> products = new ArrayList<>();
            double remise = purchaseOrderDto.getRemise();
            for (String productIItem : purchaseOrderDto.getProducts()) {
                PurchaseOrderListDto purchaseOrderListDto = new PurchaseOrderListDto();
                String[] item = productIItem.split(":");
                if (!productIItem.contains("undefined")) {
                    product = productRepository.findByName(item[0]).orElse(null);
                    assert product != null;
                    purchaseOrderListDto.setProduct(item[0]);
                    purchaseOrderListDto.setQuantity(Double.parseDouble(item[1]));
                    purchaseOrderListDto.setRefProduct(product.getRefProduct());
                    purchaseOrderListDto.setPrice(product.getPurchasePrice());
                    purchaseOrderListDto.setTotalPrice(Double.parseDouble(item[1]) * Double.parseDouble(item[2]));
                    products.add(purchaseOrderListDto);
                }

            }
            model.addAttribute("remise", remise);
            model.addAttribute("products", products);
            PurchaseOrder purchaseOrder = listPurchaseOrder.get(0);
            purchaseOrder.setUserCreated(userDetails.getUsername());
            purchaseOrderRepository.save(purchaseOrder);
        }

        if (response.get("message").equals("Bon de commande créé avec succès")) {
            return "purchase_order_recu";
        } else {
            redirectAttributes.addFlashAttribute("response", response.get("message"));
            return "redirect:/purchase";
        }

    }


    @GetMapping("/export-purchase-excel-by-date-present")
    public void exportPurchaseToExcel(HttpServletResponse response) throws IOException {
        List<Purchase> purchases = purchaseRepository.findAll().stream().filter(achat -> achat.getPurchaseDate().equals(LocalDate.now())).toList();
        purchaseSevices.exportPurchase(purchases, response);
    }


    @PostMapping("purchase-stock")
    public String purchaseStock(Model model, RedirectAttributes redirectAttributes, @ModelAttribute ProductStockDto productStockDto, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = productStockServices.createProductStock(productStockDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        redirectAttributes.addFlashAttribute("response", response.get("message"));

        List<ProductStock> productStocks = productStockRepository.findAll().stream()
                .sorted(Comparator.comparing(ProductStock::getIdProductStock).reversed())
                .toList();
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByRefPurchaseOrder(productStockDto.getRefPurchaseOrder()).orElseThrow(() -> new PurchaseNotFoundException("Bon de commande non trouvé"));


        List<MvtStock> mvtStocks = mvtStockRepository.findAll().stream()
                .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                .toList();

        if (!mvtStocks.isEmpty()) {
            MvtStock mvtStock = mvtStocks.get(0);
            mvtStock.setUserCreated(userDetails.getUsername());
            mvtStockRepository.save(mvtStock);
        }

        return "redirect:/purchase";
    }


    @PostMapping("/purchase-stock-appro")
    public String purchaseStockAppro(Model model, RedirectAttributes redirectAttributes, @ModelAttribute ApproStoreDto approStoreDto, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = productStockServices.createApproStock(approStoreDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        redirectAttributes.addFlashAttribute("response", response.get("message"));

        List<MvtStock> mvtStocks = mvtStockRepository.findAll().stream()
                .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                .toList();

        if (!mvtStocks.isEmpty()) {
            MvtStock mvtStock = mvtStocks.get(0);
            mvtStock.setUserCreated(userDetails.getUsername());
            mvtStockRepository.save(mvtStock);
        }

        return "redirect:/purchase";
    }

    @PostMapping("/purchase-stock-sorti")
    public String purchaseStockSorit(Model model, RedirectAttributes redirectAttributes, @ModelAttribute FounitureOutDto founitureOutDto, @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, String> response = productStockServices.createOutStock(founitureOutDto);

        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        redirectAttributes.addFlashAttribute("response", response.get("message"));
        if (response.get("message").equals("Fourniture commandée avec succès")) {
            List<MvtStock> mvtStocks = mvtStockRepository.findAll().stream()
                    .sorted(Comparator.comparing(MvtStock::getIdMvtStock).reversed())
                    .toList();

            if (!mvtStocks.isEmpty()) {
                MvtStock mvtStock = mvtStocks.get(0);
                mvtStock.setUserCreated(userDetails.getUsername());
                mvtStockRepository.save(mvtStock);
            }

            Users users = usersRepository.findByUsername(userDetails.getUsername()).orElse(null);

            if (users != null) {
                Enterprise enterprise = enterpriseRepository.findByName(users.getEnterprise()).orElse(null);
                model.addAttribute("enterprise", enterprise);
                model.addAttribute("agency", users.getAgency());
            }

            List<ProductFournitureDto> productFournitureDtos = new ArrayList<>();
            for (String product : founitureOutDto.getProducts()) {
                String[] items = product.split(":");
                ProductFournitureDto productFournitureDto = new ProductFournitureDto();
                productFournitureDto.setName(items[2]);
                productFournitureDto.setStore(items[1]);
                productFournitureDto.setRefProduct(items[0]);
                productFournitureDto.setQuantity(Double.parseDouble(items[3]));
                productFournitureDtos.add(productFournitureDto);
            }

            model.addAttribute("date", LocalDateTime.now());
            model.addAttribute("products", productFournitureDtos);

            return "fourniture_recu";
        } else {
            return "redirect:/purchase";
        }

    }

    @GetMapping("/reporting-stock")
    public String viewReportingStock(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("stores", storeRepository.findAll());

        String nomUtil = userDetails.getUsername();
        Users users = usersRepository.findByUsername(nomUtil).orElse(null);

        assert users != null;
        StringBuilder role = null;
        for (Role existingRole : users.getRole()) {
            role = new StringBuilder(existingRole.name());
        }
        model.addAttribute("role", role);
        model.addAttribute("nomUtil", nomUtil);
        Users existingUser = usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if (existingUser.getCash() == null) {
            Cash cash = null;
            cashier = "pas de caisse";
            model.addAttribute("cashier", cashier);
            model.addAttribute("cash", cash);
        } else {
            cashier = existingUser.getCash();
            Cash cash = cashServices.getByCash(cashier);
            model.addAttribute("cash", cash);
            model.addAttribute("cashier", cashier);
        }

        model.addAttribute("role", role);
        model.addAttribute("nomUtil", nomUtil);

        return "reporting_stock";
    }

    @PostMapping("/classLot")
    public String classLot() {
        int i = 0;
        List<CountLot> listCount = new ArrayList<>();
        for (CountLot countLot : countLotRepository.findAll()) {
            i++;
            countLot.setIdDay(i);
            countLot.setLot("LOT0" + i);
            listCount.add(countLot);
        }
        countLotRepository.saveAll(listCount);

        return "redirect:/purchase";
    }
}