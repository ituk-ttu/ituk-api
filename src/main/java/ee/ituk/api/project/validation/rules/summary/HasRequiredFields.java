package ee.ituk.api.project.validation.rules.summary;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectSummary;

import java.util.Collections;
import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_SUMMARY_MISSING_REQUIRED_FIELDS;
import static java.util.Collections.emptyList;

public class HasRequiredFields implements ValidationRule<ProjectSummary> {
    @Override
    public List<ValidationError> apply(ProjectSummary object) {
        if (object.getCreatedAt() == null
                || object.getCreatedBy() == null
                || object.getProject() == null) {
            return Collections.singletonList(ValidationError.builder().code(PROJECT_SUMMARY_MISSING_REQUIRED_FIELDS).build());
        }
        return emptyList();
    }
}
