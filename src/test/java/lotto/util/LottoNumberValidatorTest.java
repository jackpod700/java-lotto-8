package lotto.util;

import static lotto.enums.ExceptionMessages.DUPLICATE_LOTTO_NUMBERS;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE;
import static lotto.util.LottoNumberValidator.validateBonusNumber;
import static lotto.util.LottoNumberValidator.validateWinningNumbers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoNumberValidatorTest extends NsTest {
    /**
     * 당첨번호 검증(정상)
     */
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,6", "7, 14, 21, 28, 35, 42"})
    void 당첨번호_검증_정상입력(String input){

        //when & then
        assertThatCode(()->validateWinningNumbers(input))
                .doesNotThrowAnyException();
    }

    /**
     * 당첨번호 검증(예외)
     */
    @Test
    void 당첨번호_검증_빈문자열(){
        //given
        String input = "\n";

        //when & then
        assertThatCode(()->validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,5", "1,1,1,1,1,1"})
    void 당첨번호_검증_중복_숫자(String input){
        assertThatCode(()->validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DUPLICATE_LOTTO_NUMBERS.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0,2,3,4,5,6", "1,2,3,4,5,46", "-1,2,3,4,5,6"})
    void 당첨번호_검증_범위_벗어난_숫자(String input){
        assertThatCode(()->validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
    void 당첨번호_검증_6개_아닌_경우(String input){
        assertThatCode(()->validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_COUNT.getMessage());
    }

    @Test
    void 당첨번호_검증_비정상_문자_포함(){
        //given
        String input = "1,2,3,a,5,6";

        //when & then
        assertThatCode(()->validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    /**
     * 보너스번호 검증(정상)
     */
    @ParameterizedTest
    @ValueSource(strings = {"7"," 14 "})
    void 보너스번호_검증_정상입력(String input) {
        //when & then
        assertThatCode(()->validateBonusNumber(input, List.of(1, 2, 3, 4, 5, 6)))
                .doesNotThrowAnyException();
    }

    /**
     * 보너스번호 검증(예외)
     */
    @Test
    void 보너스번호_검증_빈문자열(){
        //given
        String input = "\n";

        //when & then
        assertThatCode(()->validateBonusNumber(input, List.of(1,2,3,4,5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @Test
    void 보너스번호_검증_당첨번호와_중복(){
        //given
        String input = "3";

        //when & then
        assertThatCode(()->validateBonusNumber(input, List.of(1,2,3,4,5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DUPLICATE_LOTTO_NUMBERS.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "46", "-5"})
    void 보너스번호_검증_범위_벗어난_숫자(String input){
        assertThatCode(()->validateBonusNumber(input, List.of(1,2,3,4,5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @Test
    void 보너스번호_검증_비정상_문자_포함(){
        //given
        String input = "a";

        //when & then
        assertThatCode(()->validateBonusNumber(input, List.of(1,2,3,4,5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_LOTTO_NUMBER_RANGE.getMessage());
    }

    @Override
    protected void runMain() {
    }
}
