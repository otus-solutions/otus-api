package br.org.otus.laboratory.persistence;

import br.org.otus.laboratory.participant.QualityControl;

public interface ParticipantQualityControlDao {
	
	public QualityControl findParticipantCQGroup(Long rn);

}
