package ru.monetarys.rs.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientAccountInfoRs {

    @JsonProperty(value = "accStatus", required = true)
    private String accountStatus;

    @JsonProperty(value = "accType", required = true)
    private String accountType;

    @JsonProperty(value = "accId", required = true)
    private String accountId;

    @JsonProperty(value = "cur", required = true)
    private String currency;

    @JsonProperty(required = true)
    private String bankName;

    @JsonProperty(required = true)
    private BigDecimal balance;

    @JsonProperty( required = true)
    private String bic;

}
