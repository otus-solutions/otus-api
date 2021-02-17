package org.ccem.otus.participant.business.extraction.enums;

public enum ParticipantExtractionHeaders {

  RECRUITMENT_NUMBER("numero_de_recrutamento"),
  FULL_NAME("nome_completo"),
  SEX("sexo"),
  CENTER("centro"),
  BIRTHDATE("data_de_nascimento"),
  EMAIL("email_de_login"),
  REGISTERED_BY("registrado_por"),
  MAIN_PHONE("telefone_principal"),
  MAIN_PHONE_OBS("telefone_principal_observacao"),
  SECOND_PHONE("segundo_telefone"),
  SECOND_PHONE_OBS("segundo_telefone_observacao"),
  THIRD_PHONE("terceiro_telefone"),
  THIRD_PHONE_OBS("terceiro_telefone_observacao"),
  FOURTH_PHONE("quarto_telefone"),
  FOURTH_PHONE_OBS("quarto_telefone_observacao"),
  FIFTH_PHONE("quinto_telefone"),
  FIFTH_PHONE_OBS("quinto_telefone_observacao"),
  MAIN_EMAIL("email_principal"),
  MAIN_EMAIL_OBS("email_principal_observacao"),
  SECOND_EMAIL("segundo_email"),
  SECOND_EMAIL_OBS("segundo_email_observacao"),
  THIRD_EMAIL("terceiro_email"),
  THIRD_EMAIL_OBS("terceiro_email_observacao"),
  FOURTH_EMAIL("quarto_email"),
  FOURTH_EMAIL_OBS("quarto_email_observacao"),
  FIFTH_EMAIL("quinto_email"),
  FIFTH_EMAIL_OBS("quinto_email_observacao"),
  MAIN_ADDRESS_CEP("endereco_principal_cep"),
  MAIN_ADDRESS_STREET("endereco_principal_rua"),
  MAIN_ADDRESS_NUMBER("endereco_principal_numero"),
  MAIN_ADDRESS_COMPLEMENTS("endereco_principal_complemento"),
  MAIN_ADDRESS_NEIGHBOURHOOD("endereco_principal_bairro"),
  MAIN_ADDRESS_CITY("endereco_principal_cidade"),
  MAIN_ADDRESS_UF("endereco_principal_uf"),
  MAIN_ADDRESS_COUNTRY("endereco_principal_pais"),
  MAIN_ADDRESS_OBSERVATION("endereço_principal_observacao"),
  SECOND_ADDRESS_CEP("segundo_endereco_cep"),
  SECOND_ADDRESS_STREET("segundo_endereco_rua"),
  SECOND_ADDRESS_NUMBER("segundo_endereco_numero"),
  SECOND_ADDRESS_COMPLEMENTS("segundo_endereco_complemento"),
  SECOND_ADDRESS_NEIGHBOURHOOD("segundo_endereco_bairro"),
  SECOND_ADDRESS_CITY("segundo_endereco_cidade"),
  SECOND_ADDRESS_UF("segundo_endereco_uf"),
  SECOND_ADDRESS_COUNTRY("segundo_endereco_pais"),
  SECOND_ADDRESS_OBSERVATION("segundo_endereco_observacao"),
  THIRD_ADDRESS_CEP("terceiro_endereco_cep"),
  THIRD_ADDRESS_STREET("terceiro_endereco_rua"),
  THIRD_ADDRESS_NUMBER("terceiro_endereco_numero"),
  THIRD_ADDRESS_COMPLEMENTS("terceiro_endereco_complemento"),
  THIRD_ADDRESS_NEIGHBOURHOOD("terceiro_endereco_bairro"),
  THIRD_ADDRESS_CITY("terceiro_endereco_cidade"),
  THIRD_ADDRESS_UF("terceiro_endereco_uf"),
  THIRD_ADDRESS_COUNTRY("terceiro_endereco_pais"),
  THIRD_ADDRESS_OBSERVATION("terceiro_endereco_observacao"),
  FOURTH_ADDRESS_CEP("quarto_endereco_cep"),
  FOURTH_ADDRESS_STREET("quarto_endereco_rua"),
  FOURTH_ADDRESS_NUMBER("quarto_endereco_numero"),
  FOURTH_ADDRESS_COMPLEMENTS("quarto_endereco_complemento"),
  FOURTH_ADDRESS_NEIGHBOURHOOD("quarto_endereco_bairro"),
  FOURTH_ADDRESS_CITY("quarto_endereco_cidade"),
  FOURTH_ADDRESS_UF("quarto_endereco_uf"),
  FOURTH_ADDRESS_COUNTRY("quarto_endereco_pais"),
  FOURTH_ADDRESS_OBSERVATION("quarto_endereco_observacao"),
  FIFTH_ADDRESS_CEP("quinto_endereco_cep"),
  FIFTH_ADDRESS_STREET("quinto_endereco_rua"),
  FIFTH_ADDRESS_NUMBER("quinto_endereco_numero"),
  FIFTH_ADDRESS_COMPLEMENTS("quinto_endereco_complemento"),
  FIFTH_ADDRESS_NEIGHBOURHOOD("quinto_endereco_bairro"),
  FIFTH_ADDRESS_CITY("quinto_endereco_cidade"),
  FIFTH_ADDRESS_UF("quinto_endereco_uf"),
  FIFTH_ADDRESS_COUNTRY("quinto_endereco_pais"),
  FIFTH_ADDRESS_OBSERVATION("quinto_endereco_observacao");

  private final String value;

  public String getValue() {
    return value;
  }

  ParticipantExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}
