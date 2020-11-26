package ru.monetarys.exceptions;

import lombok.Data;

@Data
public class ClientProfileNotFound extends RuntimeException {

    private ErrorCode errorCode;

    public ClientProfileNotFound(ErrorCode errorCode, String message, String guid) {
        super("Error: " + errorCode + " | Message: " + message + " | GUID: " + guid);
        this.setErrorCode(errorCode);
    }

    public enum ErrorCode {
        PROFILE_NOT_FOUND
    }

}
