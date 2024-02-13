package site.hclub.hyndai.service;

import site.hclub.hyndai.dto.MatchDTO;

public interface CompService {
    MatchDTO getMatchDetail(long matchHistoryNo);
}
