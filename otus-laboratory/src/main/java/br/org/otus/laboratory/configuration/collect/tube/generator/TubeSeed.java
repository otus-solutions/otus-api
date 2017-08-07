package br.org.otus.laboratory.configuration.collect.tube.generator;

import org.ccem.otus.participant.model.Participant;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;

public class TubeSeed {

	private Participant participant;
	private CollectGroupDescriptor collectGroupDescriptor;
	private int tubeCount;

	private TubeSeed(Participant participant, CollectGroupDescriptor collGroupDescriptor, Integer tubeCount) {
		this.participant = participant;
		this.collectGroupDescriptor = collGroupDescriptor;
		this.tubeCount = tubeCount;
	}

	public Integer getFieldCenterCode() {
		return participant.getFieldCenter().getCode();
	}

	public String getFieldCenterAcronym() {
		return participant.getFieldCenter().getAcronym();
	}

	public CollectGroupDescriptor getCollectGroupDescriptor() {
		return collectGroupDescriptor;
	}

	public String getParticipantCollectGroupName() {
		return getCollectGroupDescriptor().getName();
	}

	public Integer getTubeCount() {
		return tubeCount;
	}

	public void setTubeCount(Integer count) {
		this.tubeCount = count;
	}

	public static TubeSeed generate(Participant participant, CollectGroupDescriptor collectGroupDescriptor) {
		return new TubeSeed(participant, collectGroupDescriptor, 0);
	}

}
