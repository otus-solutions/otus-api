package br.org.otus.laboratory.participant.tube;

import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;

public interface TubeService {

	public List<Tube> generateTubes(TubeSeed tubeSeed);

}
