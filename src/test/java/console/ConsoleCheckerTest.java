package console;

import console.ConsoleChecker;
import custom_exceptions.IllegalNumberFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConsoleCheckerTest {

    private final ConsoleChecker consoleChecker = new ConsoleChecker();

    @Test
    void checkNumberOfColumnValid_ThrowException() {
        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
            consoleChecker.checkNumberOfColumnValid(new String[]{"wer"}, 14);
        });

        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
            consoleChecker.checkNumberOfColumnValid(new String[]{"15"}, 14);
        });
        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
            consoleChecker.checkNumberOfColumnValid(new String[]{"0"}, 14);
        });
        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
            consoleChecker.checkNumberOfColumnValid(new String[]{"023"}, 14);
        });
        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
            consoleChecker.checkNumberOfColumnValid(new String[]{"2", "5"}, 14);
        });
    }

    @Test
    void checkNumberOfColumnValid_CorrectValue() {
        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid(new String[]{"14"}, 14));
        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid(new String[]{"5"}, 14));
        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid(new String[]{"1"}, 14));
    }
}