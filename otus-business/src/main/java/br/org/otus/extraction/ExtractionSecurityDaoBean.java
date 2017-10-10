package br.org.otus.extraction;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.User;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import static com.mongodb.client.model.Filters.eq;

public class ExtractionSecurityDaoBean extends MongoGenericDao<Document>  implements ExtractionSecurityDao{
    private static final String COLLECTION_NAME = "user";

    public ExtractionSecurityDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public User validateSecurityCredentials(String securityToken, String ip) throws DataNotFoundException {
        Document result = collection.find(eq("extractionToken", securityToken)).first();
        if (result == null) {
            throw new DataNotFoundException(new Throwable("Security token: " + securityToken + " not found."));
        } else {
            return User.deserialize(result.toJson());
        }
    }
}
