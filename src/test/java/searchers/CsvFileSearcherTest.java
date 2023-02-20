package searchers;

import enums.ColumnType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import preparers.CsvFilePreparer;
import preparers.FilePreparer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileSearcherTest {

    private List<String> allSortedValuesOfColumns = new ArrayList<>();
    private HashMap<Integer, Integer> numberAirportAndStartByteMap = new HashMap<>();
    private TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap = new TreeMap<>();
    private HashMap<Integer, String> numberOfAirportsAndValuesColumnMap = new HashMap<>();
    private final String pathToFile = "/test.csv";
    private FilePreparer filePreparer;
    private FileSearcher fileSearcher;

    @BeforeEach
    public void createFilePrepare() {
        filePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);
    }

    @BeforeEach
    public void createFileSearcher() {
        fileSearcher = new CsvFileSearcher(allSortedValuesOfColumns,
                numberAirportAndStartByteMap,
                valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap);
    }

    @AfterEach
    public void cleanLists() {
        allSortedValuesOfColumns.clear();
        numberAirportAndStartByteMap.clear();
        valuesColumnAndNumberOfAirportsMap.clear();
        numberOfAirportsAndValuesColumnMap.clear();
    }

    @Test
    void search_FirstColumn() throws IOException {
        filePreparer.prepare(pathToFile, 1, ColumnType.DOUBLE);

        StringBuilder stringBuilder = fileSearcher.search("2", ColumnType.DOUBLE);
        assertNotNull(stringBuilder);
        assertEquals(stringBuilder.toString(), "\n2[null]");
    }

    @Test
    void search_DoubleColumn() throws IOException {
        filePreparer.prepare(pathToFile, 7, ColumnType.DOUBLE);

        StringBuilder stringBuilder = fileSearcher.search("-5.82678985595", ColumnType.DOUBLE);

        assertNotNull(stringBuilder);
        assertEquals(stringBuilder.toString(), "\n-5.826789855957031[null]");
    }

    @Test
    void search_StringColumn() throws IOException {
        filePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        StringBuilder stringBuilder = fileSearcher.search("Mount Hagen Kagamuga Airport", ColumnType.STRING);

        assertNotNull(stringBuilder);
        assertEquals(stringBuilder.toString(), "\n\"Mount Hagen Kagamuga Airport\"[null]");
    }

    @Test
    void search_CheckIgnoreCaseInStringColumn() throws IOException {
        filePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        StringBuilder stringBuilder = fileSearcher.search("MOUNT", ColumnType.STRING);

        assertNotNull(stringBuilder);
        assertEquals(stringBuilder.toString(), "\n\"Mount Hagen Kagamuga Airport\"[null]");
    }

    @Test
    void search_CheckSeveralStrings() throws IOException {
        filePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        StringBuilder stringBuilder = fileSearcher.search("M", ColumnType.STRING);

        assertNotNull(stringBuilder);
        assertEquals(stringBuilder.toString(), "\n\"Madang Airport\"[null]" +
                "\n\"Mount Hagen Kagamuga Airport\"[null]");
    }

    @Test
    void search_CheckNotFoundResults() throws IOException {
        filePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        StringBuilder stringBuilder = fileSearcher.search("QWERTY", ColumnType.STRING);

        assertNull(stringBuilder);
    }

}