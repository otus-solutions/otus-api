package br.org.otus.system;

import br.org.otus.email.BasicEmailSender;

public interface SystemConfigDao {
    void persist(SystemConfig systemConfig);

    Boolean isReady();

    SystemConfig fetchSystemConfig();

    BasicEmailSender findEmailSender();
}
