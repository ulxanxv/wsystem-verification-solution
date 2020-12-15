package ru.monetarys.exceptions;

import lombok.Data;

@Data
public class ClientException extends RuntimeException {

    private ClientErrorCode clientErrorCode;
    private String guid;

    public ClientException(ClientErrorCode clientErrorCode, String guid) {
        this.setClientErrorCode(clientErrorCode);
        this.setGuid(guid);
    }

}
