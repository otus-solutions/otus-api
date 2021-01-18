package org.ccem.otus.model.survey.activity.navigation;

import java.util.List;

public class NavigationTrackingItem {

  public String objectType;
  public String id;
  public String state;
  public String previous;
  public List<String> inputs;
  public List<String> outputs;

  public String getState() {
    return state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NavigationTrackingItem that = (NavigationTrackingItem) o;

    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
