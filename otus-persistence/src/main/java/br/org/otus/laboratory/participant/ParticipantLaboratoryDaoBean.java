package br.org.otus.laboratory.participant;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.participant.tube.TubeCollectionData;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;

import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import br.org.mongodb.MongoGenericDao;
import org.ccem.otus.service.ISOStringHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticipantLaboratoryDaoBean extends MongoGenericDao<Document> implements ParticipantLaboratoryDao {
	private static final String COLLECTION_NAME = "participant_laboratory";
	private static final String TRANSPORTATION_LOT_CODE = "transportation_lot.code";
	private static final String ALIQUOT_LIST_CODE = "aliquotList.code";
	private static final String TUBES_ALIQUOTES_CODE = "tubes.aliquotes.code";
	private static final String TRANSPORTATION_LOT = "transportation_lot";
	private static final String FIELD_CENTER_ACRONYM = "participant.fieldCenter.acronym";
	private static final String DATA_PROCESSING_MATCH = "tubes.aliquotes.aliquotCollectionData.processing";
	private static final String CODE_MATCH = "tubes.aliquotes.code";
	private static final String RECRUITMENT_NUMBER = "recruitmentNumber";
	private static final String PARTICIPANT = "participant";
	private static final String EXAM_FIELD = "EXAM";

	private static final String FIELD_CENTER_ATTRIBUTE = "fieldCenter";
	private static final String SEX_ATTRIBUTE = "sex";
	private static final String CONTAINER_ATTRIBUTE = "container";
	private static final String ROLE_ATTRIBUTE = "role";
	private static final String ALIQUOT_COLLECTION_DATA_ATTRIBUTE = "aliquotCollectionData";
	private static final String CODE_ATTRIBUTE = "code";
	private static final String NAME_ATTRIBUTE = "name";
	private static final String BIRTHDATE_ATTRIBUTE = "birthdate";
	private static final String OBJECT_TYPE_ATTRIBUTE = "objectType";

	private static final String $PARTICIPANT_FIELD_CENTER_VALUE = "$participant.fieldCenter";
	private static final String $TUBES_ALIQUOTES_ALIQUOT_COLLECTION_DATA_VALUE = "$tubes.aliquotes.aliquotCollectionData";
	private static final String $TUBES_ALIQUOTES_ROLE_VALUE = "$tubes.aliquotes.role";
	private static final String $TUBES_ALIQUOTES_CONTAINER_VALUE = "$tubes.aliquotes.container";
	private static final String $PARTICIPANT_SEX_VALUE = "$participant.sex";
	private static final String $PARTICIPANT_BIRTHDATE_VALUE = "$participant.birthdate";
	private static final String $RECRUITMENT_NUMBER_VALUE = "$recruitmentNumber";
	private static final String $WORK_ALIQUOT_VALUE = "WorkAliquot";
	private static final String $TUBES_ALIQUOTES_NAME_VALUE = "$tubes.aliquotes.name";
	private static final String $TUBES_ALIQUOTES_CODE_VALUE = "$tubes.aliquotes.code";

	private static final String $EXISTS = "$exists";
	private static final String $TUBES_ALIQUOTES = "$tubes.aliquotes";
	private static final String $PARTICIPANT = "$participant";
	private static final String $TUBES = "$tubes";
	private static final String $LTE = "$lte";
	private static final String $GTE = "$gte";
	private static final String $NIN = "$nin";

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
			throw new DataNotFoundException(
					new Throwable("Laboratory of Participant recruitment number: " + rn + " not found."));
		}
		return ParticipantLaboratory.deserialize(result.toJson());
	}

	@Override
	public ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant)
			throws DataNotFoundException {
		Document parsed = Document.parse(ParticipantLaboratory.serialize(labParticipant));
		parsed.remove("_id");

		UpdateResult updateLabData = collection.updateOne(eq(RECRUITMENT_NUMBER, labParticipant.getRecruitmentNumber()),
				new Document("$set", parsed), new UpdateOptions().upsert(false));

		if (updateLabData.getMatchedCount() == 0) {
			throw new DataNotFoundException(new Throwable("Laboratory of Participant recruitment number: "
					+ labParticipant.getRecruitmentNumber() + " does not exists."));
		}

		return labParticipant;
	}

	@Override
	public Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException {
		Document parsedCollectionData = Document.parse(TubeCollectionData.serialize(tube.getTubeCollectionData()));

		UpdateResult updateLabData = collection.updateOne(
				and(eq(RECRUITMENT_NUMBER, rn), eq("tubes.code", tube.getCode())),
				set("tubes.$.tubeCollectionData", parsedCollectionData), new UpdateOptions().upsert(false));

		if (updateLabData.getMatchedCount() == 0) {
			throw new DataNotFoundException(
					new Throwable("Laboratory of Participant recruitment number: " + rn + " does not exists."));
		}

		return tube;
	}

	public Document findDocumentByAliquotCode(String aliquotCode) throws DataNotFoundException {
		Document first = collection.find(eq(TUBES_ALIQUOTES_CODE, aliquotCode)).first();
		if (first == null) {
			throw new DataNotFoundException();
		}
		return first;

	}

	@Override
	public ArrayList<Aliquot> getFullAliquotsList() {
		ArrayList<Aliquot> fullList = new ArrayList<Aliquot>();

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
	public ArrayList<WorkAliquot> getAliquotsByPeriod(WorkAliquotFiltersDTO workAliquotFiltersDTO) {

		ArrayList<WorkAliquot> workAliquotList = new ArrayList<>();

		ISOStringHandler.removeTimePart(workAliquotFiltersDTO.getInitialDate());

		String formatedInitialDate = ISOStringHandler.removeTimePart(workAliquotFiltersDTO.getInitialDate());
		String formatedFinalDate = ISOStringHandler.removeTimePart(workAliquotFiltersDTO.getFinalDate());


		List<Bson> queryAggregateList = Arrays.asList(
				Aggregates.match(new Document(DATA_PROCESSING_MATCH, new Document()
						.append($GTE, workAliquotFiltersDTO.getInitialDate())
						.append($LTE, workAliquotFiltersDTO.getFinalDate()))),
				Aggregates.lookup(PARTICIPANT, RECRUITMENT_NUMBER, RECRUITMENT_NUMBER, PARTICIPANT),
				Aggregates.unwind($PARTICIPANT),
				Aggregates.unwind($TUBES),
				Aggregates.unwind($TUBES_ALIQUOTES),
				Aggregates.match(new Document(TUBES_ALIQUOTES_CODE, new Document()
						.append($NIN, workAliquotFiltersDTO.getAliquotList()))),
				Aggregates.match(and(new Document(DATA_PROCESSING_MATCH,new Document().append($GTE, formatedInitialDate).append($LTE, formatedFinalDate)),
						new Document(FIELD_CENTER_ACRONYM, workAliquotFiltersDTO.getFieldCenter()))),
				Aggregates.lookup(TRANSPORTATION_LOT, TUBES_ALIQUOTES_CODE, ALIQUOT_LIST_CODE, TRANSPORTATION_LOT),
				Aggregates.match(new Document(TRANSPORTATION_LOT_CODE, new Document().append($EXISTS, 0))),
				Aggregates.project(Projections.fields(Projections.excludeId(),
						Projections.computed(CODE_ATTRIBUTE, $TUBES_ALIQUOTES_CODE_VALUE),
						Projections.computed(NAME_ATTRIBUTE, $TUBES_ALIQUOTES_NAME_VALUE),
						Projections.computed(CONTAINER_ATTRIBUTE, $TUBES_ALIQUOTES_CONTAINER_VALUE),
						Projections.computed(ROLE_ATTRIBUTE, $TUBES_ALIQUOTES_ROLE_VALUE),
						Projections.computed(ALIQUOT_COLLECTION_DATA_ATTRIBUTE,	$TUBES_ALIQUOTES_ALIQUOT_COLLECTION_DATA_VALUE),
						Projections.computed(OBJECT_TYPE_ATTRIBUTE, $WORK_ALIQUOT_VALUE),
						Projections.computed(RECRUITMENT_NUMBER, $RECRUITMENT_NUMBER_VALUE),
						Projections.computed(BIRTHDATE_ATTRIBUTE, $PARTICIPANT_BIRTHDATE_VALUE),
						Projections.computed(SEX_ATTRIBUTE, $PARTICIPANT_SEX_VALUE),
						Projections.computed(FIELD_CENTER_ATTRIBUTE, $PARTICIPANT_FIELD_CENTER_VALUE))),
				Aggregates.match(Filters.in(ROLE_ATTRIBUTE, Arrays.asList(EXAM_FIELD, workAliquotFiltersDTO.getRole())))
		);

		AggregateIterable<Document> result = collection.aggregate(queryAggregateList);
		result.forEach((Block<Document>) document -> {
			WorkAliquot aliquot = WorkAliquot.deserialize(document.toJson());
			workAliquotList.add(aliquot);
		});

		return workAliquotList;
	}

	@Override
	public WorkAliquot getAliquot(WorkAliquotFiltersDTO workAliquotFiltersDTO) {

		ArrayList<WorkAliquot> workAliquotList = new ArrayList<>();
		Document response = new Document();

		List<Bson> queryAggregateList = Arrays.asList(Aggregates.match(new Document(CODE_MATCH, workAliquotFiltersDTO.getCode())),
				Aggregates.lookup(PARTICIPANT, RECRUITMENT_NUMBER, RECRUITMENT_NUMBER, PARTICIPANT),
				Aggregates.unwind($PARTICIPANT),
				Aggregates.unwind($TUBES),
				Aggregates.unwind($TUBES_ALIQUOTES),

				Aggregates.match(and(new Document(CODE_MATCH, workAliquotFiltersDTO.getCode()), new Document(FIELD_CENTER_ACRONYM, workAliquotFiltersDTO.getFieldCenter()))),
				Aggregates.lookup(TRANSPORTATION_LOT, TUBES_ALIQUOTES_CODE, ALIQUOT_LIST_CODE, TRANSPORTATION_LOT),
				Aggregates.match(new Document(TRANSPORTATION_LOT_CODE, new Document().append($EXISTS, 0))),
				Aggregates.project(Projections.fields(Projections.excludeId(),
						Projections.computed(CODE_ATTRIBUTE, $TUBES_ALIQUOTES_CODE_VALUE),
						Projections.computed(NAME_ATTRIBUTE, $TUBES_ALIQUOTES_NAME_VALUE),
						Projections.computed(CONTAINER_ATTRIBUTE, $TUBES_ALIQUOTES_CONTAINER_VALUE),
						Projections.computed(ROLE_ATTRIBUTE, $TUBES_ALIQUOTES_ROLE_VALUE),
						Projections.computed(ALIQUOT_COLLECTION_DATA_ATTRIBUTE,
								$TUBES_ALIQUOTES_ALIQUOT_COLLECTION_DATA_VALUE),
						Projections.computed(OBJECT_TYPE_ATTRIBUTE, $WORK_ALIQUOT_VALUE),
						Projections.computed(RECRUITMENT_NUMBER, $RECRUITMENT_NUMBER_VALUE),
						Projections.computed(BIRTHDATE_ATTRIBUTE, $PARTICIPANT_BIRTHDATE_VALUE),
						Projections.computed(SEX_ATTRIBUTE, $PARTICIPANT_SEX_VALUE),
						Projections.computed(FIELD_CENTER_ATTRIBUTE, $PARTICIPANT_FIELD_CENTER_VALUE))));

		AggregateIterable<Document> result = collection.aggregate(queryAggregateList);

		result.forEach((Block<Document>) document -> {
			WorkAliquot aliquot = WorkAliquot.deserialize(document.toJson());
			if (!aliquot.getCode().isEmpty()) {
				workAliquotList.add(aliquot);
			}
		});

		if (!workAliquotList.isEmpty()) {
			return workAliquotList.get(0);
		} else {
			return null;
		}

	}

}
