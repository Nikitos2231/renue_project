package searchers;

import enums.ColumnType;

import java.io.IOException;
import java.util.List;

public interface FileSearcher {

    StringBuilder search(String searchSubstring, ColumnType columnType) throws IOException;

}
