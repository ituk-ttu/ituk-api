package ee.ituk.api.meeting.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.meeting.general.GeneralMeeting;

import java.util.List;
import java.util.Objects;

import static ee.ituk.api.common.validation.ValidationUtil.DATE_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasDate implements ValidationRule<GeneralMeeting> {

    @Override
    public List<ErrorMessage> apply(GeneralMeeting meeting) {
        if (Objects.nonNull(meeting)) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(DATE_MISSING).build());
    }
}
