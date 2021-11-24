package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class TesteCaseResourceTest {

    @Test
    void approveFinancial() {

        TesteCaseResource testeCaseResource = new TesteCaseResource();
        ResponseEntity re = testeCaseResource.approveFinancial();
        assertEquals(re.getBody().toString(),"ok teste case");
    }
}