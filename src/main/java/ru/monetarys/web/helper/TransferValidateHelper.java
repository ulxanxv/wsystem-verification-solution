package ru.monetarys.web.helper;

import lombok.extern.slf4j.Slf4j;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferValidateException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
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

    public <Y extends TransferValidateException> void throwIfNotValid(Class<Y> exceptionClass) {
        if (!errorCodes.isEmpty()) {

            Y exception;

            try {
                exception = exceptionClass.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                log.error(e.toString());
                return;
            }

            errorCodes.forEach(exception::addCode);
            throw exception;
        }
    }

    public static TransferValidateHelper newInstance() {
        return new TransferValidateHelper();
    }

}
