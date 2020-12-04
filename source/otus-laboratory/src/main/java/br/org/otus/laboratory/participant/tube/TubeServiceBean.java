package br.org.otus.laboratory.participant.tube;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeGenerator;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.CenterGenerator;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.DefaultGenerator;
import br.org.otus.laboratory.configuration.collect.tube.qualifier.QualityControlGenerator;
import br.org.otus.laboratory.configuration.label.LabelReference;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Stateless
public class TubeServiceBean implements TubeService {

  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;

  @Inject
  @DefaultGenerator
  private TubeGenerator defaultTubeGenerator;
  @Inject
  @QualityControlGenerator
  private TubeGenerator qualityControlTubeGenerator;
  @Inject
  @CenterGenerator
  private TubeGenerator centerTubeGenerator;

  @Override
  public List<Tube> generateTubes(TubeSeed tubeSeed) throws DataNotFoundException {
    List<Tube> tubes = new ArrayList<>();
    tubes.addAll(defaultTubeGenerator.generateTubes(tubeSeed));
    tubes.addAll(qualityControlTubeGenerator.generateTubes(tubeSeed));
    tubes.addAll(centerTubeGenerator.generateTubes(tubeSeed));
    reorderTubes(tubes, tubeSeed);
    return tubes;
  }

  private void reorderTubes(List<Tube> tubes, TubeSeed tubeSeed) {
    String orderName = tubeSeed.getCollectGroupName();
    List<LabelReference> order = laboratoryConfigurationService.getLabelOrderByName(orderName);
    order.forEach(mergeReferenceOrderWithTube(tubes));
    Collections.sort(tubes);
  }

  private Consumer<LabelReference> mergeReferenceOrderWithTube(List<Tube> tubes) {
    return new Consumer<LabelReference>() {
      private AtomicInteger counter = new AtomicInteger();

      @Override
      public void accept(LabelReference reference) {
        Tube selectedTube = tubes.stream().filter(filterByReference(reference)).findFirst().get();
        selectedTube.setOrder(counter.incrementAndGet());
      }
    };
  }

  private Predicate<Tube> filterByReference(LabelReference reference) {
    return new Predicate<Tube>() {

      @Override
      public boolean test(Tube tube) {
        boolean isSameGroup = tube.getGroupName().equals(reference.getGroupName());
        boolean isSameType = tube.getType().equals(reference.getType());
        boolean isSameMoment = tube.getMoment().equals(reference.getMoment());
        boolean isNotOrdered = !tube.isOrdered();

        return isSameGroup && isSameType && isSameMoment && isNotOrdered;
      }
    };
  }

}
