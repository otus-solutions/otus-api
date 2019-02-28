package br.org.otus.laboratory.project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;

public class ExamLotDaoBean extends MongoGenericDao<Document> implements ExamLotDao {

  private static final String COLLECTION_NAME = "exam_lot";

  private final static Integer CODE_EXAM_LOT = 300000000;

  public ExamLotDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId persist(ExamLot examLot) {
    Document parsed = Document.parse(ExamLot.serialize(examLot));
    parsed.remove("aliquotList");
    parsed.remove("_id");
    super.persist(parsed);
    return (ObjectId) parsed.get("_id");
  }

  @Override
  public ExamLot findByCode(String code) throws DataNotFoundException {
    Document document = collection
        .aggregate(
            Arrays
                .asList(new Document("$match", new Document("code", code)),
                    new Document(
                        "$lookup", new Document("from", "aliquot")
                            .append("let",
                                new Document(
                                    "lotId", "$_id"))
                            .append("pipeline",
                                Arrays.asList(
                                    new Document("$match",
                                        new Document("$expr", new Document("$or",
                                            Arrays.asList(new Document("$eq", Arrays.asList("$examLotId", "$$lotId")),
                                                new Document("$eq", Arrays.asList("$examLotData.id", "$$lotId")))))),
                                    new Document("$sort", new Document("examLotData.position", 1))))
                            .append("as", "aliquotList"))))
        .first();
    if (document != null) {
      return ExamLot.deserialize(document.toJson());
    } else {
      throw new DataNotFoundException(new Throwable("Exam Lot not found"));
    }
  }

  @Override
  public ExamLot update(ExamLot examLot) throws DataNotFoundException {
    Document parsed = Document.parse(ExamLot.serialize(examLot));
    parsed.remove("_id");

    UpdateResult updateLotData = collection.updateOne(eq("code", examLot.getCode()), new Document("$set", parsed),
        new UpdateOptions().upsert(false));

    if (updateLotData.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Exam Lot not found"));
    }

    return examLot;
  }

  @Override
  public List<ExamLot> find(String centerAcronym) {
    ArrayList<ExamLot> ExamLots = new ArrayList<>();

    FindIterable<Document> output = collection.find(new Document("fieldCenter.acronym", centerAcronym));

    for (Object anOutput : output) {
      Document next = (Document) anOutput;
      ExamLots.add(ExamLot.deserialize(next.toJson()));
    }

    return ExamLots;
  }

  @Override
  public void delete(ObjectId id) throws DataNotFoundException {
    Document updateResult = collection.findOneAndReplace(new Document("_id", id),
        new Document("objectType", "DeletedExamLot").append("code", find(id).getCode()));
    if (updateResult.size() == 0) {
      throw new DataNotFoundException(new Throwable("Exam Lot does not exist"));
    }
  }

  @Override
  public String checkIfThereInExamLot(String aliquotCode) {
    Document document = collection.find(eq("aliquotList.code", aliquotCode)).first();
    if (document != null) {
      ExamLot lot = ExamLot.deserialize(document.toJson());
      return lot.getCode();
    } else {
      return null;
    }
  }

  @Override
  public ExamLot find(ObjectId examLotId) throws DataNotFoundException {
    Document result = collection.find(eq("_id", examLotId)).first();
    if (result == null) {
      throw new DataNotFoundException(new Throwable("Exam Lot not found"));
    }
    return ExamLot.deserialize(result.toJson());
  }

  @Override
  public Integer getLastExamLotCode() {
    Integer lastLotCode = new Integer(CODE_EXAM_LOT);
    FindIterable lastExamLotCode = super.findLast().projection(new Document("code", -1).append("_id", 0));
    for (Object result : lastExamLotCode) {
      Document document = (Document) result;
      lastLotCode = Integer.parseInt(document.get("code").toString());
    }
    return lastLotCode;
  }
}