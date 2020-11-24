package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientPersonalInfo {

    @JsonProperty(value = "taxpayerNumber", required = true)
    private String taxpayerNumber;

    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    @JsonProperty(value = "firstName", required = true)
    private String firstName;

    @JsonProperty(value = "citizenshipCountryCode")
    private String citizenshipCountryCode;

    @JsonProperty(value = "middleName")
    private String middleName;

    @JsonProperty(value = "birthday")
    private String birthday;

}
