package ru.monetarys.web.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.util.ReflectionUtils;
import ru.monetarys.exceptions.TransferErrorCode;
import ru.monetarys.exceptions.TransferValidateException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
public class TransferValidateHelper {

    private final Set<TransferErrorCode> errorCodes = new HashSet<>();;

    public <T> TransferValidateHelper validate(T value, Predicate<T> predicate, TransferErrorCode errorCode) {
        if (predicate.test(value)) {
            errorCodes.add(errorCode);
        }
        return this;
    }

    public <Y extends TransferValidateException> void throwIfNotValid(Class<Y> exceptionClass) {
        if (!errorCodes.isEmpty()) {
            Y exception = ReflectionUtils.createInstanceIfPresent(exceptionClass.getName(), null);
            errorCodes.forEach(exception::addCode);
            throw exception;
        }
    }

    public static TransferValidateHelper newInstance() {
        return new TransferValidateHelper();
    }

}
