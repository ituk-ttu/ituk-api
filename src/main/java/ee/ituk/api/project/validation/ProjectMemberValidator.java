package ee.ituk.api.project.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.project.domain.ProjectMember;
import ee.ituk.api.project.validation.rules.HasUserXorName;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;

public class ProjectMemberValidator extends Validator {

    public ValidationResult validateOnCreate(ProjectMember projectMember) {
        var validationResult = applyRules(projectMember, singletonList(
                new HasUserXorName()
        ));
        return validationResult;
    }
}
