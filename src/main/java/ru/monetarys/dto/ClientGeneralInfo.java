package ru.monetarys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClientGeneralInfo {

    @JsonProperty(value = "guid", required = true)
    private String guid;



    @JsonProperty(value = "personalInfo", required = false)
    private ClientPersonalInfo personalInfo;

    @JsonProperty(value = "identityDoc", required = false)
    private ClientIdentityDoc identityDoc;

    @JsonProperty(value = "accountList", required = false)
    private List<ClientAccountInfo> accountList;

    @JsonProperty(value = "addresses", required = false)
    private ClientAddressesInfo addresses;

    @JsonProperty(value = "contacts", required = false)
    private ClientContactsInfo contacts;

    @JsonProperty(value = "cardList", required = false)
    private List<ClientCardInfo> cardList;

    @JsonProperty(value = "depList", required = false)
    private List<ClientDepositeInfo> depList;

}
