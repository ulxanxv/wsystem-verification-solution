package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class ClientRegAddress {

    @JsonProperty(value = "fullAddress", required = true)
    @JsonPropertyDescription(value = "Регистрационный адрес клиента")
    private String fullAddress;

}
