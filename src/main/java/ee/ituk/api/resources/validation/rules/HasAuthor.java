package ee.ituk.api.resources.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.resources.domain.Resource;

import java.util.List;
import java.util.Objects;

import static ee.ituk.api.common.validation.ValidationUtil.RESOURCE_AUTHOR_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasAuthor implements ValidationRule<Resource> {

    @Override
    public List<ErrorMessage> apply(Resource resource) {
        if (Objects.nonNull(resource.getAuthor()) && Objects.nonNull(resource.getAuthor().getId())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(RESOURCE_AUTHOR_MISSING).build());
    }
}
