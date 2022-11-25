package com.example.demo.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PersonDetails implements UserDetails {
    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private List<Role> roles;

    @JsonIgnore
    private String password;

    @Builder.Default
    private boolean blocked = true;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    @Builder.Default
    private boolean accountNonLocked = true;

    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return String.format("%s %s", lastName, firstName);
    }

    public static PersonDetails build(PersonDTO person) {
        return PersonDetails.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .blocked(person.getIsBlocked())
                .password(person.getPassword())
                .authorities(person.getRoles().stream()
                        .map(Role::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
