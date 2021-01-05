package ru.monetarys.domain.web;

import lombok.Data;

import java.util.List;

@Data
public class TransferHistoryFilterRequest {

    private Period period;
    private Amount amount;
    private String payerAccount;
    private List<String> statuses;

}
