package org.ccem.otus.service.extraction.model;

import org.ccem.otus.model.SerializableModel;

public class Rscript extends SerializableModel {

  private String name;
  private String script;

  public String getName() {
    return name;
  }

  public String getScript() {
    return script;
  }

  public static Rscript deserialize(String json){
    return (Rscript)deserialize(json, Rscript.class);
  }
}
