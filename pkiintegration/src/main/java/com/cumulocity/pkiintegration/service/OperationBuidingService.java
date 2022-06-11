package com.cumulocity.pkiintegration.service;

import org.springframework.stereotype.Service;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.operation.OperationRepresentation;

@Service
public class OperationBuidingService {
	
		public static OperationRepresentation buildKeyPairCreationOpeation(final String deviceId)	{
			OperationRepresentation operationRepresentation = new OperationRepresentation();
			operationRepresentation.set("text to be added", "c8y_Command");
			GId deviceIdAsGId = new GId(deviceId);
			operationRepresentation.setDeviceId(deviceIdAsGId);
			return operationRepresentation;
		}

}
