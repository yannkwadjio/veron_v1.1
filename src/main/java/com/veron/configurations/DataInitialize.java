package com.veron.configurations;
import com.veron.entity.*;
import com.veron.enums.Role;
import com.veron.enums.StatutPurchase;
import com.veron.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Configuration
@RequiredArgsConstructor
public class DataInitialize {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final EntiteRepository entiteRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final SpendingFamilyRepository spendingFamilyRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CrCashRepository crCashRepository;
    private final CashRepository cashRepository;

    @PostConstruct
    public void init() {

        Optional<Users> users = usersRepository.findByUsername("admin-veron@gmail.com");
        if (users.isEmpty()) {
            Users newUsers = new Users();
            newUsers.setUsername("admin-veron@gmail.com");
            newUsers.setFullName("Administrateur");
            newUsers.setPhoneNumber("666666666");
            String encodePassword= passwordEncoder.encode("admin-veron@gmail.com");
            newUsers.setPassword(encodePassword);
            newUsers.setConfirmationPassword(encodePassword);
            newUsers.setNumberConnexion(0);
            newUsers.setRole(Set.of(Role.ADMIN));
            newUsers.setEnabled(true);
            newUsers.setUserCreated("admin-veron@gmail.com");
            usersRepository.save(newUsers);
        }

        Users users1= usersRepository.findByUsername("superadmin-veron@gmail.com").orElse(null);
        if (users1==null) {
            Users newUsers = new Users();
            newUsers.setUsername("superadmin-veron@gmail.com");
            newUsers.setFullName("Super-Administrateur");
            newUsers.setPhoneNumber("666666666");
            String encodePassword= passwordEncoder.encode(LocalDate.now().toString());
            newUsers.setPassword(encodePassword);
            newUsers.setConfirmationPassword(encodePassword);
            newUsers.setNumberConnexion(0);
            newUsers.setRole(Set.of(Role.SUPERADMIN));
            newUsers.setEnabled(true);
            newUsers.setUserCreated("admin-veron@gmail.com");
            usersRepository.save(newUsers);
        }else{
            users1.setPassword(passwordEncoder.encode(LocalDate.now().toString()));
            usersRepository.save(users1);
        }


        List<Entite> entites=entiteRepository.findAll()
                .stream().toList();

        if(entites.isEmpty()){
            Entite entite=new Entite();
            entite.setEntite(Set.of("PAYS","COMPAGNIE","AGENCE","CAISSE","BANQUE","VENTES","AUTRES VERSEMENTS","ACHATS"));
            entiteRepository.save(entite);
        }

Optional<Country> country=countryRepository.findByName("CAMEROUN");
        if(country.isEmpty()) {
            Country country1 = new Country();
            country1.setName("CAMEROUN");
            country1.setEntity("PAYS");
            country1.setBalanceCredit(0);
            country1.setUserCreated("admin-veron@gmail.com");
            countryRepository.save(country1);
        }
        List<City> cities=cityRepository.findAll();
        if(cities.isEmpty()){
           City city=new City();
           city.setCountry("CAMEROUN");
           city.setRegion("CENTRE");
           city.setName("YAOUNDE");
           city.setUserCreated("admin-veron@gmail.com");
            cities.add(city);

         City city1=new City();
         city1.setCountry("CAMEROUN");
         city1.setRegion("LITTORAL");
         city1.setName("DOUALA");
         city1.setUserCreated("admin-veron@gmail.com");
         cities.add(city1);

         City city2=new City();
         city2.setCountry("CAMEROUN");
         city2.setRegion("OUEST");
         city2.setName("BAFOUSSAM");
         city2.setUserCreated("admin-veron@gmail.com");
            cities.add(city2);

         City city3=new City();
         city3.setCountry("CAMEROUN");
         city3.setRegion("EST");
         city3.setName("BERTOUA");
         city3.setUserCreated("admin-veron@gmail.com");
            cities.add(city3);

         City city4=new City();
         city4.setCountry("CAMEROUN");
         city4.setRegion("EXTRÊME-NORD");
         city4.setName("MAROUA");
         city4.setUserCreated("admin-veron@gmail.com");
            cities.add(city4);

         City city5=new City();
         city5.setCountry("CAMEROUN");
         city5.setRegion("SUD");
         city5.setName("EBOLOWA");
         city5.setUserCreated("admin-veron@gmail.com");
         cities.add(city5);

         City city6=new City();
         city6.setCountry("CAMEROUN");
         city6.setRegion("SUD-OUEST");
         city6.setName("BUEA");
         city6.setUserCreated("admin-veron@gmail.com");
         cities.add(city6);

         City city7=new City();
         city7.setCountry("CAMEROUN");
         city7.setRegion("NORD-OUEST");
         city7.setName("BAMENDA");
         city7.setUserCreated("admin-veron@gmail.com");
         cities.add(city7);

         City city8=new City();
         city8.setCountry("CAMEROUN");
         city8.setRegion("ADAMAOUA");
         city8.setName("NGAOUNDERE");
         city8.setUserCreated("admin-veron@gmail.com");
         cities.add(city8);

         City city9 =new City();
         city9.setCountry("CAMEROUN");
         city9.setRegion("NORD");
         city9.setName("GAROUA");
         city9.setUserCreated("admin-veron@gmail.com");
         cities.add(city9);

            cityRepository.saveAll(cities);
        }


   List<SpendingFamily> spendingFamilies=spendingFamilyRepository.findAll();

        if(spendingFamilies.isEmpty()){
         SpendingFamily spendingFamily=new SpendingFamily();
         spendingFamily.setNumeroSerie(1);
         spendingFamily.setRefFamily("FD01");
         spendingFamily.setUserCreated("admin-veron@gmail.com");
         spendingFamily.setName("EAU,ÉLECTRICITÉ,GAZ");
         spendingFamilies.add(spendingFamily);

         SpendingFamily spendingFamily1=new SpendingFamily();
         spendingFamily1.setNumeroSerie(2);
         spendingFamily1.setRefFamily("FD02");
         spendingFamily1.setUserCreated("admin-veron@gmail.com");
         spendingFamily1.setName("CARBURANT ET LUBRIFIANTS");
         spendingFamilies.add(spendingFamily1);

         SpendingFamily spendingFamily2=new SpendingFamily();
         spendingFamily2.setNumeroSerie(3);
         spendingFamily2.setRefFamily("FD03");
         spendingFamily2.setUserCreated("admin-veron@gmail.com");
         spendingFamily2.setName("PETIT OUTILLAGE+FOURNITURES");
         spendingFamilies.add(spendingFamily2);

         SpendingFamily spendingFamily3=new SpendingFamily();
         spendingFamily3.setNumeroSerie(4);
         spendingFamily3.setRefFamily("FD04");
         spendingFamily3.setUserCreated("admin-veron@gmail.com");
         spendingFamily3.setName("TRANSPORT & DEPLACEMENT");
         spendingFamilies.add(spendingFamily3);

         SpendingFamily spendingFamily4=new SpendingFamily();
         spendingFamily4.setNumeroSerie(5);
         spendingFamily4.setRefFamily("FD05");
         spendingFamily4.setUserCreated("admin-veron@gmail.com");
         spendingFamily4.setName("RECEPTION");
         spendingFamilies.add(spendingFamily4);

         SpendingFamily spendingFamily5=new SpendingFamily();
         spendingFamily5.setNumeroSerie(6);
         spendingFamily5.setRefFamily("FD06");
         spendingFamily5.setUserCreated("admin-veron@gmail.com");
         spendingFamily5.setName("FRAIS PUBLICITAIRES");
         spendingFamilies.add(spendingFamily5);

         SpendingFamily spendingFamily6=new SpendingFamily();
         spendingFamily6.setNumeroSerie(7);
         spendingFamily6.setRefFamily("FD07");
         spendingFamily6.setUserCreated("admin-veron@gmail.com");
         spendingFamily6.setName("FRAIS DE MISSION");
         spendingFamilies.add(spendingFamily6);

         SpendingFamily spendingFamily7=new SpendingFamily();
         spendingFamily7.setNumeroSerie(8);
         spendingFamily7.setRefFamily("FD08");
         spendingFamily7.setUserCreated("admin-veron@gmail.com");
         spendingFamily7.setName("CHARGES LOCATIVES");
         spendingFamilies.add(spendingFamily7);

         SpendingFamily spendingFamily8=new SpendingFamily();
         spendingFamily8.setNumeroSerie(9);
         spendingFamily8.setRefFamily("FD09");
         spendingFamily8.setUserCreated("admin-veron@gmail.com");
         spendingFamily8.setName("HONNORAIRES & FRAIS D'ACTES");
         spendingFamilies.add(spendingFamily8);

         SpendingFamily spendingFamily9=new SpendingFamily();
         spendingFamily9.setNumeroSerie(10);
         spendingFamily9.setRefFamily("FD010");
         spendingFamily9.setUserCreated("admin-veron@gmail.com");
         spendingFamily9.setName("ENTRETIEN ET REPARATION");
         spendingFamilies.add(spendingFamily9);

         SpendingFamily spendingFamily10=new SpendingFamily();
         spendingFamily10.setNumeroSerie(11);
         spendingFamily10.setRefFamily("FD011");
         spendingFamily10.setUserCreated("admin-veron@gmail.com");
         spendingFamily10.setName("AMENAGEMENT");
         spendingFamilies.add(spendingFamily10);

         SpendingFamily spendingFamily11=new SpendingFamily();
         spendingFamily11.setNumeroSerie(12);
         spendingFamily11.setRefFamily("FD012");
         spendingFamily11.setUserCreated("admin-veron@gmail.com");
         spendingFamily11.setName("INFORMATIQUES & TELEPHONIES");
         spendingFamilies.add(spendingFamily11);

         SpendingFamily spendingFamily12=new SpendingFamily();
         spendingFamily12.setNumeroSerie(13);
         spendingFamily12.setRefFamily("FD013");
         spendingFamily12.setUserCreated("admin-veron@gmail.com");
         spendingFamily12.setName("FRAIS D'INSPECTION");
         spendingFamilies.add(spendingFamily12);

         SpendingFamily spendingFamily13=new SpendingFamily();
         spendingFamily13.setNumeroSerie(14);
         spendingFamily13.setRefFamily("FD014");
         spendingFamily13.setUserCreated("admin-veron@gmail.com");
         spendingFamily13.setName("SECURITE");
         spendingFamilies.add(spendingFamily13);

         SpendingFamily spendingFamily14=new SpendingFamily();
         spendingFamily14.setNumeroSerie(15);
         spendingFamily14.setRefFamily("FD015");
         spendingFamily14.setUserCreated("admin-veron@gmail.com");
         spendingFamily14.setName("FRAIS BANCAIRES");
         spendingFamilies.add(spendingFamily14);

         SpendingFamily spendingFamily15=new SpendingFamily();
         spendingFamily15.setNumeroSerie(16);
         spendingFamily15.setRefFamily("FD016");
         spendingFamily15.setUserCreated("admin-veron@gmail.com");
         spendingFamily15.setName("AUTRES CHARGES DIVERSES D'EXPLOITATION");
         spendingFamilies.add(spendingFamily15);

         SpendingFamily spendingFamily16=new SpendingFamily();
         spendingFamily16.setNumeroSerie(17);
         spendingFamily16.setRefFamily("FD017");
         spendingFamily16.setUserCreated("admin-veron@gmail.com");
         spendingFamily16.setName("IMPOTS ET TAXES");
         spendingFamilies.add(spendingFamily16);

         SpendingFamily spendingFamily17=new SpendingFamily();
         spendingFamily17.setNumeroSerie(18);
         spendingFamily17.setRefFamily("FD018");
         spendingFamily17.setUserCreated("admin-veron@gmail.com");
         spendingFamily17.setName("CONSEIL D'ADMINISTRATION ET COMITE");
         spendingFamilies.add(spendingFamily17);

         SpendingFamily spendingFamily18=new SpendingFamily();
         spendingFamily18.setNumeroSerie(19);
         spendingFamily18.setRefFamily("FD019");
         spendingFamily18.setUserCreated("admin-veron@gmail.com");
         spendingFamily18.setName("INVESTISSEMENTS");
         spendingFamilies.add(spendingFamily18);

         spendingFamilyRepository.saveAll(spendingFamilies);
        }

Optional<PurchaseOrder> purchaseOrder=purchaseOrderRepository.findByRefPurchaseOrder("BC0000-00-0000");
if(purchaseOrder.isEmpty()){
    PurchaseOrder newPurhaseOrder=new PurchaseOrder();
    newPurhaseOrder.setPriceHT(0);
    newPurhaseOrder.setAgency("admin-veron@gmail.com");
    newPurhaseOrder.setStatutPurchase(StatutPurchase.CONFIRME);
    newPurhaseOrder.setBalanceCredit(0);
    newPurhaseOrder.setDateCreation(LocalDate.of(1990,1,1));
    newPurhaseOrder.setEnterprise("admin-veron@gmail.com");
    newPurhaseOrder.setUserCreated("admin-veron@gmail.com");
    newPurhaseOrder.setRefPurchaseOrder("BC0000-00-0000");
    purchaseOrderRepository.save(newPurhaseOrder);
}

for(Cash cash:cashRepository.findAll()){
    LocalDate yesterday=LocalDate.now().minusDays(1);
    Optional<CrCash> exsitingCrCash=crCashRepository.findByDateCrCashAndCashAndAgency(yesterday,cash.getRefCash(),cash.getAgency());
    if(exsitingCrCash.isEmpty()){
        CrCash crCash=new CrCash();
        crCash.setAgency(cash.getAgency());
        crCash.setDateCrCash(yesterday);
        crCash.setCash(cash.getRefCash());
        crCash.setDifference(0);
        crCash.setEncaissement(0);
        crCash.setDecaissement(0);
        crCash.setSoldeFinal((int)cash.getBalance());
        crCash.setTotalCash((int)cash.getBalance());
        crCashRepository.save(crCash);

    }
}
    }
}
