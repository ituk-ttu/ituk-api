package ee.ituk.api.common.validation;

import ee.ituk.api.common.exception.ErrorMessage;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class Validator {

    protected ValidationResult applyRule(BasicValidationRule validationRule) {
        List<ErrorMessage> messages = validationRule.apply();
        return new ValidationResult(messages);
    }

    protected  <T> ValidationResult applyRule(T object, ValidationRule<T> validationRule) {
        List<ErrorMessage> messages = validationRule.apply(object);
        return new ValidationResult(messages);
    }

    protected ValidationResult applyRules(List<BasicValidationRule> validationRules) {
        List<ErrorMessage> messages = validationRules.stream()
                .map(BasicValidationRule::apply)
                .flatMap(Collection::stream)
                .collect(toList());
        return new ValidationResult(messages);
    }

    protected  <T> ValidationResult applyRules(T object, List<ValidationRule<T>> validationRules) {
        List<ErrorMessage> messages = validationRules.stream()
                .map(rule -> rule.apply(object))
                .flatMap(Collection::stream)
                .collect(toList());
        return new ValidationResult(messages);
    }
}
