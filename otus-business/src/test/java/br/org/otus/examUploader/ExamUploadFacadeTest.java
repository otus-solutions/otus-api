package br.org.otus.examUploader;

import javax.inject.Inject;

import org.mockito.InjectMocks;

import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.ExamUploadService;

public class ExamUploadFacadeTest {

  @InjectMocks
  private ExamUploadFacade facade;

  @Inject
  private ExamUploadService examUploadService;

}
