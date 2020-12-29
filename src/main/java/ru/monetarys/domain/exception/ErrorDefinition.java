package ru.monetarys.domain.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ErrorDefinition {

    private String attributeName;
    private String code;
    private String msg;

}
