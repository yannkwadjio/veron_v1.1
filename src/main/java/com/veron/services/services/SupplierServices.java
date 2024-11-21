package com.veron.services.services;

import com.veron.dto.TiersDto;
import com.veron.entity.*;
import com.veron.repository.SupplierRepository;
import com.veron.services.interfaces.SupplierInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SupplierServices implements SupplierInterfaces {
    private final SupplierRepository supplierRepository;

    @Override
    public Map<String, String> createSupplier(TiersDto tiersDto) {
        Map<String,String> response=new HashMap<>();

        Optional<Supplier> existingSupplier=supplierRepository.findByNumberPhone(tiersDto.getPhoneNumber());
        if(existingSupplier.isEmpty()){
            existingSupplier=supplierRepository.findByEmail(tiersDto.getEmail());
            if(existingSupplier.isEmpty()){
                if(!tiersDto.getFullName().isEmpty()){
                    if(!tiersDto.getCountry().isEmpty()){
                                if(!tiersDto.getEnterprise().isEmpty()){
                                    if(!tiersDto.getPhoneNumber().isEmpty()){
                                        if(!tiersDto.getEmail().isEmpty()){
                                            if (!tiersDto.getType().isEmpty()){
                                                if(tiersDto.getEmail().matches(".*@.*")){
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
                                                    supplier.setCountry(tiersDto.getCountry());
                                                    supplier.setEnterprise(tiersDto.getFullName());
                                                    supplier.setRefSupplier("FR"+LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth())+numeroSerie);
                                                    supplier.setAdresse(tiersDto.getAdresse().toUpperCase());
                                                    supplier.setNumberPhone(tiersDto.getPhoneNumber());
                                                    supplier.setEmail(tiersDto.getEmail());
                                                    supplier.setBalanceCredit(0);
                                                    supplier.setIdDay(numeroSerie);
                                                    supplier.setDateCreation(LocalDate.now());
                                                    supplier.setPointFocal(tiersDto.getPointFocal());
                                                    supplier.setUserCreated("admin-veron@gmail.com");
                                                    supplierRepository.save(supplier);

                                                }else{
                                                    response.put("message","Adresse E-mail invalide");}

                                            }else{
                                                response.put("message","Type de tier non sélectionné");}

                                        }else{
                                            response.put("message","Adresse E-mail non renseigné");}
                                    }else{
                                        response.put("message","Nº téléphone non renseigné");}
                                }else{
                                    response.put("message","Entreprise non sélectionnée");}
                            }else{
                        response.put("message","Pays non sélectionné");}
                }else{
                    response.put("message","Nom client non renseigné");}
            }else{
                response.put("message","Cette adresse e-mail est utilisé par un autre client");}
        }else{
            response.put("message","Ce numéro est utilisé par un autre client");
        }
        return response;
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }
}
