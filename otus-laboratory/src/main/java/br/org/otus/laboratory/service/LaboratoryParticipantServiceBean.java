package br.org.otus.laboratory.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.ParticipantDao;

import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.participant.QualityControl;
import br.org.otus.laboratory.participant.Tube;
import br.org.otus.laboratory.persistence.LaboratoryParticipantDao;
import br.org.otus.laboratory.persistence.ParticipantQualityControlDao;

@Stateless
public class LaboratoryParticipantServiceBean implements LaboratoryParticipantService {

	@Inject
	private LaboratoryParticipantDao labParticipantDao;
	@Inject
	private LaboratoryConfigurationService laboratoryConfigurationService;
	@Inject
	private ParticipantDao participantDao;
	@Inject
	private ParticipantQualityControlDao participantQualityControlDao;

	@Override
	public LaboratoryParticipant create(Long rn) throws DataNotFoundException {
		Participant participant = participantDao.findByRecruitmentNumber(rn);
		QualityControl qualityControl = participantQualityControlDao.findParticipantCQGroup(rn);
		if(qualityControl != null){
			LaboratoryParticipant laboratoryParticipant = new LaboratoryParticipant(rn, raffleCQGroup(rn), new ArrayList<>(), new ArrayList<>());
			laboratoryParticipant.setTubes(generateTubesQualityControl(participant.getFieldCenter().getCode(), qualityControl.getCode()));
			labParticipantDao.persist(laboratoryParticipant);
			return laboratoryParticipant;
		} else {
			LaboratoryParticipant laboratoryParticipant = new LaboratoryParticipant(rn, null, new ArrayList<>(), new ArrayList<>());
			laboratoryParticipant.setTubes(generateTubesDefault(participant.getFieldCenter().getCode()));
			labParticipantDao.persist(laboratoryParticipant);
			return laboratoryParticipant;
		}
	}

	@Override
	public LaboratoryParticipant createEmptyLaboratory(Long rn) throws DataNotFoundException {
		LaboratoryParticipant laboratoryParticipant = new LaboratoryParticipant(rn, new ArrayList<>(), new ArrayList<>());
		labParticipantDao.persist(laboratoryParticipant);
		return laboratoryParticipant;
	}

	@Override
	public LaboratoryParticipant addTubesToParticipant(Long rn) throws DataNotFoundException {
		Participant participant = participantDao.findByRecruitmentNumber(rn);
		QualityControl qualityControl = participantQualityControlDao.findParticipantCQGroup(rn);
		
		if(qualityControl != null){
			LaboratoryParticipant laboratoryParticipant = labParticipantDao.findByRecruitmentNumber(rn);
			laboratoryParticipant.setTubes(generateTubesQualityControl(participant.getFieldCenter().getCode(), qualityControl.getCode()));
			labParticipantDao.updateLaboratoryData(laboratoryParticipant);
			return laboratoryParticipant;
		} else {
			LaboratoryParticipant laboratoryParticipant = labParticipantDao.findByRecruitmentNumber(rn);
			laboratoryParticipant.setTubes(generateTubesDefault(participant.getFieldCenter().getCode()));
			labParticipantDao.updateLaboratoryData(laboratoryParticipant);
			return laboratoryParticipant;
		}
	}

	private List<Tube> generateTubesDefault(Integer fieldCenterCode) {
		List<Tube> tubes = new ArrayList<Tube>();
		
		List<String> defaultTubeList = laboratoryConfigurationService.getDefaultTubeList();
		Integer defaultTubesQuantity = defaultTubeList.size();
		List<String> defaultCodeList = laboratoryConfigurationService.getCodes(defaultTubesQuantity, fieldCenterCode);
		
		for (int i = 0; i < defaultTubeList.size(); i++) {
			Tube tube = new Tube(defaultTubeList.get(i), defaultCodeList.get(i));
			tubes.add(tube);
		}
		
		return tubes;
	}
	
	private List<Tube> generateTubesQualityControl(Integer fieldCenterCode, String qualityControl) {
		List<Tube> tubes = new ArrayList<Tube>();
		
		List<String> qualityControlTubeList = laboratoryConfigurationService.getQualityControlTubeList(qualityControl);
		Integer qualityControlTubesQuantity = qualityControlTubeList.size();
		List<String> qualityControlCodeList = laboratoryConfigurationService.getCodes(qualityControlTubesQuantity, fieldCenterCode);
		
		for (int i = 0; i < qualityControlTubeList.size(); i++) {
			Tube tube = new Tube(qualityControlTubeList.get(i), qualityControlCodeList.get(i));
			tubes.add(tube);
		}
		
		return tubes;
	}
	
	//TODO: Quando existir um sorteio para o código CQGroup o código do método deve ser modificado 
	private String raffleCQGroup(Long rn) throws DataNotFoundException {
		QualityControl qualityControl = participantQualityControlDao.findParticipantCQGroup(rn);
		return qualityControl.getCode();
	}

	@Override
	public boolean hasLaboratory(Long rn){
		try {
			labParticipantDao.findByRecruitmentNumber(rn);
			return true;
		} catch (DataNotFoundException e) {
			return false;
		}
	}

	@Override
	public LaboratoryParticipant getLaboratory(Long rn) throws DataNotFoundException {
		try {
			LaboratoryParticipant laboratoryParticipant = labParticipantDao.findByRecruitmentNumber(rn);
			return laboratoryParticipant;
		} catch (DataNotFoundException e) {
			return null;
		}
	}

}
