package br.org.otus.report;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.persistence.ReportTemplateDTO;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;

public class ReportDaoBean extends MongoGenericDao<Document> implements ReportDao {

	private static final String COLLECTION_NAME = "report";

	public ReportDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException, ValidationException {
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
	public ActivityReportTemplate getActivityReport(String acronym, Integer version) throws DataNotFoundException {
		Document query = new Document();
		query.put("acronym", acronym);
		query.put("versions",version);

		Document result = this.collection.find(query).first();

		if (result == null) {
			throw new DataNotFoundException(new Throwable("Report with acronym {" + acronym + " and version " + version +"} not found."));
		}

		return ActivityReportTemplate.deserialize(result.toJson());
	}

	@Override
	public ActivityReportTemplate insertActivityReport(ActivityReportTemplate activityReportTemplate) throws ValidationException {

		String acronym = activityReportTemplate.getAcronym();
		List<Integer> version = activityReportTemplate.getVersions();
		Document parsed = Document.parse(ActivityReportTemplate.serialize(activityReportTemplate));

		if(findVersionActivityReport(acronym, version)){
			super.persist(parsed);
			activityReportTemplate.setId((ObjectId) parsed.get("_id"));
		} else {
			throw new ValidationException(new Throwable("Activity Report with acronym {" + acronym + " and version " + version +"} exists in base."));
		}

		return activityReportTemplate;
	}

	@Override
	public List<ActivityReportTemplate> getActivityReportList(String acronym) throws DataNotFoundException {
		ArrayList<ActivityReportTemplate> results = new ArrayList<>();

		FindIterable<Document> result = collection.find(eq("acronym", acronym));
		result.forEach((Block<Document>) document -> {
			results.add(ActivityReportTemplate.deserialize(document.toJson()));
		});

		if (results.isEmpty()) {
			throw new DataNotFoundException(new Throwable("Report with acronym {" + acronym + "} not found."));
		}

		return results;
	}

	@Override
	public void updateActivityReport(ObjectId reportId, ArrayList<Integer> versions) throws DataNotFoundException {

		UpdateResult updateReportData = collection.updateOne(eq("_id", reportId), new Document("$set", new Document("versions", versions)));

		if (updateReportData.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Activity Report not found"));
		}

	}

	private Boolean findVersionActivityReport(String acronym, List<Integer> versions) {
		Boolean confirmation = false;
		Document query = new Document();
		query.put("acronym", acronym);
		query.put("versions", new Document("$all", versions));

		Document result = this.collection.find(query).first();

		if(result == null){
			confirmation = true;
		}

		return confirmation;
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
			throw new DataNotFoundException(new Throwable("Report not found. Id: " + id));
		}

	}

	@Override
	public List<ReportTemplateDTO> getByCenter(String fieldCenter) throws ValidationException {
		ArrayList<ReportTemplateDTO> results = new ArrayList<>();
		MongoCursor iterator = collection.find(eq("fieldCenter", fieldCenter)).iterator();

		while (iterator.hasNext()) {
			Document next = (Document) iterator.next();
			ReportTemplateDTO dto = new ReportTemplateDTO(ReportTemplate.deserializeWithoutValidation(next.toJson()));
			results.add(dto);
		}
		iterator.close();

		return results;
	}

	@Override
	public List<ReportTemplate> getAll() throws ValidationException {
		ArrayList<ReportTemplate> results = new ArrayList<>();

		MongoCursor iterator = collection.find(eq("objectType", "ParticipantReport")).iterator();

		while (iterator.hasNext()) {
			Document next = (Document) iterator.next();
			results.add(ReportTemplate.deserialize(next.toJson()));
		}
		iterator.close();

		return results;
	}

	@Override
	public ReportTemplate getById(String id) throws DataNotFoundException, ValidationException {
		Document query = new Document("_id", new ObjectId(id));

		Document first = (Document) collection.find(query).first();

		if (first == null) {
			throw new DataNotFoundException(new Throwable("ExamReport not found. Id: " + id));
		}

		return ReportTemplate.deserialize(first.toJson());
	}

	@Override
	public ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException {

		UpdateResult updateReportData = collection.updateOne(eq("_id", reportTemplate.getId()), new Document("$set", new Document("fieldCenter", reportTemplate.getFieldCenter())),
				new UpdateOptions().upsert(false));

		if (updateReportData.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Exam Report not found"));
		}

		return reportTemplate;
	}
}
