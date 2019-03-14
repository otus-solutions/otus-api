package br.org.otus.permission;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.permissions.persistence.user.UserPermissionsDao;

public class PermissionDaoBean extends MongoGenericDao<Document> implements UserPermissionsDao {

    private static final String COLLECTION_NAME = "user_permission";

    public PermissionDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

}
