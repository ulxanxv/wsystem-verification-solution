package ru.monetarys.exceptions;

import lombok.Data;

@Data
public class ClientException extends RuntimeException {

    private ErrorCode errorCode;
    private String guid;

    public ClientException(ErrorCode errorCode, String guid) {
        this.setErrorCode(errorCode);
        this.setGuid(guid);
    }

}
