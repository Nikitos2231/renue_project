import console.ConsoleChecker;
import custom_exceptions.IllegalNumberFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConsoleCheckerTest {

    private final ConsoleChecker consoleChecker = new ConsoleChecker();

//    @Test
//    void checkNumberOfColumnValid_ThrowException() {
//        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
//            consoleChecker.checkNumberOfColumnValid("wer", 14);
//        });
//
//        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
//            consoleChecker.checkNumberOfColumnValid("15", 14);
//        });
//        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
//            consoleChecker.checkNumberOfColumnValid("0", 14);
//        });
//        Assertions.assertThrows(IllegalNumberFormatException.class, () -> {
//            consoleChecker.checkNumberOfColumnValid("023", 14);
//        });
//    }

//    @Test
//    void checkNumberOfColumnValid_CorrectValue() {
//        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid("14", 14));
//        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid("5", 14));
//        Assertions.assertDoesNotThrow(() -> consoleChecker.checkNumberOfColumnValid("1", 14));
//    }
}