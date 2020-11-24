package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClientDepositeInfo {

    @JsonProperty(value = "depStatus", required = true)
    private String depStatus;

    @JsonProperty(value = "bankName", required = true)
    private String bankName;

    @JsonProperty(value = "depStart", required = true)
    private String depStart;

    @JsonProperty(value = "depType", required = true)
    private String depType;

    @JsonProperty(value = "balance", required = true)
    private BigDecimal balance;

    @JsonProperty(value = "accNum", required = true)
    private String accountNumber;

    @JsonProperty(value = "accId", required = true)
    private String accountId;

    @JsonProperty(value = "cur", required = true)
    private String cur;

    @JsonProperty(value = "bic", required = true)
    private String bic;



    @JsonProperty(value = "limitList")
    private List<ClientLimit> limitList;

    @JsonProperty(value = "depClose")
    private String depClose;


}
