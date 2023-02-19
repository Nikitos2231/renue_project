package searchers;

import enums.ColumnType;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class CsvFileSearcher implements FileSearcher {

    private final List<String> allSortedValuesOfColumns;
    private final String pathToFile;

    private final HashMap<Integer, Integer> numberAirportAndStartByteMap;

    private final TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap;

    private final HashMap<Integer, String> numberOfAirportsAndValuesColumnMap;

    public static int sizeResultList = 0;

    public CsvFileSearcher(List<String> allSortedValuesOfColumns, String pathToFile, HashMap<Integer, Integer> numberAirportAndStartByteMap, TreeMap<String, List<Integer>> valuesColumnAndNumberOfAirportsMap, HashMap<Integer, String> numberOfAirportsAndValuesColumnMap) {
        this.allSortedValuesOfColumns = allSortedValuesOfColumns;
        this.pathToFile = pathToFile;
        this.numberAirportAndStartByteMap = numberAirportAndStartByteMap;
        this.valuesColumnAndNumberOfAirportsMap = valuesColumnAndNumberOfAirportsMap;
        this.numberOfAirportsAndValuesColumnMap = numberOfAirportsAndValuesColumnMap;
    }

    public StringBuilder search(String searchSubstring, ColumnType columnType) throws IOException {
        int middleIndex = getMiddleIndex(searchSubstring);
        if (middleIndex == -1) {
            return null;
        }
        List<String> suitableValues = getAllSuitableEntries(middleIndex, searchSubstring);

        sortSuitableValues(columnType, suitableValues);

        List<Integer> numbersOfAirports = getNumberOfAirports(suitableValues);

        StringBuilder resultList;
        try {
            resultList = getAllSuitableStringFromTheFile(numbersOfAirports);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return resultList;
    }

    private void sortSuitableValues(ColumnType columnType, List<String> suitableValues) {
//        if (columnType == ColumnType.INTEGER) {
//            suitableValues.sort(Comparator.comparingInt(Integer::parseInt));
//        }
        if (columnType == ColumnType.DOUBLE) {
            suitableValues.sort(Comparator.comparingDouble(Double::parseDouble));
        }
    }

    private StringBuilder getAllSuitableStringFromTheFile(List<Integer> numbersOfAirports) throws IOException {
        sizeResultList = 0;
        StringBuilder resultList = new StringBuilder();

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("airports123.csv", "rw")) {
            for (int i = 0; i < numbersOfAirports.size(); i++) {
                int numberOfAirport = numbersOfAirports.get(i);
                int index = numberAirportAndStartByteMap.get(numberOfAirport);
                randomAccessFile.seek(index);
                String line = randomAccessFile.readLine();
                sizeResultList++;
                resultList.append("\n").append(numberOfAirportsAndValuesColumnMap.get(numberOfAirport)).append("[").append(line).append("]");
            }
        } catch (IOException e) {
            throw new IOException("Unexpected error has occurred");
        }


        return resultList;
    }

    public List<Integer> getNumberOfAirports(List<String> suitableValues) {
        List<Integer> numbersOfAirports = new ArrayList<>();
        List<Integer> numbersOfAirportsForValue;
        for (String value : suitableValues) {
            numbersOfAirportsForValue = valuesColumnAndNumberOfAirportsMap.get(value);
            numbersOfAirports.addAll(numbersOfAirportsForValue);
        }
        return numbersOfAirports;
    }

    public List<String> getAllSuitableEntries(int middleIndex, String search) {

        int sizeOfList = allSortedValuesOfColumns.size();
        int leftIndex = getLeftIndex(middleIndex, search.toLowerCase());
        int rightIndex = getRightIndex(middleIndex, search.toLowerCase(), sizeOfList);

        return new ArrayList<>(fillListSuitableElements(leftIndex, rightIndex, middleIndex));
    }

    private int getLeftIndex(int middleIndex, String search) {
        int leftIndex = middleIndex;
        String leftElement = allSortedValuesOfColumns.get(leftIndex).replaceAll("\"", "");
        if (leftElement.length() >= search.length()) {
            boolean isLeftElementSuit = leftElement.toLowerCase().startsWith(search);
            while (isLeftElementSuit) {
                leftIndex -= 1;
                if (leftIndex < 0) {
                    leftIndex++;
                    break;
                }
                leftElement = allSortedValuesOfColumns.get(leftIndex).replaceAll("\"", "").toLowerCase();
                isLeftElementSuit = leftElement.length() >= search.length() && leftElement.toLowerCase().startsWith(search);
            }
        }
        return leftIndex;
    }

    private int getRightIndex(int middleIndex, String search, int sizeOfList) {
        int rightIndex = middleIndex;
        String rightElement = allSortedValuesOfColumns.get(rightIndex).replaceAll("\"", "");
        if (rightElement.length() >= search.length()) {
            boolean isRightElementSuit = rightElement.toLowerCase().startsWith(search);
            while (isRightElementSuit) {
                rightIndex += 1;
                if (rightIndex >= sizeOfList) {
                    rightIndex--;
                    break;
                }
                rightElement = allSortedValuesOfColumns.get(rightIndex).replaceAll("\"", "").toLowerCase();
                isRightElementSuit = rightElement.length() >= search.length() && rightElement.toLowerCase().startsWith(search);
            }
        }
        return rightIndex;
    }

    private LinkedHashSet<String> fillListSuitableElements(int leftIndex, int rightIndex, int middleIndex) {
        LinkedHashSet<String> listSuitableElements = new LinkedHashSet<>();
        for (int i = leftIndex + 1; i < rightIndex; i++) {
            listSuitableElements.add(allSortedValuesOfColumns.get(i));
        }
        if (listSuitableElements.size() == 0) {
            listSuitableElements.add(allSortedValuesOfColumns.get(middleIndex));
        }
        return listSuitableElements;
    }

    private int getMiddleIndex(String search) {
        int low = 0;
        int high = allSortedValuesOfColumns.size() - 1;
        int middle = 0;

        int sizeOfElement;
        int sizeOfSearchingRequest;
        String element;


        while (low <= high) {
            middle = low + ((high - low) / 2);

            element = allSortedValuesOfColumns.get(middle).replaceAll("\"", "");
            sizeOfElement = element.length();
            sizeOfSearchingRequest = search.length();

            if (sizeOfElement < sizeOfSearchingRequest) {

                String substring = search.replaceAll("\"", "").substring(0, sizeOfElement);

                if (element.compareToIgnoreCase(substring) < 0) {
                    low = middle + 1;
                }
                else if (element.compareToIgnoreCase(substring) > 0) {
                    high = middle - 1;
                }
                else {
                    low = middle + 1;
                }
            }
            else {
                if (element.compareToIgnoreCase(search) < 0) {
                    low = middle + 1;
                }
                else if (element.compareToIgnoreCase(search) > 0) {
                    high = middle - 1;
                }
                else {
                    break;
                }
                if (element.toLowerCase().startsWith(search.toLowerCase())) {
                    break;
                }
            }
        }

        if (!allSortedValuesOfColumns.get(middle).replaceAll("\"", "").toLowerCase().startsWith(search.toLowerCase())) {
            return -1;
        }
        return middle;
    }
}
