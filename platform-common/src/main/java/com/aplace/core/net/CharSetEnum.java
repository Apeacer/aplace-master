package com.aplace.core.net;

import com.aplace.core.enums.ValuesEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * CharSet
 *
 * @author apeace
 * @version 2017/6/10.
 */
public enum CharSetEnum implements ValuesEnums {

    UTF8(1, "UTF-8"),
    GBK(2, "GBK");

    private static final Map<Integer, CharSetEnum> VALUE_MAP = new HashMap<Integer, CharSetEnum>();

    static {
        for (CharSetEnum item : CharSetEnum.values()) {
            VALUE_MAP.put(item.value, item);
        }
    }
    public static CharSetEnum parse(Integer value, CharSetEnum defaultEnum) {
        if (null == value || !VALUE_MAP.containsKey(value)) {
            return defaultEnum;
        }
        return VALUE_MAP.get(value);
    }


    private Integer value;
    private String name;

    CharSetEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return this.name;
    }

}
