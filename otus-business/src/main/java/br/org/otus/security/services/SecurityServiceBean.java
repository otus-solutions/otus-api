package br.org.otus.security.services;

import br.org.otus.model.User;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.user.UserDao;
import br.org.tutty.Equalizer;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@Stateless
public class SecurityServiceBean implements SecurityService {

    @Inject
    private UserDao userDao;

    @Inject
    private SystemConfigDaoBean systemConfigDao;

    @Inject
    private SecurityContextService securityContextService;

    @Override
    public UserSecurityAuthorizationDto authenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException {
        try {
            User user = userDao.fetchByEmail(authenticationData.getUserEmail());

            if (user.getPassword().equals(authenticationData.getKey())) {
                if (user.isEnable()) {
                    UserSecurityAuthorizationDto userSecurityAuthorizationDto = new UserSecurityAuthorizationDto();
                    Equalizer.equalize(user, userSecurityAuthorizationDto);

                    if(user.getFieldCenter() != null) {
                    	userSecurityAuthorizationDto.getFieldCenter().acronym = user.getFieldCenter().getAcronym();
                    }

                    String token = initializeToken(authenticationData);
                    userSecurityAuthorizationDto.setToken(token);
                    return userSecurityAuthorizationDto;

                } else {
                    throw new AuthenticationException();
                }
            } else {
                throw new AuthenticationException();
            }
        } catch (DataNotFoundException e) {
            throw new AuthenticationException();
        }
    }

  @Override
  public String getPasswordResetToken(AuthenticationData authenticationData) throws TokenException, AuthenticationException, DataNotFoundException {
      userDao.fetchByEmail(authenticationData.getUserEmail());

      //TODO 16/08/18: uncomment
      //aqui ele já cadastra o token na session
      //usaremos a mesma?
      String token = initializeToken(authenticationData);

      return token;

  }

  @Override
    public String projectAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException {
        try {
            SystemConfig systemConfig = systemConfigDao.fetchSystemConfig();
            String password = authenticationData.getKey();

            if (authenticationData.isValid()) {
                if (systemConfig.getProjectToken().equals(password)) {
                    return initializeToken(authenticationData);

                } else {
                    throw new AuthenticationException();
                }
            } else {
                throw new AuthenticationException();
            }
        } catch (NoResultException e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public void invalidate(String token) {
        securityContextService.removeToken(token);
    }

    private String initializeToken(AuthenticationData authenticationData) throws TokenException {
        byte[] secretKey = securityContextService.generateSecretKey();
        String jwtSignedAndSerialized = securityContextService.generateToken(authenticationData, secretKey);
        SessionIdentifier sessionIdentifier = new SessionIdentifier(jwtSignedAndSerialized, secretKey, authenticationData);
        securityContextService.addSession(sessionIdentifier);

        return jwtSignedAndSerialized;
    }

}
