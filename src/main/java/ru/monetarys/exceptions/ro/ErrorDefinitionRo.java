package ru.monetarys.exceptions.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDefinitionRo {

    @JsonProperty(value = "attributeName")
    private String attrName;
    private String code;
    private String message;

}
