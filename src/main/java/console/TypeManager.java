package console;

import enums.ColumnType;

import java.util.regex.Pattern;

public class TypeManager {

    public ColumnType getColumnType(String firstValueIntoColumn) {
//        if (isColumnValueInteger(firstValueIntoColumn)) {
//            return ColumnType.DOUBLE;
//        }
        if (isColumnValueDoubleOrInteger(firstValueIntoColumn)) {
            return ColumnType.DOUBLE;
        }
        return ColumnType.STRING;
    }

//    private boolean isColumnValueInteger(String firstValueIntoColumn) {
//        return Pattern.matches("^(\\d+)$", firstValueIntoColumn);
//    }

    private boolean isColumnValueDoubleOrInteger(String firstValueIntoColumn) {
        return Pattern.matches("^(-?\\d+\\.\\d+)$", firstValueIntoColumn) || Pattern.matches("^(\\d+)$", firstValueIntoColumn);
    }

}
