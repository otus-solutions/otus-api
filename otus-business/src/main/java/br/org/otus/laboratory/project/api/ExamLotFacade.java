package br.org.otus.laboratory.project.api;

import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.business.AliquotService;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import br.org.otus.laboratory.project.exam.examLot.persistence.ExamLotAliquotFilterDTO;
import br.org.otus.response.builders.Security;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import br.org.otus.laboratory.project.exam.examLot.businnes.ExamLotService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class ExamLotFacade {

    @Inject
    private ExamLotService examLotService;

    @Inject
    private AliquotService aliquotService;

    @Inject
    private LaboratoryProjectService laboratoryProjectService;

    public ExamLot create(ExamLot examLot, String userEmail) {
        try {
            return examLotService.create(examLot, userEmail);
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new HttpResponseException(
                    Security.Validation.build(e.getCause().getMessage(), e.getData()));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new HttpResponseException(
                    ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }

    public ExamLot update(ExamLot examLot) {
        try {
            return examLotService.update(examLot);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new HttpResponseException(
                    ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
        }
    }

    public void delete(String code) {
        try {
            examLotService.delete(code);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public List<ExamLot> getLots(String centerAcronym) {
        return examLotService.list(centerAcronym);
    }

    public Aliquot getAliquot(ExamLotAliquotFilterDTO examLotAliquotFilterDTO) {
        try {
            return examLotService.validateNewAliquot(examLotAliquotFilterDTO);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(),e.getData()));
        }
    }

    public LinkedHashSet<AliquoteDescriptor> getAvailableExams(String center) {
        try {
            return laboratoryProjectService.getAvailableExams(center);
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
        }
    }

    public List<Aliquot> getAliquots(String lotId) {
        ObjectId lotOId = new ObjectId(lotId);
        return aliquotService.getExamLotAliquots(lotOId);
    }

}
