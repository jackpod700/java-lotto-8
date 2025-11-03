package lotto.view;

import static lotto.enums.LottoPrize.getPrizeMessageByRank;

import java.util.List;
import java.util.Map;
import lotto.model.Lotto;

public class OutputView {
    private static final String PURCHASE_COUNT_MESSAGE = "\n%d개를 구매했습니다.\n";
    private static final String PRE_RESULT_MESSAGE = "\n당첨 통계\n---\n";
    private static final String RANK_RESULT_MESSAGE = " - %d개\n";
    private static final String PROFIT_RESULT_MESSAGE = "총 수익률은 %.1f%%입니다.";

    public static void printLottos(int ticketCount, List<Lotto> lottos) {
        System.out.printf((PURCHASE_COUNT_MESSAGE), ticketCount);
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbersAsString());
        }
    }

    public static void printResult(Map<Integer, Long> prizeCounts, Double profitRate) {
        System.out.printf(PRE_RESULT_MESSAGE);

        for (int rank = 5; rank > 0; rank--) {
            System.out.printf(getPrizeMessageByRank(rank) + RANK_RESULT_MESSAGE
                    , prizeCounts.getOrDefault(rank, 0L));
        }

        System.out.printf(PROFIT_RESULT_MESSAGE, profitRate);
    }
}
