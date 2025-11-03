package lotto.util;

import static lotto.enums.ExceptionMessages.INVALID_PURCHASE_AMOUNT;
import static lotto.enums.LottoConstant.LOTTO_PRICE;

public class PurchaseAmountValidator {
    public static void validatePurchaseAmount(String input) throws IllegalArgumentException {
        int amount = validateIntegerParsable(input);
        validateNegativeInput(amount);
        validateUnit(amount);
    }

    private static void validateNegativeInput(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }

    /**
     * 로또의 금액(1000원) 단위의 입력인지 검증하는 메소드
     *
     * @param amount
     */
    private static void validateUnit(int amount) {
        if (amount % LOTTO_PRICE.getValue() != 0) {
            throw new IllegalArgumentException(INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }

    private static int validateIntegerParsable(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PURCHASE_AMOUNT.getMessage());
        }
    }
}
