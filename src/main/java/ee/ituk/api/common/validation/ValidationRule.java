package ee.ituk.api.common.validation;

import ee.ituk.api.common.exception.ErrorMessage;

import java.util.List;

@FunctionalInterface
public interface ValidationRule<T> {

    List<ErrorMessage> apply(T object);

}
