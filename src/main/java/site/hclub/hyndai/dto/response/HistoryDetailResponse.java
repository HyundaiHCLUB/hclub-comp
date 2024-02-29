package site.hclub.hyndai.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.domain.Team;
@Data
public class HistoryDetailResponse {
    private long                matchHistoryNo;
    private String              matchLoc;
    private GetTeamFromScoreNoResponse                winTeam;
    private GetTeamFromScoreNoResponse                loseTeam;
    private String              imageUrl;
    private String              matchDate;
}
