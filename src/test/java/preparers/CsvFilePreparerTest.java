package preparers;

import enums.ColumnType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CsvFilePreparerTest {

    private final TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap = new TreeMap<>();
    private final HashMap<Integer, String> numberOfAirportsAndValuesColumnMap = new HashMap<>();
    private final HashMap<Integer, Integer> numberAirportAndStartByteMap = new HashMap<>();
    private final List<String> allSortedValuesOfColumns = new ArrayList<>();
    private final String pathToFile = "/test.csv";

    @AfterEach
    public void clearMaps() {
        valuesColumnAndNumberOfAirportsMap.clear();
        numberOfAirportsAndValuesColumnMap.clear();
        numberAirportAndStartByteMap.clear();
        allSortedValuesOfColumns.clear();
    }

    @Test
    void prepare_testIntColumn() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 1, ColumnType.DOUBLE);

        assertEquals(List.of(1), valuesColumnAndNumberOfAirportsMap.get("1"));
        assertEquals(List.of(2), valuesColumnAndNumberOfAirportsMap.get("2"));
        assertEquals(List.of(3), valuesColumnAndNumberOfAirportsMap.get("3"));
        assertEquals(List.of(4), valuesColumnAndNumberOfAirportsMap.get("4"));

        assertEquals("1", numberOfAirportsAndValuesColumnMap.get(1));
        assertEquals("2", numberOfAirportsAndValuesColumnMap.get(2));
        assertEquals("3", numberOfAirportsAndValuesColumnMap.get(3));
        assertEquals("4", numberOfAirportsAndValuesColumnMap.get(4));

        assertEquals(0, numberAirportAndStartByteMap.get(1));
        assertEquals(152, numberAirportAndStartByteMap.get(2));
        assertEquals(298, numberAirportAndStartByteMap.get(3));
        assertEquals(474, numberAirportAndStartByteMap.get(4));

        assertEquals("1", allSortedValuesOfColumns.get(0));
        assertEquals("2", allSortedValuesOfColumns.get(1));
        assertEquals("3", allSortedValuesOfColumns.get(2));
        assertEquals("4", allSortedValuesOfColumns.get(3));
    }

    @Test
    void prepare_testStringColumn() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        assertEquals(List.of(1), valuesColumnAndNumberOfAirportsMap.get("\"Goroka Airport\""));
        assertEquals(List.of(2), valuesColumnAndNumberOfAirportsMap.get("\"Madang Airport\""));
        assertEquals(List.of(3), valuesColumnAndNumberOfAirportsMap.get("\"Mount Hagen Kagamuga Airport\""));
        assertEquals(List.of(4, 5), valuesColumnAndNumberOfAirportsMap.get("\"Nadzab Airport\""));

        assertEquals("\"Goroka Airport\"", numberOfAirportsAndValuesColumnMap.get(1));
        assertEquals("\"Madang Airport\"", numberOfAirportsAndValuesColumnMap.get(2));
        assertEquals("\"Mount Hagen Kagamuga Airport\"", numberOfAirportsAndValuesColumnMap.get(3));
        assertEquals("\"Nadzab Airport\"", numberOfAirportsAndValuesColumnMap.get(5));

        assertEquals(0, numberAirportAndStartByteMap.get(1));
        assertEquals(152, numberAirportAndStartByteMap.get(2));
        assertEquals(298, numberAirportAndStartByteMap.get(3));
        assertEquals(474, numberAirportAndStartByteMap.get(4));

        assertEquals("\"Goroka Airport\"", allSortedValuesOfColumns.get(0));
        assertEquals("\"Madang Airport\"", allSortedValuesOfColumns.get(1));
        assertEquals("\"Mount Hagen Kagamuga Airport\"", allSortedValuesOfColumns.get(2));
        assertEquals("\"Nadzab Airport\"", allSortedValuesOfColumns.get(3));
    }

    @Test
    void prepare_testDoubleColumn() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 7, ColumnType.STRING);

        assertEquals(List.of(1), valuesColumnAndNumberOfAirportsMap.get("-6.081689834590001"));
        assertEquals(List.of(2), valuesColumnAndNumberOfAirportsMap.get("-5.20707988739"));
        assertEquals(List.of(3), valuesColumnAndNumberOfAirportsMap.get("-5.826789855957031"));
        assertEquals(List.of(4, 5, 6), valuesColumnAndNumberOfAirportsMap.get("-6.569803"));

        assertEquals("-6.081689834590001", numberOfAirportsAndValuesColumnMap.get(1));
        assertEquals("-5.20707988739", numberOfAirportsAndValuesColumnMap.get(2));
        assertEquals("-5.826789855957031", numberOfAirportsAndValuesColumnMap.get(3));
        assertEquals("-6.569803", numberOfAirportsAndValuesColumnMap.get(5));

        assertEquals(0, numberAirportAndStartByteMap.get(1));
        assertEquals(152, numberAirportAndStartByteMap.get(2));
        assertEquals(298, numberAirportAndStartByteMap.get(3));
        assertEquals(474, numberAirportAndStartByteMap.get(4));

        assertEquals("-5.20707988739", allSortedValuesOfColumns.get(0));
        assertEquals("-5.826789855957031", allSortedValuesOfColumns.get(1));
        assertEquals("-6.081689834590001", allSortedValuesOfColumns.get(2));
        assertEquals("-6.569803", allSortedValuesOfColumns.get(3));
    }

    @Test
    void prepare_testSimilarColumn() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 14, ColumnType.STRING);

        assertEquals(List.of(1, 2, 3, 4, 5, 6), valuesColumnAndNumberOfAirportsMap.get("\"OurAirports\""));
    }

    @Test
    void prepare_testDoubleSort() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 7, ColumnType.STRING);

        assertEquals("-5.20707988739" ,valuesColumnAndNumberOfAirportsMap.firstKey());
        assertEquals("-5.20707988739", allSortedValuesOfColumns.get(0));
    }

    @Test
    void prepare_testSIzeOfStructure() throws IOException {
        FilePreparer csvFilePreparer = new CsvFilePreparer(valuesColumnAndNumberOfAirportsMap,
                numberOfAirportsAndValuesColumnMap,
                numberAirportAndStartByteMap,
                allSortedValuesOfColumns);

        csvFilePreparer.prepare(pathToFile, 2, ColumnType.STRING);

        assertEquals(5, valuesColumnAndNumberOfAirportsMap.size());
        assertEquals(6, numberOfAirportsAndValuesColumnMap.size());
        assertEquals(6, numberAirportAndStartByteMap.size());
        assertEquals(6, allSortedValuesOfColumns.size());
    }
}