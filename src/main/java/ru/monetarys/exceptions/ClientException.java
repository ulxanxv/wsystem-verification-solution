package ru.monetarys.exceptions;

import lombok.Data;

@Data
public class ClientException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
    private String guid;

    public ClientException(ErrorCode errorCode, String message, String guid) {
        this.setErrorCode(errorCode);
        this.setMessage(message);
        this.setGuid(guid);
    }

}
