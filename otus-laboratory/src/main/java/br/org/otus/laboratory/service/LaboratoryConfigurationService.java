package br.org.otus.laboratory.service;

import java.util.List;

public interface LaboratoryConfigurationService {

	String getTubeCode(Integer fieldCenterCode);

	List<String> getCodes(Integer quantity, Integer fieldCenterCode);

	List<String> getDefaultTubeList();
	
	List<String> getQualityControlTubeList(String cqGroup);

}
