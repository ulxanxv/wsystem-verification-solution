package ru.monetarys.ro.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransferHistoryFilterRequestRo {

    private Period period;
    private Amount amount;
    private String payerAccount;
    private List<String> statuses;

    @Data
    public static class Period {

        @JsonProperty(required = true)
        private LocalDateTime from;

        @JsonProperty(required = true)
        private LocalDateTime to;
    }

    @Data
    public static class Amount {

        @JsonProperty(required = true)
        private BigDecimal from;

        @JsonProperty(required = true)
        private BigDecimal to;
    }

}