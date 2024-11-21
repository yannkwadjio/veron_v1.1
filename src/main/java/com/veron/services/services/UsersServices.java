package com.veron.services.services;


import com.veron.dto.UserDto;
import com.veron.entity.*;
import com.veron.enums.Role;
import com.veron.repository.*;
import com.veron.services.interfaces.UsersInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersServices implements UsersInterfaces {
    private final UsersRepository usersRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Map<String, String> createUser(UserDto userDto) {
        Map<String,String> response=new HashMap<>();
        String regex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+";
       Optional<Users> existingUser=usersRepository.findByUsername(userDto.getUsername());
        if(existingUser.isEmpty()){
           if(!userDto.getUsername().isEmpty()){
               if(!userDto.getFullName().isEmpty()){
                   if(!userDto.getPhoneNumber().isEmpty()){
                       if(!userDto.getPassword().isEmpty()) {
                           if (userDto.getPassword().length() > 7) {
if(userDto.getPassword().matches(".*"+regex+".*")){
    if(userDto.getPassword().equals(userDto.getConfirmationPassword())){
        if(!userDto.getRole().isEmpty()){
           if(!userDto.getEnterprise().isEmpty()) {
                    if(!userDto.getAgency().isEmpty()) {
                               Users users=new Users();
                        users.setUsername(userDto.getUsername());
                        users.setFullName(userDto.getFullName());
                        List<Users> listUser=usersRepository.findAll().stream()
                                        .filter(userPhone->userPhone.getPhoneNumber().equals(userDto.getPhoneNumber()))
                                                .toList();
                        if(listUser.isEmpty()){
                            users.setPhoneNumber(userDto.getPhoneNumber());
                            users.setPassword(passwordEncoder.encode(userDto.getPassword()));
                            users.setConfirmationPassword(passwordEncoder.encode(userDto.getConfirmationPassword()));
                            users.setNumberConnexion(0);
                            users.setEnabled(false);
                            users.setRole(Set.of(Role.valueOf(userDto.getRole())));
                            Enterprise enterprise=enterpriseRepository.findByName(userDto.getEnterprise().toUpperCase()).orElse(null);
                            assert enterprise != null;
                            users.setCountry(enterprise.getCountry());
                            users.setEnterprise(userDto.getEnterprise());
                            users.setAgency(userDto.getAgency());
                            users.setUserCreated("admin-veron@gmail.com");
                            usersRepository.save(users);
                            response.put("message","Employé créé avec succès");
                        }else{
                            response.put("message","Ce nº de téléphone est déjà utilisé par un autre utilisateur");
                        }

                    } else{
                        response.put("message","Agence non sélectionnée");
                    }

           }else{
               response.put("message","Entreprise non sélectionnée");
           }
        }else{
            response.put("message","Rôle de l'employé non sélectionné");
        }
    }else{
        response.put("message","les mots de passe ne sont pas identiques");
    }
}else{
    response.put("message","Le mot de passe doit contenir au moins un caractère spécial");
}
                           } else {
                               response.put("message", "Le mot de passe doit contenir au moins 8 caractères");
                           }
                       }else{
                           response.put("message","Mot de passe non renseigné");
                       }
                   }else{
                       response.put("message","Nº de téléphone non renseigné");
                   }
               }else{
                   response.put("message","Nom complet de l'employé non renseigné");
               }
           }else{
               response.put("message","adresse e-mail non renseigné");
           }
        }else{
            response.put("message","Cet adresse e-mail est utilisé par un autre utilisateur");
        }
        return response;
    }

    @Override
    public Users getByUserName(String username) {
        return usersRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Map<String, String> updateByUsername(String username,String fullName,String phoneNumber,String role,boolean isEnabled) {
       Map<String,String> response=new HashMap<>();
        Users users=usersRepository.findByUsername(username).orElse(null);
       if(users==null){
          response.put("message","Utilisateur introuvable");
       }else{
           users.setFullName(fullName.toUpperCase());
           users.setPhoneNumber(phoneNumber);
           List<Users> userFound=usersRepository.findAll().stream()
                             .filter(users1 -> users1.getPhoneNumber().equals(phoneNumber))
                                   .toList();
           if(userFound.get(0).getUsername().equals(username) && userFound.size()==1){
               users.setEnabled(isEnabled);
               users.setRole(Set.of(Role.valueOf(role)));
               usersRepository.save(users);
               response.put("message","Mise à jour effectué avec succès");
           } else{
               response.put("message","Ce numéro de téléphone est utilisé par un autre utilisateur");
           }

       }
        return response;
    }

    @Override
    public Map<String, String> updatePassword(String username, String password, String repassword) {
       Map<String,String> response=new HashMap<>();
       Users users=usersRepository.findByUsername(username).orElse(null);
       if(users!=null){
           if(!username.isEmpty()){
              if(!password.isEmpty()){
                  String regex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+";
                  if(password.matches(".*"+regex+".*")){
                      if(password.length()> 7){
                          if(password.equals(repassword)){
                              users.setPassword(passwordEncoder.encode(password));
                              usersRepository.save(users);
                              response.put("message","Mot de passe modifié avec succès");
                          }else{
                              response.put("message","Le mot de passe n'est pas identique");
                          }

                      } else{
                          response.put("message","Le mot de passe doit contenir au moins 8 caractères");
                      }
                  } else{
                      response.put("message","Le mot de passe doit contenir au moins un caractère spécial");
                  }
              }else{
                  response.put("message","Mot de passe non renseigné");
              }
           }else{
               response.put("message","E-mail non renseigné");
           }
       }else{
           response.put("message","Utilisateur introuvable");
       }
        return response;
    }

    @Override
    public Users getBycash(String cash) {
        return usersRepository.findByCash(cash).orElse(null);
    }

    @Override
    public Map<String, String> reinitialzePassword(String username) {
Map<String,String> response=new HashMap<>();
if(!username.isEmpty()){
    Users users=usersRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
String password=UUID.randomUUID().toString();
    users.setCodeReset(password);
    users.setPassword(passwordEncoder.encode(password));
    usersRepository.save(users);
    response.put("message","Mot de passe réinitialisé:"+password);
}else{
    response.put("message","Nom utilisateur non sélectionné");
}
        return response;
    }


}
