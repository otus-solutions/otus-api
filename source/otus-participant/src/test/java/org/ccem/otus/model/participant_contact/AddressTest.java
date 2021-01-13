package org.ccem.otus.model.participant_contact;

import com.google.gson.internal.LinkedTreeMap;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

public class AddressTest {

  private static final String POSTAL_CODE = "98765-432";
  private static final String STREET = "Rua X";
  private static final Integer STREET_NUMBER = 0;
  private static final String COMPLEMENTS = "apto 10";
  private static final String NEIGHBOURHOOD = "neighbourhood";
  private static final String CITY = "Rainbow City";
  private static final String STATE = "RS";
  private static final String COUNTRY = "Brazil";
  private static final String CENSUS = "census";

  private Address address = new Address();

  @Before
  public void setUp(){
    Whitebox.setInternalState(address, "postalCode", POSTAL_CODE);
    Whitebox.setInternalState(address, "street", STREET);
    Whitebox.setInternalState(address, "streetNumber", STREET_NUMBER);
    Whitebox.setInternalState(address, "complements", COMPLEMENTS);
    Whitebox.setInternalState(address, "neighbourhood", NEIGHBOURHOOD);
    Whitebox.setInternalState(address, "city", CITY);
    Whitebox.setInternalState(address, "state", STATE);
    Whitebox.setInternalState(address, "country", COUNTRY);
    Whitebox.setInternalState(address, "census", CENSUS);
  }

  @Test
  public void test_for_invoke_getters(){
    assertEquals(POSTAL_CODE, address.getPostalCode());
    assertEquals(STREET, address.getStreet());
    assertEquals(STREET_NUMBER, address.getStreetNumber());
    assertEquals(COMPLEMENTS, address.getComplements());
    assertEquals(NEIGHBOURHOOD, address.getNeighbourhood());
    assertEquals(CITY, address.getCity());
    assertEquals(STATE, address.getState());
    assertEquals(COUNTRY, address.getCountry());
    assertEquals(CENSUS, address.getCensus());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    assertTrue(Address.deserialize("{}") instanceof Address);
  }

  @Test
  public void toJson_method_should_convert_objectModel_to_JsonString(){
    assertTrue(address.toJson() instanceof String);
  }

  @Test
  public void isValid_method_should_return_TRUE(){
    assertTrue(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_street(){
    Whitebox.setInternalState(address, "street", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_streetNumber(){
    Whitebox.setInternalState(address, "streetNumber", (Integer)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_neighbourhood(){
    Whitebox.setInternalState(address, "neighbourhood", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_city(){
    Whitebox.setInternalState(address, "city", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_state(){
    Whitebox.setInternalState(address, "state", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_country(){
    Whitebox.setInternalState(address, "country", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void isValid_method_should_return_FALSE_in_case_null_census(){
    Whitebox.setInternalState(address, "census", (String)null);
    assertFalse(address.isValid());
  }

  @Test
  public void setFromLinkedTreeMap_method_should_set_all_attributes_from_LinkedTreeMap(){

    final String POSTAL_CODE_2 = POSTAL_CODE+"2";
    final String STREET_2 = STREET+"2";
    final Integer STREET_NUMBER_2 = STREET_NUMBER+2;
    final String COMPLEMENTS_2 = COMPLEMENTS+"2";
    final String NEIGHBOURHOOD_2 = NEIGHBOURHOOD+"2";
    final String CITY_2 = CITY+"2";
    final String STATE_2 = STATE+"2";
    final String COUNTRY_2 = COUNTRY+"2";
    final String CENSUS_2 = CENSUS+"2";

    LinkedTreeMap map = new LinkedTreeMap();
    map.put("postalCode", POSTAL_CODE_2);
    map.put("street", STREET_2);
    map.put("streetNumber", STREET_NUMBER_2.doubleValue());
    map.put("complements", COMPLEMENTS_2);
    map.put("neighbourhood", NEIGHBOURHOOD_2);
    map.put("city", CITY_2);
    map.put("state", STATE_2);
    map.put("country", COUNTRY_2);
    map.put("census", CENSUS_2);

    address.setFromLinkedTreeMap(map);

    assertEquals(POSTAL_CODE_2, address.getPostalCode());
    assertEquals(STREET_2, address.getStreet());
    assertEquals(STREET_NUMBER_2, address.getStreetNumber());
    assertEquals(COMPLEMENTS_2, address.getComplements());
    assertEquals(NEIGHBOURHOOD_2, address.getNeighbourhood());
    assertEquals(CITY_2, address.getCity());
    assertEquals(STATE_2, address.getState());
    assertEquals(COUNTRY_2, address.getCountry());
    assertEquals(CENSUS_2, address.getCensus());
  }

}
