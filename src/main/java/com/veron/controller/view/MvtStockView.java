package com.veron.controller.view;

import com.veron.entity.MvtStock;
import com.veron.repository.MvtStockRepository;
import com.veron.services.services.MvtStockServices;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MvtStockView {
private MvtStockRepository mvtStockRepository;
private MvtStockServices mvtStockServices;

    @PostMapping("/export-stock-excel-date")
    public void exportStockToExcel(HttpServletResponse response, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws IOException {
        List<MvtStock> mvtStocks = mvtStockRepository.findByDateCreationBetween(startDate,endDate);
        mvtStockServices.exportMvtStock(mvtStocks, response);
    }
}
