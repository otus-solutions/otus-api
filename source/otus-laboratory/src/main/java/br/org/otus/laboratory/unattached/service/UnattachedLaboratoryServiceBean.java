package br.org.otus.laboratory.unattached.service;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeService;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.UnattachedLaboratoryDao;
import br.org.otus.laboratory.unattached.enums.UnattachedLaboratoryActions;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
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
  public void create(String userEmail, Integer unattachedLaboratoryLastInsertion, CollectGroupDescriptor collectGroupDescriptor, FieldCenter fieldCenter){
    List<Tube> tubes = tubeService.generateTubes(TubeSeed.generate(fieldCenter, collectGroupDescriptor));
    UnattachedLaboratory laboratory = new UnattachedLaboratory(unattachedLaboratoryLastInsertion, fieldCenter.getAcronym(), collectGroupDescriptor.getName(), tubes);
    laboratory.addUserHistory(userEmail, UnattachedLaboratoryActions.CREATED);
    unattachedLaboratoryDao.persist(laboratory);
  }

  @Override
  public ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) throws DataNotFoundException {
    return unattachedLaboratoryDao.find(fieldCenterAcronym, collectGroupDescriptorName, page, quantityByPage);
  }
}
