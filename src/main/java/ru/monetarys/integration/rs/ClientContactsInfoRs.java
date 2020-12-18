package ru.monetarys.integration.rs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientContactsInfoRs {

    @JsonProperty(value = "phoneNumber", required = true)
    private String phoneNumber;

}
