package ru.monetarys.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferIntegrationValidateException;
import ru.monetarys.exceptions.TransferValidateException;
import ru.monetarys.exceptions.domain.ErrorDefinition;
import ru.monetarys.exceptions.ro.ErrorDefinitionRo;
import ru.monetarys.exceptions.ro.ErrorRo;
import ru.monetarys.integration.ApplicationProperties;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TransferControllerAdvice {

    private final ApplicationProperties applicationProperties;

    @ExceptionHandler(TransferValidateException.class)
    public ResponseEntity<?> handleTransferValidateException(TransferValidateException exception) {
        log.error(exception.toString());

        ErrorRo errorRo = new ErrorRo("validation.Error");
        errorRo.setAttributeErrors(getErrorDefinitionRo(exception));

        return ResponseEntity.badRequest().body(errorRo);
    }

    @ExceptionHandler(TransferIntegrationValidateException.class)
    public ResponseEntity<?> handleTransferIntegrationValidateException(TransferIntegrationValidateException exception) {
        log.error(exception.toString());

        ErrorRo errorRo = new ErrorRo("business.Error");
        errorRo.setAttributeErrors(getErrorDefinitionRo(exception));

        return ResponseEntity.badRequest().body(errorRo);
    }

    private List<ErrorDefinitionRo> getErrorDefinitionRo(TransferValidateException exception) {
        List<ErrorDefinitionRo> definitionRo = new ArrayList<>();

        for (TransferErrorCode code : exception.getErrorCodes()) {
            ErrorDefinition definition = applicationProperties.getTransferMoney().getValidationErrors().get(code.name());

            definitionRo.add(ErrorDefinitionRo.builder()
                    .code(definition.getCode())
                    .message(definition.getMsg())
                    .attrName(definition.getAttributeName())
                    .build()
            );
        }

        return definitionRo;
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleTransferClientException(ClientException exception) {
        log.error(exception.toString());

        ErrorRo errorRo = new ErrorRo("integration.Error");
        ErrorDefinition definition = applicationProperties
                .getClientProfileService()
                .getErrorMappings()
                .get(exception.getClientErrorCode().name());

        errorRo.getAttributeErrors().add(ErrorDefinitionRo.builder()
                .attrName(definition.getAttributeName())
                .message(definition.getMsg())
                .code(definition.getCode())
                .build()
        );

        return ResponseEntity.badRequest().body(errorRo);
    }

}
