package br.org.otus.survey.activity.builder;

import org.bson.conversions.Bson;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ActivityAccessPermissionQueryBuilder {

  public List<Bson> getByAcronymVersion(String acronym, Integer version) {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{$match:{\n" +
      "        'acronym': '"+acronym+"',\n" +
      "        'version': "+version.toString()+"\n" +
      "    }}"));
    return pipeline;
  }
}
