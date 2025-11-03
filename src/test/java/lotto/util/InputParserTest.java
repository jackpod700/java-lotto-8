package lotto.util;

import static lotto.util.InputParser.parseBonusNumber;
import static lotto.util.InputParser.parsePurchaseAmount;
import static lotto.util.InputParser.parseWinningNumbers;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 이미 검증된 값에 대해 파싱하므로 예외 케이스에 대한 테스트 불필요
 */
public class InputParserTest extends NsTest {

    /**
     * 구입금액 파싱(정상)
     */
    @DisplayName("구입금액 파싱 - 정상")
    @ParameterizedTest
    @CsvSource(value = {"8000, 8", "1000, 1", "12000, 12"}, delimiter = ',')
    void 구입금액_파싱_정상입력(String input, int expectedCount) {
        //when
        int ticketCount = parsePurchaseAmount(input);

        // then
        assertThat(ticketCount).isEqualTo(expectedCount);
    }

    /**
     * 당첨번호 파싱(정상)
     */
    @DisplayName("당첨번호 파싱 - 정상")
    @ParameterizedTest
    @MethodSource("winningNumbersProvider")
    void 당첨번호_파싱_정상입력(String input, List<Integer> expected) {
        //when
        List<Integer> result = parseWinningNumbers(input);

        // then
        assertThat(result).isEqualTo(expected);
    }

    // 당첨번호 파싱 테스트를 위한 데이터 제공 메서드
    private static Stream<Arguments> winningNumbersProvider() {
        return Stream.of(
                Arguments.of("1,2,3,4,5,6", List.of(1, 2, 3, 4, 5, 6)),
                Arguments.of("7, 14, 21, 28, 35, 42", List.of(7, 14, 21, 28, 35, 42)) // 공백 포함
        );
    }

    /**
     * 보너스번호 파싱(정상)
     */
    @DisplayName("보너스번호 파싱 - 정상")
    @ParameterizedTest
    @CsvSource(value = {"7, 7", "1, 1", "45, 45"}, delimiter = ',')
    void 보너스번호_파싱_정상입력(String input, int expected) {
        //when
        int result = parseBonusNumber(input);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Override
    protected void runMain() {
    }
}