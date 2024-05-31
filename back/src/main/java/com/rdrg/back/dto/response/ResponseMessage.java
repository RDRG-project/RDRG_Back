package com.rdrg.back.dto.response;

public interface ResponseMessage {
    String SUCCESS = "Success.";
    String VALIDATION_FAIL = "Validation Failed.";
    String DUPLICATE_ID = "Duplicated Id.";
    String DUPLICATE_EMAIL = "Duplicated Email.";
    String NO_EXIST_BOARD = "No Exist Board";
    String NO_EXIST_DEVICE = "No Exist Device.";
    String NOT_RENTAL_DEVICE = "Not Rental Device.";
    String WRITTEN_COMMENT = "Written Comment";
    String SIGN_IN_FAIL = "Sign in Failed.";
    String AUTHENTICATION_FAIL = "Authentication Failed.";
    String AUTHORIZATION_FAIL = "Authorization Failed.";
    String NOT_FOUND = "Not Found";
    String PASSWORD_CHANGE_FAILED = "Password Change Failed";
    String TOKEN_CREATION_FAIL = "Token creation Failed.";
    String MAIL_SEND_FAIL = "Mail send Failed.";
    String DATABASE_ERROR = "Database Error.";
    String NO_EXIST_USERID = "No Exist UserId";
    String NO_EXIST_RENT_DETAIL = "No Exist Rent Detail";
}
