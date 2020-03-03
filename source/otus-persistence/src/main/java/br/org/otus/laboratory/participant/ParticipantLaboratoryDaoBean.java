package br.org.otus.laboratory.participant;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeCollectionData;
import org.ccem.otus.service.ParseQuery;

public class ParticipantLaboratoryDaoBean extends MongoGenericDao<Document> implements ParticipantLaboratoryDao {
  private static final String COLLECTION_NAME = "participant_laboratory";
  private static final String COLLECTION_ALIQUOT = "aliquot";
  private static final String TUBES_ALIQUOTS_CODE = "code";
  private static final String RECRUITMENT_NUMBER = "recruitmentNumber";

  public ParticipantLaboratoryDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(ParticipantLaboratory laboratoryParticipant) {
    super.persist(ParticipantLaboratory.serialize(laboratoryParticipant));
  }

  @Override
  public ParticipantLaboratory find() {
    Document document = super.findFirst();
    return ParticipantLaboratory.deserialize(document.toJson());
  }

  @Override
  public ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException {
    Document result = collection.find(eq(RECRUITMENT_NUMBER, rn)).first();
    if (result == null) {
      throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: " + rn + " not found."));
    }
    return ParticipantLaboratory.deserialize(result.toJson());
  }

  @Override
  public Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException {
    Document parsedCollectionData = Document.parse(TubeCollectionData.serialize(tube.getTubeCollectionData()));
    UpdateResult updateLabData = collection.updateOne(and(eq(RECRUITMENT_NUMBER, rn), eq("tubes.code", tube.getCode())), set("tubes.$.tubeCollectionData", parsedCollectionData),
      new UpdateOptions().upsert(false));

    if (updateLabData.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: " + rn + " does not exists."));
    }

    return tube;
  }

  public ParticipantLaboratory findParticipantLaboratory(String aliquotCode) throws DataNotFoundException {
    Document first = collection
      .aggregate(Arrays.asList(Aggregates.lookup(COLLECTION_ALIQUOT, TUBES_ALIQUOTS_CODE, TUBES_ALIQUOTS_CODE, COLLECTION_ALIQUOT), Aggregates.match(eq(TUBES_ALIQUOTS_CODE, aliquotCode)))).first();
    if (first == null) {
      throw new DataNotFoundException();
    }
    return ParticipantLaboratory.deserialize(first.toJson());
  }

  @Override
  public ArrayList<SimpleAliquot> getFullAliquotsList() {
    ArrayList<SimpleAliquot> fullList = new ArrayList<SimpleAliquot>();
    FindIterable<Document> list = collection.find();
    list.forEach((Block<Document>) document -> {
      ParticipantLaboratory laboratory = ParticipantLaboratory.deserialize(document.toJson());
      fullList.addAll(laboratory.getAliquotsList());
    });

    return fullList;
  }

  @Override
  public ArrayList<ParticipantLaboratory> getAllParticipantLaboratory() {
    FindIterable<Document> result = super.list();
    ArrayList<ParticipantLaboratory> participantList = new ArrayList<ParticipantLaboratory>();

    result.forEach((Block<Document>) document -> {
      participantList.add(ParticipantLaboratory.deserialize(document.toJson()));
    });
    return participantList;
  }

  @Override
  public LinkedList<LaboratoryRecordExtraction> getLaboratoryExtractionByParticipant() {
    return null;
  }

  @Override
  public AggregateIterable<Document> aggregate(ArrayList<Bson> pipeline) {
    return collection.aggregate(pipeline).allowDiskUse(true);
  }

  @Override
  public Tube getTube(String tubeCode) throws DataNotFoundException {
    Document first = collection.aggregate(Arrays.asList(
      ParseQuery.toDocument("{ \n" +
        "        $match: {\n" +
        "            \"tubes.code\":'" + tubeCode + "'" +
        "        }\n" +
        "    }"),
      ParseQuery.toDocument("{\n" +
        "        $unwind: \"$tubes\"\n" +
        "    }"),
      ParseQuery.toDocument("{ \n" +
        "        $match: {\n" +
        "            \"tubes.code\":" + tubeCode + "'" +
        "        }\n" +
        "    }"),
      ParseQuery.toDocument("{\n" +
        "        $replaceRoot: { newRoot: \"$tubes\" }\n" +
        "    }")
    )).first();

    if (first == null) {
      throw new DataNotFoundException("Tube not Found");
    }

    return Tube.deserialize(first.toJson());
  }

  @Override
  public ObjectId getTubeLocationPoint(String tubeCode) throws DataNotFoundException {
    Document locationPoint = collection.find(eq("tubes.code", tubeCode)).projection(new Document("locationPoint", 1)).first();
    if (locationPoint == null) {
      throw new DataNotFoundException();
    }
    return (ObjectId) locationPoint.get("locationPoint");
  }

}
