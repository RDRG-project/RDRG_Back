package com.rdrg.back.dto.response;

public interface ResponseCode {
    String SUCCESS = "SU";
    String VALIDATION_FAIL = "VF";
    String DUPLICATE_ID = "DI";
    String DUPLICATE_EMAIL = "DE";
    String NO_EXIST_BOARD = "NB";
    String NO_EXIST_DEVICE = "ND";
    String NOT_RENTAL_DEVICE = "NRD";
    String WRITTEN_COMMENT = "WC";
    String SIGN_IN_FAIL = "SF";
    String AUTHENTICATION_FAIL = "AF";
    String AUTHORIZATION_FAIL = "AF";
    String NOT_FOUND = "NF";
    String PASSWORD_CHANGE_FAILED = "PCF";
    String TOKEN_CREATION_FAIL = "TF";
    String MAIL_SEND_FAIL = "MF";
    String DATABASE_ERROR = "DBE";
    String NO_EXIST_USERID = "NU";
    String NO_EXIST_RENT_DETAIL = "NRD";
}
