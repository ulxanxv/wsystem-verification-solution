package ru.monetarys.rs.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClientGeneralInfoRs {

    @JsonProperty(value = "guid", required = true)
    private String guid;

    private ClientPersonalInfoRs personalInfo;
    private List<ClientAccountInfoRs> accountList;
    private ClientContactsInfoRs contacts;

}
