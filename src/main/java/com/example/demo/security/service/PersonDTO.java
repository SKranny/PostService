package com.example.demo.security.service;

import com.example.demo.security.service.location.City;
import com.example.demo.security.service.location.Country;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
    @NotNull
    @JsonProperty(required = true)
    private Long id;

    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @NotNull
    @JsonProperty(value = "last_name", required = true)
    private String lastName;

    @JsonProperty("reg_date")
    private Long regDate;

    @JsonProperty("birth_date")
    private Long birthDate;

    @NotNull
    @JsonProperty(required = true)
    private String email;

    private String phone;

    private String photo;

    private String about;

    private City city;

    private Country country;

    @JsonProperty("messages_permission")
    private String messagePermission;

    @JsonProperty("last_online_time")
    private Long lastOnlineTime;

    @NotNull
    @JsonProperty(value = "is_blocked", required = true)
    private Boolean isBlocked;

    @NotNull
    @JsonProperty(value = "type", required = true)
    private List<Role> roles;

    @NotNull
    @JsonProperty(required = true)
    private String password;
}
