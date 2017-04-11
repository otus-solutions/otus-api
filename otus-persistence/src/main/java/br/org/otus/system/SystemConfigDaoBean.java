package br.org.otus.system;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.email.BasicEmailSender;
import org.bson.Document;

import javax.persistence.NoResultException;

public class SystemConfigDaoBean extends MongoGenericDao implements SystemConfigDao{
	private static final String COLLECTION_NAME = "system_config";

	public SystemConfigDaoBean() {
		super(COLLECTION_NAME);
	}

	@Override
	public void persist(SystemConfig systemConfig) {
		super.persist(SystemConfig.serialize(systemConfig));
	}

	@Override
	public Boolean isReady() {
		Document result = collection.find().first();
		return (result != null);
	}

	@Override
	public SystemConfig fetchSystemConfig() {
		Document result = collection.find().first();
		if(result != null){
			return SystemConfig.deserialize(result.toJson());
		}else{
			throw new NoResultException();
		}
	}

	@Override
	public BasicEmailSender findEmailSender() {
		SystemConfig systemConfig = this.fetchSystemConfig();
		return systemConfig.getEmailSender();
	}

}