package br.org.otus.extraction;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.persistence.AttachmentsExtractionDao;
import br.org.otus.persistence.builder.AttachmentsExtractionQueryBuilder;
import com.mongodb.client.DistinctIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.persistence.ActivityDao;

import javax.inject.Inject;
import java.util.ArrayList;


public class AttachmentsExtractionDaoBean implements AttachmentsExtractionDao {

    @Inject
    private ActivityDao activityDao;
    @Inject
    private FileStoreBucket fileStoreBucket;

    @Override
    public AttachmentsReport fetchAttachmentsReport(String acronym, Integer version) {
        Document objectIds = fileStoreBucket.distinctIds();

        ArrayList<Bson> attachmentsStoredReportQuery = new AttachmentsExtractionQueryBuilder().getAttachmentsStoredReportQuery(acronym, version, (ArrayList<ObjectId>) objectIds.get("ObjectIds"));
        ArrayList<Bson> attachmentsRemovedReportQuery = new AttachmentsExtractionQueryBuilder().getAttachmentsRemovedReportQuery(acronym, version, (ArrayList<ObjectId>) objectIds.get("ObjectIds"));
        Document storedReport = activityDao.aggregate(attachmentsStoredReportQuery).first();
        Document removedReport = activityDao.aggregate(attachmentsRemovedReportQuery).first();
        AttachmentsReport attachmentsStoredReport = AttachmentsReport.deserialize(storedReport.toJson());
        if (removedReport != null){
            AttachmentsReport attachmentsRemovedReport = AttachmentsReport.deserialize(removedReport.toJson());
            attachmentsStoredReport.concat(attachmentsRemovedReport.getAttachmentsList());
        }
        return attachmentsStoredReport;
    }

}
