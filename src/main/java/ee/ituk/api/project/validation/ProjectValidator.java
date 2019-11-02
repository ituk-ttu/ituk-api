package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.validation.rules.budgetrow.TotalCostOverZero;
import ee.ituk.api.project.validation.rules.member.HasUserXorName;

import static java.util.Collections.singletonList;

public class ProjectValidator extends Validator {

    public ValidationResult validateData(Project project) {

        ValidationResult validationResult = new ValidationResult();

        // validate members
        project.getMembers().forEach(member -> validationResult.add(
                applyRules(member, singletonList(new HasUserXorName())))
        );

        // validate budgetRows
        project.getBudget().getRows().forEach(row -> validationResult.add(
                applyRules(row, singletonList(new TotalCostOverZero())))
        );

        return validationResult;
    }
}
