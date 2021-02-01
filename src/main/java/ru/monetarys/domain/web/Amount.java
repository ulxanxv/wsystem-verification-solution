package ru.monetarys.domain.web;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {

    private BigDecimal from;
    private BigDecimal to;

}
