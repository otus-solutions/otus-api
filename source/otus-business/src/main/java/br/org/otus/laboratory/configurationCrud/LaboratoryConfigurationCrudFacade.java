package br.org.otus.laboratory.configurationCrud;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configurationCrud.business.LaboratoryConfigurationCrudService;
import br.org.otus.laboratory.configurationCrud.business.aliquot.AliquotConfigurationService;
import br.org.otus.laboratory.configurationCrud.business.controlGroup.ControlGroupConfigurationService;
import br.org.otus.laboratory.configurationCrud.business.exam.ExamConfigurationService;
import br.org.otus.laboratory.configurationCrud.business.moment.MomentConfigurationService;
import br.org.otus.laboratory.configurationCrud.business.tube.TubeConfigurationService;
import br.org.otus.laboratory.configurationCrud.model.*;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;

public class LaboratoryConfigurationCrudFacade {

  @Inject
  private TubeConfigurationService tubeConfigurationService;
  @Inject
  private AliquotConfigurationService aliquotConfigurationService;
  @Inject
  private MomentConfigurationService momentConfigurationService;
  @Inject
  private ExamConfigurationService examConfigurationService;
  @Inject
  private ControlGroupConfigurationService controlGroupConfigurationService;
  @Inject
  private LaboratoryConfigurationCrudService laboratoryConfigurationCrudService;

  public LaboratoryConfiguration persistConfiguration(LaboratoryConfiguration laboratoryConfiguration) {
    return laboratoryConfigurationCrudService.persistConfiguration(laboratoryConfiguration);
  }

  public void createTube(TubeConfiguration tubeConfiguration) {
    tubeConfigurationService.create(tubeConfiguration);
  }

  public ArrayList<TubeConfiguration> indexTube() {
    try{
      return tubeConfigurationService.index();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

  public void createAliquot(AliquotConfiguration aliquotConfiguration) {
    aliquotConfigurationService.create(aliquotConfiguration);
  }

  public ArrayList<AliquotConfiguration> indexAliquot() {
    try{
      return aliquotConfigurationService.index();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

  public void createMoment(MomentConfiguration momentConfiguration) {
    momentConfigurationService.create(momentConfiguration);
  }

  public ArrayList<MomentConfiguration> indexMoment() {
    try{
      return momentConfigurationService.index();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

  public void createExam(ExamConfiguration examConfiguration) {
    examConfigurationService.create(examConfiguration);
  }

  public ArrayList<ExamConfiguration> indexExam() {
    try{
      return examConfigurationService.index();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }

  public void createControlGroup(ControlGroupConfiguration controlGroupConfiguration) {
    controlGroupConfigurationService.create(controlGroupConfiguration);
  }

  public ArrayList<ControlGroupConfiguration> indexControlGroup() {
    try{
      return controlGroupConfigurationService.index();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }
}