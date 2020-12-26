package ru.monetarys.ro.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDefinitionRo {

    @JsonProperty(value = "attrName")
    private String attributeName;
    private String code;
    private String message;

}
