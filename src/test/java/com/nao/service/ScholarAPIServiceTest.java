package com.nao.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScholarAPIServiceTest {

    @Test
    void testServiceInitialization() {
        ScholarAPIService service = new ScholarAPIService();
        assertNotNull(service);
    }
}
