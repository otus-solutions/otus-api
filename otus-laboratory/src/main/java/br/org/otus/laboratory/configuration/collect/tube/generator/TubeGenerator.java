package br.org.otus.laboratory.configuration.collect.tube.generator;

import java.util.List;

import br.org.otus.laboratory.participant.tube.Tube;

public interface TubeGenerator {

	List<Tube> generateTubes(TubeSeed tubeSeed);

}
