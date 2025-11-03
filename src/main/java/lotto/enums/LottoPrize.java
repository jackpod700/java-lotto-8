package lotto.enums;

import java.util.Arrays;

public enum LottoPrize {

    RANK_1(1, 2_000_000_000, 6, false, "6개 일치 (2,000,000,000원)"),
    RANK_2(2, 30_000_000, 5, true, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
    RANK_3(3, 1_500_000, 5, false, "5개 일치 (1,500,000원)"),
    RANK_4(4, 50_000, 4, false, "4개 일치 (50,000원)"),
    RANK_5(5, 5_000, 3, false, "3개 일치 (5,000원)"),
    NONE(0, 0, 0, false, "");

    private final int rank;
    private final int prize;
    private final int matchCount;
    private final boolean needsBonus; // 보너스 볼이 필요한지 여부
    private final String prizeMessage;

    LottoPrize(int rank, int prize, int matchCount, boolean needsBonus, String prizeMessage) {
        this.rank = rank;
        this.prize = prize;
        this.matchCount = matchCount;
        this.needsBonus = needsBonus;
        this.prizeMessage = prizeMessage;
    }

    /**
     * 일치 개수와 보너스 여부를 받아 등수를 반환합니다. 이 메서드는 enum 외부에서 호출되므로 static으로 선언해야 합니다.
     *
     * @param matchCount 일치하는 번호의 개수
     * @param hasBonus 보너스 번호 일치 여부
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

    public static long getPrizeByRank(int rank) {
        return Arrays.stream(values())
                .filter(prize -> prize.rank == rank)
                .findFirst()
                .map(LottoPrize::getPrize)
                .orElse(0);
    }

    public static String getPrizeMessageByRank(int rank) {
        return Arrays.stream(values())
                .filter(prize -> prize.rank == rank)
                .findFirst()
                .map(LottoPrize::getPrizeMessage)
                .orElse("");
    }

    public int getPrize() {
        return prize;
    }

    public int getRank() {
        return rank;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public String getPrizeMessage() {
        return prizeMessage;
    }
}