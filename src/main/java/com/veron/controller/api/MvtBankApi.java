package com.veron.controller.api;

import com.veron.services.interfaces.MvtCashInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mvt-bank/")
@RequiredArgsConstructor
public class MvtBankApi {
    @SuppressWarnings("unused")
    private final MvtCashInterfaces mvtCashInterfaces;

}
