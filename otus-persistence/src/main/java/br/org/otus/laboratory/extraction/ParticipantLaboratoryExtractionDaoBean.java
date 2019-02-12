package br.org.otus.laboratory.extraction;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;

import br.org.otus.laboratory.extraction.builder.ParticipantLaboratoryExtractionQueryBuilder;
import br.org.otus.laboratory.extraction.model.ParticipantLaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;

public class ParticipantLaboratoryExtractionDaoBean {

  @Inject
  private AliquotDao aliquotDao;
  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;

  @SuppressWarnings("unchecked")
  public LinkedList<ParticipantLaboratoryRecordExtraction> getLaboratoryExtractionByParticipant() {
    LinkedList<ParticipantLaboratoryRecordExtraction> participantLaboratoryRecordExtractions = new LinkedList<ParticipantLaboratoryRecordExtraction>();
    Document tubeCodeDocument = aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getTubeCodesInAliquotQuery()).first();

    if (tubeCodeDocument != null) {
      AggregateIterable<Document> notAliquotedTubesInDocument = participantLaboratoryDao
          .aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getNotAliquotedTubesQuery((ArrayList<String>) tubeCodeDocument.get("tubeCodes")));
      if (notAliquotedTubesInDocument != null) {
        for (Document notAliquotedTube : notAliquotedTubesInDocument) {
          participantLaboratoryRecordExtractions.add(ParticipantLaboratoryRecordExtraction.deserialize(notAliquotedTube.toJson()));
        }
      }
    }

    AggregateIterable<Document> aliquotedTubesInDocument = aliquotDao.aggregate(new ParticipantLaboratoryExtractionQueryBuilder().getAliquotedTubesQuery());
    if (aliquotedTubesInDocument != null) {
      for (Document aliquotedTubes : aliquotedTubesInDocument) {
        participantLaboratoryRecordExtractions.add(ParticipantLaboratoryRecordExtraction.deserialize(aliquotedTubes.toJson()));
      }
    }

    return participantLaboratoryRecordExtractions;
  }

}
