package lotto.model;

import static lotto.enums.LottoPrize.findRankByMatches;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lotto.enums.LottoPrize;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public LottoPrize calculateRank(List<Integer> winningNumbers, int bonusNumber){
        boolean matchBonus=false;
        Set<Integer> winningNumberSet = new HashSet<>(winningNumbers);

        long matchCount = numbers.stream()
                .filter(winningNumberSet::contains)
                .count();
        if(numbers.contains(bonusNumber)){
            matchBonus = true;
        }
        return findRankByMatches((int) matchCount,matchBonus);
    }

    public String getNumbersAsString(){
        return numbers.toString();
    }

}
