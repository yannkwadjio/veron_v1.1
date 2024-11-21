package com.veron.controller.view;

import com.veron.dto.CrCashDto;
import com.veron.dto.MvtCashDto;
import com.veron.entity.*;
import com.veron.enums.*;
import com.veron.exceptions.AgencyNotFountException;
import com.veron.exceptions.CashNotFoundException;
import com.veron.exceptions.EnterpriseNotFoundException;
import com.veron.repository.*;
import com.veron.services.services.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CashView {

    private final CashServices cashServices;
    private final CashRepository cashRepository;
    private final UsersRepository usersRepository;
    private final MvtCashServices mvtCashServices;
    private final MvtCashRepository mvtCashRepository;
    private final CrCashRepository crCashRepository;
    private final CrCashServices crCashServices;
    private final EnterpriseRepository enterpriseRepository;
    private final AgencyRepository agencyRepository;



    @GetMapping("/cash-bank")
    public String viewCashBank(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){
        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }
        String nomUtil=userDetails.getUsername();
        model.addAttribute("modes", PaymentMethod.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("nomUtil",nomUtil);
        model.addAttribute("mvtCashDto",new MvtCashDto());
        Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);
        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            Cash cash=null;
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
            model.addAttribute("cash",cash);
        }else{
            cashier=existingUser.getCash();
            Cash cash=cashServices.getByCash(cashier);
            model.addAttribute("cash",cash);
            model.addAttribute("cashier",cashier);
        }


        model.addAttribute("cashType", CashType.values());
        model.addAttribute("mvtCashes",mvtCashRepository.findAll().stream().filter(date->date.getDateMvtCash().equals(LocalDate.now()) && date.getUserCreated().equals(userDetails.getUsername())).toList());
        model.addAttribute("date", LocalDate.now());
        return "cash_bank";
    }

    @PostMapping("/mvt-cash")
    public String createMvtCashView(Model model,@ModelAttribute MvtCashDto mvtCashDto,@AuthenticationPrincipal UserDetails userDetails,RedirectAttributes redirectAttributes){
        Map<String,String> response=mvtCashServices.createMvtCash(mvtCashDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        List<MvtCash> listMvtCash=mvtCashRepository.findAll().stream()
                .sorted(Comparator.comparing(MvtCash::getIdMvtCash).reversed())
                .toList();

        MvtCash mvtCash=new MvtCash();
        if(!listMvtCash.isEmpty()){
            mvtCash=listMvtCash.get(0);
            mvtCash.setUserCreated(userDetails.getUsername());
            mvtCashRepository.save(mvtCash);
        }


        if(!mvtCashDto.getType().equals(CashType.AVANCE_PERCUE.name())){
            return "redirect:/cash-bank";
        }else if(response.get("message").equals("Opération effectuée avec succès")){
            model.addAttribute("mvtCash",mvtCash);
            Cash cash=cashRepository.findByRefCash(mvtCashDto.getCashRef()).orElseThrow(()->new CashNotFoundException("Caisse introuvable"));
            Enterprise enterprise=enterpriseRepository.findByName(cash.getEnterprise()).orElseThrow(()->new EnterpriseNotFoundException("Entreprise introuvable"));
            model.addAttribute("enterprise",enterprise);
            Agency agency=agencyRepository.findByName(cash.getAgency()).orElseThrow(()->new AgencyNotFountException("Agence introuvable"));
            model.addAttribute("agency",agency);
            return "advance_recu";
        }else{
            return "redirect:/cash-bank";
        }

    }

    @GetMapping("cr-cash")
    public String crCashView(Model model,RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetails userDetails){

        if(model.containsAttribute("msg")) {
            String response = (String) model.asMap().get("msg");
            model.addAttribute("response", response);
        }
        String nomUtil=userDetails.getUsername();
        model.addAttribute("nomUtil",nomUtil);

        Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);

        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            Cash cash=null;
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
            model.addAttribute("cash",cash);
        }else{
            cashier=existingUser.getCash();
            Cash cash=cashServices.getByCash(cashier);
            model.addAttribute("cash",cash);
            model.addAttribute("cashier",cashier);
        }

        model.addAttribute("crCashDto",new CrCashDto());

        List<CrCash> crCashes = crCashRepository.findAll().stream()
                .filter(mvt -> mvt.getCash().equals(existingUser.getCash()))
                .toList();

        boolean state=false;
        for(CrCash crCash:crCashes){
            if(crCash.getDateCrCash().equals(LocalDate.now())){
                state=true;
            }
        }

        model.addAttribute("state",state);

  return "cr_cash";
    }




    @PostMapping("/cr-cash")
    public String postCrCash(RedirectAttributes redirectAttributes,@ModelAttribute CrCashDto crCashDto,Model model,@AuthenticationPrincipal UserDetails userDetails){
     Map<String,String> response=crCashServices.createCrCash(crCashDto);
        redirectAttributes.addFlashAttribute("msg", response.get("message"));
        int mtAchat=0;
        int mtVentes=0;
        int mtDepenses=0;
        int mtMADEnc=0;
        int mtMADDec=0;
        int mtAuv=0;
        int mtAud=0;
        int totalEnc=0;
        int totalDec=0;
        int soldeFin=0;
        int soldeInit=0;
        int reglement=0;
        int soldePhysique=0;
        int ecart=0;
        String agency = "";
        LocalDate dte=LocalDate.now();

        Cash cash=cashRepository.findByRefCash(crCashDto.getRefCash()).orElse(null);
        if(cash!=null) {
            agency= cash.getAgency();
            List<CrCash> listCrCash=crCashRepository.findAll().stream()
                    .filter(crCash ->  crCash.getCash().equals(cash.getRefCash()))
                    .sorted(Comparator.comparing(CrCash::getIdCrCash).reversed())
                    .toList();
            if(!listCrCash.isEmpty()){
                soldeInit= listCrCash.get(0).getSoldeInitial();
            }
        }

        List<MvtCash> listMvtCash=mvtCashRepository.findAll().stream()
                .filter(mvtCash -> mvtCash.getDateMvtCash().equals(LocalDate.now()))
                .toList();

        Users users=usersRepository.findByUsername(userDetails.getUsername()).orElse(null);
        String responsable= "";
        if(users!=null){
            responsable=users.getFullName()+"/Caisse: "+users.getCash();
        }

        for(MvtCash mvtCash:listMvtCash){
            if(mvtCash.getType().equals(CashType.BON_DE_COMANDE.name()) && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtAchat= mtAchat+(int)(mvtCash.getAmount()+mvtCash.getFee());
                totalDec=totalDec+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals("VENTES") && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtVentes=mtVentes+ (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalEnc=totalEnc+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals("REGLEMENT_FACTURE") && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                reglement=reglement+ (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalEnc=totalEnc+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals("DEPENSES") && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtDepenses= (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalDec=totalDec+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals(CashType.APPRO_CAISSE_EN_ENTREE.name()) || mvtCash.getType().equals(CashType.APPRO_CAISSE_VIA_LA_BANQUE.name())){
                if(mvtCash.getUserCreated().equals(userDetails.getUsername())){
                    mtMADEnc= mtMADEnc+(int)(mvtCash.getAmount()+mvtCash.getFee());
                    totalEnc=totalEnc+(int)(mvtCash.getAmount()+mvtCash.getFee());
                }
            }else if(mvtCash.getType().equals(CashType.RETRAIT_POUR_VERSEMENT_BANQUE.name()) && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtMADDec=mtMADDec+ (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalDec=totalDec+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals(CashType.AUTRES_VERSEMENTS.name()) && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtAuv=mtAuv+ (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalEnc=totalEnc+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }else if(mvtCash.getType().equals(CashType.AUTRES_RETRAITS.name()) && mvtCash.getUserCreated().equals(userDetails.getUsername())){
                mtAud=mtAud+ (int)(mvtCash.getAmount()+mvtCash.getFee());
                totalDec=totalDec+(int)(mvtCash.getAmount()+mvtCash.getFee());
            }
        }
        soldeFin=soldeInit+totalEnc-totalDec;
        soldePhysique=crCashDto.getTotalCash();
        ecart=crCashDto.getDifference();
        model.addAttribute("soldeInit",soldeInit);
        model.addAttribute("agency",agency);
        model.addAttribute("dte",dte);
        model.addAttribute("soldeFin",soldeFin);
        model.addAttribute("mtAchat",mtAchat);
        model.addAttribute("mtVentes",mtVentes);
        model.addAttribute("mtDepenses",mtDepenses);
        model.addAttribute("mtMADEnc",mtMADEnc);
        model.addAttribute("mtMADDec",mtMADDec);
        model.addAttribute("mtAuv",mtAuv);
        model.addAttribute("mtAud",mtAud);
        model.addAttribute("reglement",reglement);
        model.addAttribute("totalEnc",totalEnc);
        model.addAttribute("totalDec",totalDec);
        model.addAttribute("soldePhysique",soldePhysique);
        model.addAttribute("ecart",ecart);
        model.addAttribute("responsable",responsable);

        return "cr_casher";
    }


    @GetMapping("/export-cash-excel-by-date-present")
    public void exportPurchaseToExcelByDate(HttpServletResponse response) throws IOException {
        List<MvtCash> mvtCashes = mvtCashRepository.findAll().stream().filter(mvtCash->mvtCash.getDateMvtCash().equals(LocalDate.now())).toList();
        mvtCashServices.exportMvtCash(mvtCashes, response);
    }


    @PostMapping("/export-cash-excel-date")
    public void exportPurchaseToExcel(HttpServletResponse response, @RequestParam LocalDate startDate,@RequestParam LocalDate endDate) throws IOException {
        List<MvtCash> mvtCashes = mvtCashRepository.findByDateMvtCashBetween(startDate,endDate);
        mvtCashServices.exportMvtCash(mvtCashes, response);
    }

    @GetMapping("/state-cash")
    public String viewStatMvtCash(Model model,@AuthenticationPrincipal UserDetails userDetails){
        String nomUtil=userDetails.getUsername();
        model.addAttribute("nomUtil",nomUtil);

        Users users=usersRepository.findByUsername(nomUtil).orElse(null);
        assert users != null;
        StringBuilder role = null;
        for(Role existingRole:users.getRole()){
            role= new StringBuilder(existingRole.name());
        }
        model.addAttribute("role",role);
        model.addAttribute("nomUtil",nomUtil);Users existingUser=usersRepository.findByUsername(nomUtil).orElse(null);

        String cashier;
        assert existingUser != null;
        if(existingUser.getCash()==null){
            Cash cash=null;
            cashier="pas de caisse";
            model.addAttribute("cashier",cashier);
            model.addAttribute("cash",cash);
        }else{
            cashier=existingUser.getCash();
            Cash cash=cashServices.getByCash(cashier);
            model.addAttribute("cash",cash);
            model.addAttribute("cashier",cashier);
        }

        int mt_cash=0;

        Cash cash=cashRepository.findByRefCash(users.getCash()).orElse(null);
        if(cash!=null){
            mt_cash=(int)cash.getBalance();
        }
        model.addAttribute("mt_cash",mt_cash);

      return "state_cash";
    }


}
