package lotto.model;

import java.util.stream.Stream;
import lotto.enums.LottoPrize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // TODO: 추가 기능 구현에 따른 테스트 코드 작성

    @ParameterizedTest
    @MethodSource("lottoRankScenarios")
    void 로또_등수_계산_테스트(List<Integer> userNumbers, List<Integer> winningNumbers, int bonusNumber,
                      LottoPrize expectedRank) {
        // given
        Lotto userLotto = new Lotto(userNumbers);

        // when
        LottoPrize actualRank = userLotto.calculateRank(winningNumbers, bonusNumber);

        // then
        assertThat(actualRank).isEqualTo(expectedRank);
    }

    private static Stream<Arguments> lottoRankScenarios() {
        final List<Integer> winning = List.of(1, 2, 3, 4, 5, 6);
        final int bonus = 7;

        return Stream.of(
                // Arguments.of( 유저 로또, 당첨 번호, 보너스, 기대 등수 )
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), winning, bonus, LottoPrize.RANK_1), // 1등: 6개 일치
                Arguments.of(List.of(1, 2, 3, 4, 5, 7), winning, bonus, LottoPrize.RANK_2), // 2등: 5개 + 보너스 일치
                Arguments.of(List.of(1, 2, 3, 4, 5, 8), winning, bonus, LottoPrize.RANK_3), // 3등: 5개 일치 (보너스 X)
                Arguments.of(List.of(1, 2, 3, 4, 8, 9), winning, bonus, LottoPrize.RANK_4), // 4등: 4개 일치
                Arguments.of(List.of(1, 2, 3, 4, 7, 8), winning, bonus, LottoPrize.RANK_4), // 4등: 4개 일치 (보너스 O -> 4등)
                Arguments.of(List.of(1, 2, 3, 8, 9, 10), winning, bonus, LottoPrize.RANK_5), // 5등: 3개 일치
                Arguments.of(List.of(1, 2, 3, 7, 8, 9), winning, bonus, LottoPrize.RANK_5), // 5등: 3개 일치 (보너스 O -> 5등)
                Arguments.of(List.of(1, 2, 8, 9, 10, 11), winning, bonus, LottoPrize.NONE), // 꽝: 2개 일치
                Arguments.of(List.of(1, 7, 8, 9, 10, 11), winning, bonus, LottoPrize.NONE), // 꽝: 1개 + 보너스
                Arguments.of(List.of(10, 11, 12, 13, 14, 15), winning, bonus, LottoPrize.NONE) // 꽝: 0개 일치
        );
    }
}
