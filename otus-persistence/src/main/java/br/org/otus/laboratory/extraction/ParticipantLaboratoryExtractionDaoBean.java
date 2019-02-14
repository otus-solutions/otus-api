package br.org.otus.laboratory.extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

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

  @SuppressWarnings("unchecked")
  public LinkedList<LaboratoryRecordExtraction> getLaboratoryExtraction() {
    LinkedList<LaboratoryRecordExtraction> participantLaboratoryRecordExtractions = new LinkedList<LaboratoryRecordExtraction>();

    CompletableFuture<AggregateIterable<Document>> future1 = CompletableFuture.supplyAsync(() -> {
      AggregateIterable<Document> notAliquotedTubesDocument = null;
      Document tubeCodeDocument = aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getTubeCodesInAliquotQuery()).first();
      if (tubeCodeDocument != null) {
        notAliquotedTubesDocument = participantLaboratoryDao
            .aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getNotAliquotedTubesQuery((ArrayList<String>) tubeCodeDocument.get("tubeCodes")));
      }

      return notAliquotedTubesDocument;
    });

    CompletableFuture<AggregateIterable<Document>> future2 = CompletableFuture.supplyAsync(() -> aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getAliquotedTubesQuery()));

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
