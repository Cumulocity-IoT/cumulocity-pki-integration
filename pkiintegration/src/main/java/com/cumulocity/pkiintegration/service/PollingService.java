package com.cumulocity.pkiintegration.service;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import com.cumulocity.pkiintegration.model.PollingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PollingService {

	/**
	 * Execute the provided callable method until it returns true or the method gets timedout.
	 *
	 * @param <T>
	 * @param pollee
	 *                        - Callable method that returns boolean value.
	 * @param callerReference
	 *                        - A reference for the context in which the method was used.
	 * @param pollingDelay
	 *                        - Waiting time after the first call to the callable method.
	 * @param timeout
	 *                        - Time after which the method stops polling
	 */
	public PollingResult poll(final Callable<PollingResult> pollee, final String callerReference, final Duration pollingDelay, final Duration timeout) {

		final AtomicReference<PollingResult> resultRef = new AtomicReference<>();

		final Thread poller = new Thread(() -> {
			try {
				final long startMs = System.currentTimeMillis();
				final long timeoutMs = timeout.toMillis();
				while (true) {
					final PollingResult pollingResult = pollee.call();
					resultRef.set(pollingResult);
					if (isTimeoutReached(startMs, timeoutMs)) {
						log.trace("Polling timedout for {}", callerReference);
						resultRef.get();
						return;
					}
					if (pollingResult.isSuccessful()) {
						log.trace("Polling finished for {}", callerReference);
						return;
					} else {
						Thread.sleep(pollingDelay.toMillis());
						log.trace("Polling continues for {}", callerReference);
					}
				}
			} catch (final InterruptedException e) {
				log.error("Interrupted polling", e);
				resultRef.set(new PollingResult(e.getClass().getName() + ": " + e.getMessage(), false));
			} catch (final Exception e) {
				log.error("Polling error", e);
				resultRef.set(new PollingResult(e.getClass().getName() + ": " + e.getMessage(), false));
			}
		});

		poller.start();
		log.trace("Polling for {} started with period of {}", callerReference, pollingDelay);
		try {
			poller.join();
		} catch (final InterruptedException e) {
			log.error("Interrupted polling", e);
		}
		final PollingResult pollingResult = resultRef.get();
		if (pollingResult == null) {
			return new PollingResult("Polling failed with unknown error", false);
		} else {
			return pollingResult;
		}
	}
	
	private boolean isTimeoutReached(final long startMs, final long timeoutMs) {
		return System.currentTimeMillis() - startMs > timeoutMs;
	}	

}