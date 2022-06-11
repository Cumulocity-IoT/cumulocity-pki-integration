package com.cumulocity.pkiintegration.service;

import java.time.Duration;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.model.operation.OperationStatus;
import com.cumulocity.pkiintegration.model.PollingResult;
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
	private final PollingService pollingService;
	
	public void createOperation(final OperationRepresentation operationRepresentation)	{
		
		try {
			OperationRepresentation opCreateResponse = deviceControl.create(operationRepresentation);
		} catch (final SDKException e) {
			log.error("Error occured while creating  operation for device " + operationRepresentation.getDeviceId().getValue() + ".", e);
		}		
	}
	
	public String traceOperation(final GId operationId)	{
		
		final Callable<PollingResult> checkForFinalOperationStatus = () -> {
			OperationRepresentation currentOperationRepresentation = deviceControl.getOperation(operationId);
			String currentOperationStatus = currentOperationRepresentation.getStatus();
			if (currentOperationRepresentation.getStatus().equalsIgnoreCase(OperationStatus.FAILED.toString()) || currentOperationRepresentation.getStatus().equalsIgnoreCase(OperationStatus.SUCCESSFUL.toString()))	{
				return new PollingResult(currentOperationStatus, true);
			} else	{
				return new PollingResult(null, false);
			}
		};	
		log.debug("Start polling for final status of operation with id[{}] of file to AC=[{}] fileName=[{}] version=[{}]", operationId.getValue());
		final PollingResult pollingResult = pollingService.poll(checkForFinalOperationStatus, "OperationStatus#" + operationId.getValue(),
				Duration.ofSeconds(10),
				Duration.ofSeconds(5));
		return pollingResult.getOperationStatus();
		
	}
}
