package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.STUDENT_CODE_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@AllArgsConstructor
public class HasValidStudentCode implements ValidationRule<PersonalData> {

    private final Pattern studentCodePattern = Pattern.compile("^\\d{6}[IYT][A-Z]{2}[BMD]?$");

    @Override
    public List<ErrorMessage> apply(PersonalData person) {
        if (studentCodePattern.matcher(person.getStudentCode().toUpperCase()).matches()) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(STUDENT_CODE_INCORRECT).build());
    }
}
