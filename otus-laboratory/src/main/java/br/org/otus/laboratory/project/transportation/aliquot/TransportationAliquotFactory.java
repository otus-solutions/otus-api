package br.org.otus.laboratory.project.transportation.aliquot;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import java.util.ArrayList;
import java.util.List;

public class TransportationAliquotFactory {


	public static List<TransportationAliquot> getTransportationAliquotList(ParticipantLaboratoryDao participantLaboratoryDao, ParticipantDao participantDao) throws DataNotFoundException {
		List<TransportationAliquot> aliquotList = new ArrayList<>();

		List<ParticipantLaboratory> participantList = participantLaboratoryDao.getAllParticipantLaboratory();

		for (ParticipantLaboratory participantLaboratory : participantList) {
			Participant participant;

			participant = participantDao.findByRecruitmentNumber(participantLaboratory.getRecruitmentNumber());

			participantLaboratory.getTubes().forEach(tube -> {
				tube.getAliquots().forEach(aliquot -> {
					TransportationAliquot transportationAliquot;
					transportationAliquot = new TransportationAliquot(aliquot, participant.getRecruitmentNumber(),
							participant.getBirthdate(), participant.getSex(), participant.getFieldCenter());
					aliquotList.add(transportationAliquot);
				});
			});

		}

		return aliquotList;
	}

}
