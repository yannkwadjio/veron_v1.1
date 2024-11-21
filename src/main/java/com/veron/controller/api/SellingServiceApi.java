package com.veron.controller.api;

import com.veron.dto.SellingServiceDto;
import com.veron.entity.SellingService;
import com.veron.services.interfaces.SellingServiceInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/selling-services/")
@RequiredArgsConstructor
public class SellingServiceApi {
    private final SellingServiceInterfaces sellingServiceInterfaces;

    @PostMapping("create")
    public Map<String,String> createService(@RequestBody SellingServiceDto sellingServiceDto){
        return sellingServiceInterfaces.createService(sellingServiceDto);
    }

    @GetMapping("get-all")
    public List<SellingService> getAllServices(){
        return sellingServiceInterfaces.getAllServices();
    }

    @GetMapping("get-by-name/{name}")
    public SellingService getSellingServiceByName(@PathVariable("name") String name){
        return sellingServiceInterfaces.getSellingServiceByName(name);
    }



    @PutMapping("update-by-name")
    public Map<String,String> updateSellingServiceByName(@RequestParam("name") String name,@RequestParam("price") double price,@RequestParam("price") String description){
        return sellingServiceInterfaces.updateSellingServiceByName(name,price,description);
    }

    @DeleteMapping("delete/{idService}")
    private Map<String,String> deleteServiceSelling(@PathVariable("idService") int idService){
        return sellingServiceInterfaces.deleteServiceSelling(idService);
    }
}
