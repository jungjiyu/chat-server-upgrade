package com.chat.kit.my_util.account.enums;

import com.chat.kit.my_util.account.entity.BusinessPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum PolicyProperties {

    INFO_ACCESS_LIMIT_DAYS(7),
    LONG_COMMENT_POINT(8),
    MIDDLE_COMMENT_POINT(5),
    SHORT_COMMENT_POINT(3),
    LIKE_POINT(1);

    private int defaultValue;

    public static BusinessPolicy toEntity(PolicyProperties policyProperties){
        return BusinessPolicy.builder()
                .policyName(policyProperties)
                .value(policyProperties.getDefaultValue())
                .build();
    }

    public String getName() {
        return name();
    }

    public String getDescription() {
        return name();
    }
}
