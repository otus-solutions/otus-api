package br.org.otus.fieldCenter;

import br.org.mongodb.MongoGenericDao;
import com.google.gson.Gson;
import com.mongodb.Block;
import org.bson.Document;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.persistence.FieldCenterDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class FieldCenterDaoBean extends MongoGenericDao<Document> implements FieldCenterDao {
	private static final String COLLECTION_NAME = "field_center";

	public FieldCenterDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	/**
	 * Verify if exist code
	 *
	 * @param code
	 * @return
	 */
	public Boolean codeInUse(Integer code) {
		if (fetchByCode(code) != null) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Return field center by code or null
	 *
	 * @param code
	 * @return
	 */
	public FieldCenter fetchByCode(Integer code) {
		Document document = collection.find(eq("code", code)).first();
		if (document != null) {
			return FieldCenter.fromJson(document.toJson());
		} else {
			return null;
		}
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
	 * Method responsible for performing the update of a field center. Ignore
	 * possible to update the value of the acronym field because it is an
	 * immutable field.
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

	@Override
	public Map<String, FieldCenter> getFieldCentersMap() {
		ArrayList<FieldCenter> fieldCenters = find();
		Map<String, FieldCenter> fieldCenterMap = new HashMap<String, FieldCenter>();
		for (FieldCenter fieldCenter : fieldCenters) {
			fieldCenterMap.put(fieldCenter.getAcronym(), fieldCenter);
		}
		return fieldCenterMap;
	}
	
	@Override
    public ArrayList<String> listAcronyms() {
        ArrayList<String> results = new ArrayList<>();
        Document query = new Document();
        for(String acronym:collection.distinct("acronym", String.class)) {
          results.add(acronym);
        };
 
        return results;
    }
}
