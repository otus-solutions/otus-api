package br.org.otus.extraction;

import br.org.mongodb.gridfs.FileStoreBucket;
import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.extraction.builder.AttachmentsExtractionQueryBuilder;
import br.org.otus.persistence.AttachmentsExtractionDao;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.ActivityDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class AttachmentsExtractionDaoBean implements AttachmentsExtractionDao {

    @Inject
    private ActivityDao activityDao;
    @Inject
    private FileStoreBucket fileStoreBucket;

    @Override
    @SuppressWarnings("unchecked")
    public AttachmentsReport fetchAttachmentsReport(String acronym, Integer version) throws DataNotFoundException {
        AttachmentsReport attachmentsReport = new AttachmentsReport();

        ArrayList<ObjectId> objectIds = new ArrayList<>();

        Document objectIdsDocument = fileStoreBucket.distinctIds();

        if (objectIdsDocument != null)
                objectIds = (ArrayList<ObjectId>) objectIdsDocument.get("ObjectIds");

        ArrayList<Bson> attachmentsStoredReportQuery = new AttachmentsExtractionQueryBuilder().getAttachmentsStoredReportQuery(acronym, version, objectIds);
        ArrayList<Bson> attachmentsRemovedReportQuery = new AttachmentsExtractionQueryBuilder().getAttachmentsRemovedReportQuery(acronym, version, objectIds);

        CompletableFuture<Document> fetchStoredReportFuture = CompletableFuture.supplyAsync(() -> activityDao.aggregate(attachmentsStoredReportQuery).first());
        CompletableFuture<Document> fetchRemovedReportFuture = CompletableFuture.supplyAsync(() -> activityDao.aggregate(attachmentsRemovedReportQuery).first());

        Document storedReportDocument = getFutureResult(fetchStoredReportFuture);
        Document removedReportDocument = getFutureResult(fetchRemovedReportFuture);


        if (storedReportDocument != null) {
            attachmentsReport.concat(AttachmentsReport.deserialize(storedReportDocument.toJson()).getAttachmentsList());
        }

        if (removedReportDocument != null){
            attachmentsReport.concat(AttachmentsReport.deserialize(removedReportDocument.toJson()).getAttachmentsList());
        }

        if(attachmentsReport.getAttachmentsList().size() == 0){
            throw new DataNotFoundException(new Throwable("There are no result for given acronym or version"));
        }

        return attachmentsReport;
    }

    private Document getFutureResult(CompletableFuture<Document> Future) throws DataNotFoundException {
        Document resultDocument = null;
        try {
            resultDocument = Future.get();
        } catch (InterruptedException ignored) {} catch (ExecutionException e) {
            throw new DataNotFoundException(new Throwable("There are no result"));
        }
        return resultDocument;
    }

}
