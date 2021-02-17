package org.ccem.otus.model.searchSettingsDto;

import org.ccem.otus.model.SerializableModel;

public abstract class SearchSettingsDto extends SerializableModel {

  protected int currentQuantity;
  protected int quantityToGet;
  protected OrderSettingsDto order;

  public SearchSettingsDto(){}

  public SearchSettingsDto(Enum orderFieldsOptions) {
    order = new OrderSettingsDto(orderFieldsOptions);
  }

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  public int getQuantityToGet() {
    return quantityToGet;
  }

  public OrderSettingsDto getOrder() {
    return order;
  }

  public Boolean isValid() {
    return (currentQuantity >= 0 && quantityToGet > 0 && (order == null || order.isValid()));
  }

}
