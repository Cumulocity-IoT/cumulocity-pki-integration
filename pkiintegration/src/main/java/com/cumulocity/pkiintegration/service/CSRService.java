package com.cumulocity.pkiintegration.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

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

@Service
@RequiredArgsConstructor
public class CSRService {

	public String GenerateCSR() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keypair = keyGen.genKeyPair();

			//X500Principal subject = new X500Principal("CN=local.zedwood.com, O=zedwood, OU=org, L=Orem, ST=Utah, C=US");
			X500Principal subject = new X500Principal("CN=Requested Test Certificate");
			
			PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(subject,
					keypair.getPublic());
			JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
			ContentSigner signer;
			try {
				signer = csBuilder.build(keypair.getPrivate());
				PKCS10CertificationRequest csr = p10Builder.build(signer);
				System.out.println(csr.getEncoded().toString());
				try {
					PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csr.getEncoded());
					Writer csrStringWriter = new StringWriter();
					JcaPEMWriter pemWriter = new JcaPEMWriter(csrStringWriter);
					pemWriter.writeObject(pemObject);
					pemWriter.close();
					csrStringWriter.close();
					return csrStringWriter.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (OperatorCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
