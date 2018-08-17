package br.org.otus.security.dtos;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public interface AuthenticationData{

	String getUserEmail();

    String getKey();

    String getMode();

    String getRequestAddress();

    void setRequestAddress(String requestAddress);

    void encrypt() throws EncryptedException;

    JWTClaimsSet buildClaimSet();

    Boolean isValid();
}
