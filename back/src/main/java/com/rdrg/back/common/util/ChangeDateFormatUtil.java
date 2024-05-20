package com.rdrg.back.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeDateFormatUtil {
    
    public static String changeYYMMDD(String original) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = simpleDateFormat.parse(original);
        simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
        String writerDatetime = simpleDateFormat.format(datetime);
        return writerDatetime;
    }
    
    public static String changeYYYYMMDD(String original) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = simpleDateFormat.parse(original);
        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String writerDatetime = simpleDateFormat.format(datetime);
        return writerDatetime;
    }

    public static String changeYYMM(String original) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = simpleDateFormat.parse(original);
        simpleDateFormat = new SimpleDateFormat("yy-MM");
        String writerDatetime = simpleDateFormat.format(datetime);
        return writerDatetime;
    }
}
