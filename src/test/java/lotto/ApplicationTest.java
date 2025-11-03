package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static lotto.enums.ExceptionMessages.DUPLICATE_LOTTO_NUMBERS;
import static lotto.enums.ExceptionMessages.INVALID_BONUS_NUMBER;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE;
import static lotto.enums.ExceptionMessages.INVALID_PURCHASE_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    @DisplayName("정상 테스트")
    void 정상_기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    @DisplayName("2등과 3등이 포함된 당첨 및 수익률 테스트")
    void 보너스_볼_2등_3등_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("3000", "1,2,3,4,5,6", "7"); // 3장 구매
                    assertThat(output()).contains(
                            "3개를 구매했습니다.",
                            "[1, 2, 3, 4, 5, 7]", // 2등 (5개 + 보너스)
                            "[1, 2, 3, 4, 5, 8]", // 3등 (5개)
                            "[10, 11, 12, 13, 14, 15]", // 꽝
                            "3개 일치 (5,000원) - 0개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 1개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 1개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 1050000.0%입니다." // (30,000,000 + 1,500,000) / 3,000 * 100
                    );
                },
                List.of(1, 2, 3, 4, 5, 7), // 2등
                List.of(1, 2, 3, 4, 5, 8), // 3등
                List.of(10, 11, 12, 13, 14, 15) // 꽝
        );
    }

    @Test
    @DisplayName("당첨이 하나도 없는 경우 (0% 수익률) 테스트")
    void 당첨_없음_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("2000", "1,2,3,4,5,6", "7"); // 2장 구매
                    assertThat(output()).contains(
                            "2개를 구매했습니다.",
                            "[10, 11, 12, 13, 14, 15]", // 꽝 (0개 일치)
                            "[8, 9, 10, 11, 12, 13]", // 꽝 (0개 일치)
                            "3개 일치 (5,000원) - 0개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 0.0%입니다." // 0 / 2,000 * 100
                    );
                },
                List.of(10, 11, 12, 13, 14, 15),
                List.of(8, 9, 10, 11, 12, 13)
        );
    }

    /**
     * 예외 처리 및 재입력 테스트
     */

    @Test
    @DisplayName("구입 금액 예외 테스트 - 1000원 단위가 아닐 경우")
    void 구입금액_1000원_단위_예외() {
        assertSimpleTest(() -> {
            // 1234 (오류) -> 1000 (정상) -> 나머지 정상 입력
            run("1234", "1000", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_PURCHASE_AMOUNT.getMessage(),
                    "1개를 구매했습니다.", // 1000원으로 재시도 성공
                    "당첨 통계" // 끝까지 실행됨
            );
        });
    }

    @Test
    @DisplayName("구입 금액 예외 테스트 - 숫자가 아닐 경우")
    void 구입금액_숫자_아님_예외() {
        assertSimpleTest(() -> {
            run("lotto", "1000", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_PURCHASE_AMOUNT.getMessage(),
                    "1개를 구매했습니다." // 1000원으로 재시도 성공
            );
        });
    }

    @Test
    @DisplayName("구입 금액 예외 테스트 - 0 이하일 경우")
    void 구입금액_0_이하_예외() {
        assertSimpleTest(() -> {
            run("0", "1000", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_PURCHASE_AMOUNT.getMessage(),
                    "1개를 구매했습니다." // 1000원으로 재시도 성공
            );
        });
    }

    @Test
    @DisplayName("당첨 번호 예외 테스트 - 6개가 아닐 경우")
    void 당첨번호_6개_아님_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_LOTTO_NUMBER_COUNT.getMessage(),
                    "보너스 번호를 입력해 주세요." // 당첨 번호 재입력 성공 후, 보너스 번호로 넘어감
            );
        });
    }

    @Test
    @DisplayName("당첨 번호 예외 테스트 - 중복된 숫자가 있을 경우")
    void 당첨번호_중복_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,1,2,3,4,5", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    DUPLICATE_LOTTO_NUMBERS.getMessage(),
                    "보너스 번호를 입력해 주세요."
            );
        });
    }

    @Test
    @DisplayName("당첨 번호 예외 테스트 - 1~45 범위를 벗어난 경우")
    void 당첨번호_범위_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5,46", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_LOTTO_NUMBER_RANGE.getMessage(),
                    "보너스 번호를 입력해 주세요."
            );
        });
    }

    @Test
    @DisplayName("당첨 번호 예외 테스트 - 숫자가 아닌 문자가 포함된 경우")
    void 당첨번호_숫자_아님_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,a,b,c", "1,2,3,4,5,6", "7");
            assertThat(output()).contains(
                    INVALID_LOTTO_NUMBER_RANGE.getMessage(),
                    "보너스 번호를 입력해 주세요."
            );
        });
    }

    @Test
    @DisplayName("보너스 번호 예외 테스트 - 당첨 번호와 중복된 경우")
    void 보너스번호_중복_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5,6", "6", "7");
            assertThat(output()).contains(
                    INVALID_BONUS_NUMBER.getMessage(),
                    "당첨 통계" // 보너스 번호 재입력 성공 후, 통계로 넘어감
            );
        });
    }

    @Test
    @DisplayName("보너스 번호 예외 테스트 - 1~45 범위를 벗어난 경우")
    void 보너스번호_범위_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5,6", "47", "7");
            assertThat(output()).contains(
                    INVALID_LOTTO_NUMBER_RANGE.getMessage(),
                    "당첨 통계"
            );
        });
    }

    @Test
    @DisplayName("보너스 번호 예외 테스트 - 숫자가 아닐 경우")
    void 보너스번호_숫자_아님_예외() {
        assertSimpleTest(() -> {
            run("1000", "1,2,3,4,5,6", "bonus", "7");
            assertThat(output()).contains(
                    INVALID_LOTTO_NUMBER_RANGE.getMessage(),
                    "당첨 통계"
            );
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}