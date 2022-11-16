package com.example.demo.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "id",
        "first_name",
        "last_name",
        "reg_date",
        "birth_date",
        "email",
        "phone",
        "photo",
        "about",
        "city",
        "country",
        "messages_permission",
        "last_online_time",
        "is_blocked",
        "token"
})
public class PersonData {
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("reg_date")
    private Long regDate;

    @JsonProperty("birth_date")
    private Long birthDate;

    private String email;

    private String phone;

    private String photo;

    private String about;

    private City city;

    private Country country;

    @JsonProperty("messages_permission")
    private MessagePermission messagePermission;

    @JsonProperty("last_online_time")
    private Long lastOnlineTime;

    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    private String token;
}
