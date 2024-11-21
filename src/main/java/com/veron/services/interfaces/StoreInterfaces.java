package com.veron.services.interfaces;

import com.veron.dto.StoreDto;
import com.veron.entity.Store;

import java.util.List;
import java.util.Map;

public interface StoreInterfaces {
    Map<String, String> createStores(StoreDto storeDto);

    List<Store> getAllStores();

    Store getStoreByRef(String refStore);

    Map<String, String> updateStore(String enterprise, String agency, String refStore, String name, String usersStores);

    Map<String, String> updateStoreToStore(String agency, String usersStores, String store1, String store2);
}
