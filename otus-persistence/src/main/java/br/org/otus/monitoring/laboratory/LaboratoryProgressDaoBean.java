package br.org.otus.monitoring.laboratory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;
import org.jetbrains.annotations.Nullable;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

  @Inject
  private ExamResultDao examResultDao;
  @Inject
  private AliquotDao aliquotDao;

  @Override
  public LaboratoryProgressDTO getDataOrphanByExams() throws DataNotFoundException {
    return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getOrphansQuery());
  }

  @Override
  @SuppressWarnings("unchecked")
  public LaboratoryProgressDTO getDataQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException {
    LaboratoryProgressDTO fullDTO;
    LaboratoryProgressDTO partialDTO = new LaboratoryProgressDTO();
    Document SecondPartOfDTO = null;

    fullDTO = aliquotDaoAggregate(
        new LaboratoryProgressQueryBuilder().getQuantitativeByTypeOfAliquotsFirstPartialResultQuery(center));

    Document fetchCenterAliquotCodesDocument = fetchAliquotCodes(center);

    if (fetchCenterAliquotCodesDocument != null) {
      Object aliquotCodes = fetchCenterAliquotCodesDocument.get("aliquotCodes");
      Document fetchAliquotCodesInExams = examResultDao
          .aggregate(
              new LaboratoryProgressQueryBuilder().getAliquotCodesInExamsQuery((ArrayList<String>) aliquotCodes))
          .first();
      if (fetchAliquotCodesInExams != null) {
        Object aliquotCodesInExam = fetchAliquotCodesInExams.get("aliquotCodes");
        SecondPartOfDTO = aliquotDao
            .aggregate(new LaboratoryProgressQueryBuilder()
                .getQuantitativeByTypeOfAliquotsSecondPartialResultQuery((ArrayList<String>) aliquotCodesInExam))
            .first();
      }
    }

    if (SecondPartOfDTO != null) {
      partialDTO = LaboratoryProgressDTO.deserialize(SecondPartOfDTO.toJson());
    }

    fullDTO.concatReceivedToAliquotStats(partialDTO);
    return fullDTO;
  }

  @Override
  @SuppressWarnings("unchecked")
  public LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException {

    CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();

    CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams
        .thenApply(allAliquotsWithExamsDocument -> {
          LaboratoryProgressDTO laboratoryProgressDTO = new LaboratoryProgressDTO();
          LaboratoryProgressDTO laboratoryProgressPartialDTO = new LaboratoryProgressDTO();

          if (allAliquotsWithExamsDocument != null) {

            Object allAliquotCodesInExams = allAliquotsWithExamsDocument.get("aliquotCodes");

            CompletableFuture<Document> Future1 = CompletableFuture.supplyAsync(() -> aliquotDao
                .aggregate(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotFirstPartialResultQuery(
                    (ArrayList<String>) allAliquotCodesInExams, center))
                .first());

            CompletableFuture<Document> Future2 = CompletableFuture.supplyAsync(() -> aliquotDao
                .aggregate(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotSecondPartialResultQuery(
                    (ArrayList<String>) allAliquotCodesInExams, center))
                .first());

            Document firstPartOfDTO = null;
            try {
              firstPartOfDTO = Future1.get();
            } catch (InterruptedException ignored) {
            } catch (ExecutionException e) {
              throw new IllegalStateException();
            }

            if (firstPartOfDTO != null) {
              laboratoryProgressDTO = LaboratoryProgressDTO.deserialize(firstPartOfDTO.toJson());
              laboratoryProgressDTO.concatReceivedToPendingResults(laboratoryProgressPartialDTO);
            }

            Document secondPartOfDTO = null;
            try {
              secondPartOfDTO = Future2.get();
            } catch (InterruptedException ignored) {
            } catch (ExecutionException e) {
              throw new IllegalStateException();
            }

            if (firstPartOfDTO == null && secondPartOfDTO == null) {
              throw new IllegalStateException();
            }

            if (secondPartOfDTO != null) {
              laboratoryProgressPartialDTO = LaboratoryProgressDTO.deserialize(secondPartOfDTO.toJson());
              if (firstPartOfDTO != null) {
                laboratoryProgressDTO.concatReceivedToPendingResults(laboratoryProgressPartialDTO);
              } else {
                laboratoryProgressDTO = LaboratoryProgressDTO.deserialize(secondPartOfDTO.toJson());
                laboratoryProgressDTO.fillEmptyWaitingToPendingResults();
              }
            }

          } else {
            Document pendingAliquots = aliquotDao
                    .aggregate(new LaboratoryProgressQueryBuilder().getPendingResultsByAliquotQuery(center)).first();

            if (pendingAliquots == null){
              throw new IllegalStateException();
            }

            laboratoryProgressDTO = LaboratoryProgressDTO.deserialize(pendingAliquots.toJson());
            laboratoryProgressDTO.concatReceivedToPendingResults(laboratoryProgressPartialDTO);
          }

          return laboratoryProgressDTO;
        });

    return getLaboratoryProgressDTO(greetingFuture);
  }

  @Override
  @SuppressWarnings("unchecked")
  public LaboratoryProgressDTO getDataByExam(String center) throws DataNotFoundException {
    Document fetchAllAliquotCodes = fetchAliquotCodes(center);
    validateFirst(fetchAllAliquotCodes);
    Object aliquotCodes = fetchAllAliquotCodes.get("aliquotCodes");
    Document first1 = examResultDao
        .aggregate(new LaboratoryProgressQueryBuilder().getDataByExamQuery((ArrayList<String>) aliquotCodes)).first();
    validateFirst(first1);
    return LaboratoryProgressDTO.deserialize(first1.toJson());

  }

  @Override
  public LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException {
    return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(center));
  }

  @Override
  @SuppressWarnings("unchecked")
  public LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws DataNotFoundException {
    CompletableFuture<Document> aliquotsWithExams = this.fetchAliquotsWithExams();
    CompletableFuture<LaboratoryProgressDTO> greetingFuture = aliquotsWithExams.thenApply(aliquotsWithExamsDocument -> {
      if (aliquotsWithExamsDocument == null) {
        throw new IllegalStateException();
      }

      Object aliquotCodes = aliquotsWithExamsDocument.get("aliquotCodes");
      Document first = aliquotDao.aggregate(
          new LaboratoryProgressQueryBuilder().getPendingAliquotsCsvDataQuery((ArrayList<String>) aliquotCodes, center))
          .first();

      if (first == null) {
        throw new IllegalStateException();
      }
      return LaboratoryProgressDTO.deserialize(first.toJson());
    });

    return getLaboratoryProgressDTO(greetingFuture);
  }

  @Nullable
  private LaboratoryProgressDTO getLaboratoryProgressDTO(CompletableFuture<LaboratoryProgressDTO> greetingFuture)
      throws DataNotFoundException {
    try {
      return greetingFuture.get();
    } catch (InterruptedException ignored) {
    } catch (ExecutionException e) {
      throw new DataNotFoundException(new Throwable("There are no result"));
    }

    return null;
  }

  @Override
  public LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException {
    return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery());
  }

  private Document fetchAliquotCodes(String center) {
    return aliquotDao.aggregate(new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesQuery(center)).first();
  }

  private CompletableFuture<Document> fetchAliquotsWithExams() {
    return CompletableFuture.supplyAsync(
        () -> examResultDao.aggregate(new LaboratoryProgressQueryBuilder().fetchAllAliquotCodesInExamsQuery()).first());
  }

  private LaboratoryProgressDTO aliquotDaoAggregate(List<Bson> query) throws DataNotFoundException {
    Document first = aliquotDao.aggregate(query).first();
    validateFirst(first);
    return LaboratoryProgressDTO.deserialize(first.toJson());
  }

  private LaboratoryProgressDTO examResultDaoAggregate(List<Bson> query) throws DataNotFoundException {
    Document first = examResultDao.aggregate(query).first();
    validateFirst(first);
    return LaboratoryProgressDTO.deserialize(first.toJson());
  }

  private void validateFirst(Document first) throws DataNotFoundException {
    if (first == null) {
      throw new DataNotFoundException(new Throwable("There are no result"));
    }
  }
}
