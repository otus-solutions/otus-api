package unattached;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import org.ccem.otus.model.FieldCenter;

public interface UnattachedLaboratoryService {
  UnattachedLaboratory create(Integer unattachedLaboratoryLastInsertion, CollectGroupDescriptor collectGroupDescriptor, FieldCenter fieldCenterAcronym);
}
