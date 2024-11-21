package com.veron.services.services;

import com.veron.entity.Entite;
import com.veron.repository.EntiteRepository;
import com.veron.services.interfaces.EntiteInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntiteServices implements EntiteInterfaces {
    private final EntiteRepository entiteRepository;


    @Override
    public List<String> getAllEntite() {
        List<Entite> listEntite=entiteRepository.findAll().stream().toList();
        Set<String> entite=listEntite.get(0).getEntite();
        return new ArrayList<>(entite);
    }
}
