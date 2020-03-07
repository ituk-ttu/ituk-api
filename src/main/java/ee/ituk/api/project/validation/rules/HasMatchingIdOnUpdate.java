package ee.ituk.api.project.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.Project;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_ID_MISMATCH;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasMatchingIdOnUpdate implements ValidationRule<Project> {

    private final Long id;

    public HasMatchingIdOnUpdate(Long id) {
        this.id = id;
    }

    @Override
    public List<ErrorMessage> apply(Project object) {
        if (object.getId().equals(id)) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PROJECT_ID_MISMATCH).build());
    }
}