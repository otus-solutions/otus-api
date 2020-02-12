package unattached;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import org.ccem.otus.model.FieldCenter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UnattachedLaboratoryServiceBean implements UnattachedLaboratoryService{

  @Inject
  private TubeService tubeService;
  @Inject
  private UnattachedLaboratoryDao unattachedLaboratoryDao;

  @Override
  public UnattachedLaboratory create(Integer unattachedLaboratoryLastInsertion, CollectGroupDescriptor collectGroupDescriptor, FieldCenter fieldCenter){
    List<Tube> tubes = tubeService.generateTubes(TubeSeed.generate(fieldCenter, collectGroupDescriptor));
    UnattachedLaboratory laboratory = new UnattachedLaboratory(unattachedLaboratoryLastInsertion, fieldCenter.getAcronym(), collectGroupDescriptor.getName(), tubes);
    unattachedLaboratoryDao.persist(laboratory);
    return laboratory;
  }
}
