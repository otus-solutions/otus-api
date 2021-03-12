package org.ccem.otus.participant.business.extraction.factories;

import org.ccem.otus.participant.business.extraction.enums.ParticipantExtractionHeaders;

import java.util.LinkedList;
import java.util.List;

public class ParticipantExtractionHeadersFactory {

  private List<String> headers;

  public ParticipantExtractionHeadersFactory() {
    this.headers = new LinkedList<String>();
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(ParticipantExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.FULL_NAME.getValue());
    this.headers.add(ParticipantExtractionHeaders.SEX.getValue());
    this.headers.add(ParticipantExtractionHeaders.CENTER.getValue());
    this.headers.add(ParticipantExtractionHeaders.BIRTHDATE.getValue());
    this.headers.add(ParticipantExtractionHeaders.EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.REGISTERED_BY.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_PHONE.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_PHONE_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_PHONE.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_PHONE_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_PHONE.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_PHONE_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_PHONE.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_PHONE_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_PHONE.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_PHONE_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_EMAIL_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_EMAIL_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_EMAIL_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_EMAIL_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_EMAIL.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_EMAIL_OBS.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_CEP.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_STREET.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_COMPLEMENTS.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_NEIGHBOURHOOD.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_CITY.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_UF.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_CENSUS.getValue());
    this.headers.add(ParticipantExtractionHeaders.MAIN_ADDRESS_OBSERVATION.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_CEP.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_STREET.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_COMPLEMENTS.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_NEIGHBOURHOOD.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_CITY.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_UF.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_CENSUS.getValue());
    this.headers.add(ParticipantExtractionHeaders.SECOND_ADDRESS_OBSERVATION.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_CEP.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_STREET.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_COMPLEMENTS.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_NEIGHBOURHOOD.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_CITY.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_UF.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_CENSUS.getValue());
    this.headers.add(ParticipantExtractionHeaders.THIRD_ADDRESS_OBSERVATION.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_CEP.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_STREET.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_COMPLEMENTS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_NEIGHBOURHOOD.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_CITY.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_UF.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_CENSUS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FOURTH_ADDRESS_OBSERVATION.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_CEP.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_STREET.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_COMPLEMENTS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_NEIGHBOURHOOD.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_CITY.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_UF.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_CENSUS.getValue());
    this.headers.add(ParticipantExtractionHeaders.FIFTH_ADDRESS_OBSERVATION.getValue());
  }

}
