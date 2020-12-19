package ru.monetarys.exceptions.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class ErrorRo {

    private String errorCode;

    @JsonProperty(value = "attrErrors")
    private List<ErrorDefinitionRo> attributeErrors = new ArrayList<>();

    public ErrorRo(String errorCode) {
        this.errorCode = errorCode;
    }

}
