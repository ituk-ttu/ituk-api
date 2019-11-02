package ee.ituk.api.project.validation.rules.budgetrow;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectBudgetRow;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_BUDGET_ROW_MISSING_REQUIRED_FIELDS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasRequiredFields implements ValidationRule<ProjectBudgetRow> {
    @Override
    public List<ValidationError> apply(ProjectBudgetRow object) {
        if (object.getProjectBudget() == null || object.getDescription() == null) {
            return singletonList(ValidationError.builder().code(PROJECT_BUDGET_ROW_MISSING_REQUIRED_FIELDS).build());
        }
        return emptyList();
    }
}
