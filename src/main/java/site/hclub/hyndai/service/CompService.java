package site.hclub.hyndai.service;

import site.hclub.hyndai.dto.MatchDetailResponse;

public interface CompService {
    MatchDetailResponse getMatchDetail(Long matchHistoryNo);
}
