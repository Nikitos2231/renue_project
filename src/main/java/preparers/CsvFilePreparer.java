package preparers;

import console.ConsoleChecker;
import console.ConsoleWorker;
import enums.ColumnType;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/* Класс предназначен для заполнения нужных структур данных, для реализации быстрого поиска по файлу */
public class CsvFilePreparer implements FilePreparer {

    /*
    Treemap, который заполняем по следующим правилам:
    Ключ: значение колонки, по которой осуществляем поиск
    Значение: список номеров аэропортов, в которых имеются значения колонки, по которой осуществляется поиск
    (Используется для быстрого извления списка всех аэропортов, подходящих под поисковый запрос)
    */
    private final TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap;

    /*
    HashMap, который хранит:
    Ключ: номер аэропорта
    Значение: колонка, по которой осуществляется поиск
    (Используется для быстрого обратного извлечения значения колонки по номеру аэропорта)
    */
    private final HashMap<Integer, String> numberOfAirportsAndValuesColumnMap;

    /*
    HashMap, который хранит:
    Ключ: номер аэропорта (1 колонка в каждой записи)
    Значение: номер байта в файле, на котором начинается строка с заданым номером аэропорта
    (Используется для быстрого парсинга файла в виде результата поиска)
    */
    private final HashMap<Integer, Integer> numberAirportAndStartByteMap;

    /* Список, который содержит занчения колонки, отсортированный как String */
    private final List<String> allSortedValuesOfColumns;

    public CsvFilePreparer(TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap,
                           HashMap<Integer, String> numberOfAirportsAndValuesColumnMap,
                           HashMap<Integer, Integer> numberAirportAndStartByteMap,
                           List<String> allSortedValuesOfColumns) {
        this.valuesColumnAndNumberOfAirportsMap = valuesColumnAndNumberOfAirportsMap;
        this.numberOfAirportsAndValuesColumnMap = numberOfAirportsAndValuesColumnMap;
        this.numberAirportAndStartByteMap = numberAirportAndStartByteMap;
        this.allSortedValuesOfColumns = allSortedValuesOfColumns;
    }

    @Override
    public void prepare(String pathToFile, int numberOfColumn, ColumnType columnType) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(pathToFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
//        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String line = reader.readLine();
        int currentByte = 0;

        int countColumnsInFile = line.split(",").length;

        while (line != null) {
            String[] massOfColumnValues = line.split(",");

            if (massOfColumnValues.length > countColumnsInFile) {
                massOfColumnValues = getRightElements(line, countColumnsInFile);
            }

            String valueColumn = massOfColumnValues[numberOfColumn - 1];
            int numberOfAirport = Integer.parseInt(massOfColumnValues[0]);

            fillValuesColumnAndNumberOfAirportsMap(valueColumn, numberOfAirport);
            fillNumberOfAirportsAndValuesColumnMap(numberOfAirport, valueColumn);
            fillNumberAirportAndStartByteMap(numberOfAirport, currentByte);

            currentByte += line.getBytes().length + 1;

            line = reader.readLine();
        }
        fillSortedValuesOfColumns();
    }


    private String[] getRightElements(String line, int countOfElementsInMass) {
        String[] strings = line.split("");
        int index = 0;
        String[] newArray = new String[countOfElementsInMass];
        StringBuilder newElement = new StringBuilder();
        int sizeOfElementsInMass = 0;
        for (int i = 0; i < strings.length - 1; i++) {
            newElement.append(strings[i]);
            if (Pattern.matches(",", strings[i])) {
                if (Pattern.matches("\\S", strings[i + 1])) {
                    newArray[index++] = newElement.substring(0 , newElement.length() - 1);
                    sizeOfElementsInMass += newElement.length();
                    newElement = new StringBuilder();
                }
            }
        }
        newArray[index] = line.substring(sizeOfElementsInMass);
        return newArray;
    }

    private void fillValuesColumnAndNumberOfAirportsMap(String columnValue, int numberOfAirport) {
        if (valuesColumnAndNumberOfAirportsMap.containsKey(columnValue)) {
            List<Integer> numbersOfAirports = new ArrayList<>(valuesColumnAndNumberOfAirportsMap.get(columnValue));
            numbersOfAirports.add(numberOfAirport);
            valuesColumnAndNumberOfAirportsMap.put(columnValue, numbersOfAirports);
        }
        else {
            valuesColumnAndNumberOfAirportsMap.put(columnValue, List.of(numberOfAirport));
        }
    }

    private void fillNumberOfAirportsAndValuesColumnMap(int numberOfAirport, String columnValue) {
        numberOfAirportsAndValuesColumnMap.put(numberOfAirport, columnValue);
    }

    private void fillNumberAirportAndStartByteMap(int numberOfAirport, int currentByte) {
        numberAirportAndStartByteMap.put(numberOfAirport, currentByte);
    }

    private void fillSortedValuesOfColumns() {
        allSortedValuesOfColumns.addAll(numberOfAirportsAndValuesColumnMap.values());
        allSortedValuesOfColumns.sort((o1, o2) -> {
            if (o1.contains("\\N") && o2.contains("\\N")) {
                return 0;
            } else if (o1.contains("\\N") && !o2.contains("\\N")) {
                return -100;
            } else if (!o1.contains("\\N") && o2.contains("\\N")) {
                return 100;
            } else {
                return o1.compareToIgnoreCase(o2);
            }
        });
    }

}
