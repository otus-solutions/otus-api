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
    public ReportTemplate findReport(long ri) {
        Document result = this.collection.find(eq("id", ri)).first();
        ReportTemplate report = ReportTemplate.deserialize(result.toJson());
        return report;
    }

    @Override
    public boolean getResults(ReportTemplate reportTemplate) {
        return true;
    }
}
