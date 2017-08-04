package br.org.otus.laboratory.participant.collect.tube;

import java.util.List;

import br.org.otus.laboratory.participant.collect.tube.generator.TubeSeed;

public interface TubeService {

	public List<Tube> generateTubes(TubeSeed tubeSeed);

}
