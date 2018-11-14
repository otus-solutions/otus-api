package br.org.otus.laboratory.project.aliquot;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import java.util.ArrayList;
import java.util.List;

public class WorkAliquotFactory {
  
  public static List<WorkAliquot> getAliquotList(ParticipantLaboratoryDao participantLaboratoryDao, ParticipantDao participantDao) throws DataNotFoundException {
    List<WorkAliquot> aliquotList = new ArrayList<WorkAliquot>();

    List<ParticipantLaboratory> participantList = participantLaboratoryDao.getAllParticipantLaboratory();

    for (ParticipantLaboratory participantLaboratory : participantList) {
      Participant participant = participantDao.findByRecruitmentNumber(participantLaboratory.getRecruitmentNumber());

      participantLaboratory.getTubes().forEach(tube -> {
        tube.getAliquots().forEach(aliquot -> {
//          WorkAliquot workAliquot = new WorkAliquot(aliquot, participant.getRecruitmentNumber(), participant.getBirthdate(), participant.getSex(), participant.getFieldCenter());
//          aliquotList.add(workAliquot);
        });
      });
    }
    return aliquotList;
  }

  public static List<WorkAliquot> getAliquotsByPeriod(ParticipantLaboratoryDao participantLaboratoryDao, WorkAliquotFiltersDTO workAliquotFiltersDTO) throws DataNotFoundException {
    return participantLaboratoryDao.getAliquotsByPeriod(workAliquotFiltersDTO);
  }

  public static WorkAliquot getAliquot(ParticipantLaboratoryDao participantLaboratoryDao, WorkAliquotFiltersDTO workAliquotFiltersDTO) {
    return participantLaboratoryDao.getAliquot(workAliquotFiltersDTO);
  }
}
