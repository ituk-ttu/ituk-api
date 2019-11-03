package ee.ituk.api.project.validation.rules.member;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectMember;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_MEMBER_NOT_VALID;

public class HasUserXorName implements ValidationRule<ProjectMember> {

    @Override
    public List<ValidationError> apply(ProjectMember object) {
        // projectMember must have either an User or a name, but not both
        if (isNotBlank(object.getName()) ^ object.getUser() != null) {
            return emptyList();
        }
        return singletonList(ValidationError.builder().code(PROJECT_MEMBER_NOT_VALID).build());
    }
}
