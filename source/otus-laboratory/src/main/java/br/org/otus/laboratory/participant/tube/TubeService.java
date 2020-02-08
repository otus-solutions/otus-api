package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;

import java.util.List;

public interface TubeService {

  public List<Tube> generateTubes(TubeSeed tubeSeed);

}
