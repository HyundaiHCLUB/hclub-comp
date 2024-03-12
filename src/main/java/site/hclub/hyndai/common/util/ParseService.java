package site.hclub.hyndai.common.util;

import org.springframework.stereotype.Service;
/**
 * @author 김동욱
 * @description: 프론트 데이터 파싱 관련 Service
 * ===========================
      AUTHOR      NOTE
 * ---------------------------
 *    김동욱        최초생성
 * ===========================
 */
@Service
public class ParseService {

    public String parseSportsToImage(String sports) {
        switch (sports) {
            case "SOCCER":
                return "https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/soccer.png";
            case "BASKETBALL":
                return "https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/basketball.png";
            case "BOWLING":
                return "https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/bowling.png";
            case "DART":
                return "https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/dart.png";
        }
        return null;
    }
    
}
