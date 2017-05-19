package br.org.otus.user;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.User;

public class UserDaoBean extends MongoGenericDao<User> implements UserDao {

	@Inject
	private FieldCenterDao fieldCenterDao;
	private static final String COLLECTION_NAME = "user";
	private static final String EMAIL = "email";
	private static final String ADM = "adm";

	public UserDaoBean() {
		super(COLLECTION_NAME, User.class);
	}

	@Override
	public void persist(User user) {
		this.collection.insertOne(user);
	}

	@Override
	public User fetchByEmail(String email) throws DataNotFoundException {
		User user = this.collection.find(eq(EMAIL, email)).first();
		if (user == null) {
			throw new DataNotFoundException(new Throwable("User with email: {" + email + "} not found."));
		}
		attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
		return user;
	}

	@Override
	public Boolean emailExists(String email) {
		try {
			fetchByEmail(email);
			return Boolean.TRUE;
		} catch (DataNotFoundException e) {
			return Boolean.FALSE;
		}
	}

	@Override
	public User findAdmin() {
		User user = this.collection.find(eq(ADM, true)).first();
		attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
		return user;
	}

	@Override
	public List<User> fetchAll() {
		ArrayList<User> users = new ArrayList<User>();
		this.collection.find().into(users);
		for (User user : users) {
			attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
		}
		return users;
	}

	private void attachfieldCenter(User user, Map<String, FieldCenter> fieldCentersMap) {
		if (user.getFieldCenter() != null) {
			user.setFieldCenter(fieldCentersMap.get(user.getFieldCenter().getAcronym()));
		} else {
			user.setFieldCenter(new FieldCenter());
		}
	}

	@Override
	public User update(User user) {
		// TODO: It needs to remove the properties that must not be updated such uuid and email.
		return this.collection.findOneAndUpdate(eq(EMAIL, user.getEmail()), new Document("$set", user));
	}

}
