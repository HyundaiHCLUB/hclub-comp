package site.hclub.hyndai.common.util;

import org.springframework.stereotype.Service;
import site.hclub.hyndai.domain.Team;

@Service
public class EloService {

    /**
     *
     * Elo 등급 계산
     * NewRatingP1 = RatingP1 + K * (S - EP1)
     *
     * 여기서:
     * RatingP1 = Player1의 현재 등급
     * K = K-인자 (변화율을 결정, 일반적으로 32, 24, 16 중 하나)
     * S = 실제 점수 (승리 1, 무승부 0.5, 패배 0)
     * EP1 = Q1 / (Q1 + Q2 + Q3)
     * Q(i) = 10 ^ (RatingP(i)/400) : i번째 플레이어의 상대적 강도
     *
     */


    /**
    *  Elo 알고리즘을 이용해 경기 결과에 따른 레이팅 변화 수치를 리턴
    * - 팀 A -> 팀원 각각 레이팅 += (리턴값)
    * - 팀 B -> 팀원 각각 -= (리턴값)
    * @param teamARating : A 팀 레이팅
    * @param teamBRating : B 팀 레이팅
    * @param result : 경기 결과 ("WIN", "DRAW", "LOSE" 중 하나)
    */
    public Long getRatingChange(Long teamARating, Long teamBRating, String result){
        double actualScore = 0;

        if (result.equals("WIN")) {// 승리
            actualScore = 1.0;
        } else if (result.equals("DRAW")) {// 무승부
            actualScore = 0.5;
        } else if (result.equals("LOSE")) {// 패배
            actualScore = 0;
        } else {
            throw new IllegalStateException("잘못된 결과 입력입니다");
        }

        // 예상 결과 계산
        double exponent = (double) (teamARating - teamBRating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-인자
        int K = determineK((int)((teamARating + teamBRating) / 2));

        // 새 등급 계산
        Long ratingChange = (Long) Math.round(teamARating + K * (actualScore - expectedOutcome));

        return ratingChange;
    }

    /**
     * 현재 등급에 기반하여 등급 상수 K-인자 결정
     *
     * @param rating
     *            플레이어 등급
     * @return K-인자
     */
    public static int determineK(int rating) {
        int K;
        if (rating < 2000) {
            K = 32;
        } else if (rating >= 2000 && rating < 2400) {
            K = 24;
        } else {
            K = 16;
        }
        return K;
    }
}
