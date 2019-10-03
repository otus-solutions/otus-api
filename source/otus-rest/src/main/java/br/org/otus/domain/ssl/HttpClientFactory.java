package br.org.otus.domain.ssl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.ccem.otus.exceptions.webservice.http.RestCallException;

public class HttpClientFactory {

	/**
	 * Ignore any validation about certificate. Use of this feature has security
	 * consequences
	 *
	 * @return
	 * @throws RestCallException
	 */
	public static CloseableHttpClient createIgnoringCertificate() throws RestCallException {
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setHostnameVerifier(new AllowAllHostnameVerifier())
					.setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build()).build();

			return httpClient;
		} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
			throw new RestCallException();
		}
	}

	public static HttpClient createBasicClient() {
		return HttpClientBuilder.create().build();
	}

}
