package com.jp.dbbasedsession.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingServiceTest {

    private final GreetingService service = new GreetingService();

    @Test
    void testGreet() {
        String result = service.greet("JP");
        System.out.println("result = " + result);
        assertEquals("Hello, JP", result);
        System.out.println("assertEquals  result = " + result);
    }
}
