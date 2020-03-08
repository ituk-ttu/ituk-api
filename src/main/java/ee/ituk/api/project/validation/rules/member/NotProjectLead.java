package ee.ituk.api.project.validation.rules.member;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.project.domain.ProjectMember;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PROJECT_LEAD_MUST_NOT_BE_IN_MEMBERS_LIST;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NotProjectLead implements ValidationRule<ProjectMember> {

    @Override
    public List<ErrorMessage> apply(ProjectMember object) {
        if (!object.getUser().getId().equals(object.getProjectSummary().getProject().getProjectLead().getId())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PROJECT_LEAD_MUST_NOT_BE_IN_MEMBERS_LIST).build());
    }
}
