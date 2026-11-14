package com.svetlana.deepfake_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "ai.detector.mode=heuristic")
class DeepfakeGatewayApplicationTests {

    @Test
    void contextLoads() {
    }
}

