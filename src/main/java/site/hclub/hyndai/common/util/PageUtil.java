package site.hclub.hyndai.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.hclub.hyndai.dto.request.PageRequestDTO;

@Component
@Slf4j

public class PageUtil {

    public PageRequestDTO parsePaginationComponents(String gameType, Long players, Long page, String sortBy, Integer inOrder) {

        // Default 파라미터 세팅
        String defaultGameType = null;
        Long defaultPlayers = 0L;
        String defaultFrom = "20230201";
        String defaultTo = "20230301";
        String defaultSortBy = "dates";
        int defaultInOrder = 1;
        Long defaultPage = 1L;

        Long p = page == null ? defaultPage : page;
        String sb = sortBy == null ? defaultSortBy : sortBy;
        int io = inOrder == null ? defaultInOrder : inOrder;

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(p);
        pageRequestDTO.setSortBy(sb);
        pageRequestDTO.setInOrder(io);

        return pageRequestDTO;
    }

}