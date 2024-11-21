package com.veron.services.services;

import com.veron.entity.*;
import com.veron.enums.PaymentMethod;
import com.veron.exceptions.PayementNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.PaymentInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServices implements PaymentInterfaces {
    private final PaymentRepository paymentRepository;
    private final CashRepository cashRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final MvtCashRepository mvtCashRepository;
    private final ProfitRepository profitRepository;
    private final SaleRepository saleRepository;


    @Override
    public Map<String, String> createPayment(String refInvoice, String customer, double payment, String paymentMethod, String receiveAccount, String refCash, String motif,double amount) {
        Map<String,String> response=new HashMap<>();
        if(!customer.isEmpty()){
            if(!refInvoice.isEmpty()){
                if(!paymentMethod.isEmpty()){
                    if(amount>0){
                        if(!receiveAccount.isEmpty()){
                            Payment newPayment=new Payment();
                            if(paymentMethod.equals(PaymentMethod.ESPECES.name())){
                                Cash cash=cashRepository.findByRefCash(receiveAccount).orElseThrow(()->new PayementNotFoundException("Vous n'êtes pas autorisé à effecuer cette opération"));
                                assert cash !=null;
                                Invoice invoice=invoiceRepository.findByRefInvoice(refInvoice).orElse(null);
                                assert invoice!=null;
                               if(amount<=invoice.getRest()){

                                   Customer newCustomer=customerRepository.findByFullName(customer).orElse(null);
                                   assert newCustomer!=null;
                                   if(cash.getBalanceCredit()>=amount){
                                       List<Payment> payments=paymentRepository.findAll().stream()
                                               .sorted(Comparator.comparing(Payment::getIdPayment).reversed())
                                               .toList();
                                       if(!payments.isEmpty()){
                                           newPayment.setRefPayment("REG"+invoice.getRefInvoice()+"0"+(payments.get(0).getIdPayment()+1));
                                       }else{
                                           newPayment.setRefPayment("REG"+invoice.getRefInvoice()+"01");
                                       }
                                       newPayment.setAmountInvoice(invoice.getRest());
                                       newPayment.setRest(invoice.getRest()-amount);
                                       newPayment.setDateCreation(LocalDate.now());
                                       newPayment.setCustomer(customer);
                                       newPayment.setInvoice(refInvoice);
                                       newPayment.setAmount(amount);
                                       newPayment.setPaymentMethod(PaymentMethod.ESPECES);
                                       newPayment.setAgency(cash.getAgency());
                                       newPayment.setUserCreated("admin-veron@gmail.com");
                                       paymentRepository.save(newPayment);

                                       List<Payment> listPayment=paymentRepository.findAll().stream()
                                               .sorted(Comparator.comparing(Payment::getIdPayment).reversed())
                                               .toList();

                                       Payment paymentItem=listPayment.get(0);

                                       if(paymentItem.getRest()==0){
                                           Profit newProfit = new Profit();
                                           newProfit.setAgency(cash.getAgency());
                                           newProfit.setEnterprise(cash.getEnterprise());
                                           newProfit.setProfitDate(LocalDate.now());
                                           newProfit.setMontant(invoice.getProfit());
                                           profitRepository.save(newProfit);

                                           Sale sale=new Sale();
                                           sale.setService("REGLEMENT_FACTURE");
                                           sale.setSaleDate(LocalDate.now());
                                           sale.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
                                           sale.setAgency(cash.getAgency());
                                           sale.setTva(0);
                                           sale.setRemise(0);
                                           sale.setPriceHT(0);
                                           sale.setRefSale("REG"+LocalDate.now());
                                           sale.setCustomer(customer);
                                           sale.setProfit(invoice.getProfit());
                                           sale.setUserCreated("admin-veron@gmail.com");
                                           saleRepository.save(sale);
                                       }


                                       MvtCash mvtCash=new MvtCash();
                                       List<MvtCash> listMvtCash = mvtCashRepository.findAll().stream()
                                               .filter(listCash -> listCash.getDateMvtCash().equals(LocalDate.now()))
                                               .sorted(Comparator.comparing(MvtCash::getIdDay).reversed())
                                               .toList();
                                       int idDay = 0;
                                       if (listMvtCash.isEmpty()) {
                                           idDay = 1;
                                       } else {
                                           idDay = listMvtCash.get(0).getIdDay() + 1;
                                       }
                                       mvtCash.setDateMvtCash(LocalDate.now());
                                       mvtCash.setRefOperationCash("CASH"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())+"0"+idDay);
                                       mvtCash.setType("REGLEMENT_FACTURE");
                                       mvtCash.setMotif("REGLEMENT FACTURE Nº "+refInvoice);
                                       mvtCash.setSens("ENCAISSEMENT");
                                       mvtCash.setReference("REG"+refInvoice+LocalDateTime.now());
                                       mvtCash.setBalanceBefore(cash.getBalance());
                                       mvtCash.setAmount(amount);
                                       mvtCash.setFee(0);
                                       mvtCash.setBalanceAfter(cash.getBalance()+amount);
                                       mvtCash.setCash(cash.getRefCash());
                                       mvtCash.setAgency(cash.getAgency());
                                       mvtCash.setValidated(false);
                                       mvtCash.setIdDay(idDay);
                                       mvtCash.setUserCreated("admin-veron@gmail.com");
                                       mvtCashRepository.save(mvtCash);


                                       cash.setBalance(cash.getBalance()+amount);
                                       cash.setBalanceCredit(cash.getBalanceCredit()-amount);
                                       cashRepository.save(cash);

                                       invoice.setBalanceCredit(invoice.getBalanceCredit()-amount);
                                       invoice.setAdvance(invoice.getAdvance()+amount);
                                       invoice.setRest(invoice.getRest()-amount);
                                       invoiceRepository.save(invoice);

                                       newCustomer.setDepot(newCustomer.getDepot()+ amount);
                                       newCustomer.setBalance(newCustomer.getBalance()+ amount);
                                       newCustomer.setBalanceCredit(newCustomer.getBalanceCredit()+amount);
                                       customerRepository.save(newCustomer);
                                       response.put("message","Versement effectué avec succès");

                                   }else{
                                       response.put("message","Crédit insuffisant");
                                   }

                               }else{
                                   response.put("message","Le montant du règlement ne doit pas être supérieur au montant restant de la facture");
                               }

                            }
                        }else{
                            response.put("message","Destinataire non selectionné");
                        }

                    }else{
                        response.put("message","Montant non renseigné");
                    }
                }else{
                    response.put("message","Mode de paiement non selectionné");
                }
            }else{
                response.put("message","Nº facture non selectionné");
            }
        } else{
            response.put("message","Client non selectionné");
        }
        return response;
    }
}
