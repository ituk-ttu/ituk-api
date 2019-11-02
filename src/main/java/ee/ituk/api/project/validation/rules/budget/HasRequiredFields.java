package ee.ituk.api.project.validation.rules.budget;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectBudget;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_BUDGET_MISSING_REQUIRED_FIELDS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasRequiredFields implements ValidationRule<ProjectBudget> {
    @Override
    public List<ValidationError> apply(ProjectBudget object) {
        if (object.getProject() == null) {
            return singletonList(ValidationError.builder().code(PROJECT_BUDGET_MISSING_REQUIRED_FIELDS).build());
        }
        return emptyList();
    }
}
