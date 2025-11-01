package lotto.enums;

public enum LottoConstant {
    LOTTO_PRICE(1000),
    LOTTO_NUMBER_COUNT(6),
    LOTTO_NUMBER_MIN(1),
    LOTTO_NUMBER_MAX(45);

    private final int value;

    LottoConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
