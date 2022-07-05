package com.cumulocity.pkiintegration.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CSRService {

	public Optional<String> GenerateCSR(String deviceExternalId, String tenantId) {
		try {
			KeyPairGenerator keyGen;
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keypair = keyGen.genKeyPair();

			// X500Principal subject = new X500Principal("CN=local.zedwood.com, O=zedwood,
			// OU=org, L=Orem, ST=Utah, C=US");
			X500Principal subject = new X500Principal("CN=" + deviceExternalId + tenantId);

			PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(subject,
					keypair.getPublic());
			JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
			ContentSigner signer;
			signer = csBuilder.build(keypair.getPrivate());
			PKCS10CertificationRequest csr = p10Builder.build(signer);
			PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csr.getEncoded());
			Writer csrStringWriter = new StringWriter();
			JcaPEMWriter pemWriter = new JcaPEMWriter(csrStringWriter);
			pemWriter.writeObject(pemObject);
			pemWriter.close();
			csrStringWriter.close();
			return Optional.of(csrStringWriter.toString());
		} catch (NoSuchAlgorithmException | OperatorCreationException | IOException e) {
			log.error("An error while creating PKCS10 certificate signing request for device {} on tenant {}",
					deviceExternalId, tenantId);
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}
}
