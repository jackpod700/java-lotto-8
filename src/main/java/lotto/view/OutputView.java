package lotto.view;

import static lotto.enums.LottoPrize.getPrizeMessageByRank;

import java.util.List;
import java.util.Map;
import lotto.model.Lotto;

public class OutputView {
    public static void printLottos(int ticketCount, List<Lotto> lottos){
        System.out.println("\n"+ticketCount + "개를 구매했습니다.");
        for(Lotto lotto : lottos){
            System.out.println(lotto.getNumbersAsString());
        }
    }

    public static void printResult(Map<Integer, Long> prizeCounts, Double profitRate){
        System.out.println("\n당첨 통계");
        System.out.println("---");

        for(int rank=5;rank>0;rank--){
            System.out.printf(getPrizeMessageByRank(rank)
                    +" - %d개%n",prizeCounts.getOrDefault(rank, 0L));
        }

        System.out.printf("총 수익률은 %.1f%%입니다.", profitRate);
    }
}
