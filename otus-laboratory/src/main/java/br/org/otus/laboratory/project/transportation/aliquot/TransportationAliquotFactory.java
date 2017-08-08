package br.org.otus.laboratory.project.transportation.aliquot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;

public class TransportationAliquotFactory {
	
	@Inject
	private static ParticipantLaboratoryDao participantLaboratoryDao;
	@Inject
	private static ParticipantDao participantDao;
	
	public static List<TransportationAliquot> getTransportationAliquotList(){
		ArrayList<TransportationAliquot> aliquotList = new ArrayList<TransportationAliquot>();
		
		ArrayList<ParticipantLaboratory>  participantList = participantLaboratoryDao.getAllParticipantLaboratory();
		
		participantList.forEach(participantLaboratory -> {
			try {
				Participant participant = participantDao.findByRecruitmentNumber(participantLaboratory.getRecruitmentNumber());
				
				participantLaboratory.getTubes().forEach(tube -> {
					tube.getAliquots().forEach(aliquot -> {
						TransportationAliquot transportationAliquot;
						transportationAliquot = new TransportationAliquot(aliquot, participant.getRecruitmentNumber(), participant.getBirthdate(), participant.getSex());
						aliquotList.add(transportationAliquot);
					});
					
				});
			} catch (DataNotFoundException e) {
				// TODO Tratar Exeption
				e.printStackTrace();
			}
		});
		
		
		return aliquotList;
	}
	

}
