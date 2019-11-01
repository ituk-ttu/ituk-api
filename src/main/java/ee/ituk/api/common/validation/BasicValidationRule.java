package ee.ituk.api.common.validation;

import java.util.List;

@FunctionalInterface
public interface BasicValidationRule {

  List<ValidationError> apply();

}
