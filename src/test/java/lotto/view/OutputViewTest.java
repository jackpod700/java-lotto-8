package lotto.view;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import lotto.model.Lotto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputViewTest extends NsTest {

    /**
     * 로또 구매 목록 출력 테스트
     */
    @Test
    void 로또_목록_출력_테스트(){
        //given
        Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));
        List<Lotto> lottos = List.of(lotto1, lotto2);
        int ticketCount = 2;

        //when
        OutputView.printLottos(ticketCount, lottos);

        //then
        String expectedOutput = """
                2개를 구매했습니다.
                [1, 2, 3, 4, 5, 6]
                [7, 8, 9, 10, 11, 12]""";

        assertThat(output()).isEqualTo(expectedOutput);
    }

    /**
     * 당첨 통계 및 수익률 출력 테스트
     */
    @Test
    void 당첨_통계_출력_테스트() {
        //given
        Map<Integer, Long> prizeCounts = new HashMap<>();
        prizeCounts.put(5, 1L); // 5등 1개
        prizeCounts.put(3, 2L); // 3등 2개

        Double profitRate = 120.5;

        //when
        OutputView.printResult(prizeCounts, profitRate);

        //then
        String expectedOutput = """
                
                당첨 통계
                ---
                3개 일치 (5,000원) - 1개
                4개 일치 (50,000원) - 0개
                5개 일치 (1,500,000원) - 2개
                5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
                6개 일치 (2,000,000,000원) - 0개
                총 수익률은 120.5%입니다.""";

        assertThat(output()).isEqualTo(expectedOutput);
    }


    @Override
    protected void runMain() {
    }
}