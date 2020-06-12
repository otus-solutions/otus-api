package br.org.otus.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class Response {

  private Object data;

  public Object getData() {
    return data;
  }

  public Response setData(Object data) {
    this.data = data;
    return this;
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public String toJson(GsonBuilder builder) {
    return builder.create().toJson(this);
  }

  public String toSurveyJson() {
    return SurveyActivity.getGsonBuilder().create().toJson(this);
  }

  public String toJsonWithStringOid() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder.create().toJson(this);
  }

  public Response buildSuccess(Object data) {
    this.data = data;
    return this;
  }

  public Response buildSuccess() {
    this.data = Boolean.TRUE;
    return this;
  }

}
