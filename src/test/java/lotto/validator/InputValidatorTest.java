package lotto.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import lotto.enums.ExceptionMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest extends NsTest {

    private static final InputValidator inputValidator = new InputValidator();

    /**
     * 구입금액 검증(정상)
     */
    @Test
    void 구입금액_검증_정상입력(){
        //given
        String input = "5000";

        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .doesNotThrowAnyException();
    }

    /**
     * 구입금액 검증(예외)
     */
    @Test
    void 구입금액_검증_빈문자열(){
        //given
        String input = "";

        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @Test
    void 구입금액_검증_1000원_단위_아닌_경우(){
        //given
        String input = "5500";

        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1000", "0"})
    void 구입금액_검증_0이하(String input){
        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "12ab", "!!@@##","-1000"})
    void 구입금액_검증_문자_포함된_경우(String input){
        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @Test
    void 구입금액_검증_너무_큰_숫자(){
        //given
        String input = "100000000000000000000";

        //when & then
        assertThat(inputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    /**
     * 당첨번호 검증(정상)
     */

    /**
     * 당첨번호 검증(예외)
     */

    /**
     * 보너스번호 검증(정상)
     */

    /**
     * 보너스번호 검증(예외)
     */
    @Override
    protected void runMain() {
    }
}
