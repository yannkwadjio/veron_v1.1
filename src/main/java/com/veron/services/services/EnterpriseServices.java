package com.veron.services.services;

import com.veron.dto.EnterpriseDto;
import com.veron.entity.*;
import com.veron.exceptions.EnterpriseNotFoundException;
import com.veron.repository.*;
import com.veron.services.interfaces.EnterpriseInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EnterpriseServices implements EnterpriseInterfaces {
    private final EnterpriseRepository enterpriseRepository;
    private final SellingServiceRepository sellingServiceRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final SellingServiceCategoryRepository sellingServiceCategoryRepository;



    private final Path route= Paths.get("src/main/resources/static/img/logo");
    @Override
    public Map<String, String> createEnterprise(EnterpriseDto enterpriseDto, MultipartFile file) {
Map<String,String> response=new HashMap<>();
List<Enterprise> enterprises=enterpriseRepository.findAll();
if(enterprises.isEmpty()){
    Optional<Enterprise> existingEnterprise=enterpriseRepository.findByName(enterpriseDto.getName().toUpperCase());
    if(existingEnterprise.isEmpty()){
        Enterprise enterprise=new Enterprise();
        if(enterpriseDto.getEmail().matches(".*@.*")){
            if(!enterpriseDto.getCountry().isEmpty()){
                enterprise.setName(enterpriseDto.getName().toUpperCase());
                enterprise.setCountry(enterpriseDto.getCountry());
                enterprise.setEmail(enterpriseDto.getEmail());
                enterprise.setPhoneNumber(enterpriseDto.getPhoneNumber());
                enterprise.setUniqueIdentificationNumber(enterpriseDto.getUniqueIdentificationNumber().toUpperCase());
                enterprise.setRegistreCommerce(enterpriseDto.getRegistreCommerce().toUpperCase());
                enterprise.setBoitePostale(enterpriseDto.getBoitePostale());
                enterprise.setSlogan(enterpriseDto.getSlogan());
                enterprise.setBalanceCredit(0);
                try{
                    Files.copy(file.getInputStream(),this.route.resolve(file.getOriginalFilename()));
                    enterprise.setFileName(enterpriseDto.getName().toUpperCase());
                    enterprise.setFileType(file.getContentType());
                }catch(Exception e){
                    e.getMessage();
                }
                enterprise.setEntity("COMPAGNIE");
                enterprise.setUserCreated("admin-veron@gmail.com");
                enterpriseRepository.save(enterprise);



                Optional<SellingService> existingService=sellingServiceRepository.findByName("VENTES");
                if(existingService.isEmpty()){
                    SellingService sellingService=new SellingService();
                    sellingService.setName("VENTES");
                    sellingService.setDescription("Ventes des produits et autres...");
                    sellingService.setPrice(0);
                    sellingService.setBalanceCredit(0);
                    sellingService.setCategory("VENTES");
                    sellingService.setUserCreated("admin-veron@gmail.com");
                    sellingServiceRepository.save(sellingService);

                    Optional<SellingServiceCategory> existingCategoryService=sellingServiceCategoryRepository.findByName("VENTES");
                    if(existingCategoryService.isEmpty()){
                        SellingServiceCategory sellingServiceCategory=new SellingServiceCategory();
                        sellingServiceCategory.setName("VENTES");
                        sellingServiceCategory.setUserCreated("admin-veron@gmail.com");
                        sellingServiceCategoryRepository.save(sellingServiceCategory);
                    }


                    Optional<Supplier> existingSupplier=supplierRepository.findByEnterprise("FOURNISSEUR INCONNU");
                    if(existingSupplier.isEmpty()){
                        Supplier supplier=new Supplier();
                        List<Supplier> listSupplier=supplierRepository.findAll().stream()
                                .filter(supplier1->supplier1.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Supplier::getIdDay).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!listSupplier.isEmpty()){
                            numeroSerie=listSupplier.get(0).getIdDay()+1;
                        }else{
                            numeroSerie=1;
                        }

                        supplier.setCountry(enterpriseDto.getCountry());
                        supplier.setEnterprise("FOURNISSEUR INCONNU");
                        supplier.setRefSupplier("FR"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                        supplier.setAdresse("CAMEROUN");
                        supplier.setNumberPhone("600000001");
                        supplier.setEmail("fournisseur@reply.com");
                        supplier.setBalanceCredit(0);
                        supplier.setIdDay(numeroSerie);
                        supplier.setDateCreation(LocalDate.now());
                        supplier.setPointFocal("FOURNISSEUR");
                        supplier.setUserCreated("admin-veron@gmail.com");
                        supplierRepository.save(supplier);

                    }

                    Optional<Customer> existingCustomer=customerRepository.findByFullName(enterpriseDto.getName().toUpperCase());
                    if(existingCustomer.isEmpty()){
                        Customer customer=new Customer();
                        List<Customer> listCustomer=customerRepository.findAll().stream()
                                .filter(customer1->customer1.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Customer::getIdDay).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!listCustomer.isEmpty()){
                            numeroSerie=listCustomer.get(0).getIdDay()+1;
                        }else{
                            numeroSerie=1;
                        }
                        customer.setCountry(enterpriseDto.getCountry());
                        customer.setEnterprise(enterpriseDto.getName());
                        customer.setRefCustomer("CL"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                        customer.setFullName(enterpriseDto.getName().toUpperCase());
                        customer.setAdresse("CAMEROUN");
                        customer.setPhoneNumber(enterpriseDto.getPhoneNumber());
                        customer.setEmail(enterpriseDto.getEmail());
                        customer.setDepot(0);
                        customer.setRetrait(0);
                        customer.setBalance(0);
                        customer.setType("CLIENT");
                        customer.setBalanceCredit(0);
                        customer.setIdDay(numeroSerie);
                        customer.setDateCreation(LocalDate.now());
                        customer.setUserCreated("admin-veron@gmail.com");
                        customerRepository.save(customer);

                    }


                    Optional<Customer> existingCustomer1=customerRepository.findByPhoneNumber("600000000");
                    if(existingCustomer1.isEmpty()){
                        Customer customer=new Customer();
                        List<Customer> listCustomer=customerRepository.findAll().stream()
                                .filter(customer1->customer1.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Customer::getIdDay).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!listCustomer.isEmpty()){
                            numeroSerie=listCustomer.get(0).getIdDay()+1;
                        }else{
                            numeroSerie=1;
                        }
                        customer.setCountry(enterpriseDto.getCountry());
                        customer.setEnterprise(enterpriseDto.getName());
                        customer.setRefCustomer("CL"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                        customer.setFullName("CLIENT INCONNU");
                        customer.setAdresse("CAMEROUN");
                        customer.setPhoneNumber("600000000");
                        customer.setEmail("client@reply.com");
                        customer.setDepot(0);
                        customer.setRetrait(0);
                        customer.setBalance(0);
                        customer.setType("CLIENT");
                        customer.setBalanceCredit(0);
                        customer.setIdDay(numeroSerie);
                        customer.setDateCreation(LocalDate.now());
                        customer.setUserCreated("admin-veron@gmail.com");
                        customerRepository.save(customer);

                    }

                    Store store=new Store();
                    store.setRefStore("STORE01");
                    store.setAgencies(enterpriseDto.getName().toUpperCase());
                    store.setUserStores(Set.of("admin-veron@gmail.com"));
                    store.setName("MAGASIN PRINCIPAL");
                    storeRepository.save(store);



                }
                response.put("message","Entreprise créé avec succès");
            }else{
                response.put("message","Pays non sélectionné");
            }
        }else{
            response.put("message","Adresse Email incorrecte");
        }
    }else if(!existingEnterprise.get().getCountry().equals(enterpriseDto.getCountry())){
        Enterprise enterprise=new Enterprise();
        if(enterpriseDto.getEmail().matches(".*@.*")){
            if(!enterpriseDto.getCountry().isEmpty()){
                enterprise.setName(enterpriseDto.getName().toUpperCase());
                enterprise.setCountry(enterpriseDto.getCountry());
                enterprise.setEmail(enterpriseDto.getEmail());
                enterprise.setPhoneNumber(enterpriseDto.getPhoneNumber());
                enterprise.setUniqueIdentificationNumber(enterpriseDto.getUniqueIdentificationNumber().toUpperCase());
                enterprise.setRegistreCommerce(enterpriseDto.getRegistreCommerce().toUpperCase());
                enterprise.setBoitePostale(enterpriseDto.getBoitePostale());
                enterprise.setSlogan(enterpriseDto.getSlogan());
                enterprise.setBalanceCredit(0);
                try{
                    Files.copy(file.getInputStream(),this.route.resolve(file.getOriginalFilename()));
                    enterprise.setFileName(enterpriseDto.getName().toUpperCase());
                    enterprise.setFileType(file.getContentType());
                }catch(Exception e){
                    e.getMessage();
                }
                enterprise.setEntity("COMPAGNIE");
                enterprise.setUserCreated("admin-veron@gmail.com");
                enterpriseRepository.save(enterprise);

                Optional<SellingService> existingService=sellingServiceRepository.findByName("VENTES");
                if(existingService.isEmpty()){
                    SellingService sellingService=new SellingService();
                    sellingService.setName("VENTES");
                    sellingService.setDescription("Ventes des produits pharmaceutiques et autres...");
                    sellingService.setPrice(0);
                    sellingService.setBalanceCredit(0);
                    sellingService.setUserCreated("admin-veron@gmail.com");
                    sellingServiceRepository.save(sellingService);


                    Optional<Supplier> existingSupplier=supplierRepository.findByEnterprise("FOURNISSEUR INCONNU");
                    if(existingSupplier.isEmpty()){
                        Supplier supplier=new Supplier();
                        List<Supplier> listSupplier=supplierRepository.findAll().stream()
                                .filter(supplier1->supplier1.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Supplier::getIdDay).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!listSupplier.isEmpty()){
                            numeroSerie=listSupplier.get(0).getIdDay()+1;
                        }else{
                            numeroSerie=1;
                        }

                        supplier.setCountry(enterpriseDto.getCountry());
                        supplier.setEnterprise("FOURNISSEUR INCONNU");
                        supplier.setRefSupplier("FR"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                        supplier.setAdresse("CAMEROUN");
                        supplier.setNumberPhone("600000001");
                        supplier.setEmail("fournisseur@reply.com");
                        supplier.setBalanceCredit(0);
                        supplier.setIdDay(numeroSerie);
                        supplier.setDateCreation(LocalDate.now());
                        supplier.setPointFocal("FOURNISSEUR");
                        supplier.setUserCreated("admin-veron@gmail.com");
                        supplierRepository.save(supplier);

                    }



                    Optional<Customer> existingCustomer=customerRepository.findByPhoneNumber("600000000");
                    if(existingCustomer.isEmpty()){
                        Customer customer=new Customer();
                        List<Customer> listCustomer=customerRepository.findAll().stream()
                                .filter(customer1->customer1.getDateCreation().equals(LocalDate.now()))
                                .sorted(Comparator.comparing(Customer::getIdDay).reversed())
                                .toList();
                        int numeroSerie=0;
                        if(!listCustomer.isEmpty()){
                            numeroSerie=listCustomer.get(0).getIdDay()+1;
                        }else{
                            numeroSerie=1;
                        }
                        customer.setCountry(enterpriseDto.getCountry());
                        customer.setEnterprise(enterpriseDto.getName());
                        customer.setRefCustomer("CL"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                        customer.setFullName("CLIENT INCONNU");
                        customer.setAdresse("CAMEROUN");
                        customer.setPhoneNumber("600000000");
                        customer.setEmail("client@reply.com");
                        customer.setDepot(0);
                        customer.setType("CLIENT");
                        customer.setRetrait(0);
                        customer.setBalance(0);
                        customer.setBalanceCredit(0);
                        customer.setIdDay(numeroSerie);
                        customer.setDateCreation(LocalDate.now());
                        customer.setUserCreated("admin-veron@gmail.com");
                        customerRepository.save(customer);

                    }



                }
                response.put("message","Entreprise créé avec succès");
            }else{
                response.put("message","Pays non sélectionné");
            }
        }else{
            response.put("message","Adresse Email incorrecte");
        }
    }else{
        response.put("message","Cette entreprise existe déjà");
    }
}else{
    throw new EnterpriseNotFoundException("Pour cette version, vous ne pouvez créer qu'une seule entreprise");
}

        return response;
    }

    @Override
    public List<Enterprise> getAllEnterprise() {
        return enterpriseRepository.findAll();
    }

    @Override
    public Map<String, String> deleteByIdEnterprise(int idEnterprise) {
        Map<String,String> response=new HashMap<>();
        boolean exisitngEnterprise=enterpriseRepository.existsById((long)idEnterprise);
        if(exisitngEnterprise){
           enterpriseRepository.deleteById((long)idEnterprise);
           response.put("message","Entreprise supprimée avec succès");
        }else{
            response.put("message","Cet entreprise n'existe pas");
        }
        return response;
    }


}
