package site.hclub.hyndai.common.util;
/**
 * @author 김동욱
 * @description: String <-> LocalDateTime
 * ===========================
AUTHOR      NOTE
 * ---------------------------
 *    김동욱        최초생성
 * ===========================
 */
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {


    public String parseLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }

    public LocalDateTime parseStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        return dateTime;
    }


    // LocalDateTime -> String 파싱
    public String parseLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm");
        String formattedDate = dateTime.format(formatter);

        return formattedDate;
    }
}
