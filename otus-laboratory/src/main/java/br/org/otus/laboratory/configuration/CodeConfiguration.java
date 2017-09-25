package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;

import java.util.ArrayList;
import java.util.List;

public class CodeConfiguration {

	private Integer waveNumberToken;
	private Integer tubeToken;
	private Integer palletToken;
	private Integer cryotubeToken;
	private Integer lastInsertion;

	public Integer getWaveNumberToken() {
		return waveNumberToken;
	}

	public Integer getTubeToken() {
		return tubeToken;
	}

	public Integer getPalletToken() {
		return palletToken;
	}

	public Integer getCryotubeToken() {
		return cryotubeToken;
	}

	public Integer getLastInsertion() {
		return lastInsertion;
	}

	public Integer allocCodeAndGetStartingPoint(Integer allocationSize) {
		Integer startingPoint = lastInsertion;
		lastInsertion += allocationSize;
		return ++startingPoint;
	}

	public List<String> generateCodeList(TubeSeed seed, Integer startingPoint) {
		List<String> codes = new ArrayList<>();
		String codePrefix = this.getTubeCodePrefix(seed.getFieldCenterCode());

		for (Integer insertion = startingPoint; insertion <= lastInsertion; insertion++) {
			String codeSufix = this.getTubeCodeSufix(insertion);
			codes.add(codePrefix + codeSufix);
		}

		return codes;
	}

	private String getTubeCodePrefix(Integer fieldCenterToken) {
		StringBuilder prefixBuilder = new StringBuilder();
		prefixBuilder.append(waveNumberToken.toString());
		prefixBuilder.append(fieldCenterToken.toString());
		prefixBuilder.append(tubeToken.toString());
		return prefixBuilder.toString();
	}

	private String getTubeCodeSufix(Integer number) {
		return String.format("%06d", number);
	}

}
