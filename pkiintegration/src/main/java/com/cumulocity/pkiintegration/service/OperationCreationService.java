package com.cumulocity.pkiintegration.service;

import org.springframework.stereotype.Service;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.operation.OperationRepresentation;

@Service
public class OperationCreationService {
	
		public static OperationRepresentation createKeyPairCreationOpeation(final GId deviceId)	{
			OperationRepresentation operationRepresentation = new OperationRepresentation();
			operationRepresentation.set("text to be added", "c8y_Command");
			operationRepresentation.setDeviceId(deviceId);
			return operationRepresentation;
		}

}
