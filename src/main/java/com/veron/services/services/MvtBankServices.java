package com.veron.services.services;

import com.veron.repository.MvtBankRepository;
import com.veron.services.interfaces.MvtBankInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MvtBankServices implements MvtBankInterfaces {
    @SuppressWarnings("unused")
    private final MvtBankRepository mvtBankRepository;
}
