package lotto.util;

import static lotto.enums.ExceptionMessages.DUPLICATE_LOTTO_NUMBERS;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_COUNT;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_MAX;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_MIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoNumberValidator {

    public static final String NUMBER_DELIMITER = ",";

    public static void validateWinningNumbers(String input) throws IllegalArgumentException {
        List<Integer> numbers = Arrays.stream(input.split(NUMBER_DELIMITER))
                .map(LottoNumberValidator::validateIntegerParsable)
                .toList();

        validateDuplication(numbers);

        validateNumberCount(numbers);

        numbers.forEach(LottoNumberValidator::validateRange);
    }

    public static void validateBonusNumber(String input, List<Integer> winningNumbers) throws IllegalArgumentException {
        int bonusNumber = validateIntegerParsable(input);

        validateRange(bonusNumber);

        List<Integer> numbers = new ArrayList<>(winningNumbers);
        numbers.add(bonusNumber);
        validateDuplication(numbers);
    }

    private static int validateIntegerParsable(String input){
        try{
            return Integer.parseInt(input.trim());
        }catch(NumberFormatException e){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
    }

    /**
     * 로또 당첨번호의 개수가 6개가 맞는지 검증하는 메소드
     * @param numbers
     */
    private static void validateNumberCount(List<Integer> numbers){
        if(numbers.size() != LOTTO_NUMBER_COUNT.getValue()){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_COUNT.getMessage());
        }
    }

    private static void validateRange(int number){
        if(number<LOTTO_NUMBER_MIN.getValue()||number>LOTTO_NUMBER_MAX.getValue()){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }
    }

    private static void validateDuplication(List<Integer> numbers){
        Set<Integer> numberSet = new HashSet<>(numbers);
        if(numberSet.size() != numbers.size()){
            throw new IllegalArgumentException(DUPLICATE_LOTTO_NUMBERS.getMessage());
        }
    }


}
