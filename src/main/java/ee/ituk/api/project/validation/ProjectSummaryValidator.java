package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.ProjectSummary;
import ee.ituk.api.project.validation.rules.summary.HasRequiredFields;

import static java.util.Collections.singletonList;

public class ProjectSummaryValidator extends Validator {

    public ValidationResult validateOnCreate(ProjectSummary projectSummary) {
        return applyRules(projectSummary, singletonList(
                new HasRequiredFields()
        ));
    }
}
