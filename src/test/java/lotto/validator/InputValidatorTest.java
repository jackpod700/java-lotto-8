package lotto.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.Arrays;
import java.util.List;
import lotto.enums.ExceptionMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
                .doesNotThrowAnyException()
                .isEqualTo(5000);
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
    @ParameterizedTest
    @CsvSource({"'1,2,3,4,5,6','1,2,3,4,5,6'", "'7, 14, 21, 28, 35, 42', '7,14,21,28,35,42'"})
    void 당첨번호_검증_정상입력(String input, String expected){
        List<Integer> expectedList = Arrays.stream(expected.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();

        //when & then
        assertThat(inputValidator.validateWinningNumbers(input))
                .doesNotThrowAnyException()
                .isEqualTo(expectedList);
    }

    /**
     * 당첨번호 검증(예외)
     */
    @Test
    void 당첨번호_검증_빈문자열(){
        //given
        String input = "";

        //when & then
        assertThat(inputValidator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,5", "1,1,1,1,1,1"})
    void 당첨번호_검증_중복_숫자(String input){
        assertThat(inputValidator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.DUPLICATE_LOTTO_NUMBERS.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,2,3,4,5,6", "1,2,3,4,5,46", "-1,2,3,4,5,6"})
    void 당첨번호_검증_범위_벗어난_숫자(String input){
        assertThat(inputValidator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
    void 당첨번호_검증_6개_아닌_경우(String input){
        assertThat(inputValidator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT.getMessage());
    }

    @Test
    void 당첨번호_검증_비정상_문자_포함(){
        //given
        String input = "1,2,3,a,5,6";

        //when & then
        assertThat(inputValidator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

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
