package lotto.util;

import static lotto.enums.ExceptionMessages.INVALID_PURCHASE_AMOUNT;
import static lotto.util.PurchaseAmountValidator.validatePurchaseAmount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PurchaseAmountValidatorTest extends NsTest {
    /**
     * 구입금액 검증(정상)
     */
    @Test
    void 구입금액_검증_정상입력(){
        //given
        String input = "5000";

        //when & then
        assertThatCode(()->validatePurchaseAmount(input))
                .doesNotThrowAnyException();
    }

    /**
     * 구입금액 검증(예외)
     */
    @Test
    void 구입금액_검증_빈문자열(){
        //given
        String input = "\n";

        //when & then
        assertThatThrownBy(()->validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @Test
    void 구입금액_검증_1000원_단위_아닌_경우(){
        //given
        String input = "5500";

        //when & then
        assertThatCode(()->validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1000", "0"})
    void 구입금액_검증_0이하(String input){
        //when & then
        assertThatCode(()->validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "12ab", "!!@@##","-1000"})
    void 구입금액_검증_문자_포함된_경우(String input){
        //when & then
        assertThatCode(()->validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @Test
    void 구입금액_검증_너무_큰_숫자(){
        //given
        String input = "100000000000000000000";

        //when & then
        assertThatCode(()->validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @Override
    protected void runMain() {
    }
}
