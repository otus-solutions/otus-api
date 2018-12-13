package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.inject.Inject;
import java.util.List;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;
    @Inject
    private AliquotDao aliquotDao;

    @Override
    public LaboratoryProgressDTO getOrphanExams() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getOrphansQuery());
    }

    @Override
    public LaboratoryProgressDTO getQuantitativeByTypeOfAliquots(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getQuantitativeQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getPendingResultsQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataOfStorageByAliquot(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getStorageByAliquotQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataOfResultsByExam(String center) throws DataNotFoundException {
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getResultsByExamQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) throws  DataNotFoundException{
        return aliquotDaoAggregate(new LaboratoryProgressQueryBuilder().getCSVOfPendingResultsQuery(center));
    }

    @Override
    public LaboratoryProgressDTO getDataToCSVOfOrphansByExam() throws DataNotFoundException {
        return examResultDaoAggregate(new LaboratoryProgressQueryBuilder().getCSVOfOrphansByExamQuery());
    }

    private LaboratoryProgressDTO aliquotDaoAggregate(List<Bson> query) throws  DataNotFoundException{
        Document first = aliquotDao.aggregate(query).first();
        validateFirst(first);
        return LaboratoryProgressDTO.deserialize(first.toJson());
    }

    private LaboratoryProgressDTO examResultDaoAggregate(List<Bson> query) throws  DataNotFoundException{
        Document first = examResultDao.aggregate(query).first();
        validateFirst(first);
        return LaboratoryProgressDTO.deserialize(first.toJson());
    }

    private void validateFirst(Document first) throws DataNotFoundException {
        if (first == null){
            throw new DataNotFoundException(new Throwable("There are no result"));
        }
    }
}
