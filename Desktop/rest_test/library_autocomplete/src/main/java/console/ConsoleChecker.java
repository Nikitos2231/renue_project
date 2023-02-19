package console;

import custom_exceptions.IllegalNumberFormatException;

import java.util.regex.Pattern;

public class ConsoleChecker {

    public void checkNumberOfColumnValid(String enteredValue, int countColumn) throws IllegalNumberFormatException {
        if (Pattern.matches("^([1-9]\\d*)$", enteredValue)) {
            int parseValue = Integer.parseInt(enteredValue);
            if (parseValue > countColumn || parseValue <= 0) {
                throw new IllegalNumberFormatException("Was entered incorrect amount of columns" +
                        "\n" +
                        "Amount of columns: " + countColumn +
                        ", and you entered: " + enteredValue);
            }
        }
        else {
            throw new IllegalNumberFormatException("Was entered not integer figure");
        }
    }
}
