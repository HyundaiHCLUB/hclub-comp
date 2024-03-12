package site.hclub.hyndai.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /**
     @author: 김동욱
     @description: 멤버의 레이팅을 불러옵니다.
     */
    public Long getMemberRating(Long memberNo);
}
