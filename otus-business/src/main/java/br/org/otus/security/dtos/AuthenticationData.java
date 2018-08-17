package br.org.otus.security.dtos;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public interface AuthenticationData extends JWTClaimSetBuilder{

	String getUserEmail();

    String getKey();

    String getMode();

    String getRequestAddress();

    void setRequestAddress(String requestAddress);

    void encrypt() throws EncryptedException;

    Boolean isValid();
}
