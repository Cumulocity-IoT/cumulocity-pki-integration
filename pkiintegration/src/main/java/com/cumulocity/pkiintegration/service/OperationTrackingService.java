package com.cumulocity.pkiintegration.service;

import org.springframework.stereotype.Service;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.SDKException;
import com.cumulocity.sdk.client.devicecontrol.DeviceControlApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationTrackingService {

	private final DeviceControlApi deviceControl;
	
	public void createOperation(final GId deviceId)	{
		
		final OperationRepresentation operation = new OperationRepresentation();
		operation.setDeviceId(deviceId);
		OperationRepresentation opCreateResponse = null;
		try {
			opCreateResponse = deviceControl.create(operation);
		} catch (final SDKException e) {
			log.error("Error occured while creating  operation for device " + deviceId + ".", e);
		}		
	}
}
