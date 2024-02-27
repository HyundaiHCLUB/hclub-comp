package site.hclub.hyndai.common.util;

import org.springframework.stereotype.Service;

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

    public String parseCapacityToString(Long teamCapacity) {
        return teamCapacity + " ON " + teamCapacity;
    }
}
