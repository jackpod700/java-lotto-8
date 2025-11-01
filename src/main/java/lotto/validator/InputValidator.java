package lotto.validator;

import static lotto.enums.ExceptionMessages.DUPLICATE_LOTTO_NUMBERS;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_COUNT;
import static lotto.enums.ExceptionMessages.INVALID_LOTTO_NUMBER_RANGE;
import static lotto.enums.ExceptionMessages.INVALID_PURCHASE_AMOUNT;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_COUNT;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_MAX;
import static lotto.enums.LottoConstant.LOTTO_NUMBER_MIN;
import static lotto.enums.LottoConstant.LOTTO_PRICE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import lotto.enums.ExceptionMessages;

public class InputValidator {

    private static final String NUMBER_DELIMITER = ",";

    public static void validatePurchaseAmount(String input) throws IllegalArgumentException {
        int amount;

        try{
            amount = Integer.parseInt(input);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException(INVALID_PURCHASE_AMOUNT.getMessage());
        }

        if (amount <= 0 || amount % LOTTO_PRICE.getValue() != 0) {
            throw new IllegalArgumentException(INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }

    public static void validateWinningNumbers(String input) throws IllegalArgumentException {
        List<Integer> numbers;
        try{
            numbers = Arrays.stream(input.split(NUMBER_DELIMITER))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }


        HashSet<Integer> numberSet = new HashSet<>(numbers);
        if(numberSet.size() != numbers.size()){
            throw new IllegalArgumentException(DUPLICATE_LOTTO_NUMBERS.getMessage());
        }

        if(numbers.size() != LOTTO_NUMBER_COUNT.getValue()){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_COUNT.getMessage());
        }

        numbers.stream()
                .filter(n -> n < LOTTO_NUMBER_MIN.getValue() || n > LOTTO_NUMBER_MAX.getValue()).findAny()
                .ifPresent(n -> {
                    throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
                });

    }

    public static void validateBonusNumber(String input, List<Integer> integers) throws IllegalArgumentException {
        int bonusNumber;
        try{
            bonusNumber = Integer.parseInt(input.trim());
        }catch(NumberFormatException e){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }

        if(bonusNumber < LOTTO_NUMBER_MIN.getValue() || bonusNumber > LOTTO_NUMBER_MAX.getValue()){
            throw new IllegalArgumentException(INVALID_LOTTO_NUMBER_RANGE.getMessage());
        }

        if(integers.contains(bonusNumber)){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_BONUS_NUMBER.getMessage());
        }
    }

}
