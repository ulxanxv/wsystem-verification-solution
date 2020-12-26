package ru.monetarys.domain.integration;

import lombok.Data;

@Data
public class ClientPersonalInfo {

    private String lastName;
    private String firstName;
    private String citizenshipCountryCode;
    private String middleName;

}
