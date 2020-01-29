package br.org.otus.laboratory.configuration.label;

import java.util.List;
import java.util.Map;

public class LabelPrintConfiguration {

  private Map<String, List<LabelReference>> orders;

  public LabelPrintConfiguration(Map<String, List<LabelReference>> orders) {
    this.orders = orders;
  }

  public Map<String, List<LabelReference>> getOrders() {
    return orders;
  }

}
