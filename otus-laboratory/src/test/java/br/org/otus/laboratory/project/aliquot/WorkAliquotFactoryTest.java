package br.org.otus.laboratory.project.aliquot;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;

public class WorkAliquotFactoryTest {

  @Mock
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Mock
  private ParticipantDao participantDao;
  @Mock
  private WorkAliquotFiltersDTO workAliquotFiltersDTO;

  @Test
  public void method_getAliquotList_should_call_getAllParticipantLaboratory() throws DataNotFoundException {
    ParticipantLaboratoryDao mock = PowerMockito.mock(ParticipantLaboratoryDao.class);

    WorkAliquotFactory.getAliquotList(mock, participantDao);

    Mockito.verify(mock, Mockito.times(1)).getAllParticipantLaboratory();
  }

  @Test
  public void method_getAliquotsByPeriod_should_call_getAliquotsByPeriod() throws DataNotFoundException {
    ParticipantLaboratoryDao mock = PowerMockito.mock(ParticipantLaboratoryDao.class);

    WorkAliquotFactory.getAliquotsByPeriod(mock, workAliquotFiltersDTO);

    Mockito.verify(mock, Mockito.times(1)).getAliquotsByPeriod(workAliquotFiltersDTO);
  }

  @Ignore
  @Test
  public void method_getAliquotsByPeriod_should_return_a_list_of_workAliquot() throws DataNotFoundException {
    ParticipantLaboratoryDao mock = PowerMockito.mock(ParticipantLaboratoryDao.class);

    Assert.assertThat(WorkAliquotFactory.getAliquotsByPeriod(mock, workAliquotFiltersDTO), Matchers.instanceOf(WorkAliquot.class));
  }

  @Test
  public void method_getAliquot_should_call_method_getAliquot() {
    ParticipantLaboratoryDao mock = PowerMockito.mock(ParticipantLaboratoryDao.class);

    WorkAliquotFactory.getAliquot(mock, workAliquotFiltersDTO);

    Mockito.verify(mock, Mockito.times(1)).getAliquot(workAliquotFiltersDTO);
  }

}
