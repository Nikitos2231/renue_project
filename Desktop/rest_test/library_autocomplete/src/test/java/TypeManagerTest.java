import console.TypeManager;
import enums.ColumnType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeManagerTest {

    private TypeManager typeManager = new TypeManager();

    @Test
    void getColumnType_IntegerValue() {
//        assertEquals(ColumnType.INTEGER, typeManager.getColumnType("123"));
//        assertEquals(ColumnType.INTEGER, typeManager.getColumnType("1"));
//        assertEquals(ColumnType.INTEGER, typeManager.getColumnType("124325"));
    }

    @Test
    void getColumnType_StringValue() {
        assertEquals(ColumnType.STRING, typeManager.getColumnType("wer"));
        assertEquals(ColumnType.STRING, typeManager.getColumnType("123T"));
        assertEquals(ColumnType.STRING, typeManager.getColumnType("q123"));
    }

    @Test
    void getColumnType_DoubleValue() {
        assertEquals(ColumnType.DOUBLE, typeManager.getColumnType("12.0"));
        assertEquals(ColumnType.DOUBLE, typeManager.getColumnType("-12.0"));
        assertEquals(ColumnType.DOUBLE, typeManager.getColumnType("0.234"));
    }
}