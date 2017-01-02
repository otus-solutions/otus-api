package br.org.otus.fieldCenter;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import com.google.gson.Gson;
import com.mongodb.Block;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class FieldCenterDaoBean extends MongoGenericDao implements FieldCenterDao {
    private static final String COLLECTION_NAME = "FieldCenter";

    public FieldCenterDaoBean() {
        super(COLLECTION_NAME);
    }

    /**
     * Verify if exist acronym
     *
     * @param acronym
     * @return
     */
    public Boolean acronymInUse(String acronym) {
        if (fetchByAcronym(acronym) != null) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Return field center by acronym or null
     *
     * @param acronym
     * @return
     */
    public FieldCenter fetchByAcronym(String acronym) {
        Document document = collection.find(eq("acronym", acronym)).first();
        if (document != null) {
            return FieldCenter.fromJson(document.toJson());
        } else {
            return null;
        }
    }

    /**
     * Method responsible for performing the update of a field center.
     * Ignore possible to update the value of the acronym field because it is an immutable field.
     *
     * @param fieldCenter
     */
    @Override
    public void update(FieldCenter fieldCenter) {
        Document document = Document.parse(fieldCenter.toJson());
        document.remove("acronym");
        super.collection.updateOne(eq("acronym", fieldCenter.getAcronym()), new Document("$set", document));
    }

    @Override
    public void persist(FieldCenter fieldCenter) {
        super.persist(fieldCenter.toJson());
    }

    @Override
    public ArrayList<FieldCenter> find() {
        ArrayList<FieldCenter> fieldCenters = new ArrayList<>();
        list().forEach((Block<Document>) document -> {
            fieldCenters.add(new Gson().fromJson(document.toJson(), FieldCenter.class));
        });

        return fieldCenters;
    }
}
