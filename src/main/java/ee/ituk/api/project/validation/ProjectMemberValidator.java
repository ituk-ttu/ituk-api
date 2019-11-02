package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.ProjectMember;
import ee.ituk.api.project.validation.rules.member.HasUserXorName;

import static java.util.Collections.singletonList;

public class ProjectMemberValidator extends Validator {

    public ValidationResult validateOnCreate(ProjectMember projectMember) {
        return applyRules(projectMember, singletonList(
                new HasUserXorName()
        ));
    }
}
