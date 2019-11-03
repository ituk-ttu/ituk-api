package ee.ituk.api.resources.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.resources.domain.Resource;
import ee.ituk.api.resources.validation.rules.HasAuthor;
import ee.ituk.api.resources.validation.rules.HasUrl;
import ee.ituk.api.resources.validation.rules.ResourceHasName;

import java.util.Arrays;

public class ResourceValidator extends Validator {

    public ValidationResult validateData(Resource resource) {
        ValidationResult validationResult = new ValidationResult();

        validationResult.add(applyRules(resource, Arrays.asList(
                new ResourceHasName(),
                new HasUrl(),
                new HasAuthor()
        )));


        return validationResult;
    }
}
