package org.ccem.otus.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.Participant;
import org.ccem.otus.model.Sex;

public class ParticipantBuilder {
	
	private Participant participant;
	private SimpleDateFormat simpleDateFormat;
	private List<FieldCenter> fieldCenters;
	
	public ParticipantBuilder(List<FieldCenter> availableFieldCenters) {
		fieldCenters = new ArrayList<FieldCenter>();
		fieldCenters = availableFieldCenters;
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
		
	public Participant buildFromPartipantToImport(ParticipantImport participantToImport) {
		participant = new Participant(participantToImport.getRn());
		participant.setName(participantToImport.getName());
		participant.setSex(Sex.valueOf(participantToImport.getSex()));
		try {
			participant.setBirthdate(simpleDateFormat.parse(participantToImport.getBirthdate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		participant.setFieldCenter(getFieldCenterByInitials(participantToImport.getCenter()));
		
		return participant;
	}
	
	private FieldCenter getFieldCenterByInitials(String fieldCenterInitials) {
		for(FieldCenter fieldCenter : fieldCenters) {
			if(fieldCenter.getAcronym().equals(fieldCenterInitials)){
				return fieldCenter;
			}
		}
		return null;
	}

}
