package br.org.otus.user.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.org.otus.user.dto.PasswordResetDtoTest.EMAIL;
import static org.junit.Assert.*;

public class ManagementUserDtoTest {

  private static final String NAME = "Teste User";
  private static final String SURNAME = "Surname";
  private static final Boolean EXTRACTION = Boolean.FALSE;
  private static final String PHONE = "555-555-555";
  private static final Boolean ADMIN_FLAG = Boolean.TRUE;
  private static final Boolean ENABLE_FLAG = Boolean.TRUE;

  private List<String> ips;
  private ManagementUserDto managementUserDto;

  @Before
  public void setUp() {
    ips = Arrays.asList("143.54.220.56");

    managementUserDto = new ManagementUserDto();
    managementUserDto.setName(NAME);
    managementUserDto.setSurname(SURNAME);
    managementUserDto.setExtraction(EXTRACTION);
    managementUserDto.setExtractionIps(ips);
    managementUserDto.setPhone(PHONE);
    managementUserDto.setEmail(EMAIL);
    managementUserDto.setAdmin(ADMIN_FLAG);
    managementUserDto.setEnable(ENABLE_FLAG);
  }

  @Test
  public void mustBeValidWhenAllFieldNotEmpty() {
    assertTrue(managementUserDto.isValid());
  }

  @Test
  public void mustBeInvalidWhenSomeFieldIsEmpty() {
    ManagementUserDto managementUserDto = new ManagementUserDto();
    managementUserDto.setExtraction(EXTRACTION);
    managementUserDto.setExtractionIps(new ArrayList<String>());
    managementUserDto.setPhone(PHONE);
    managementUserDto.setEmail(EMAIL);
    managementUserDto.setAdmin(ADMIN_FLAG);
    managementUserDto.setEnable(ENABLE_FLAG);

    assertFalse(managementUserDto.isValid());
  }

  @Test
  public void shouldReturnAdminFlag() {
    assertEquals(managementUserDto.getAdmin(), ADMIN_FLAG);
  }

  @Test
  public void shouldReturnEmail() {
    assertEquals(managementUserDto.getEmail(), EMAIL);
  }

  @Test
  public void shouldReturnExtractionIps() {
    assertEquals(managementUserDto.getExtractionIps(), ips);
  }

  @Test
  public void shouldReturnName() {
    assertEquals(managementUserDto.getName(), NAME);
  }

  @Test
  public void shouldReturnEnable() {
    assertEquals(managementUserDto.getEnable(), ENABLE_FLAG);
  }

  @Test
  public void shouldReturnPhone() {
    assertEquals(managementUserDto.getPhone(), PHONE);
  }

  @Test
  public void shouldReturnExtraction() {
    assertEquals(managementUserDto.getExtraction(), EXTRACTION);
  }

  @Test
  public void shouldReturnSurname() {
    assertEquals(managementUserDto.getSurname(), SURNAME);
  }

  @Test
  public void shouldReturnFieldCenter() {
    FieldCenterDTO fieldCenter = new FieldCenterDTO();
    fieldCenter.acronym = "RS";

    FieldCenterDTO fieldCenterDTOToCompare = new FieldCenterDTO();
    fieldCenterDTOToCompare.acronym = "RS";

    managementUserDto.setFieldCenter(fieldCenter);
    assertEquals(managementUserDto.getFieldCenter(), fieldCenterDTOToCompare);
  }
}