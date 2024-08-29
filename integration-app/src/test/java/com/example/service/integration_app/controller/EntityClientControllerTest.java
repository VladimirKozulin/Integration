package com.example.service.integration_app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EntityClientControllerTest extends AbstractTest {
    @Test
    public void whenAllEntitiesExist_thenReturnEntityList() throws Exception {
        assertTrue(RedisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/client/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(databaseEntityService.findAll());

        assertFalse(redisTemplate.keys("*").isEmpty());
        JsonAssertion.assertEquals(expectedResponse, actualResponse);
    }
}

