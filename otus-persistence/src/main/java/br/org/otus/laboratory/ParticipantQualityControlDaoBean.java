package br.org.otus.laboratory;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.participant.ParticipantQualityControl;
import br.org.otus.laboratory.participant.ParticipantQualityControlDao;

public class ParticipantQualityControlDaoBean extends MongoGenericDao implements ParticipantQualityControlDao {

	private static final String COLLECTION_NAME = "participant_quality_control";

	public ParticipantQualityControlDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public ParticipantQualityControl findParticipantGroup(Long rn) {
		Document result = collection.find(eq("recruitmentNumber", rn)).first();
		
		if (result != null) {
			return ParticipantQualityControl.deserialize(result.toJson());			
		} else {
			return null;			
		}		
	}
}
