package com.rakbank.feeservice.config;

import brave.Tracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TraceIdPropagationInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private Tracing tracing;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        brave.propagation.TraceContext currentSpan = tracing.currentTraceContext().get();
        if (currentSpan != null) {
            HttpHeaders headers = request.getHeaders();
            headers.add("X-B3-TraceId", currentSpan.traceIdString());
            headers.add("X-B3-SpanId", currentSpan.spanIdString());
        }
        return execution.execute(request, body);
    }
}
