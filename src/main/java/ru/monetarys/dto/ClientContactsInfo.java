package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientContactsInfo {

    @JsonProperty(value = "phoneNumber", required = true)
    private String phoneNumber;

}
