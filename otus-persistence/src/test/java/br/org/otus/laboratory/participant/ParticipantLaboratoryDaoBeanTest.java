package br.org.otus.laboratory.participant;

import static com.mongodb.client.model.Filters.and;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.mockito.Mockito.any;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ParticipantLaboratoryDaoBean.class, WorkAliquot.class, Aggregates.class, Projections.class })
public class ParticipantLaboratoryDaoBeanTest<T> {
	private static final String ALIQUOT_TEXT = "{recruitmentNumber: 1090810, fieldCenter: {acronym:'SP'}, code: 363123470 ,role:'STORAGE', objectType:'LotALiquot'}";

	private static final String TRANSPORTATION_LOT_CODE = "transportation_lot.code";
	private static final String ALIQUOT_LIST_CODE = "aliquotList.code";
	private static final String TUBES_ALIQUOTES_CODE = "tubes.aliquotes.code";
	private static final String TRANSPORTATION_LOT = "transportation_lot";
	private static final String FIELD_CENTER_ACRONYM = "participant.fieldCenter.acronym";
	private static final String DATA_PROCESSING_MATCH = "tubes.aliquotes.aliquotCollectionData.processing";
	private static final String CODE_MATCH = "tubes.aliquotes.code";
	private static final String RECRUITMENT_NUMBER = "recruitmentNumber";
	private static final String PARTICIPANT = "participant";

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

	@InjectMocks
	private ParticipantLaboratoryDaoBean participantLaboratoryDaoBean = spy(new ParticipantLaboratoryDaoBean());

	@Mock
	private MongoCollection<T> collection;
	@Mock
	private WorkAliquotFiltersDTO workAliquotFiltersDTO;
	@Mock
	private AggregateIterable result;
	@Mock
	private Bson bson;

	private WorkAliquot workAliquot;
	private ArrayList<WorkAliquot> workAliquots = spy(new ArrayList<WorkAliquot>());

	@Before
	public void setUp() throws Exception {
		workAliquot = WorkAliquot.deserialize(ALIQUOT_TEXT);
		workAliquots.add(workAliquot);
		whenNew(ArrayList.class).withNoArguments().thenReturn(workAliquots);
		when(collection.aggregate(Mockito.anyList())).thenReturn(result);
		when(workAliquotFiltersDTO.getCode()).thenReturn("363123470");
		when(workAliquotFiltersDTO.getInitialDate()).thenReturn("2018-01-26T13:57:39.378Z");
		when(workAliquotFiltersDTO.getFinalDate()).thenReturn("2018-01-26T13:57:39.378Z");
		PowerMockito.mockStatic(Aggregates.class);
		PowerMockito.mockStatic(Projections.class);
		PowerMockito.doReturn(bson).when(Projections.class, "excludeId");
	}

