package ru.monetarys.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferValidateException;

@Slf4j
@RestControllerAdvice
public class TransferControllerAdvice {

    @ExceptionHandler(TransferValidateException.class)
    public ResponseEntity<?> handleTransferValidateException(TransferValidateException exception) {
        log.error(exception.toString());
        return ResponseEntity.badRequest().body(exception.getErrorCodes());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleTransferClientException(ClientException exception) {
        log.error(exception.toString());
        return ResponseEntity.badRequest().body(exception);
    }

}
