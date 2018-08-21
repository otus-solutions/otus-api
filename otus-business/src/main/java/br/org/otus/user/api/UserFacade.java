package br.org.otus.user.api;

import java.util.List;

import javax.inject.Inject;

import br.org.otus.extraction.ExtractionSecurityService;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.dto.PasswordResetDto;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.model.User;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.user.dto.ManagementUserDto;
import br.org.otus.user.dto.SignupDataDto;
import br.org.otus.user.management.ManagementUserService;
import br.org.otus.user.signup.SignupService;

public class UserFacade {

  @Inject
  private EmailNotifierService emailNotifierService;

  @Inject
  private ManagementUserService managementUserService;

  @Inject
  private SignupService signupService;

  @Inject
  private ExtractionSecurityService extractionSecurityService;

  @Inject
  private SecurityFacade securityFacade;

  public void create(OtusInitializationConfigDto initializationConfigDto) {
    try {
      signupService.create(initializationConfigDto);

    } catch (EmailNotificationException | EncryptedException e) {
      throw new HttpResponseException(ResponseBuild.Email.CommunicationFail.build());

    } catch (AlreadyExistException e) {
      throw new HttpResponseException(ResponseBuild.User.AlreadyExist.build());

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());
    }
  }

  public void create(SignupDataDto signupDataDto) {
    try {
      signupService.create(signupDataDto);

    } catch (EncryptedException | EmailNotificationException e) {
      throw new HttpResponseException(ResponseBuild.Email.CommunicationFail.build());

    } catch (AlreadyExistException e) {
      throw new HttpResponseException(ResponseBuild.User.AlreadyExist.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.System.NotInitialized.build());

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());
    }
  }

  public List<ManagementUserDto> list() {
    return managementUserService.list();
  }

  public void disable(ManagementUserDto managementUserDto) {
    try {
      managementUserService.disable(managementUserDto);

    } catch (EmailNotificationException | EncryptedException e) {
      throw new HttpResponseException(ResponseBuild.Email.CommunicationFail.build());

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.System.NotInitialized.build());
    }
  }

  public void enable(ManagementUserDto managementUserDto) {
    try {
      managementUserService.enable(managementUserDto);

    } catch (EmailNotificationException | EncryptedException e) {
      throw new HttpResponseException(ResponseBuild.Email.CommunicationFail.build());

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.System.NotInitialized.build());
    }
  }

  public void disableExtraction(ManagementUserDto managementUserDto) {
    try {
      managementUserService.disableExtraction(managementUserDto);

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.System.NotInitialized.build());
    }
  }

  public void enableExtraction(ManagementUserDto managementUserDto) {
    try {
      managementUserService.enableExtraction(managementUserDto);

    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.System.NotInitialized.build());
    }
  }

  public void updateExtractionIps(ManagementUserDto managementUserDto) {
    try {
      managementUserService.updateExtractionIps(managementUserDto);
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());

    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build());
    }
  }

  public String getExtractionToken(String email) {
    try {
      return extractionSecurityService.getExtractionToken(email);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public void updateFieldCenter(ManagementUserDto managementUserDto) {
    try {
      managementUserService.updateFieldCenter(managementUserDto);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public User fetchByEmail(String email) {
    try {
      return managementUserService.fetchByEmail(email);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public void requestPasswordReset(PasswordResetRequestDto requestData) {
    securityFacade.removePasswordResetRequests(requestData.getEmail());
    securityFacade.requestPasswordReset(requestData);

    try {
      managementUserService.requestPasswordReset(requestData);
    } catch (EncryptedException | DataNotFoundException | EmailNotificationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public void validatePasswordRecoveryRequest(String token) {
    securityFacade.validatePasswordResetRequest(token);
  }

  public void updateUserPassword(PasswordResetDto passwordResetDto) {
    String requestEmail = securityFacade.getRequestEmail(passwordResetDto.getToken());
    passwordResetDto.setEmail(requestEmail);
    try {
      managementUserService.updateUserPassword(passwordResetDto);
    } catch (EncryptedException e) {
      e.printStackTrace();
    }
  }
}
