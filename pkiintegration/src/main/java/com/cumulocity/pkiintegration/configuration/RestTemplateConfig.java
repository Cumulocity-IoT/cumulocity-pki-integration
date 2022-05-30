package com.cumulocity.pkiintegration.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		log.info("Initializing RestTemplate to connect with Certificate-Authority API");
		String certificatePassword = "FU63C84I";
		KeyStore clientStore = KeyStore.getInstance("PKCS12");
		try {
			
			InputStream inputStream = resourceLoader.getResource("classpath:certificate/so38.2.p12").getInputStream();
			clientStore.load(inputStream, certificatePassword.toCharArray());

			Enumeration<?> aliasEnum = clientStore.aliases();

			Key key = null;
			Certificate cert = null;

			while (aliasEnum.hasMoreElements()) {
				String keyName = (String) aliasEnum.nextElement();
				key = clientStore.getKey(keyName, certificatePassword.toCharArray());
				cert = clientStore.getCertificate(keyName);
			}

			KeyPair kp = new KeyPair(cert.getPublicKey(), (PrivateKey) key);

			System.out.println(kp.getPrivate());
			System.out.println(kp.getPublic());

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());

		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());

		} catch (CertificateException e) {
			log.error(e.getMessage());

		} catch (IOException e) {
			log.error(e.getMessage());

		}

		SSLContext sslContextBuilder;
		try {
//			sslContextBuilder = new SSLContextBuilder();
//			sslContextBuilder.setProtocol("TLS");
//			sslContextBuilder.loadKeyMaterial(clientStore, certificatePassword.toCharArray());
//			sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
//
//			
//			
//			
//			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
//					sslContextBuilder.build());
//			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
//					.build(); 
//			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
//					httpClient);
//			
//			return new RestTemplate(requestFactory);

			SSLContext sslContext = new SSLContextBuilder()
					.loadKeyMaterial(clientStore, certificatePassword.toCharArray())
					.loadTrustMaterial(new TrustSelfSignedStrategy()).build();

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			return new RestTemplate(factory);

		} catch (

		UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
