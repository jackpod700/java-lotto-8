package lotto.util;

import static lotto.enums.LottoConstant.LOTTO_PRICE;
import static lotto.util.LottoNumberValidator.NUMBER_DELIMITER;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    public static int parsePurchaseAmount(String input){
        return Integer.parseInt(input)/ LOTTO_PRICE.getValue();
    }

    public static List<Integer> parseWinningNumbers(String input){
        List<Integer> winningNumbers;
        winningNumbers = Arrays.stream(input.split(NUMBER_DELIMITER))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        return winningNumbers;
    }

    public static int parseBonusNumber(String input){
        return Integer.parseInt(input);
    }
}
