package org.ccem.otus.importation.service;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.persistence.ParticipantDao;

public class ParticipantImportValidatorServiceBean implements ParticipantImportValidatorService {

	@Inject
	private FieldCenterDao fieldCenterDao;

	@Inject
	private ParticipantDao participantDao;

	private ArrayList<FieldCenter> fieldCenters;
	private ArrayList<String> availableFieldCenterAcronyms;
	private ArrayList<Participant> registeredParticipants;
	private ArrayList<Long> recruitmentNumberList;

	private void setUp() {
		getAvailableFieldCenterAcronyms();
		getRecruitmentNumberList();
	}

	@Override
	public boolean isImportable(Set<ParticipantImport> participantToImports) {
		setUp();
		
		if (availableFieldCenterAcronyms.isEmpty()) {
			// TODO: Create a specific exception
			throw new RuntimeException("Não existem centros cadastrados no sistema!");
		}
		
		participantToImports.forEach(participantToImport -> hasValidFieldCenter(participantToImport));
		
		if(!recruitmentNumberList.isEmpty()) {
			participantToImports.forEach(participantToImport -> isAvailableRecruitmentNumber(participantToImport));
		}

		return true;
	}
	
	private boolean hasValidFieldCenter(ParticipantImport participantToImport) {
		if(!availableFieldCenterAcronyms.contains(participantToImport.getFieldCenterInitials())) {
			// TODO: Create a specific exception
			throw new RuntimeException("Error: Invalid field center. \n" + participantToImport.toString());
		}
		
		return true;
	}
	
	private boolean isAvailableRecruitmentNumber(ParticipantImport participantToImport) {
		if(recruitmentNumberList.contains(participantToImport.getRecruitmentNumber())) {
			// TODO: Create a specific exception
			throw new RuntimeException("Error: Recruitment number already exists. \n" + participantToImport.toString());
		}
		
		return true;
	}
	
	private ArrayList<Long> getRecruitmentNumberList() {
		recruitmentNumberList = new ArrayList<Long>();
		getRegisteredParticipants().forEach(registeredParticipant -> recruitmentNumberList.add(registeredParticipant.getRecruitmentNumber()));
		return recruitmentNumberList;
	}

	private ArrayList<Participant> getRegisteredParticipants() {
		registeredParticipants = new ArrayList<Participant>();
		registeredParticipants = participantDao.find();
		return registeredParticipants;
	}

	private ArrayList<String> getAvailableFieldCenterAcronyms() {
		availableFieldCenterAcronyms = new ArrayList<String>();
		getAllFieldCenters().forEach(fieldCenter -> availableFieldCenterAcronyms.add(fieldCenter.getAcronym()));
		return availableFieldCenterAcronyms;
	}
	
	private ArrayList<FieldCenter> getAllFieldCenters() {
		fieldCenters = new ArrayList<FieldCenter>();
		fieldCenters = fieldCenterDao.find();
		return fieldCenters;
	}

}
