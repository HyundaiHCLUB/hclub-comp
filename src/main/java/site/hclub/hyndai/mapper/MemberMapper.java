package site.hclub.hyndai.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    public Long getMemberRating(Long memberNo);
}
