package br.org.otus.laboratory.unattached;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class UnattachedLaboratoryDaoBean extends MongoGenericDao<Document> implements UnattachedLaboratoryDao {
  private static final String COLLECTION_NAME = "unattached_laboratory";

  public UnattachedLaboratoryDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(UnattachedLaboratory unattachedLaboratory) {
    super.persist(UnattachedLaboratory.serializeToString(unattachedLaboratory));
  }

  @Override
  public ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) throws DataNotFoundException {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $match:{\n" +
      "            \"fieldCenterAcronym\":"+fieldCenterAcronym+",\n" +
      "            \"collectGroupName\":"+collectGroupDescriptorName+",\n" +
      "            \"availableToAttache\":true" +
      "        }\n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $sort: {\n" +
      "            \"identification\":-1\n" +
      "        }\n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $skip: "+((page - 1) * quantityByPage)+"\n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $limit: "+quantityByPage+"\n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $project: {\n" +
      "            _id:{$toString: \"$_id\"},\n" +
      "            collectGroupName : \"$collectGroupName\",\n" +
      "            fieldCenterAcronym : \"$fieldCenterAcronym\",\n" +
      "            identification: \"$identification\"\n" +
      "        }  \n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $group: { _id: \"\", unattachedLaboratoryList:{$push: \"$$ROOT\"}}\n" +
      "    }"));
    Document resultsDocument = super.aggregate(pipeline).first();
    if (resultsDocument == null) {
      throw new DataNotFoundException("There are no results");
    }
    return ListUnattachedLaboratoryDTO.deserialize(resultsDocument.toJson());
  }

  @Override
  public UnattachedLaboratory find(int laboratoryIdentification) throws DataNotFoundException {
    Document resultsDocument = super.collection.find(new Document("identification", laboratoryIdentification)).first();
    if (resultsDocument == null) {
      throw new DataNotFoundException("Laboratory not found");
    }
    return  UnattachedLaboratory.deserialize(resultsDocument.toJson());
  }

  @Override
  public void update(Integer identification, UnattachedLaboratory unattachedLaboratory) {
    Document parsed = Document.parse(UnattachedLaboratory.serializeToString(unattachedLaboratory));
    super.collection.findOneAndReplace(new Document("identification", identification),parsed);
  }

  @Override
  public UnattachedLaboratory findById(String laboratoryOid) throws DataNotFoundException {
    Document resultsDocument = super.collection.find(new Document("_id", new ObjectId(laboratoryOid))).first();
    if (resultsDocument == null) {
      throw new DataNotFoundException("Laboratory not found");
    }
    return  UnattachedLaboratory.deserialize(resultsDocument.toJson());
  }
}
