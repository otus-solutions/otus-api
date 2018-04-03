package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.laboratory.project.exam.ExamLot;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

public class ReportDaoBean extends MongoGenericDao<Document> implements ReportDao {

	private static final String COLLECTION_NAME = "report";

	public ReportDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException {
		Document result = this.collection.find(eq("_id", reportId)).first();
		if (reportId == null) {
			throw new DataNotFoundException("parameter reportId is NULL.");
		}
		if (result == null) {
			throw new DataNotFoundException(new Throwable("Report with ID {" + reportId + "} not found."));
		}
		return ReportTemplate.deserialize(result.toJson());
	}

	@Override
	public ReportTemplate insert(ReportTemplate reportTemplate) {
		Document parsed = Document.parse(ReportTemplate.serialize(reportTemplate));
		super.persist(parsed);
		reportTemplate.setId((ObjectId) parsed.get("_id"));
		return reportTemplate;
	}

	@Override
	public void deleteById(String id) throws DataNotFoundException {
		Document query = new Document("_id", new ObjectId(id));
		DeleteResult deleteResult = collection.deleteOne(query);

		if (deleteResult.getDeletedCount() == 0) {
			throw new DataNotFoundException(new Throwable("ExamReport not found. Id: " + id));
		}

	}

	@Override
	public List<ReportTemplate> getByCenter(String fieldCenter) {
		ArrayList<ReportTemplate> results = new ArrayList<>();
		Document query = new Document("fieldCenter", fieldCenter);
		Document projection = new Document("label", 1);
		MongoCursor iterator = collection.find(eq("fieldCenter", fieldCenter)).projection(projection).iterator();

		while (iterator.hasNext()) {
			Document next = (Document) iterator.next();
			results.add(ReportTemplate.deserialize(next.toJson()));
		}
		iterator.close();

		return results;
	}

	@Override
	public List<ReportTemplate> getAll() {
		ArrayList<ReportTemplate> results = new ArrayList<>();

		MongoCursor iterator = collection.find().iterator();

		while (iterator.hasNext()) {
			Document next = (Document) iterator.next();
			results.add(ReportTemplate.deserialize(next.toJson()));
		}
		iterator.close();

		return results;
	}

	@Override
	public ReportTemplate getById(String id) throws DataNotFoundException {
		Document query = new Document("_id", new ObjectId(id));

		Document first = (Document) collection.find(query).first();

		if (first == null) {
			throw new DataNotFoundException(new Throwable("ExamReport not found. Id: " + id));
		}

		return ReportTemplate.deserialize(first.toJson());
	}

	@Override
	public ReportTemplate update(ReportTemplate reportTemplate) throws DataNotFoundException {
		Document parsed = Document.parse(ReportTemplate.serialize(reportTemplate));
		parsed.remove("_id");

		UpdateResult updateReportData = collection.updateOne(eq("_id", reportTemplate.getId()),
				new Document("$set", new Document("fieldCenter",reportTemplate.getFieldCenter())), new UpdateOptions().upsert(false));

		if (updateReportData.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Exam Report not found"));
		}

		return reportTemplate;
	}
}
