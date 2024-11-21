package com.veron.entity;

import com.veron.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String confirmationPassword;
    private int numberConnexion;
    private boolean isEnabled;
    private Set<Role> role;
    private String userCreated;
    private String country;
    private String enterprise;
    private String agency;
    private String cash;
    private String Store;
    private String codeReset;

}
