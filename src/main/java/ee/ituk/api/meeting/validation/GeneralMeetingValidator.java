package ee.ituk.api.meeting.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.meeting.domain.GeneralMeeting;

public class GeneralMeetingValidator extends Validator {

    public ValidationResult validateOnCreate(GeneralMeeting meeting) {
        //TODO: implement validation
        return new ValidationResult();
    }

}
