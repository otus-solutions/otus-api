package br.org.otus.laboratory.service;

import br.org.otus.laboratory.config.Group;
import br.org.otus.laboratory.config.LaboratoryConfiguration;
import br.org.otus.laboratory.persistence.LaboratoryConfigurationDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class LaboratoryConfigurationServiceBean implements LaboratoryConfigurationService {

	@Inject
	private LaboratoryConfigurationDao laboratoryConfigurationDao;

	@Override
	public String getTubeCode(Integer fieldCenterCode) {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
		Integer lastInsertion = laboratoryConfiguration.getCodeDefinitions().getLastInsertion();
		return getTubeCodePrefix(fieldCenterCode) + getTubeCodeSufix(lastInsertion);
	}

	@Override
	public List<String> getCodes(Integer quantity, Integer fieldCenterCode) {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
		List<String> codeList = new ArrayList<String>();
		Integer lastInsertion = laboratoryConfiguration.getCodeDefinitions().allocCode(quantity);
		laboratoryConfigurationDao.update(laboratoryConfiguration);
		String prefixCode = getTubeCodePrefix(fieldCenterCode);
		for (int i = lastInsertion; i < lastInsertion + quantity; i++) {
			String sufixCode = getTubeCodeSufix(i);
			codeList.add(prefixCode + sufixCode);
		}
		return codeList;
	}

	@Override
	public List<String> getDefaultTubeList() {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
		List<String> defaultTubes = laboratoryConfiguration.getGroupDefinitions().getDefaultGroup().get(0).getTubeList();
		return defaultTubes;
	}

	@Override
	public List<String> getQualityControlTubeList(String cqGroup) {
		LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
		Group participantGroup = laboratoryConfiguration.getGroupDefinitions().getQualityControlGroup().stream().filter(qualityGroup -> qualityGroup.getName().equals(cqGroup)).findFirst().get();
		return participantGroup.getTubeList();
	}

	private String getTubeCodePrefix(Integer fieldCenterCode) {
		Integer waveNumber = 3;
		Integer tubeCode = 1;

		return waveNumber.toString() + fieldCenterCode.toString() + tubeCode.toString();
	}

	private String getTubeCodeSufix(Integer lastInsertion) {
		String formatted = String.format("%06d", lastInsertion);
		return formatted;
	}

}
