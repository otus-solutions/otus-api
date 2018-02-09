package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;

import static com.mongodb.client.model.Filters.eq;

public class ReportDaoBean extends MongoGenericDao<Document> implements ReportDao {

    private static final String COLLECTION_NAME = "reports";

    public ReportDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ReportTemplate find(long ri) {
        Document result = this.collection.find(eq("id", ri)).first();
        return ReportTemplate.deserialize(result.toJson());
    }

    @Override
    public boolean getResults(ReportTemplate reportTemplate) {

        return true;
    }
}
