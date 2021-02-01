package ru.monetarys.domain.web;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Period {

    private LocalDateTime from;
    private LocalDateTime to;

}
