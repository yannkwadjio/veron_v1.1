package com.veron.services.services;

import com.veron.entity.Profit;
import com.veron.repository.ProfitRepository;
import com.veron.services.interfaces.ProfitInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfitServices implements ProfitInterfaces {
    @SuppressWarnings("unused")
    private final ProfitRepository profitRepository;

    @Override
    public List<Profit> getAllProfitByDate(LocalDate startDate, LocalDate endDate) {
        return profitRepository.findByProfitDateBetween(startDate,endDate);
    }
}
