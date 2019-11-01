package ee.ituk.api.common.validation;

import java.util.List;

@FunctionalInterface
public interface ValidationRule<T> {

  List<ValidationError> apply(T object);

}
