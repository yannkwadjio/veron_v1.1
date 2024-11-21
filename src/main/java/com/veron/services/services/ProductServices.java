package com.veron.services.services;

import com.veron.dto.ProductDto;
import com.veron.entity.*;
import com.veron.enums.Category;
import com.veron.repository.EnterpriseRepository;
import com.veron.repository.ProductRepository;
import com.veron.repository.SupplierRepository;
import com.veron.services.interfaces.ProductInterfaces;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServices implements ProductInterfaces {
    private final ProductRepository productRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Map<String, String> createProducts(ProductDto productDto) {
        Map<String, String> response = new HashMap<>();
        String regex = "[!@#$%^&*()+\\-=\\[\\]{};':\"\\\\|,.<>/?]+";
        Optional<Product> existingProduct = productRepository.findByName(productDto.getName().toUpperCase());
        if (existingProduct.isEmpty()) {
            if (!productDto.getName().isEmpty()) {
                if(!productDto.getName().matches(".*"+regex+".*")) {
                    if (!productDto.getEnterprise().isEmpty()) {
                        if (!productDto.getCategory().isEmpty()) {
                            if (productDto.getPurchasePrice() != 0) {
                                if (productDto.getSellingPrice() != 0 || productDto.getCategory().equals(Category.FOURNITURES.name())) {
                                    Enterprise enterprise = enterpriseRepository.findByName(productDto.getEnterprise().toUpperCase()).orElse(null);
                                    Supplier supplier = null;
                                    if (productDto.getSupplier() != null) {
                                        supplier = supplierRepository.findByEnterprise(productDto.getSupplier().toUpperCase()).orElse(null);
                                    }
                                    List<Product> listProduct = productRepository.findAll().stream()
                                            .filter(enterpriseProduct -> enterpriseProduct.getEnterprise().equals(productDto.getEnterprise()))
                                            .sorted(Comparator.comparing(Product::getNumeroSerie).reversed())
                                            .toList();
                                    int numeroSerie = 0;
                                    if (listProduct.isEmpty()) {
                                        numeroSerie = 1;
                                    } else {
                                        numeroSerie = listProduct.get(0).getNumeroSerie() + 1;
                                    }
                                    Product product = new Product();
                                    product.setRefProduct("ART0" + numeroSerie);
                                    product.setEnterprise(productDto.getEnterprise());
                                    product.setCategory(Category.valueOf(productDto.getCategory()));
                                    product.setName(productDto.getName().toUpperCase());
                                    product.setNumeroSerie(numeroSerie);
                                    product.setPurchasePrice(productDto.getPurchasePrice());
                                    product.setUnitCost(productDto.getPurchasePrice());
                                    product.setSellingPrice(productDto.getSellingPrice());
                                    product.setFinalStock(0);
                                    product.setFinalValue(0);
                                    product.setStore("STORE01");
                                    product.setUserCreated("admin-veron@gmail.com");
                                    productRepository.save(product);
                                    assert enterprise != null;
                                    enterpriseRepository.save(enterprise);
                                    if (supplier != null) {
                                        supplierRepository.save(supplier);
                                    }
                                    response.put("message", "Produit créé avec succès");

                                } else {
                                    response.put("message", "Le prix de  vente ne doit pas être null");
                                }
                            } else {
                                response.put("message", "Le prix d'achat ne doit pas être null");
                            }

                        } else {
                            response.put("message", "Catégorie non sélectionnée");
                        }
                    } else {
                        response.put("message", "Entreprise non sélectionnée");
                    }
                } else {
                    response.put("message", "Le nom de produit ne doit pas contenir de caractères spéciaux");
                }
            } else {
                response.put("message", "Nom du produit non renseigné");
            }
        } else {
            response.put("message", "Ce produit existe déjà");
        }
        return response;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    @Override
    public Map<String, String> updateProductByName(String name,double purchasePrice,double sellingPrice) {
       Map<String,String> response=new HashMap<>();
       Product product=productRepository.findByName(name).orElse(null);
       if(product!=null){
           if(!name.isEmpty()){
              product.setPurchasePrice(purchasePrice);
              product.setSellingPrice(sellingPrice);
              product.setUnitCost(purchasePrice);
              productRepository.save(product);
               response.put("message","Produit mis à jour");
           }else{
               response.put("message","Produit non sélectionné");
           }
       }else{
           response.put("message","Produit introuvable");
       }
        return response;
    }

    @Override
    public Product getProductByRef(String refProduct) {
        return productRepository.findByRefProduct(refProduct).orElse(null);
    }

    @Override
    public Map<String, String> updateProductByRef(String refProduct, double purchasePrice, double sellingPrice) {
        Map<String,String> response=new HashMap<>();
        Product product=productRepository.findByRefProduct(refProduct).orElse(null);
        if(product!=null){
            if(!refProduct.isEmpty()){
                product.setPurchasePrice(purchasePrice);
                product.setSellingPrice(sellingPrice);
                product.setUnitCost(purchasePrice);
                productRepository.save(product);
                response.put("message","Produit mis à jour");
            }else{
                response.put("message","Produit non sélectionné");
            }
        }else{
            response.put("message","Produit introuvable");
        }
        return response;
    }

    @Override
    public void deleteProductByRef(String refProduct) {
       Optional<Product> existingProduct=productRepository.findByRefProduct(refProduct);
        existingProduct.ifPresent(product -> productRepository.deleteById(product.getIdProduct()));
    }


    public void exportProduct(List<Product> products, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Produit");

        // Création des en-têtes
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Produits");
        header.createCell(2).setCellValue("Catégorie");
        header.createCell(3).setCellValue("Stock");
        header.createCell(4).setCellValue("Prix d'achat");
        header.createCell(5).setCellValue("Cout unitaire");
        header.createCell(6).setCellValue("Valeur final");
        header.createCell(7).setCellValue("Prix de vente");
        header.createCell(8).setCellValue("Créé par:");


        // Remplissage des données
        int rowIndex = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(product.getIdProduct());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory().name());
            row.createCell(3).setCellValue(product.getFinalStock());
            row.createCell(4).setCellValue(product.getPurchasePrice());
            row.createCell(5).setCellValue(product.getUnitCost());
            row.createCell(6).setCellValue(product.getFinalValue());
            row.createCell(7).setCellValue(product.getSellingPrice());
            row.createCell(8).setCellValue(product.getUserCreated());
        }

        // Configuration de la réponse pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"produit.xlsx\"");

        // Écriture du fichier dans la réponse
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    public List<Product> readExcelFile(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();

        // Ouvrir le fichier Excel
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // Sauter l'en-tête
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                // Lire les cellules de chaque ligne
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Product product = new Product();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0 -> product.setName(currentCell.getStringCellValue().toUpperCase());
                        case 1 -> product.setCategory(Category.valueOf(currentCell.getStringCellValue()));
                        case 2 -> product.setPurchasePrice(currentCell.getNumericCellValue());
                        case 3 -> product.setSellingPrice(currentCell.getNumericCellValue());
                        case 4 -> product.setFinalStock(currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                    cellIdx++;
                }

                products.add(product);
            }
        }

        return products;
    }


}
