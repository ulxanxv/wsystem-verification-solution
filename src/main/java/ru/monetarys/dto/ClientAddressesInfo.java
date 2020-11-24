package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientAddressesInfo {

    @JsonProperty(value = "registration")
    private ClientRegAddress registration;

}
