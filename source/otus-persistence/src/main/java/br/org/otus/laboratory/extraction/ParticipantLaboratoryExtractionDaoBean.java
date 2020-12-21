package br.org.otus.laboratory.extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import br.org.otus.laboratory.unattached.UnattachedLaboratoryDao;
import org.bson.Document;

import com.mongodb.client.AggregateIterable;

import br.org.otus.laboratory.extraction.builder.ParticipantLaboratoryExtractionQueryBuilder;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.participant.validators.ParticipantLaboratoryExtractionDao;

public class ParticipantLaboratoryExtractionDaoBean implements ParticipantLaboratoryExtractionDao {

  @Inject
  private AliquotDao aliquotDao;
  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;
  @Inject
  private UnattachedLaboratoryDao unattachedLaboratoryDao;

  @SuppressWarnings("unchecked")
  public LinkedList<LaboratoryRecordExtraction> getLaboratoryExtraction() {
    LinkedList<LaboratoryRecordExtraction> participantLaboratoryRecordExtractions = new LinkedList<LaboratoryRecordExtraction>();

    Document attachedLaboratories = unattachedLaboratoryDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getAttachedLaboratoryForExtractionQuery()).first();

    if (attachedLaboratories != null) {
      LinkedList<LaboratoryRecordExtraction> laboratoryExtractionFromUnattached = laboratoryExtraction(attachedLaboratories, true);
      participantLaboratoryRecordExtractions.addAll(laboratoryExtractionFromUnattached);
    }
    LinkedList<LaboratoryRecordExtraction> laboratoryExtraction = laboratoryExtraction(attachedLaboratories, false);
    participantLaboratoryRecordExtractions.addAll(laboratoryExtraction);


    return participantLaboratoryRecordExtractions;
  }

  private LinkedList<LaboratoryRecordExtraction> laboratoryExtraction(Document attachedLaboratories, boolean extractionFromUnattached) {
    LinkedList<LaboratoryRecordExtraction> participantLaboratoryRecordExtractions = new LinkedList<LaboratoryRecordExtraction>();
    CompletableFuture<AggregateIterable<Document>> future1 = CompletableFuture.supplyAsync(() -> {
      AggregateIterable<Document> notAliquotedTubesDocument = null;
      Document tubeCodeDocument = aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getTubeCodesInAliquotQuery()).first();

      ArrayList<String> tubeCodes = null;
      if (tubeCodeDocument != null) {
        tubeCodes = (ArrayList<String>) tubeCodeDocument.get("tubeCodes");
      } else {
        tubeCodes = new ArrayList<>();
      }

      notAliquotedTubesDocument = participantLaboratoryDao
        .aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getNotAliquotedTubesQuery(tubeCodes, attachedLaboratories, extractionFromUnattached));

      return notAliquotedTubesDocument;
    });

    CompletableFuture<AggregateIterable<Document>> future2 = CompletableFuture.supplyAsync(() ->
      aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getAliquotedTubesQuery(attachedLaboratories, extractionFromUnattached))
    );

    try {
      AggregateIterable<Document> future1Result = future1.get();
      if (future1Result != null) {
        for (Document notAliquotedTube : future1Result) {
          participantLaboratoryRecordExtractions.add(LaboratoryRecordExtraction.deserialize(notAliquotedTube.toJson()));
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    try {
      AggregateIterable<Document> future2Result = future2.get();
      if (future2Result != null) {
        for (Document aliquotedTubes : future2Result) {
          participantLaboratoryRecordExtractions.add(LaboratoryRecordExtraction.deserialize(aliquotedTubes.toJson()));
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return participantLaboratoryRecordExtractions;
  }
}