	@Test
	public void methodTest_for_getAliquotsByPeriod_should_verify_some_call_Aggregates() throws Exception {

		participantLaboratoryDaoBean.getAliquotsByPeriod(workAliquotFiltersDTO);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.match(
				new Document(DATA_PROCESSING_MATCH, new Document().append($GTE, workAliquotFiltersDTO.getInitialDate())
						.append($LTE, workAliquotFiltersDTO.getFinalDate())));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.lookup(PARTICIPANT, RECRUITMENT_NUMBER, RECRUITMENT_NUMBER, PARTICIPANT);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($PARTICIPANT);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($TUBES);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($TUBES_ALIQUOTES);

		verifyStatic(Aggregates.class, Mockito.times(5));
		Aggregates.match(and(new Document(DATA_PROCESSING_MATCH, new Document().append($GTE, workAliquotFiltersDTO.getInitialDate())
				.append($LTE,workAliquotFiltersDTO.getFinalDate())), new Document(FIELD_CENTER_ACRONYM, any())));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.match(new Document(TUBES_ALIQUOTES_CODE,	new Document().append($NIN, workAliquotFiltersDTO.getAliquotList())));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.lookup(TRANSPORTATION_LOT, TUBES_ALIQUOTES_CODE, ALIQUOT_LIST_CODE, TRANSPORTATION_LOT);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.match(new Document(TRANSPORTATION_LOT_CODE, new Document().append($EXISTS, 0)));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.project(Projections.fields(Projections.excludeId(),
				Projections.computed(CODE_ATTRIBUTE, $TUBES_ALIQUOTES_CODE_VALUE),
				Projections.computed(NAME_ATTRIBUTE, $TUBES_ALIQUOTES_NAME_VALUE),
				Projections.computed(CONTAINER_ATTRIBUTE, $TUBES_ALIQUOTES_CONTAINER_VALUE),
				Projections.computed(ROLE_ATTRIBUTE, $TUBES_ALIQUOTES_ROLE_VALUE),
				Projections.computed(ALIQUOT_COLLECTION_DATA_ATTRIBUTE, $TUBES_ALIQUOTES_ALIQUOT_COLLECTION_DATA_VALUE),
				Projections.computed(OBJECT_TYPE_ATTRIBUTE, $WORK_ALIQUOT_VALUE),
				Projections.computed(RECRUITMENT_NUMBER, $RECRUITMENT_NUMBER_VALUE),
				Projections.computed(BIRTHDATE_ATTRIBUTE, $PARTICIPANT_BIRTHDATE_VALUE),
				Projections.computed(SEX_ATTRIBUTE, $PARTICIPANT_SEX_VALUE),
				Projections.computed(FIELD_CENTER_ATTRIBUTE, $PARTICIPANT_FIELD_CENTER_VALUE)));
	}

	@Test
	public void methodTest_for_getAliquot_should_verify_some_call_Aggregates() throws Exception {

		participantLaboratoryDaoBean.getAliquot(workAliquotFiltersDTO);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.match(new Document(CODE_MATCH, workAliquotFiltersDTO.getCode()));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($PARTICIPANT);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($TUBES);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.unwind($TUBES_ALIQUOTES);

		verifyStatic(Aggregates.class, Mockito.times(3));
		Aggregates.match(and(new Document(CODE_MATCH, workAliquotFiltersDTO.getCode()),
				new Document(FIELD_CENTER_ACRONYM, any())));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.lookup(TRANSPORTATION_LOT, TUBES_ALIQUOTES_CODE, ALIQUOT_LIST_CODE, TRANSPORTATION_LOT);

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.match(new Document(TRANSPORTATION_LOT_CODE, new Document().append($EXISTS, 0)));

		verifyStatic(Aggregates.class, Mockito.times(1));
		Aggregates.project(Projections.fields(Projections.excludeId(),
				Projections.computed(CODE_ATTRIBUTE, $TUBES_ALIQUOTES_CODE_VALUE),
				Projections.computed(NAME_ATTRIBUTE, $TUBES_ALIQUOTES_NAME_VALUE),
				Projections.computed(CONTAINER_ATTRIBUTE, $TUBES_ALIQUOTES_CONTAINER_VALUE),
				Projections.computed(ROLE_ATTRIBUTE, $TUBES_ALIQUOTES_ROLE_VALUE),
				Projections.computed(ALIQUOT_COLLECTION_DATA_ATTRIBUTE, $TUBES_ALIQUOTES_ALIQUOT_COLLECTION_DATA_VALUE),
				Projections.computed(OBJECT_TYPE_ATTRIBUTE, $WORK_ALIQUOT_VALUE),
				Projections.computed(RECRUITMENT_NUMBER, $RECRUITMENT_NUMBER_VALUE),
				Projections.computed(BIRTHDATE_ATTRIBUTE, $PARTICIPANT_BIRTHDATE_VALUE),
				Projections.computed(SEX_ATTRIBUTE, $PARTICIPANT_SEX_VALUE),
				Projections.computed(FIELD_CENTER_ATTRIBUTE, $PARTICIPANT_FIELD_CENTER_VALUE)));
	}
}
