package com.veron.services.services;

import com.veron.dto.CrCashDto;
import com.veron.entity.Cash;
import com.veron.entity.CrCash;
import com.veron.entity.MissingCash;
import com.veron.enums.StatutMissing;
import com.veron.repository.CashRepository;
import com.veron.repository.CrCashRepository;
import com.veron.repository.MissingCashRepository;
import com.veron.services.interfaces.CrCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrCashServices implements CrCashInterfaces {
    private final CrCashRepository crCashRepository;
    private final MissingCashRepository missingCashRepository;
    private final CashRepository cashRepository;


    @Override
    public Map<String, String> createCrCash(CrCashDto crCashDto) {
        Map<String,String> response=new HashMap<>();

            CrCash crCash=new CrCash();
            crCash.setDateCrCash(LocalDate.now());
            crCash.setNb10000(crCashDto.getNb10000());
            crCash.setNb5000(crCashDto.getNb5000());
            crCash.setNb2000(crCashDto.getNb2000());
            crCash.setNb1000(crCashDto.getNb1000());
            crCash.setNb500(crCashDto.getNb500());
            crCash.setNb100(crCashDto.getNb100());
            crCash.setNb50(crCashDto.getNb50());
            crCash.setNb25(crCashDto.getNb25());
            crCash.setNb10(crCashDto.getNb10());
            crCash.setNb5(crCashDto.getNb5());
            crCash.setNb2(crCashDto.getNb2());
            crCash.setNb1(crCashDto.getNb1());
            crCash.setDecaissement(crCashDto.getDecaissement());
            crCash.setEncaissement(crCashDto.getEncaissement());
            crCash.setSoldeFinal(crCashDto.getSoldeFinal());
            crCash.setSoldeInitial(crCashDto.getSoldeInitial());
            crCash.setTotalCash(crCashDto.getTotalCash());
            crCash.setDifference(crCashDto.getDifference());
            crCash.setCash(crCashDto.getRefCash());

        cashRepository.findByRefCash(crCashDto.getRefCash()).ifPresent(cash -> crCash.setAgency(cash.getAgency()));
        crCashRepository.save(crCash);

        if(crCashDto.getDifference()<0){
            MissingCash missingCash=new MissingCash();
            List<MissingCash> listMissing=missingCashRepository.findAll().stream()
                    .sorted(Comparator.comparing(MissingCash::getIdMissing).reversed())
                    .toList();
            long numeroSerie=0;
            if(listMissing.isEmpty()){
                numeroSerie=1;
            }else{
numeroSerie=listMissing.get(0).getIdMissing()+1;
            }
            missingCash.setRefMissingCash("MQT0"+numeroSerie);
            missingCash.setDateCreation(LocalDate.now());
            Cash cash=cashRepository.findByRefCash(crCashDto.getRefCash()).orElse(null);
            if(cash!=null){
                missingCash.setAgency(cash.getAgency());
            }
            missingCash.setAdvance(0);
            missingCash.setAmount(-crCashDto.getDifference());
            missingCash.setResponsible(crCashDto.getResponsible());
            missingCash.setRest(-crCashDto.getDifference());
            missingCash.setStatut(StatutMissing.EN_INSTANCE);
            missingCashRepository.save(missingCash);
        }
        return response;
    }

    @Override
    public List<CrCash> getAllCrCash() {
        return crCashRepository.findAll();
    }

    @Override
    public List<CrCash> getAllCrCashByDate(LocalDate startDate, LocalDate endDate) {
        return crCashRepository.findByDateCrCashBetween(startDate, endDate);
    }

    @Override
    public Map<String, String> deleteCrashById(int idCrCash) {
        Map<String,String> response=new HashMap<>();
        boolean existingCrCash=crCashRepository.existsById((long)idCrCash);
        if(existingCrCash){
            CrCash crCash=crCashRepository.findById((long)idCrCash).orElse(null);
            if(crCash!=null){
                List<MissingCash> missingCashes=missingCashRepository.findByDateCreation(crCash.getDateCrCash());
           for(MissingCash missingCash:missingCashes){
              missingCashRepository.deleteById(missingCash.getIdMissing());
           }
            }
              crCashRepository.deleteById((long)idCrCash);
            response.put("message","Journée déclôturée avec succès");
        }else{
            response.put("message","Journée non clôturée");
        }
        return response;
    }

    @Override
    public CrCash getByIdCrCash(int idCrCash) {
        return crCashRepository.findById((long)idCrCash).orElse(null);
    }


}
