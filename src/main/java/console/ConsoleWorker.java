package console;

import custom_exceptions.IllegalNumberFormatException;
import enums.ColumnType;

import java.io.*;
import java.util.Scanner;

public class ConsoleWorker {

    private final ConsoleChecker consoleChecker = new ConsoleChecker();
    private final TypeManager typeManager = new TypeManager();
    private final String pathToFile;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleWorker(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public int readFirstArgument(String[] firstArgument) throws IOException, IllegalNumberFormatException {
        String firstFileLine;
        try {
            firstFileLine = getFirstLineInTheFile();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        String[] valuesIntoFirstLine = firstFileLine.split(",");
        int countColumn = valuesIntoFirstLine.length;

        try {
            consoleChecker.checkNumberOfColumnValid(firstArgument, countColumn);
        } catch (IllegalNumberFormatException e) {
            throw new IllegalNumberFormatException(e.getMessage());
        }

        return Integer.parseInt(firstArgument[0]);
    }

    public String readSearchSubstring() {
        System.out.print("Enter substring: ");
        return scanner.nextLine();
    }

    public ColumnType defineColumnType(int numberOfColumn) throws IOException {
        String columnValue = getFirstLineInTheFile();
        String[] massOfValuesOfLine = columnValue.split(",");
        return typeManager.getColumnType(massOfValuesOfLine[numberOfColumn - 1]);
    }

    public String getFirstLineInTheFile() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(pathToFile);
        BufferedReader reader;
        String firstFileLine;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            firstFileLine = reader.readLine();
            reader.close();
        } catch (NullPointerException e) {
            throw new IOException(e.getMessage());
        }
        return firstFileLine;
    }
}
