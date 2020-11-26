package ru.monetarys.exceptions;

import lombok.Data;

@Data
public class AccountNotFound extends RuntimeException {

    private ErrorCode errorCode;

    public AccountNotFound(ErrorCode code, String message, String guid) {
        super("Error: " + code + " | Message: " + message + " | GUID: " + guid);
        this.setErrorCode(code);
    }

    public enum ErrorCode {
        ACCOUNT_NOT_FOUND
    }

}
