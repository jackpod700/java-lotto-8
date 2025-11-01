package lotto.view;

import static lotto.view.InputView.readBonusNumber;
import static lotto.view.InputView.readPurchaseAmount;
import static lotto.view.InputView.readWinningNumbers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

public class InputViewTest extends NsTest {

    @Test
    void 로또_구입금액_입력() {
        //given
        String input = "5000";

        //when
        run(input);

        //then
        assertThat(readPurchaseAmount()).isEqualTo(input);
        assertThat(output()).contains("구입금액을 입력해 주세요.");
    }

    @Test
    void 당첨_번호_입력() {
        //given
        String input = "1,2,3,4,5,6";

        //when
        run(input);

        //then
        assertThat(readWinningNumbers()).isEqualTo(input);
        assertThat(output()).contains("당첨 번호를 입력해 주세요.");
    }

    @Test
    void 보너스_번호_입력() {
        //given
        String input = "7";

        //when
        run(input);

        //then
        assertThat(readBonusNumber()).isEqualTo(input);
        assertThat(output()).contains("보너스 번호를 입력해 주세요.");
    }

    @Override
    protected void runMain() {
    }
}
