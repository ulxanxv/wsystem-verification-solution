package ru.monetarys.exceptions.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ErrorDefinition {

    private String attributeName;
    private String code;
    private String msg;

}
