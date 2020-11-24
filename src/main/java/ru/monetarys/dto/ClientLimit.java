package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientLimit {

    @JsonProperty(value = "periodType", required = true)
    private String periodType;

    @JsonProperty(value = "period", required = true)
    private Integer period;

    @JsonProperty(value = "cur", required = true)
    private String cur;

    @JsonProperty(value = "sum", required = true)
    private String sum;



    @JsonProperty(value = "code")
    private String code;

}
