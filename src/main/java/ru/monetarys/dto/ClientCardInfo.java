package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientCardInfo {

    @JsonProperty(value = "accId", required = true)
    private String accountId;



    @JsonProperty(value = "cardProd")
    private String cardProd;

    @JsonProperty(value = "pan")
    private String pan;

}
