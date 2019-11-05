package br.org.otus.configuration.dto;

import br.org.otus.email.dto.EmailSenderDto;
import br.org.otus.project.dto.ProjectDto;
import br.org.otus.user.dto.UserDto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OtusInitializationConfigDtoTest {

    @InjectMocks
    OtusInitializationConfigDto otusInitializationConfigDto;
    @Mock
    UserDto userDto;
    @Mock
    EmailSenderDto emailSenderDto;
    @Mock
    ProjectDto projectDto;
    @Mock
    DomainDto domainDto;

    @Before
    public void setUp(){
        otusInitializationConfigDto.setDomain(domainDto);
        otusInitializationConfigDto.setEmailSender(emailSenderDto);
        otusInitializationConfigDto.setProject(projectDto);
        otusInitializationConfigDto.setUser(userDto);
    }

    @Test
    public void getUser_method_should_return_userDto(){
        assertEquals(userDto,otusInitializationConfigDto.getUser());
    }

    @Test
    public void getProject_method_should_return_projectDto(){
        assertEquals(projectDto,otusInitializationConfigDto.getProject());
    }

    @Test
    public void getDomainDto_method_should_return_domainDto(){
        assertEquals(domainDto,otusInitializationConfigDto.getDomainDto());
    }

    @Test
    public void getEmailSender_method_should_return_emailSenderDto(){
        assertEquals(emailSenderDto,otusInitializationConfigDto.getEmailSender());
    }

    @Test
    public void encrypt_method_should_call_emailSender_and_user_encrypt_methods() throws EncryptedException {
        otusInitializationConfigDto.encrypt();
        verify(emailSenderDto, times(1)).encrypt();
        verify(userDto, times(1)).encrypt();
    }
}
