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

    CompletableFuture<AggregateIterable<Document>> notAliquotedTubesFuture = CompletableFuture.supplyAsync(() -> {
      Document tubeCodeDocument = aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getTubeCodesInAliquotQuery()).first();
      ArrayList<String> tubeCodes = (tubeCodeDocument == null ? new ArrayList<>() : (ArrayList<String>) tubeCodeDocument.get("tubeCodes"));
      return participantLaboratoryDao.aggregate(
          new ParticipantLaboratoryExtractionQueryBuilder().getNotAliquotedTubesQuery(tubeCodes, attachedLaboratories, extractionFromUnattached));
    });

    CompletableFuture<AggregateIterable<Document>> aliquotedTubesFuture = CompletableFuture.supplyAsync(() ->
      aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getAliquotedTubesQuery(attachedLaboratories, extractionFromUnattached))
    );

    addFoundTubes(participantLaboratoryRecordExtractions, notAliquotedTubesFuture);
    addFoundTubes(participantLaboratoryRecordExtractions, aliquotedTubesFuture);

    return participantLaboratoryRecordExtractions;
  }

  private void addFoundTubes(LinkedList<LaboratoryRecordExtraction> participantLaboratoryRecordExtractions, CompletableFuture<AggregateIterable<Document>> tubesFuture){
    try {
      AggregateIterable<Document> tubesFutureResult = tubesFuture.get();
      if (tubesFutureResult == null) {
        return;
      }
      for (Document tubes : tubesFutureResult) {
        participantLaboratoryRecordExtractions.add(LaboratoryRecordExtraction.deserialize(tubes.toJson()));
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

}
