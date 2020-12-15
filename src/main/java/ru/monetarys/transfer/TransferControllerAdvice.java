package ru.monetarys.transfer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferValidateException;

@RestControllerAdvice
public class TransferControllerAdvice {

    @ExceptionHandler(TransferValidateException.class)
    public ResponseEntity<?> handleTransferValidateException(TransferValidateException exception) {
        return ResponseEntity.badRequest().body(exception.getErrorCodes());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleTransferClientException(ClientException exception) {
        return ResponseEntity.badRequest().body(exception);
    }

}
