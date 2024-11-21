package com.veron.services.services;

import com.veron.entity.CountLot;
import com.veron.repository.CountLotRepository;
import com.veron.services.interfaces.CountLotInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountLotServices implements CountLotInterfaces {
    private final CountLotRepository countLotRepository;

    @Override
    public List<CountLot> getAllCountLot() {
        return countLotRepository.findAll();
    }
}
