package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClientAccountInfo {

    @JsonProperty(value = "accStatus", required = true)
    private String accountStatus;

    @JsonProperty(value = "accType", required = true)
    private String accountType;

    @JsonProperty(value = "accStart", required = true)
    private String accountStart;

    @JsonProperty(value = "bankName", required = true)
    private String bankName;

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

    @JsonProperty(value = "accClose")
    private String accountClose;

}
