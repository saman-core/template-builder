package io.samancore.template_ops.model;

import lombok.Getter;

@Getter
public enum ConditionType {
    VALUE(0, "_value"),
    VISIBLE(1, "_visible"),
    DISABLE(2, "_disable"),
    ALERT(3, "_alert"),
    VALIDATE(4, "_validate");

    private final int value;
    private final String suffix;

    ConditionType(int value, String suffix) {
        this.value = value;
        this.suffix = suffix;
    }
}