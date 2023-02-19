package console;

import custom_exceptions.IllegalNumberFormatException;

import java.util.regex.Pattern;

public class ConsoleChecker {

    public void checkNumberOfColumnValid(String[] enteredValue, int countColumn) throws IllegalNumberFormatException {
        if (enteredValue.length > 1) {
            throw new IllegalNumberFormatException("Was entered not integer figure");
        }
        else {
            if (Pattern.matches("^([1-9]\\d*)$", enteredValue[0])) {
                int parseValue = Integer.parseInt(enteredValue[0]);
                if (parseValue > countColumn || parseValue <= 0) {
                    throw new IllegalNumberFormatException("Was entered incorrect amount of columns" +
                            "\n" +
                            "Amount of columns: " + countColumn +
                            ", and you entered: " + enteredValue[0]);
                }
            }
            else {
                throw new IllegalNumberFormatException("Was entered not integer figure");
            }
        }

    }
}
