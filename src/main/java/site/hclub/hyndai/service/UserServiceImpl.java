package site.hclub.hyndai.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.utils.JwtTokenResolver;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    private final JwtTokenResolver jwtTokenResolver;


    @Override
    public String getUserDetails(HttpServletRequest request) {
        String token = request.getHeader("accessTokenInfo");
        String memberId = jwtTokenResolver.resolveToken(token);

        return memberId;
    }
}
