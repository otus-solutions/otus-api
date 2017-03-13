package br.org.otus.user;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.User;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserDaoBean extends MongoGenericDao implements UserDao {
    private static final String COLLECTION_NAME = "user";
    private static final String EMAIL = "email";
    private static final String ADM = "adm";

    public UserDaoBean() {
        super(COLLECTION_NAME);
    }

    public void persist(User user) {
        super.persist(User.serialize(user));
    }

    /**
     * Update User System.
     * Remove UUID and Email because they are not upgradeable
     * @param user
     */
    public void update(User user){
        Document document = Document.parse(User.serialize(user));
        document.remove("email");
        document.remove("uuid");
        super.collection.updateOne(eq("email", user.getEmail()), new Document("$set", document));
    }

    @Override
    public User fetchByEmail(String email) {
        Document result = this.collection.find(eq(EMAIL, email)).first();
        if(result != null){
            return User.deserialize(result.toJson());
        }else {
            throw new NoResultException();
        }
    }

    @Override
    public Boolean emailExists(String email) {
        try{
            fetchByEmail(email);
            return Boolean.TRUE;
        }catch (NoResultException e){
            return Boolean.FALSE;
        }
    }

    @Override
    public User findAdmin() {
        Document result = this.collection.find(eq(ADM, true)).first();
        return User.deserialize(result.toJson());
    }


    @Override
    public List<User> fetchAll() {
        ArrayList<User> users = new ArrayList<>();

        FindIterable<Document> result = this.collection.find();
        result.forEach((Block<Document>) document -> {
            users.add(User.deserialize(document.toJson()));
        });

        return users;
    }
}
