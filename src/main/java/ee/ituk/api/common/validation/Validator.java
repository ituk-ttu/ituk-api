package ee.ituk.api.common.validation;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class Validator {

    protected ValidationResult applyRule(BasicValidationRule validationRule) {
        List<ValidationError> messages = validationRule.apply();
        return new ValidationResult(messages);
    }

    protected  <T> ValidationResult applyRule(T object, ValidationRule<T> validationRule) {
        List<ValidationError> messages = validationRule.apply(object);
        return new ValidationResult(messages);
    }

    protected ValidationResult applyRules(List<BasicValidationRule> validationRules) {
        List<ValidationError> messages = validationRules.stream()
                .map(BasicValidationRule::apply)
                .flatMap(Collection::stream)
                .collect(toList());
        return new ValidationResult(messages);
    }

    protected  <T> ValidationResult applyRules(T object, List<ValidationRule<T>> validationRules) {
        List<ValidationError> messages = validationRules.stream()
                .map(rule -> rule.apply(object))
                .flatMap(Collection::stream)
                .collect(toList());
        return new ValidationResult(messages);
    }
}
