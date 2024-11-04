package com.compass.Desafio_02.jwt;

import com.compass.Desafio_02.entities.Person;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private Person usuario;

    public JwtUserDetails(Person usuario) {
        super(usuario.getEmail(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    public Long getId() {
        return this.usuario.getId();
    }

    public String getEmail() {
        return this.usuario.getEmail();
    }

    public String getRole() {
        return this.usuario.getRole().name();
    }
}
