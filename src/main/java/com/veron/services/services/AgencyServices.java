package com.veron.services.services;

import com.veron.dto.AgencyDto;
import com.veron.entity.*;
import com.veron.exceptions.AgencyNotFountException;
import com.veron.repository.*;
import com.veron.services.interfaces.AgencyInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AgencyServices implements AgencyInterfaces {
    private final AgencyRepository agencyRepository;


    @Override
    public Map<String, String> createAgency(AgencyDto agencyDto) {
        Map<String,String> response=new HashMap<>();
        Optional<Agency> existingAgency=agencyRepository.findByName(agencyDto.getName().toUpperCase());

        if(existingAgency.isEmpty()){
            if(!agencyDto.getName().isEmpty()){
                if (agencyDto.getCountry()!=null){
                   if(agencyDto.getRegion()!=null){
                       if(agencyDto.getCity()!=null){
                          if(agencyDto.getEnterprise()!=null) {
                                  List<Agency> listAgency=agencyRepository.findAll().stream()
                                 .sorted(Comparator.comparing(Agency::getNumeroAgency))
                                 .toList();

                         int numeroSerie=0;
                         if(!listAgency.isEmpty()){
                             numeroSerie=listAgency.get(0).getNumeroAgency()+1;
                         }else{
                             numeroSerie=1;
                         }
                          Agency agency=new Agency();
                         agency.setDateCreation(LocalDate.now());
                         agency.setNumeroAgency(numeroSerie);
                         agency.setRefAgency(agencyDto.getCountry().substring(0,1)+agencyDto.getRegion().substring(0,1)+agencyDto.getCity().substring(0,1)+"0"+numeroSerie);
                          agency.setCountry(agencyDto.getCountry());
                          agency.setRegion(agencyDto.getRegion());
                          agency.setCity(agencyDto.getCity().toUpperCase());
                          agency.setBalance(0);
                          agency.setEnterprise(agencyDto.getEnterprise());
                          agency.setName(agencyDto.getName().toUpperCase());
                          agency.setBalanceCredit(0);
                          agency.setEntity("AGENCE");
                          agencyRepository.save(agency);


                              response.put("message","Agence créé avec succès");
                          }else{
                              throw new AgencyNotFountException("entreprise introuvable");
                          }
                       }else{
                           throw new AgencyNotFountException("Ville introuvable");
                       }
                   }else{
                      throw new AgencyNotFountException("Région introuvable");
                   }
                }else{
                    throw new AgencyNotFountException("Entreprise introuvable");
                }
            }else{
                response.put("message","Nom de l'agence non renseigné");
            }
        }else{
            response.put("message","Cette agence existe déjà");
        }



        return response;
    }

    @Override
    public List<Agency> getAllAgency() {

        return agencyRepository.findAll();
    }

    @Override
    public Map<String, String> deleteAgencyById(int idAgency) {
        Map<String,String> response=new HashMap<>();
        boolean existingAgency=agencyRepository.existsById((long)idAgency);
        if(existingAgency){
            agencyRepository.deleteById((long)idAgency);
            response.put("message","Agence supprimée avec succès");
        }else{
            response.put("message","Agence introuvable");
        }
        return response;
    }





}
