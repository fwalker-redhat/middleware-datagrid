package org.jboss.infinispan.jazz.test.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class StringGenerator {

    public static String value(Long length) {
        return StringUtils.abbreviate(
                StringUtils.leftPad(UUID.randomUUID().toString(), length.intValue(), "0"),
                length.intValue());
    }

}
