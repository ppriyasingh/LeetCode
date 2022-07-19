package com.company.practice;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionConfig {

//    public enum = [];
    Map<String, Double> plan = new HashMap<>();

    public SubscriptionConfig() {
        plan.put("BASIC", 9.99);
        plan.put("STANDARD", 49.99);
        plan.put("PREMIUM", 249.99);

    }
}
