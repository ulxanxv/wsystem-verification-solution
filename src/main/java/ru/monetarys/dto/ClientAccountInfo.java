package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientAccountInfo {

    @JsonProperty(value = "accStatus", required = true)
    private String accountStatus;

    @JsonProperty(value = "accType", required = true)
    private String accountType;

    @JsonProperty(value = "bankName", required = true)
    private String bankName;

    @JsonProperty(value = "balance", required = true)
    private BigDecimal balance;

    @JsonProperty(value = "accId", required = true)
    private String accountId;

    @JsonProperty(value = "cur", required = true)
    private String cur;

    @JsonProperty(value = "bic", required = true)
    private String bic;

}
