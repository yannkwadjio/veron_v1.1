package com.veron.services.services;

import com.veron.dto.SellingServiceCategoryDto;
import com.veron.entity.SellingServiceCategory;
import com.veron.repository.SellingServiceCategoryRepository;
import com.veron.services.interfaces.SellingServiceCategoryInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellingServiceCategoryServices implements SellingServiceCategoryInterfaces {
    private final SellingServiceCategoryRepository sellingServiceCategoryRepository;

    @Override
    public Map<String,String> createSellingServiceCategory(SellingServiceCategoryDto sellingServiceCategoryDto) {
        Map<String,String> response=new HashMap<>();
        if(!sellingServiceCategoryDto.getName().isEmpty()){
            Optional<SellingServiceCategory> eSellingServiceCategory=sellingServiceCategoryRepository.findByName(sellingServiceCategoryDto.getName().toUpperCase());
       if(eSellingServiceCategory.isEmpty()){
           SellingServiceCategory sellingServiceCategory=new SellingServiceCategory();
           sellingServiceCategory.setName(sellingServiceCategoryDto.getName().toUpperCase());
           sellingServiceCategory.setUserCreated("admin-veron@gmail.com");
           sellingServiceCategoryRepository.save(sellingServiceCategory);

           response.put("message","Catégorie de service créée avec succès");
       }else{
           response.put("message","Cette catégorie existe déjà");
       }
        }else{
            response.put("message","Catégorie non renseignée");
        }
        return response;
    }

    @Override
    public List<SellingServiceCategory> getAllSellingServiceCategory() {
        return sellingServiceCategoryRepository.findAll();
    }
}
