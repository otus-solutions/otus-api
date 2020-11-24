package br.org.otus.security.dtos;

import br.org.otus.communication.TemplateEmailKeys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ParticipantCommunicationDataDto.class})
public class ParticipantCommunicationDataDtoTest {
  private ParticipantCommunicationDataDto dto = new ParticipantCommunicationDataDto();
  private String dtoSerialized;

  @Before
  public void setUp() throws Exception {
    dto.setEmail("mock@email.com");
    dto.setId(TemplateEmailKeys.RESET_PASSWD_PARTICIPANT.getValue());
    dto.pushVariable("host", "http://wwww.mock.com.br");
    dto.pushVariable("token", "1234567890");
    dtoSerialized = ParticipantCommunicationDataDto.serialize(dto);
  }

  @Test
  public void method_serialize_should_transform_instance_into_string(){
    assertEquals(dtoSerialized, ParticipantCommunicationDataDto.serialize(dto));
  }

  @Test
  public void method_deserialize_should_transform_string_into(){
    assertEquals(dto.getClass(), ParticipantCommunicationDataDto.deserialize(dtoSerialized).getClass());
  }
}