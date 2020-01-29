
package br.org.otus.configuration.datasource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DataSourceFactory {

  public static JsonObject create() {

    JsonObject value1 = new JsonObject();
    JsonObject value2 = new JsonObject();
    JsonObject value3 = new JsonObject();

    value1.addProperty("value", "01 ACETATO DE MEDROXIPROGESTERONA 150MG - INJETÁVEL (GENÉRICO)");
    value2.addProperty("value", "02 ADOLESS");
    value3.addProperty("value", "03 AIXA");

    JsonArray valuesList = new JsonArray();
    valuesList.add(value1);
    valuesList.add(value2);
    valuesList.add(value3);

    JsonObject dataObject = new JsonObject();
    dataObject.addProperty("id", "medicamentos_contraceptivos_hormonais");
    dataObject.addProperty("name", "MEDICAMENTOS_CONTRACEPTIVOS_HORMONAIS");
    dataObject.add("data", valuesList);


    return dataObject;

  }

}
