package ru.monetarys.rs.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientPersonalInfoRs {

    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    @JsonProperty(value = "firstName", required = true)
    private String firstName;

    private String citizenshipCountryCode;
    private String middleName;

}
