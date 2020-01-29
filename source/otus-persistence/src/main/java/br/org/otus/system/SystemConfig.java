package br.org.otus.system;

import br.org.otus.email.BasicEmailSender;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;

public class SystemConfig {

  @Equalization(name = "project_name")
  private String projectName;
  @Equalization(name = "domain_rest_url")
  private String domainRestUrl;
  @Equalization(name = "project_token")
  private String projectToken;
  private BasicEmailSender basicEmailSender;

  public SystemConfig() {
    basicEmailSender = new BasicEmailSender();
  }

  public void setProjectToken(String projectToken) {
    this.projectToken = projectToken;
  }

  public String getProjectToken() {
    return projectToken;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getDomainRestUrl() {
    return domainRestUrl;
  }

  public BasicEmailSender getEmailSender() {
    return basicEmailSender;
  }

  public static SystemConfig deserialize(String systemConfig) {
    return getGsonBuilder().create().fromJson(systemConfig, SystemConfig.class);
  }

  public static String serialize(SystemConfig systemConfig) {
    return getGsonBuilder().create().toJson(systemConfig);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }
}
