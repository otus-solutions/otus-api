package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.participant.tube.Tube;

import java.util.List;

public interface TubeGenerator {

	List<Tube> generateTubes(TubeSeed tubeSeed);

}
