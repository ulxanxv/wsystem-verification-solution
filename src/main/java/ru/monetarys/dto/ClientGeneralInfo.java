package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClientGeneralInfo {

    @JsonProperty(value = "guid", required = true)
    private String guid;

    private ClientPersonalInfo          personalInfo;
    private List<ClientAccountInfo>     accountList;
    private ClientContactsInfo          contacts;

}
