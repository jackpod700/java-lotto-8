package lotto.enums;

public enum ExceptionMessages {
    INVALID_LOTTO_NUMBER_COUNT("[ERROR] 로또 번호는 6개여야 합니다."),
    INVALID_LOTTO_NUMBER_RANGE("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    DUPLICATE_LOTTO_NUMBERS("[ERROR] 로또 번호는 중복될 수 없습니다."),
    INVALID_PURCHASE_AMOUNT("[ERROR] 구입 금액이 1,000원 단위가 아니거나 너무 큽니다");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
