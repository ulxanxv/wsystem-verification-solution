package ru.monetarys.ro.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ErrorRo {

    private String errorCode;

    @JsonProperty(value = "attrErrors")
    private List<ErrorDefinitionRo> attributeErrors;

    public ErrorRo(String errorCode) {
        this.errorCode = errorCode;
    }

}
