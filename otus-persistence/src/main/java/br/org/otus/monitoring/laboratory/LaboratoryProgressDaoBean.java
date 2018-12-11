package br.org.otus.monitoring.laboratory;

import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.monitoring.builder.LaboratoryProgressQueryBuilder;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.inject.Inject;

public class LaboratoryProgressDaoBean implements LaboratoryProgressDao {

    @Inject
    private ExamResultDao examResultDao;
    @Inject
    private AliquotDao aliquotDao;

    @Override
    public LaboratoryProgressDTO getOrphanExams() {
        LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder = new LaboratoryProgressQueryBuilder();
        return LaboratoryProgressDTO.deserialize(examResultDao.aggregate(laboratoryProgressQueryBuilder.getOrphansQuery()).first().toJson());
    }

    @Override
    public LaboratoryProgressDTO getQuantitativeByTypeOfAliquots() {
        LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder = new LaboratoryProgressQueryBuilder();
        return LaboratoryProgressDTO.deserialize(aliquotDao.aggregate(laboratoryProgressQueryBuilder.getQuantitativeQuery()).first().toJson());
    }

    @Override
    public LaboratoryProgressDTO getDataOfPendingResultsByAliquot() {
        LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder = new LaboratoryProgressQueryBuilder();
        return LaboratoryProgressDTO.deserialize(aliquotDao.aggregate(laboratoryProgressQueryBuilder.getPendingResultsQuery()).first().toJson());
    }

    @Override
    public LaboratoryProgressDTO getDataOfStorageByAliquot() {
        LaboratoryProgressQueryBuilder laboratoryProgressQueryBuilder = new LaboratoryProgressQueryBuilder();
        return LaboratoryProgressDTO.deserialize(aliquotDao.aggregate(laboratoryProgressQueryBuilder.getStorageByAliquotQuery()).first().toJson());
    }
}
