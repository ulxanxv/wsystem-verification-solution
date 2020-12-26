package ru.monetarys.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.monetarys.exceptions.ClientException;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferIntegrationValidateException;
import ru.monetarys.exceptions.TransferValidateException;
import ru.monetarys.domain.exception.ErrorDefinition;
import ru.monetarys.ro.exception.ErrorDefinitionRo;
import ru.monetarys.ro.exception.ErrorRo;
import ru.monetarys.config.ApplicationProperties;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TransferControllerAdvice {

    private final ApplicationProperties applicationProperties;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransferValidateException.class)
    public ErrorRo handleTransferValidateException(TransferValidateException exception) {
        logException(exception);

        ErrorRo errorRo = new ErrorRo("validation.Error");
        errorRo.setAttributeErrors(getErrorDefinitionRo(exception));

        return errorRo;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransferIntegrationValidateException.class)
    public ErrorRo handleTransferIntegrationValidateException(TransferIntegrationValidateException exception) {
        logException(exception);

        ErrorRo errorRo = new ErrorRo("business.Error");
        errorRo.setAttributeErrors(getErrorDefinitionRo(exception));

        return errorRo;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientException.class)
    public ErrorRo handleTransferClientException(ClientException exception) {
        logException(exception);

        ErrorRo errorRo = new ErrorRo("integration.Error");
        ErrorDefinition definition = applicationProperties
                .getClientProfileService()
                .getErrorMappings()
                .get(exception.getClientErrorCode().name());

        errorRo.getAttributeErrors().add(ErrorDefinitionRo.builder()
                .attributeName(definition.getAttributeName())
                .message(definition.getMsg())
                .code(definition.getCode())
                .build()
        );

        return errorRo;
    }

    private List<ErrorDefinitionRo> getErrorDefinitionRo(TransferValidateException exception) {
        List<ErrorDefinitionRo> definitionRo = new ArrayList<>();

        for (TransferErrorCode code : exception.getErrorCodes()) {
            ErrorDefinition definition = applicationProperties.getTransferMoney().getValidationErrors().get(code.name());

            definitionRo.add(ErrorDefinitionRo.builder()
                    .code(definition.getCode())
                    .message(definition.getMsg())
                    .attributeName(definition.getAttributeName())
                    .build()
            );
        }

        return definitionRo;
    }

    private void logException(RuntimeException e) {
        log.error("Error '{}' occured", e.toString(), e);
    }

}
