package console;

import custom_exceptions.IllegalNumberFormatException;
import enums.ColumnType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleWorker {

    private final ConsoleChecker consoleWorker = new ConsoleChecker();
    private final TypeManager typeManager = new TypeManager();

    private final String pathToFile;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleWorker(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public int readFirstArgument() throws IOException, IllegalNumberFormatException {
        System.out.print("Enter the column, which need to search: ");
        String enteredValue = scanner.nextLine();

        String firstFileLine;
        try {
            firstFileLine = getFirstLineInTheFile();
        } catch (IOException e) {
            throw new IOException("Error with the file has occurred, please check file state or path to the file");
        }
        String[] valuesIntoFirstLine = firstFileLine.split(",");
        int countColumn = valuesIntoFirstLine.length;


        try {
            consoleWorker.checkNumberOfColumnValid(enteredValue, countColumn);
        } catch (IllegalNumberFormatException e) {
            throw new IllegalNumberFormatException(e.getMessage());
        }

        return Integer.parseInt(enteredValue);
    }

    public String readSearchSubstring(ColumnType columnType) {
        System.out.print("Enter substring: ");
        String searchSubstring = scanner.nextLine();
        return searchSubstring;
    }

    public ColumnType defineColumnType(int numberOfColumn) throws IOException {
        String columnValue = getFirstLineInTheFile();
        String[] massOfValuesOfLine = columnValue.split(",");
        return typeManager.getColumnType(massOfValuesOfLine[numberOfColumn - 1]);
    }

    public String getFirstLineInTheFile() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(pathToFile));
        String firstFileLine = reader.readLine();
        reader.close();
        return firstFileLine;
    }
}
