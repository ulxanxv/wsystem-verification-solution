package ru.monetarys.exceptions;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TransferValidateException extends RuntimeException {

    private Set<TransferErrorCode> errorCodes = new HashSet<>();

    public void addCode(TransferErrorCode errorCode) {
        errorCodes.add(errorCode);
    }

}
