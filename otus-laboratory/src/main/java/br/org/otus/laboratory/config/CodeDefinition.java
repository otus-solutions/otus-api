package br.org.otus.laboratory.config;

public class CodeDefinition {

	private Integer tube;
	private Integer pallet;
	private Integer cryotube;
	private Integer lastInsertion;

	public CodeDefinition(Integer tube, Integer pallet, Integer cryotube,
			Integer lastInsertion) {
		this.lastInsertion = lastInsertion;
		this.tube = tube;
		this.pallet = pallet;
		this.cryotube = cryotube;
	}

	public Integer getTubeCode() {
		return tube;
	}

	public Integer getLastInsertion() {
		return ++lastInsertion;
	}

	public Integer allocCode(Integer quantity) {
		Integer startingPoint = lastInsertion;
		lastInsertion = lastInsertion + quantity;
		return ++startingPoint;
	}
}
