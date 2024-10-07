package com.chat.kit.my_util.account.util;

public class MemberPolicyProperties {
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,15}$";
    public static final int NICKNAME_CHANGE_COOLDOWN_DAYS = 30;
    public static final String NICKNAME_REGEX = "^[a-zA-Z0-9가-힣]{3,10}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PHONE_NUMBER_REGEX = "^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}$";
    public static final String PHONE_NUMBER_REGEX_WITHOUT_HYPHEN = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})\\d{4}$";


}

