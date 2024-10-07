package dev.sfu.demo.camel_kafka.model;

import java.util.HashMap;
import java.util.Map;

public class WordCountResult {

    private Map<String, Integer> map = new HashMap<>();

    public void addCount(String word) {
        map.compute(word, (k, v) -> v == null ? 1 : v + 1);
    }

    public int getCount(String word) {
        return map.getOrDefault(word, 0);
    }

    public String toString() {
        return map.toString();
    }
}
