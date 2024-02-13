package site.hclub.hyndai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.dto.MatchDTO;
import site.hclub.hyndai.mapper.CompMapper;

@Service
public class CompServiceImpl implements CompService {

    @Autowired
    CompMapper compMapper;
    @Override
    public MatchDTO getMatchDetail(long matchHistoryNo) {
        return null;
    }
}
