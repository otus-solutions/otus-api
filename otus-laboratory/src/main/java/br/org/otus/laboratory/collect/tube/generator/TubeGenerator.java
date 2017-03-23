package br.org.otus.laboratory.collect.tube.generator;

import java.util.List;

import br.org.otus.laboratory.collect.tube.Tube;

public interface TubeGenerator {

	List<Tube> generateTubes(TubeSeed tubeSeed);

}
