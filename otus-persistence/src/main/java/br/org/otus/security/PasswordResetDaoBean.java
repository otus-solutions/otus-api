package br.org.otus.security;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.MongoCommandException;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PasswordResetDaoBean extends MongoGenericDao<Document> implements PasswordResetControlDao {

  private static final String COLLECTION_NAME = "password_reset";

  @PostConstruct
  private void setUp() {
    try {
      this.collection.createIndex(
        new Document("creationDate", 1),
        new IndexOptions().expireAfter(1L, TimeUnit.HOURS)
      );
    }catch (MongoCommandException ignored) {}


  }

  public PasswordResetDaoBean() {
    super(COLLECTION_NAME, Document.class); //TODO 16/08/18: review this type

  }

  @Override
  public void addSession(String token) {
    Document insertion = new Document();
    insertion.append("creationDate", new Date());
    this.collection.insertOne(insertion);
  }

  @Override
  public void removeSession(String token) {

  }

  @Override
  public void findSession(String token) {

  }
//
////	@Override
//	public User fetchByEmail(String email) throws DataNotFoundException {
//		User user = this.collection.find(eq(EMAIL, email)).first();
//		if (user == null) {
//			throw new DataNotFoundException(new Throwable("User with email: {" + email + "} not found."));
//		}
//		attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
//		return user;
//	}
//
////	@Override
//	public Boolean emailExists(String email) {
//		try {
//			fetchByEmail(email);
//			return Boolean.TRUE;
//		} catch (DataNotFoundException e) {
//			return Boolean.FALSE;
//		}
//	}
//
////	@Override
//	public User findAdmin() {
//		User user = this.collection.find(eq(ADM, true)).first();
//		attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
//		return user;
//	}
//
////	@Override
//	public List<User> fetchAll() {
//		ArrayList<User> users = new ArrayList<User>();
//		this.collection.find().into(users);
//		for (User user : users) {
//			attachfieldCenter(user, fieldCenterDao.getFieldCentersMap());
//		}
//		return users;
//	}
//
//	private void attachfieldCenter(User user, Map<String, FieldCenter> fieldCentersMap) {
//		if (user.getFieldCenter() != null) {
//			user.setFieldCenter(fieldCentersMap.get(user.getFieldCenter().getAcronym()));
//		} else {
//			user.setFieldCenter(new FieldCenter());
//		}
//	}
//
////	@Override
//	public User update(User user) {
//		// TODO: It needs to remove the properties that must not be updated such uuid and email.
//		return this.collection.findOneAndUpdate(eq(EMAIL, user.getEmail()), new Document("$set", user));
//	}


}
