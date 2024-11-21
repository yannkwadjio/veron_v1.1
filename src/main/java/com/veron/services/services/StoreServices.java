package com.veron.services.services;

import com.veron.dto.StoreDto;
import com.veron.entity.Store;
import com.veron.entity.Users;
import com.veron.exceptions.StoreNotFoundException;
import com.veron.repository.StoreRepository;
import com.veron.repository.UsersRepository;
import com.veron.services.interfaces.StoreInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StoreServices implements StoreInterfaces {
    private final StoreRepository storeRepository;
    private final UsersRepository usersRepository;


    @Override
    public Map<String, String> createStores(StoreDto storeDto) {
        Map<String,String> response=new HashMap<>();
        if(storeDto.getAgency()!=null){
            if(!storeDto.getName().isEmpty()){
                Optional<Store> existingStore=storeRepository.findByNameAndAgencies(storeDto.getName().toUpperCase(),storeDto.getAgency().toUpperCase());
                if(existingStore.isPresent()){
                    if (!existingStore.get().getAgencies().equals(storeDto.getAgency())){
                            if(storeDto.getEnterprise()!=null){
                                Store store=new Store();
                                store.setAgencies(storeDto.getAgency());
                                store.setName(storeDto.getName().toUpperCase());
                                List<Store> listStores=storeRepository.findAll().stream().sorted(Comparator.comparing(Store::getIdStore).reversed()).toList();
                                if(listStores.isEmpty()){
                                    store.setRefStore("STORE01");
                                }else{
                                    long numeroSerie=listStores.get(0).getIdStore()+1;
                                    store.setRefStore("STORE0"+numeroSerie);
                                }
                                store.setUserCreated("admin-veron@gmail.com");
                                storeRepository.save(store);
                                response.put("message","Magasin créé avec succès");
                            }else{
                                throw new StoreNotFoundException("Entreprise introuvable");
                            }

                    }else{
                        response.put("message","Un magasin porte déjà ce nom");
                    }
                }else{
                    if(!storeDto.getName().isEmpty()){
                        if(storeDto.getAgency()!=null){
                            if(storeDto.getEnterprise()!=null){
                                Store store=new Store();
                                store.setAgencies(storeDto.getAgency());
                                store.setName(storeDto.getName().toUpperCase());
                                List<Store> listStores=storeRepository.findAll().stream().sorted(Comparator.comparing(Store::getIdStore).reversed()).toList();
                                if(listStores.isEmpty()){
                                    store.setRefStore("STORE01");
                                }else{
                                    long numeroSerie=listStores.get(0).getIdStore()+1;
                                    store.setRefStore("STORE0"+numeroSerie);
                                }
                                store.setUserCreated("admin-veron@gmail.com");
                                storeRepository.save(store);
                                response.put("message","Magasin créé avec succès");
                            }else{
                                throw new StoreNotFoundException("Entreprise introuvable");
                            }
                        }else{
                            throw new StoreNotFoundException("Agence introuvable");
                        }
                    }else{
                        response.put("message","nom du magasin non renseigné");
                    }
                }
            }else{
                response.put("message","nom du magasin non renseigné");
            }

        }else{
            throw new StoreNotFoundException("Agence introuvable");
        }

        return response;
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreByRef(String refStore) {
        return storeRepository.findByRefStore(refStore).orElse(null);
    }

    @Override
    public Map<String, String> updateStore(String enterprise, String agency, String refStore, String name, String usersStores) {
        Map<String, String> response = new HashMap<>();
        List<Store> stores = storeRepository.findAll();
        Set<String> existingUserStores = new HashSet<>();
        for (Store store : stores) {
            if (store.getUserStores() != null) {
                existingUserStores.add(store.getUserStores().toString());
            }
        }
        if (!existingUserStores.isEmpty()) {

            for (String users : existingUserStores) {
                if (users.contains(usersStores)) {
                    response.put("message", "Ce utilisateur est déjà présent dans un autre magasin, bv effectuer un transfet inter-magasin");
                    break;
                } else {
                    Store store = storeRepository.findByRefStore(refStore).orElse(null);
                    if (store != null) {
                        if (store.getUserStores() == null) {
                            store.setUserStores(new HashSet<>());
                        }
                        store.getUserStores().add(usersStores);
                        Users user1 = usersRepository.findByUsername(usersStores).orElse(null);
                        assert user1 != null;
                        user1.setStore(store.getRefStore());
                        storeRepository.save(store);
                        usersRepository.save(user1);
                        response.put("message", "Utilisateur affecté avec succès");
                    }
                }

            }
        } else {
            Store store = storeRepository.findByRefStore(refStore).orElse(null);
            if (store != null) {
                if (store.getUserStores() == null) {
                    store.setUserStores(new HashSet<>());
                }
                store.getUserStores().add(usersStores);
                Users user1 = usersRepository.findByUsername(usersStores).orElse(null);
                assert user1 != null;
                user1.setStore(store.getRefStore());
                storeRepository.save(store);
                usersRepository.save(user1);
                response.put("message", "Utilisateur affecté avec succès");

            }
        }
        return response;
    }

    @Override
    public Map<String, String> updateStoreToStore(String agency, String usersStores, String store1, String store2) {
       Map<String,String> response=new HashMap<>();
           if(!agency.isEmpty()){
               if(!usersStores.isEmpty()){
                  if(!store2.isEmpty()) {
                      Store store11=storeRepository.findByRefStore(store1).orElse(null);
                      Store store12=storeRepository.findByRefStore(store2).orElse(null);

                      if(store11!=null){
                          for(String ref:store11.getUserStores()){
                              if(ref.equals(usersStores)){
                                  store11.getUserStores().remove(usersStores);
                              }
                          }
                      }

                      assert store12 != null;

                      if(store12.getUserStores()==null){
                          store12.setUserStores(new HashSet<>());
                      }
                     store12.getUserStores().add(usersStores);
                      assert store11 != null;
                      List<Store> stores=new ArrayList<>();



                      stores.add(store11);
                      stores.add(store12);
                      storeRepository.saveAll(stores);

                      Users user1=usersRepository.findByUsername(usersStores).orElse(null);
                      assert user1 != null;
                      user1.setStore(store2);
                      usersRepository.save(user1);

                      response.put("message","Utilisateur transféré");

                  }else {
                      response.put("message","Magasin non sélectionnée");
                  }
               }else {
                   response.put("message","Utilisateur non sélectionnée");
               }
           }else {
               response.put("message","Agence non sélectionnée");
           }
        return response;
    }

    public void exportStores(List<Store> stores, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Magasin");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Agence");
        header.createCell(2).setCellValue("Nom");
        header.createCell(3).setCellValue("Liste des utilisateurs");
        header.createCell(4).setCellValue("Liste des agences");
        header.createCell(5).setCellValue("Créé par:");

        // Remplissage des données
        int rowIndex = 1;
        for (Store store : stores) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(store.getIdStore());
            row.createCell(1).setCellValue(store.getAgencies());
            row.createCell(2).setCellValue(store.getName());
            if(store.getUserStores()!=null){
             row.createCell(3).setCellValue(store.getUserStores().toString());
            }else{
                row.createCell(3).setCellValue("Aucun utilisateur");
            }
            row.createCell(4).setCellValue(store.getAgencies());
            row.createCell(5).setCellValue(store.getUserCreated());

        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"magasins.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
