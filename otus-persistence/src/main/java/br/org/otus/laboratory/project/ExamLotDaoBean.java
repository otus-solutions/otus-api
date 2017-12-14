package br.org.otus.laboratory.project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.aliquot.WorkAliquotFactory;
import br.org.otus.laboratory.project.exam.ExamLot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;

public class ExamLotDaoBean extends MongoGenericDao<Document> implements ExamLotDao {

	private static final String COLLECTION_NAME = "exam_lot";

	@Inject
	private ParticipantLaboratoryDao participantLaboratoryDao;
	@Inject
	private ParticipantDao participantDao;
	@Inject
	private LaboratoryConfigurationDao laboratoryConfigurationDao;

	public ExamLotDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public void persist(ExamLot examLot) {
		examLot.setCode(laboratoryConfigurationDao.createNewLotCode());
		super.persist(ExamLot.serialize(examLot));
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
	public List<ExamLot> find() {
		ArrayList<ExamLot> ExamLots = new ArrayList<>();
		
		FindIterable<Document> result = collection.find();
		result.forEach((Block<Document>) document -> ExamLots.add(ExamLot.deserialize(document.toJson())));

		return ExamLots;
	}

	@Override
	public void delete(String id) throws DataNotFoundException {
		DeleteResult deleteResult = collection.deleteOne(eq("code", id));
		if (deleteResult.getDeletedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Exam Lot does not exist"));
		}
	}

	@Override
	public List<WorkAliquot> getAllAliquotsInDB() throws DataNotFoundException {
		return WorkAliquotFactory.getAliquotList(participantLaboratoryDao, participantDao);
	}

}
