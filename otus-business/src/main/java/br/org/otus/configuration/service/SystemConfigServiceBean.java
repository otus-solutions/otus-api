package br.org.otus.configuration.service;

import br.org.otus.configuration.builder.SystemConfigBuilder;
import br.org.otus.configuration.builder.TokenBuilder;
import br.org.otus.configuration.dto.OtusInitializationConfigDto;
import br.org.otus.email.service.EmailNotifierService;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.api.UserFacade;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SystemConfigServiceBean implements SystemConfigService {

    @Inject
    private SystemConfigDaoBean systemConfigDao;

    @Inject
    private UserFacade userFacade;

    @Inject
    private EmailNotifierService emailNotifierService;

    @Override
    public void initConfiguration(OtusInitializationConfigDto initializationConfigDto, String projectToken) throws EncryptedException, EmailNotificationException, AlreadyExistException {
        if (!isReady()) {
            verifyEmailService(initializationConfigDto);

            SystemConfig systemConfig = SystemConfigBuilder.builSystemConfig(initializationConfigDto, projectToken);

            systemConfigDao.persist(systemConfig);
            userFacade.create(initializationConfigDto);
        } else {
            throw new AlreadyExistException();
        }
    }

    @Override
    public void verifyEmailService(OtusInitializationConfigDto initializationData) throws EmailNotificationException, EncryptedException {
        emailNotifierService.sendSystemInstallationEmail(initializationData);
    }

    @Override
    public String buildToken() {
        return TokenBuilder.build();
    }

    @Override
    public Boolean isReady() {
        return systemConfigDao.isReady();
    }
}
