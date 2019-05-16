package br.org.otus.laboratory;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Aliquot.class})
public class ParticipantLaboratoryResourceTest {

  private static final String CONVERTED_ALIQUOT_JSON = "{\n" +
    "    \"code\" : \"323000006\",\n" +
    "    \"role\":\"EXAM\",\n" +
    "    \"aliquotHistory\":[{\n" +
    "    \t\"type\": \"CONVERTED STORAGE\",\n" +
    "    \t\"userEmail\": \"fdrtec@gmail.com\",\n" +
    "    \t\"description\": \"Falta de material para completar os exames\",\n" +
    "    \t\"date\": \"2019-05-14T12:36:23.631Z\"\n" +
    "    }]\n" +
    "}";

  @InjectMocks
  private ParticipantLaboratoryResource resource;
  @Mock
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Mock
  private Aliquot convertedAliquot;

  @Test
  public void convertAliquotRole() throws Exception {
    mockStatic(Aliquot.class);
    when(Aliquot.class, "deserialize", CONVERTED_ALIQUOT_JSON).thenReturn(convertedAliquot);

    resource.convertAliquotRole(CONVERTED_ALIQUOT_JSON);
    verify(participantLaboratoryFacade, Mockito.times(1)).convertAliquotRole(convertedAliquot);
  }
}