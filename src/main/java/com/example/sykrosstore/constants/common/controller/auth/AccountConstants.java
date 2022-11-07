package com.example.sykrosstore.constants.common.controller.auth;

public class AccountConstants {
    public static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_STATUS_INACTIVE = "INACTIVE";

    public enum ROLE_KEY{
        ADMIN,
        USER,
        PUBLISHER,
        CUSTOMER,
        AUTHOR,
        GUEST
    }

    public static Long getRoleId(ROLE_KEY key){
        switch (key){
            case USER:
                return 2L;
            case ADMIN:
                return 1L;
            case GUEST:
                return 6L;
            case CUSTOMER:
                return 4L;
            case AUTHOR:
                return 5L;
            case PUBLISHER:
                return 3L;
        }
        return 6L;
    }
}
