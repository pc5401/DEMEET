package com.ssafy.common.util;

import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

/**
 * 강제 타입 형변환이 안되는 친구들을 변환해주는 유틸들 모음
 */
@Repository
public class TypeConverter {

    /**
     * LongToLocalDateTime
     * EpochTime(Milliseconds)를 LocalDateTime타입으로 변경해주는 함수
     * @param  milliseconds
     * @return date
     */
    public static LocalDateTime LongToLocalDateTime(Long milliseconds) {
        log.info("LongToLocalDateTime start");
        log.debug("milliseconds: " + milliseconds);
//        LocalDateTime date =LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
        LocalDateTime date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        log.debug("date: " + date.toString());
        log.info("LongToLocalDateTime 종료");
        return date;
    }

    public static Long LocalDateTimeToLong(LocalDateTime localDateTime){
        log.info("LocalDateTimeToLong start");
        log.debug("localDateTime ={}", localDateTime.toString());
        ZonedDateTime date = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        Long milliseconds = date.toInstant().toEpochMilli();
        return milliseconds;
    }

    public static String LongSecondsToStringTime(Long seconds){
        String time = "";
        int hour = (int) (seconds/3600);
        int minute = (int) (seconds%3600/60);
        int second = (int) (seconds%3600%60);
        time = hour+"시간 "+minute+"분 "+second+"초";
        return time;
    }
}
