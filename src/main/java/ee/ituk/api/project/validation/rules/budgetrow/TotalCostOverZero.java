package ee.ituk.api.project.validation.rules.budgetrow;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectBudgetRow;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_BUDGET_ROW_VALUES_NOT_VALID;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class TotalCostOverZero implements ValidationRule<ProjectBudgetRow> {
    @Override
    public List<ErrorMessage> apply(ProjectBudgetRow object) {
        // neither values can be negative, total cost must be over zero
        if (object.getFacultyCost().signum() != -1 && object.getItukCost().signum() != -1
                && object.getItukCost().add(object.getFacultyCost()).signum() == 1) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PROJECT_BUDGET_ROW_VALUES_NOT_VALID).build());
    }
}
