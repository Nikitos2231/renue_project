package preparers;

import enums.ColumnType;

import java.io.IOException;

public interface FilePreparer {

    void prepare(String pathToFile, int numberOfColumn, ColumnType columnType) throws IOException;
}
