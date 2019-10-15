package org.ccem.otus.model.dataSources.dcm.ultrasound;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

import com.google.gson.JsonObject;

public class DCMUltrasoundDataSource extends ReportDataSource<DCMUltrasoundDataSourceResult> {

  private UltrasoundDataSourceFilter filters;

  @Override
  public void addResult(DCMUltrasoundDataSourceResult result) {
    super.getResult().add(result);
  }

  @Override
  public ArrayList<Document> buildQuery(Long recruitmentNumber) {
    return null;
  }

  public String buildFilterToUltrasound(Long recruitmentNumber) {
    filters.setRecruitmentNumber(Long.toString(recruitmentNumber));
    JsonObject json = new JsonObject();
    String name = "";
    Object value = null;
    for (Field field : filters.getClass().getDeclaredFields()) {
      try {
        name = field.getName();
        field.setAccessible(true);
        value = field.get(filters);
        json.addProperty(name, value.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return json.toString();
  }

}
