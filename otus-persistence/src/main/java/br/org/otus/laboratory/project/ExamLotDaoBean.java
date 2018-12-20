package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ExamLotDaoBean extends MongoGenericDao<Document> implements ExamLotDao {

  private static final String COLLECTION_NAME = "exam_lot";

  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  private final static Integer CODE_EXAM_LOT = 300000000;


  public ExamLotDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ObjectId persist(ExamLot examLot) {
    examLot.setCode(laboratoryConfigurationDao.createNewLotCodeForExam(getLastExamLotCode()));
    Document parsed = Document.parse(ExamLot.serialize(examLot));
    parsed.remove("aliquotList");
    parsed.remove("_id");
    super.persist(parsed);
    return (ObjectId)parsed.get( "_id" );
  }

  @Override
  public ExamLot findByCode(String code) throws DataNotFoundException {
    Document document = collection.aggregate(Arrays.asList(Aggregates.match(new Document("code", code)),Aggregates.lookup("aliquot","_id","examLotId","aliquotList"))).first();
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

    UpdateResult updateLotData = collection.updateOne(eq("code", examLot.getCode()), new Document("$set", parsed), new UpdateOptions().upsert(false));

    if (updateLotData.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Exam Lot not found"));
    }

    return examLot;
  }

  @Override
  public List<ExamLot> find(String centerAcronym) {
    ArrayList<ExamLot> ExamLots = new ArrayList<>();

    FindIterable<Document> output = collection.find(new Document("fieldCenter.acronym",centerAcronym));

    for (Object anOutput : output) {
      Document next = (Document) anOutput;
      ExamLots.add(ExamLot.deserialize(next.toJson()));
    }

    return ExamLots;
  }

  @Override
  public void delete(ObjectId id) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq("_id", id));
    if (deleteResult.getDeletedCount() == 0) {
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
      if(result == null ){
          throw new DataNotFoundException(new Throwable("Exam Lot not found"));
      }
      return ExamLot.deserialize(result.toJson());
  }

  public Integer getLastExamLotCode(){
    Integer newLotCode = new Integer(CODE_EXAM_LOT);
    FindIterable lastExamLotCode = super.findLast().projection(new Document("code", -1).append("_id", 0));
    for(Object result: lastExamLotCode){
      Document document = (Document) result;
      newLotCode = Integer.parseInt(document.get("code").toString());
    }
    return newLotCode;
  }
}
