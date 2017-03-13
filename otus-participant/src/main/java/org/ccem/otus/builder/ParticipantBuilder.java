package org.ccem.otus.builder;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.Participant;
import org.ccem.otus.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParticipantBuilder {

	private Participant participant;
	private List<FieldCenter> fieldCenters;

	public ParticipantBuilder(List<FieldCenter> availableFieldCenters) {
		fieldCenters = new ArrayList<FieldCenter>();
		fieldCenters = availableFieldCenters;
	}

	public Participant buildFromPartipantToImport(ParticipantImport participantToImport) {
		participant = new Participant(participantToImport.getRn());
		participant.setName(participantToImport.getName());
		participant.setSex(Sex.valueOf(participantToImport.getSex()));
		participant.setBirthdate(new ImmutableDate(LocalDate.parse(participantToImport.getBirthdate())));

		participant.setFieldCenter(getFieldCenterByInitials(participantToImport.getCenter()));

		return participant;
	}

	private FieldCenter getFieldCenterByInitials(String fieldCenterInitials) {
		for (FieldCenter fieldCenter : fieldCenters) {
			if (fieldCenter.getAcronym().equals(fieldCenterInitials)) {
				return fieldCenter;
			}
		}
		return null;
	}

}
