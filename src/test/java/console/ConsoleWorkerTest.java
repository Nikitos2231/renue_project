package console;

import custom_exceptions.IllegalNumberFormatException;
import enums.ColumnType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleWorkerTest {

    private ConsoleWorker consoleWorker = new ConsoleWorker("/test.csv");

    @Test
    void readFirstArgument() throws IOException, IllegalNumberFormatException {
        int argument = consoleWorker.readFirstArgument(new String[]{"1"});
        assertEquals(argument, 1);
    }

    @Test
    void readFirstArgument_IllegalNumberFormatException() {
        assertThrows(IllegalNumberFormatException.class, () -> consoleWorker.readFirstArgument(new String[]{"123"}));
    }

    @Test
    void readFirstArgument_IOException() {
        ConsoleWorker consoleWorker1 = new ConsoleWorker("123.csv");
        assertThrows(IOException.class, () -> consoleWorker1.readFirstArgument(new String[]{"12"}));
    }

    @Test
    void defineColumnType_Double() throws IOException {
        ColumnType columnType = consoleWorker.defineColumnType(1);
        assertEquals(columnType, ColumnType.DOUBLE);
    }

    @Test
    void defineColumnType_String() throws IOException {
        ColumnType columnType = consoleWorker.defineColumnType(2);
        assertEquals(columnType, ColumnType.STRING);
    }

    @Test
    void getFirstLineInTheFile() throws IOException {
        String firstLine = consoleWorker.getFirstLineInTheFile();
        assertEquals(firstLine, "1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291,5282,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"");
    }

    @Test
    void getFirstLineInTheFile_IOException() {
        ConsoleWorker consoleWorkerTest = new ConsoleWorker("123.csv");
        assertThrows(IOException.class, consoleWorkerTest::getFirstLineInTheFile);
    }
}