package ee.ituk.api.project.validation.rules.project;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.Project;

import java.util.Collections;
import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_MEMBER_NOT_VALID;
import static java.util.Collections.emptyList;

public class HasRequiredFields implements ValidationRule<Project> {
    @Override
    public List<ValidationError> apply(Project object) {
        if (object.getTitle() == null || object.getDateStart() == null) {
            return Collections.singletonList(ValidationError.builder().code(PROJECT_MEMBER_NOT_VALID).build());
        }
        return emptyList();
    }
}
