package br.org.otus.laboratory;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.participant.QualityControl;
import br.org.otus.laboratory.persistence.ParticipantQualityControlDao;
import org.bson.Document;

import javax.ejb.Local;

import static com.mongodb.client.model.Filters.eq;

@Local(ParticipantQualityControlDao.class)
public class ParticipantQualityControlDaoBean extends MongoGenericDao implements ParticipantQualityControlDao{

	private static final String COLLECTION_NAME = "participant_quality_control";
		
	public ParticipantQualityControlDaoBean() {
		super(COLLECTION_NAME);
	}
	
	@Override
	public QualityControl findParticipantCQGroup(Long rn) {
		Document result = collection.find(eq("recruitmentNumber", rn)).first();
		if(result != null)
			return QualityControl.deserialize(result.toJson());
		return null;
	}
}
