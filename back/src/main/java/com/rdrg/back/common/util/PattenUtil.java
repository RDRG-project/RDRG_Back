package com.rdrg.back.common.util;

public interface PattenUtil {
    String ID_PATTERN = ("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,24}$");
    String PW_PATTERN = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~․!@#$%^&*()_\\-+=\\[\\]{}|\\\\;:‘“<>.,?/]).{8,19}$");
    String EMAIL_PATTERN = ("^[a-zA-Z0-9]*@([-.]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,4}$");
}
