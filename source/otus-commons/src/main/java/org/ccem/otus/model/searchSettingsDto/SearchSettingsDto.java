package org.ccem.otus.model.searchSettingsDto;

import org.ccem.otus.model.SerializableModel;

import java.util.Map;

public class SearchSettingsDto extends SerializableModel {

  protected int currentQuantity;
  protected int quantityToGet;
  protected Map<String, Object> filters;
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

  public void revalidate() {
    this.quantityToGet = this.quantityToGet != 0 ? this.quantityToGet : Integer.MAX_VALUE;
  }

  public Map<String, Object> getFilters() {
    return filters;
  }

  public OrderSettingsDto getOrder() {
    return order;
  }

  public Boolean isValid() {
    return (currentQuantity >= 0 && quantityToGet > 0 && (order == null || order.isValid()));
  }

  public static SearchSettingsDto deserialize(String json){
    SearchSettingsDto object = (SearchSettingsDto) deserialize(json, SearchSettingsDto.class);
    object.revalidate();
    return object;
  }

}
