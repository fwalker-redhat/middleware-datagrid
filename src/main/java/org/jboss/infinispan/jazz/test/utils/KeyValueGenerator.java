package org.jboss.infinispan.jazz.test.utils;

import java.util.HashMap;
import java.util.Map;

public class KeyValueGenerator {

    public static Map<String, String> generateKeyValues(Long count, Long keyLength, Long valueLength) {
        Map<String, String> result = new HashMap<>();
        while (result.size() < count) {
            result.put(StringGenerator.value(keyLength), StringGenerator.value(valueLength));
        }
        return result;
    }

}
