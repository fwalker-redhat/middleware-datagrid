package org.jboss.infinispan.jazz.test.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class StringGenerator {

    public static String value(int length) {
        return StringUtils.abbreviate(
                StringUtils.leftPad(UUID.randomUUID().toString(), length, "0"), length);
    }

}
