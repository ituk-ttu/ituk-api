package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectSummary;
import ee.ituk.api.project.validation.rules.HasMatchingIdOnUpdate;
import ee.ituk.api.project.validation.rules.budgetrow.TotalCostOverZero;
import ee.ituk.api.project.validation.rules.member.HasUserXorName;
import ee.ituk.api.project.validation.rules.member.NotProjectLead;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ProjectValidator extends Validator {

    public ValidationResult validateSummaryData(ProjectSummary projectSummary) {

        ValidationResult validationResult = new ValidationResult();

        // validate members
        projectSummary.getMembers().forEach(member -> validationResult.add(
                applyRules(member, asList(new HasUserXorName(), new NotProjectLead())))
        );

        // validate budgetRows
        projectSummary.getBudgetRows().forEach(row -> validationResult.add(
                applyRules(row, singletonList(new TotalCostOverZero())))
        );

        return validationResult;
    }

    public ValidationResult validateUpdate(Project project, Long id) {

        ValidationResult validationResult = new ValidationResult();

        validationResult.add(applyRules(project, singletonList(new HasMatchingIdOnUpdate(id))));

        return validationResult;
    }
}
