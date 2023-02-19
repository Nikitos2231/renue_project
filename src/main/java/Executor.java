import console.ConsoleWorker;
import custom_exceptions.IllegalNumberFormatException;
import enums.ColumnType;
import preparers.CsvFilePreparer;
import preparers.FilePreparer;
import searchers.CsvFileSearcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Executor {

    private final String pathToFile = "/airports123.csv";

    private final TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap = new TreeMap<>();
    private final HashMap<Integer, String> numberOfAirportsAndValuesColumnMap = new HashMap<>();
    private final HashMap<Integer, Integer> numberAirportAndStartByteMap = new HashMap<>();
    private final List<String> allSortedValuesOfColumns = new ArrayList<>();
    private final FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
            numberOfAirportsAndValuesColumnMap,
            numberAirportAndStartByteMap,
            allSortedValuesOfColumns);

    private final CsvFileSearcher csvFileSearcher = new CsvFileSearcher(allSortedValuesOfColumns,
            pathToFile,
            numberAirportAndStartByteMap,
            valuesColumnAndNumberOfAirportsMap,
            numberOfAirportsAndValuesColumnMap);

    public void execute(String[] firstArgument) {
        int numberOfColumn;
        ColumnType columnType;
        ConsoleWorker consoleWorker = new ConsoleWorker(pathToFile);
        try {
            numberOfColumn = consoleWorker.readFirstArgument(firstArgument);
            columnType = consoleWorker.defineColumnType(numberOfColumn);
        } catch (IllegalNumberFormatException | IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        try {
            csvFilePreparer.prepare(pathToFile, numberOfColumn, columnType);
        } catch (IOException e) {
            System.err.println("Unexpected error has occurred");
            return;
        }
        getSearchingResults(columnType, consoleWorker);
    }

    private void getSearchingResults(ColumnType columnType, ConsoleWorker consoleWorker) {
        while (true) {
            String searchingSubstring = consoleWorker.readSearchSubstring(columnType);

            if (searchingSubstring.equals("!quit")) {
                return;
            }
            if (searchingSubstring.equals("")) {
                continue;
            }

            long time = System.currentTimeMillis();

            StringBuilder elements;
            try {
                elements = csvFileSearcher.search(searchingSubstring, columnType);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (elements == null) {
                System.out.print("Founded entries: " + 0 + "; ");
                System.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
                continue;
            }

            System.out.println(elements);
            System.out.print("Time: " + (System.currentTimeMillis() - time) + " ms; ");
            System.out.println("Founded entries: " + CsvFileSearcher.sizeResultList);
        }
    }



}
