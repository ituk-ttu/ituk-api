package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.validation.rules.project.HasRequiredFields;

import static java.util.Collections.singletonList;

public class ProjectValidator extends Validator {

    public ValidationResult validateOnCreate(Project project) {
        return applyRules(project, singletonList(
                new HasRequiredFields()
        ));
    }
}
