package site.hclub.hyndai.dto.response;

import lombok.*;
import site.hclub.hyndai.common.domain.Pagination;
import site.hclub.hyndai.dto.TeamDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetTeamListResponse {
    private List<TeamDTO> teamList;
    private Pagination pagination;
}
