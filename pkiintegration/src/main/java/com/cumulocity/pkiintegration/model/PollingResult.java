package com.cumulocity.pkiintegration.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Data
@RequiredArgsConstructor
public class PollingResult {
	private final String result;
	private final boolean successful;
}
