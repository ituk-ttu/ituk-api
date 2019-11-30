package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import lombok.Getter;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PERSONAL_CODE_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasValidPersonalCode implements ValidationRule<PersonalData> {

    @Override
    public List<ErrorMessage> apply(PersonalData person) {
        if (person.getPersonalCode().length() == 11 && person.getPersonalCode().matches("\\d+")) {
            IdNumber myId = new IdNumber(person);
            if (isValidGenderNumber(myId) && isValidYear(myId) && isValidMonth(myId) && isValidDayNumber(myId) && isValidBornUniqueNumber(myId) && isValidControlNumber(myId)) {
                return emptyList();
            }
        }
        return singletonList(ErrorMessage.builder().code(PERSONAL_CODE_INCORRECT).build());
    }

    private boolean isValidGenderNumber(IdNumber number) {
        return 0 <= number.getGenderNumber() && number.getGenderNumber() <= 6;
    }

    private boolean isValidYear(IdNumber number) {
        return 0<= number.getYearNumber() && number.getYearNumber() <= 99;
    }

    private boolean isValidMonth(IdNumber number) {
        return 0 <= number.getMonthNumber() && number.getMonthNumber() <= 12;
    }

    private boolean isValidDayNumber(IdNumber number) {
        return 0 <= number.getDayNumber() && number.getDayNumber() <= 31;
    }

    private boolean isValidBornUniqueNumber(IdNumber number) {
        return 0 <= number.getDayNumber() && number.getDayNumber() <= 999;
    }

    private boolean isValidControlNumber(IdNumber number) {
        List<Integer> firstList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1);
        List<Integer> secondList = List.of(3, 4, 5, 6, 7, 8, 9, 1, 2, 3);
        int firstNumber = 0;
        int secondNumber = 0;
        for (int x = 0; x < firstList.size() ; x++){
            firstNumber += firstList.get(x) * Integer.parseInt(String.valueOf(number.getIdCode().charAt(x)));
        }
        firstNumber = firstNumber % 11;
        if (firstNumber == 10 ){
            for (int i = 0; i < secondList.size(); i++){
                secondNumber += secondList.get(i) * Integer.parseInt(String.valueOf(number.getIdCode().charAt(i)));
            }
            secondNumber = secondNumber % 11;
            if (secondNumber == 10) {
                secondNumber = 0;
            }
            return checkNumber(secondNumber, number);
        }
        else {
            return checkNumber(firstNumber, number);
        }
    }

    private boolean checkNumber(int countedNumber, IdNumber id) {
        return countedNumber == id.getControlNumber();
    }

    @Getter
    private class IdNumber {
        private String idCode;
        private int genderNumber;
        private int yearNumber;
        private int monthNumber;
        private int dayNumber;
        private int bornUniqueNumber;
        private int controlNumber;

        public IdNumber(PersonalData person){
            this.idCode = person.getPersonalCode();
            this.genderNumber = Integer.parseInt(idCode.substring(0, 1));
            this.yearNumber = Integer.parseInt(idCode.substring(1, 3));
            this.monthNumber = Integer.parseInt(idCode.substring(3, 5));
            this.dayNumber = Integer.parseInt(idCode.substring(5, 7));
            this.bornUniqueNumber = Integer.parseInt(idCode.substring(7, 10));
            this.controlNumber = Integer.parseInt(idCode.substring(10, 11));
        }
    }

}
