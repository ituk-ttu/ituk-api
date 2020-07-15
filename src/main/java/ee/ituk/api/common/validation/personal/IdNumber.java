package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;

import java.util.List;

public class IdNumber {

    private final String idCode;
    private final int genderNumber;
    private final int yearNumber;
    private final int monthNumber;
    private final int dayNumber;
    private final int bornUniqueNumber;
    private final int controlNumber;

    public IdNumber(PersonalData person) {
        this.idCode = person.getPersonalCode();
        this.genderNumber = Integer.parseInt(idCode.substring(0, 1));
        this.yearNumber = Integer.parseInt(idCode.substring(1, 3));
        this.monthNumber = Integer.parseInt(idCode.substring(3, 5));
        this.dayNumber = Integer.parseInt(idCode.substring(5, 7));
        this.bornUniqueNumber = Integer.parseInt(idCode.substring(7, 10));
        this.controlNumber = Integer.parseInt(idCode.substring(10, 11));
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    boolean isValidEstonianIdNumber() {
        return isValidGenderNumber() && isValidYear() && isValidMonth() && isValidDayNumber() && isValidBornUniqueNumber() && isValidControlNumber();
    }

    private boolean isValidGenderNumber() {
        return 0 <= genderNumber && genderNumber <= 6;
    }

    private boolean isValidYear() {
        return 0 <= yearNumber && yearNumber <= 99;
    }

    private boolean isValidMonth() {
        return 0 <= monthNumber && monthNumber <= 12;
    }

    private boolean isValidDayNumber() {
        return 0 <= dayNumber && dayNumber <= 31;
    }

    private boolean isValidBornUniqueNumber() {
        return 0 <= bornUniqueNumber && bornUniqueNumber <= 999;
    }

    private boolean isValidControlNumber() {
        List<Integer> firstList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1);
        List<Integer> secondList = List.of(3, 4, 5, 6, 7, 8, 9, 1, 2, 3);
        int firstNumber = 0;
        int secondNumber = 0;
        for (int x = 0; x < firstList.size(); x++) {
            firstNumber += firstList.get(x) * Integer.parseInt(String.valueOf(idCode.charAt(x)));
        }
        firstNumber = firstNumber % 11;
        if (firstNumber == 10) {
            for (int i = 0; i < secondList.size(); i++) {
                secondNumber += secondList.get(i) * Integer.parseInt(String.valueOf(idCode.charAt(i)));
            }
            secondNumber = secondNumber % 11;
            if (secondNumber == 10) {
                secondNumber = 0;
            }
            return checkNumber(secondNumber);
        } else {
            return checkNumber(firstNumber);
        }
    }

    private boolean checkNumber(int countedNumber) {
        return countedNumber == controlNumber;
    }
}
