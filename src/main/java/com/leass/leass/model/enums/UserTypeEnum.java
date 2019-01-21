package com.leass.leass.model.enums;

public enum UserTypeEnum {

    EMPLOYE ("EM"),
    CLIENT ("CL");

    private String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserTypeEnum getValueCode(String value) {
        for (UserTypeEnum item : UserTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
