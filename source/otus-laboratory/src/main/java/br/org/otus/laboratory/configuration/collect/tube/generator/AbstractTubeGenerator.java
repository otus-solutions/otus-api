package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import br.org.otus.laboratory.participant.tube.Tube;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTubeGenerator implements TubeGenerator {

  @Inject
  protected LaboratoryConfigurationService laboratoryConfigurationService;

  @Override
  public List<Tube> generateTubes(TubeSeed tubeSeed) throws DataNotFoundException {
    List<TubeDefinition> tubeTypeDefinitions = getTubeDefinitions(tubeSeed);
    List<String> codesToUse = laboratoryConfigurationService.generateCodes(tubeSeed);
    List<Tube> tubes = new ArrayList<>();

    for (TubeDefinition tubeDefinition : tubeTypeDefinitions) {
      Integer count = tubeDefinition.getCount();

      for (int i = 0; i < count; i++) {
        Tube newTube = createTube(codesToUse.get(0), tubeDefinition);
        tubes.add(newTube);
        codesToUse.remove(0);
      }
    }

    return tubes;
  }

  private Tube createTube(String code, TubeDefinition tubeDefinition) {
    return new Tube(tubeDefinition.getType(), tubeDefinition.getMoment(), code, tubeDefinition.getGroup());
  }

  public abstract List<TubeDefinition> getTubeDefinitions(TubeSeed tubeSeed) throws DataNotFoundException;

  protected Integer sumTubeCounts(List<TubeDefinition> tubeDefinitions) {
    Integer tubeCount = 0;
    for (TubeDefinition tubeDefinition : tubeDefinitions) {
      tubeCount += tubeDefinition.getCount();
    }
    return tubeCount;
  }
}
