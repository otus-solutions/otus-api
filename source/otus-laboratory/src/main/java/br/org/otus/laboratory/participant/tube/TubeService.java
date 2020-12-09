package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface TubeService {

  List<Tube> generateTubes(TubeSeed tubeSeed) throws DataNotFoundException;

}
