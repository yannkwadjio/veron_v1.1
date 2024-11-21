package com.veron.services.services;

import com.veron.dto.SpendingFamilyDto;
import com.veron.entity.SpendingFamily;
import com.veron.repository.SpendingFamilyRepository;
import com.veron.services.interfaces.SpendingFamilyInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SpendingFamilyServices implements SpendingFamilyInterfaces {
    private final SpendingFamilyRepository spendingFamilyRepository;

    @Override
    public Map<String,String> createSpendingFamily(SpendingFamilyDto spendingFamilyDto) {
        Map<String,String> response=new HashMap<>();
        Optional<SpendingFamily> existingSpendingFamily=spendingFamilyRepository.findByName(spendingFamilyDto.getName().toUpperCase());
        if(existingSpendingFamily.isEmpty()){
           if(!spendingFamilyDto.getName().isEmpty()) {
               SpendingFamily spendingFamily=new SpendingFamily();
               List<SpendingFamily> listSpendingFamily=spendingFamilyRepository.findAll().stream()
                       .sorted(Comparator.comparing(SpendingFamily::getNumeroSerie).reversed())
                       .toList();
               int numeroSerie=0;
               if(listSpendingFamily.isEmpty()){
                   numeroSerie=1;
               }else{
                   numeroSerie=listSpendingFamily.get(0).getNumeroSerie()+1;
               }
               spendingFamily.setRefFamily("FD0"+numeroSerie);
               spendingFamily.setName(spendingFamilyDto.getName().toUpperCase());
               spendingFamily.setNumeroSerie(numeroSerie);
               spendingFamily.setUserCreated("admin-veron@gmail.com");
               spendingFamilyRepository.save(spendingFamily);
               response.put("message","Famille de dépense créée avec succès");

           }else{
               response.put("message","Famille de dépense non renseignée");
           }
        }else{
            response.put("message","Cette famille existe déjà");
        }

        return response;
    }

    @Override
    public List<SpendingFamily> getAllSpendingFamily() {
        return spendingFamilyRepository.findAll();
    }
}
