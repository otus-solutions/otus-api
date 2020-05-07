package org.ccem.otus.model.survey.activity.mode;

import com.google.gson.annotations.SerializedName;

public enum ActivityMode {
  @SerializedName("OFFLINE")
  OFFLINE,
  @SerializedName("ONLINE")
  ONLINE,
  @SerializedName("PAPER")
  PAPER,
  @SerializedName("AUTOFILL")
  AUTOFILL

}
