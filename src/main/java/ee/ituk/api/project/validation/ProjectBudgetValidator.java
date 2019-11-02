package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.ProjectBudget;
import ee.ituk.api.project.validation.rules.budget.HasRequiredFields;
import ee.ituk.api.project.validation.rules.budgetrow.TotalCostOverZero;

import java.util.Arrays;

import static java.util.Collections.singletonList;

public class ProjectBudgetValidator extends Validator {

    public ValidationResult validateOnCreate(ProjectBudget projectBudget) {
        ValidationResult validationResult = applyRules(projectBudget, singletonList(
                new HasRequiredFields()
        ));

        projectBudget.getRows().forEach(row -> validationResult.add(applyRules(row, Arrays.asList(
                new ee.ituk.api.project.validation.rules.budgetrow.HasRequiredFields(),
                new TotalCostOverZero()
        ))));

        return validationResult;
    }
}
