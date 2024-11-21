package com.veron.services.services;

import com.veron.entity.MissingCash;
import com.veron.repository.MissingCashRepository;
import com.veron.services.interfaces.MissingCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MissingCashServices implements MissingCashInterfaces {
    private final MissingCashRepository missingCashRepository;

    @Override
    public Map<String, String> createMissingCash(MissingCash missingCash) {
        return Map.of();
    }

    @Override
    public List<MissingCash> getAllMissingCash() {
        return missingCashRepository.findAll();
    }

    @Override
    public List<MissingCash> getAllMissingByDate(LocalDate startDate, LocalDate endDate) {
        return missingCashRepository.findByDateCreationBetween(startDate, endDate);
    }

    @Override
    public MissingCash getByRefMissing(String refMissingCash) {
        return missingCashRepository.findByRefMissingCash(refMissingCash).orElse(null);
    }
}
