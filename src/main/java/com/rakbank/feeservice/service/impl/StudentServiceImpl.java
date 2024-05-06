package com.rakbank.feeservice.service.impl;


import com.rakbank.feeservice.core.exception.BusinessException;
import com.rakbank.feeservice.data.dto.Student;
import com.rakbank.feeservice.properties.StudentServiceProperties;
import com.rakbank.feeservice.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final String SERVICE_NAME = "student-service";

    @Qualifier("student-service-rest-client")
    private final RestClient restClient;

    private final StudentServiceProperties properties;

    @CircuitBreaker(name=SERVICE_NAME)
    @Retry(name = SERVICE_NAME, fallbackMethod = "getStudentInformationFromCache")
    @Override
    public List<Student> getStudentsByGrade(String grade) {
            List<Student> result = restClient.get()
                    .uri(properties.getStudentsByGradeApi(), grade)
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new BusinessException("2002", HttpStatus.INTERNAL_SERVER_ERROR, "Student Not Found");
                    })
                    .body(new ParameterizedTypeReference<List<Student>>() {
                    });
            return result;
    }

    /**
     * If All 3 retries failed it will fetch from redis cache
     * @param grade
     * @param e
     * @return
     */
    private List<Student> getStudentInformationFromCache(String grade, Exception e) {
//        throw new BusinessException("2001", HttpStatus.INTERNAL_SERVER_ERROR, "Student Not Found");
        // TOTO incase of payment service we need to retry the payment.
        return new ArrayList<>();
    }
}
