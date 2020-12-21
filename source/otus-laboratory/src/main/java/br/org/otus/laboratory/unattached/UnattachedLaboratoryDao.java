package br.org.otus.laboratory.unattached;

import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface UnattachedLaboratoryDao {
  void persist(UnattachedLaboratory unattachedLaboratory);

  ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) throws DataNotFoundException;

  UnattachedLaboratory find(int laboratoryIdentification) throws DataNotFoundException;

  void update(Integer identification, UnattachedLaboratory unattachedLaboratory);

  UnattachedLaboratory findById(String laboratoryOid) throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);
}
