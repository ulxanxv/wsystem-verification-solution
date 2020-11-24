package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientIdentityDoc {

    @JsonProperty(value = "typeName", required = true)
    private String typeName;

    @JsonProperty(value = "typeId", required = true)
    private int typeId;

    @JsonProperty(value = "number", required = true)
    private String number;

    @JsonProperty(value = "endDate")
    private String endDate;

    @JsonProperty(value = "serial")
    private String serial;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "whom")
    private String whom;

    @JsonProperty(value = "when")
    private String when;

}
