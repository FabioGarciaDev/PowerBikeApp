package com.PowerBike.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank
    private String name;

    private String lastname;

    private String secondLastname;

    @NotBlank
    @Size(max = 30)
    private String documentType;

    @NotBlank
    @Size(max = 30)
    private String documentNumber;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    private String password;

    private String recoveryPassword = null;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(max = 80)
    private String phoneNumber;

    @NotBlank
    @Size(max = 80)
    private String address;

    @NotBlank
    private String city;

    private LocalDate dateOfBirth;
    /*//Con este metodo debo parsear la fecha de nacimiento ingresado por el usuario
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);*/

    private String idCard;

    private LocalDateTime updateDate;

    private boolean activeUser;

    private ERole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
