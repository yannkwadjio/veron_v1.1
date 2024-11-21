package com.veron.controller.api;

import com.veron.dto.StoreDto;
import com.veron.entity.Store;
import com.veron.services.interfaces.StoreInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stores/")
@RequiredArgsConstructor
public class StoreApi {
    private final StoreInterfaces storeInterfaces;

    @PostMapping("create")
    public Map<String,String> createStores(@RequestBody StoreDto storeDto){
        return storeInterfaces.createStores(storeDto);
    }

    @GetMapping("get-all")
    public List<Store> getAllStores(){
        return storeInterfaces.getAllStores();
    }

    @GetMapping("get-by-ref/{refStore}")
    public Store getStoreByRef(@PathVariable("refStore") String refStore){
        return storeInterfaces.getStoreByRef(refStore);
    }

    @PutMapping("update-store")
    public Map<String,String> updateStore(@RequestParam String enterprise,@RequestParam String agency,@RequestParam String refStore,@RequestParam String name,@RequestParam String usersStores){
        return storeInterfaces.updateStore(enterprise,agency,refStore,name,usersStores);
    }

    @PutMapping("/update-store-to-store")
    public Map<String,String> updateStoreToStore(@RequestParam String agency,@RequestParam String usersStores,@RequestParam String store1,@RequestParam String store2){
        return storeInterfaces.updateStoreToStore(agency,usersStores,store1,store2);
    }

}
