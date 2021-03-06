package com.encens.khipus.model.employees;

/**
 * @author
 * @version 3.4
 */
public enum DiscountRuleRangeType {
    MINUTE("DiscountRuleRangeType.minutes"),
    AMOUNT("DiscountRuleRangeType.AMOUNT");

    private String resourceKey;

    DiscountRuleRangeType(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

}
