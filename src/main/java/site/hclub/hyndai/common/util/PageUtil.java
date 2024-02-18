package site.hclub.hyndai.common.util;

//@Component
//@Slf4j
//
//public class PageUtil {
//
//    public PageRequestDTO parsePaginationComponents(String gameType, Long players, Long page, String sortBy) {
//
//        // Default 파라미터 세팅
//        String defaultGameType = "ALL";
//        Long defaultPlayers = 0L;
//        String defaultFrom = "20230201";
//        String defaultTo = "20240801";
//        String defaultSortBy = "dates";
//        Long defaultPage = 1L;
//
//        Long p = page == null ? defaultPage : page;
//        String sb = sortBy == null ? defaultSortBy : sortBy;
//
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(p)
//                .inOrder(io)
//                .sortBy(sb)
//                .from().build();
//
//
//        return pageRequestDTO;
//    }
//
//}