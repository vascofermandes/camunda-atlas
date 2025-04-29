package com.vasco.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public class DelegateSafeGets {
    public static String getSafeStringVariable(DelegateExecution execution, String variableName, String defaultValue) {
        Object value = execution.getVariable(variableName);
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        } else {
            return defaultValue;
        }
    }

    public static Integer getSafeIntegerVariable(DelegateExecution execution, String variableName, Integer defaultValue) {
        Object value = execution.getVariable(variableName);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public static Boolean getSafeBooleanVariable(DelegateExecution execution, String variableName, Boolean defaultValue) {
        Object value = execution.getVariable(variableName);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        } else {
            return defaultValue;
        }
    }

}
