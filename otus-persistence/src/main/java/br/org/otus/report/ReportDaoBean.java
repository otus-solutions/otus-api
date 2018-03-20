package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.persistence.ReportDao;

import static com.mongodb.client.model.Filters.eq;

public class ReportDaoBean extends MongoGenericDao<Document> implements ReportDao {

    private static final String COLLECTION_NAME = "reports";
    public ReportDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ReportTemplate findReport(ObjectId reportId) throws DataNotFoundException {
        Document result = this.collection.find(eq("_id", reportId)).first();
        if(reportId == null){
            throw new DataNotFoundException("parameter reportId is NULL.");
        }
        if (result == null) {
            throw new DataNotFoundException(
                    new Throwable("Report with ID {" + reportId + "} not found."));
        }
        return ReportTemplate.deserialize(result.toJson());
    }

    @Override
    public boolean getResults(ReportTemplate reportTemplate) {
        return true;
    }
}
