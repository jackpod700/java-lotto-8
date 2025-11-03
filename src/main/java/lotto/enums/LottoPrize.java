package lotto.enums;

import java.util.Arrays;

public enum LottoPrize {

    RANK_1(2_000_000_000, 6, false),
    RANK_2(30_000_000, 5, true),
    RANK_3(1_500_000, 5, false),
    RANK_4(50_000, 4, false),
    RANK_5(5_000, 3, false),
    NONE(0, 0, false);

    private final int prize;
    private final int matchCount;
    private final boolean needsBonus; // 보너스 볼이 필요한지 여부

    LottoPrize(int prize, int matchCount, boolean needsBonus) {
        this.prize = prize;
        this.matchCount = matchCount;
        this.needsBonus = needsBonus;
    }

    /**
     * 일치 개수와 보너스 여부를 받아 등수를 반환합니다.
     * 이 메서드는 enum 외부에서 호출되므로 static으로 선언해야 합니다.
     * * @param matchCount 일치하는 번호의 개수
     * @param hasBonus   보너스 번호 일치 여부
     * @return
     */
    public static LottoPrize findRankByMatches(int matchCount, boolean hasBonus) {
        // 2등 케이스 먼저 처리
        if (matchCount == 5 && hasBonus) {
            return RANK_2;
        }

        return Arrays.stream(values())
                .filter(prize -> prize.matchCount == matchCount)
                .filter(prize -> !prize.needsBonus)
                .findFirst()
                .orElse(NONE);
    }

    public int getPrize() {
        return prize;
    }

    public int getMatchCount() {
        return matchCount;
    }
}