package console;

import custom_exceptions.IllegalNumberFormatException;
import enums.ColumnType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleWorker {

    private final ConsoleChecker consoleWorker = new ConsoleChecker();
    private final TypeManager typeManager = new TypeManager();

    private final String pathToFile;
    private Scanner scanner = new Scanner(System.in);

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
            consoleWorker.checkNumberOfColumnValid(firstArgument, countColumn);
        } catch (IllegalNumberFormatException e) {
            throw new IllegalNumberFormatException(e.getMessage());
        }

        return Integer.parseInt(firstArgument[0]);
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
//        BufferedReader reader;
//        reader = new BufferedReader(new FileReader(pathToFile));
        InputStream inputStream = getClass().getResourceAsStream(pathToFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String firstFileLine = reader.readLine();
        reader.close();
        return firstFileLine;
    }
}
