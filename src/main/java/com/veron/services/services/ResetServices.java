package com.veron.services.services;

import com.veron.repository.*;
import com.veron.services.interfaces.ResetInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetServices implements ResetInterfaces {
    private final ResetRepository resetRepository;
    private final AgencyRepository agencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CashRepository cashRepository;
    private final CityRepository cityRepository;
    private final CountLotRepository countLotRepository;
    private final CrCashRepository crCashRepository;
    private final CustomerRepository customerRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final EntiteRepository entiteRepository;
    private final InvoiceRepository invoiceRepository;
    private final MissingCashRepository missingCashRepository;
    private final MvtBankRepository mvtBankRepository;
    private final MvtCashRepository mvtCashRepository;
    private final MvtCreditRepository mvtCreditRepository;
    private final PaymentRepository paymentRepository;
    private final MvtStockRepository mvtStockRepository;
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final ProfitRepository profitRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SaleRepository saleRepository;
    private final SellingServiceRepository sellingServiceRepository;
    private final SellingServiceCategoryRepository sellingServiceCategoryRepository;
    private final SpendingFamilyRepository spendingFamilyRepository;
    private final SpentRepository spentRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;
    private final UsersRepository usersRepository;
    @Override
    public void deleteAllBdd() {
        agencyRepository.deleteAll();
        bankAccountRepository.deleteAll();
        cashRepository.deleteAll();
        cityRepository.deleteAll();
        countLotRepository.deleteAll();
        crCashRepository.deleteAll();
        customerRepository.deleteAll();
        enterpriseRepository.deleteAll();
        invoiceRepository.deleteAll();
        entiteRepository.deleteAll();
        missingCashRepository.deleteAll();
        mvtBankRepository.deleteAll();
        mvtCashRepository.deleteAll();
        mvtCreditRepository.deleteAll();
        paymentRepository.deleteAll();
        mvtStockRepository.deleteAll();
        productRepository.deleteAll();
        productStockRepository.deleteAll();
        profitRepository.deleteAll();
        purchaseRepository.deleteAll();
        purchaseOrderRepository.deleteAll();
        saleRepository.deleteAll();
        sellingServiceRepository.deleteAll();
        sellingServiceCategoryRepository.deleteAll();
        spendingFamilyRepository.deleteAll();
        spentRepository.deleteAll();
        storeRepository.deleteAll();
        supplierRepository.deleteAll();
        usersRepository.deleteAll();
    }
}
