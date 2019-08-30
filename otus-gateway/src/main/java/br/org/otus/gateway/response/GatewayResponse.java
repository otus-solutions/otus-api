package br.org.otus.gateway.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GatewayResponse {

  private Object data;

  public Object getData() {
    return data;
  }

  public GatewayResponse setData(Object data) {
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

  public GatewayResponse buildSuccess(Object data) {
    this.data = data;
    return this;
  }

  public GatewayResponse buildSuccess() {
    this.data = Boolean.TRUE;
    return this;
  }

}
