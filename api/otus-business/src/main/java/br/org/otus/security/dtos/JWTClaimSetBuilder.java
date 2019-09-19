package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;

public interface JWTClaimSetBuilder {

  JWTClaimsSet buildClaimSet();

}
