package br.org.otus.laboratory.participant;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.*;

import br.org.otus.laboratory.project.exam.utils.ExamResultTube;
import com.mongodb.client.MongoCursor;
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
        "            \"tubes.code\":'" + tubeCode + "'" +
        "        }\n" +
        "    }"),
      ParseQuery.toDocument("{\n" +
        "        $replaceRoot: { newRoot: \"$tubes\" }\n" +
        "    }")
    )).first();

    if (first == null) {
      throw new DataNotFoundException("Tube not found");
    }

    return Tube.deserialize(first.toJson());
  }

  @Override
  public ParticipantLaboratory get(String tubeCode) throws DataNotFoundException {
    Document first =  collection.find(eq("tubes.code", tubeCode)).first();

    if (first == null) {
      throw new DataNotFoundException("Tube not found");
    }

    return ParticipantLaboratory.deserialize(first.toJson());
  }

  @Override
  public ObjectId getTubeLocationPoint(String tubeCode) throws DataNotFoundException {
    Document locationPoint = collection.find(eq("tubes.code", tubeCode)).projection(new Document("locationPoint", 1)).first();
    if (locationPoint == null) {
      throw new DataNotFoundException("Tube origin location not found");
    }
    return (ObjectId) locationPoint.get("locationPoint");
  }

  @Override
  public ArrayList<Tube> getTubes(ArrayList<String> tubeCodeList) {
    ArrayList<Tube> tubes = new ArrayList<>();
    MongoCursor<Document> cursor = collection.aggregate(Arrays.asList(
      new Document("$match",new Document("tubes.code",new Document("$in",tubeCodeList))),
      ParseQuery.toDocument("{\n" +
        "        $unwind: \"$tubes\"\n" +
        "    }"),
      new Document("$match",new Document("tubes.code",new Document("$in",tubeCodeList))),
      ParseQuery.toDocument("{\n" +
        "        $replaceRoot: { newRoot: \"$tubes\" }\n" +
        "    }")
    )).iterator();

    try {
      while (cursor.hasNext()) {
        tubes.add(Tube.deserialize(cursor.next().toJson()));
      }
    } finally {
      cursor.close();
    }

    return tubes;
  }

  @Override
  public HashMap<String, ExamResultTube> getTubesParticipantData(List<String> tubeCodes) {
    HashMap<String, ExamResultTube> hmap = new HashMap<>();
    MongoCursor<Document> cursor = collection.aggregate(Arrays.asList(
      new Document("$match",new Document("tubes.code",new Document("$in",tubeCodes))),
      ParseQuery.toDocument("{\n" +
        "        $lookup: {\n" +
        "            from:\"participant\",\n" +
        "            localField:\"recruitmentNumber\",\n" +
        "            foreignField:\"recruitmentNumber\",\n" +
        "            as:\"participantData\"\n" +
        "        }\n" +
        "    }"),
      ParseQuery.toDocument("{\n" +
        "        $unwind: \"$tubes\"\n" +
        "    }"),
      new Document("$match",new Document("tubes.code",new Document("$in",tubeCodes))),
      ParseQuery.toDocument("{\n" +
        "        $project:{\n" +
        "            tubeCode: \"$tubes.code\",\n" +
        "            recruitmentNumber: \"$recruitmentNumber\",\n" +
        "            isCollected: \"$tubes.tubeCollectionData.isCollected\",\n" +
        "            name: \"$tubes.type\",\n" +
        "            participantData: {$arrayElemAt:[\"$participantData\",0]}\n" +
        "        }\n" +
        "    }")
    )).iterator();

    try {
      while (cursor.hasNext()) {
        ExamResultTube examResultTube = ExamResultTube.deserialize(cursor.next().toJson());
        hmap.putIfAbsent(examResultTube.getTubeCode(), examResultTube);
      }
    } finally {
      cursor.close();
    }

    return hmap;
  }

  @Override
  public void updateTubeCustomMetadata(Tube tube) throws DataNotFoundException {
    //TODO
  }

}
