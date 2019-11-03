package ee.ituk.api.resources.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.resources.domain.Resource;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;


public class ResourceHasName implements ValidationRule<Resource> {

    @Override
    public List<ErrorMessage> apply(Resource resource) {
        if (isNotBlank(resource.getName())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(NAME_MISSING).build());
    }
}
