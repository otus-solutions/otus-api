//package org.ccem.otus.utils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.modules.junit4.PowerMockRunner;
//import com.google.gson.JsonArray;
//
//import static org.junit.Assert.*;
//@RunWith(PowerMockRunner.class)
//public class CsvToJsonTest {
//
//    private static final String DELIMITER = ",";
//    private static final int SIZE = 5;
//    private static final CsvToJson json = new CsvToJson(DELIMITER, new byte[] {
//            (byte) 74,
//            (byte) 97,
//            (byte) 110,
//            (byte) 101,
//            (byte) 105,
//            (byte) 114,
//            (byte) 111,
//            (byte) 44,
//            (byte) 49,
//            (byte) 10,
//            (byte) 70,
//            (byte) 101,
//            (byte) 118,
//            (byte) 101,
//            (byte) 114,
//            (byte) 101,
//            (byte) 105,
//            (byte) 114,
//            (byte) 111,
//            (byte) 44,
//            (byte) 50,
//            (byte) 10,
//            (byte) 109,
//            (byte) 97,
//            (byte) 114,
//            (byte) 61,
//            (byte) 89,
//            (byte) 111,
//            (byte) 44,
//            (byte) 51,
//            (byte) 10,
//            (byte) 97,
//            (byte) 98,
//            (byte) 114,
//            (byte) 105,
//            (byte) 108,
//            (byte) 44,
//            (byte) 52,
//            (byte) 10,
//            (byte) 109,
//            (byte) 97,
//            (byte) 105,
//            (byte) 111,
//            (byte) 44,
//            (byte) 53,
//            (byte) 10
//    });
//
//    @Test
//    public void execute() {
//        JsonArray jsonArray = json.execute(DELIMITER);
//        assertEquals(jsonArray.size(), SIZE);
//    }
//}
//
//
//
//
//
//
