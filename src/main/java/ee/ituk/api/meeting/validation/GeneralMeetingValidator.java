package ee.ituk.api.meeting.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.meeting.validation.rules.HasDate;

import java.util.Arrays;

public class GeneralMeetingValidator extends Validator {

    public ValidationResult validateOnCreate(GeneralMeeting meeting) {
        return applyRules(meeting, Arrays.asList(
                new HasDate()
        ));
    }

}
