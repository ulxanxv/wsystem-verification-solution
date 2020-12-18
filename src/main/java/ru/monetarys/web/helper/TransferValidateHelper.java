package ru.monetarys.web.helper;

import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferValidateException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class TransferValidateHelper {

    private final Set<TransferErrorCode> errorCodes;

    {
        errorCodes = new HashSet<>();
    }

    public <T> TransferValidateHelper validate(T value, Predicate<T> predicate, TransferErrorCode errorCode) {
        if (predicate.test(value)) {
            errorCodes.add(errorCode);
        }
        return this;
    }

    public void throwIfNotValid() {
        if (!errorCodes.isEmpty()) {
            TransferValidateException exception = new TransferValidateException();
            errorCodes.forEach(exception::addCode);
            throw exception;
        }
    }

    public static TransferValidateHelper newInstance() {
        return new TransferValidateHelper();
    }

}
