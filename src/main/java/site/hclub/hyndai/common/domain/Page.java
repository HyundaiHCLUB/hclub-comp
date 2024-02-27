package site.hclub.hyndai.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page {
    private String sortBy;
    private String orderBy;

    private Long page; // 현재 페이지 번호
    private Long recordSize; // 페이지당 출력할 데이터 개수
    private Long pageSize; // 화면 하단에 출력할 페이지 사이즈

    public Page(Long page, Long recordSize, Long pageSize) {
        this.page = page;
        this.recordSize = recordSize;
        this.pageSize = pageSize;
    }

    public Long getOffset() {
        return (page - 1) * recordSize;
    }
}
