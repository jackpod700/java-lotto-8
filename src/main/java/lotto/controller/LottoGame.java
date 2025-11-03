package lotto.controller;

import static camp.nextstep.edu.missionutils.Randoms.pickUniqueNumbersInRange;
import static lotto.enums.LottoConstant.LOTTO_PRICE;
import static lotto.util.InputParser.parseBonusNumber;
import static lotto.util.InputParser.parsePurchaseAmount;
import static lotto.util.InputParser.parseWinningNumbers;
import static lotto.util.LottoNumberValidator.validateBonusNumber;
import static lotto.util.PurchaseAmountValidator.validatePurchaseAmount;
import static lotto.util.LottoNumberValidator.validateWinningNumbers;
import static lotto.view.InputView.readBonusNumber;
import static lotto.view.InputView.readPurchaseAmount;
import static lotto.view.InputView.readWinningNumbers;
import static lotto.view.OutputView.printLottos;
import static lotto.view.OutputView.printResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.enums.LottoPrize;
import lotto.model.Lotto;

public class LottoGame {

    private int ticketCount;
    private List<Integer> winningNumbers;
    private int bonusNumber;
    private List<Lotto> lottoTickets;
    private Map<Integer, Long> prizeCounts; //(rank, count)
    private Double profitRate;

    public LottoGame() {
        winningNumbers = new ArrayList<>();
        lottoTickets = new ArrayList<>();
        prizeCounts = new HashMap<>();
    }

    public void start() {
        getPurchaseAmount();
        generateLottoTickets();
        printLottos(ticketCount,lottoTickets);
        getWinningNumbers();
        getBonusNumber();
        calculatePrize();
        calculateProfit();
        printResult(prizeCounts, profitRate);
    }

    private void getPurchaseAmount(){
        String purchaseAmountInput;
        while(true){
            try{
                purchaseAmountInput = readPurchaseAmount();
                validatePurchaseAmount(purchaseAmountInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            }
            break;

        }
        ticketCount = parsePurchaseAmount(purchaseAmountInput);
    }

    private void getWinningNumbers(){
        String winningNumbersInput;
        while(true){
            try{
                winningNumbersInput = readWinningNumbers();
                validateWinningNumbers(winningNumbersInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        winningNumbers = parseWinningNumbers(winningNumbersInput);
    }

    private void getBonusNumber(){
        String bonusNumberInput;
        while(true){
            try{
                bonusNumberInput = readBonusNumber();
                validateBonusNumber(bonusNumberInput, winningNumbers);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        bonusNumber = parseBonusNumber(bonusNumberInput);
    }

    private void generateLottoTickets(){
        for(int i=0;i<ticketCount;i++){
            lottoTickets.add(new Lotto(pickUniqueNumbersInRange(1,45,6)));
        }
    }

    private void calculatePrize(){
        List<LottoPrize> prizes = new ArrayList<>();
        for(Lotto lotto : lottoTickets){
            prizes.add(lotto.calculateRank(winningNumbers, bonusNumber));
        }
        prizeCounts = prizes.stream()
                .collect(Collectors.groupingBy(
                        LottoPrize::getRank,  // (1) LottoPrize::getRank() 호출
                        Collectors.counting()   // (2) 개수 세기
                ));
    }

    private void calculateProfit(){
        long totalPrize = prizeCounts.entrySet().stream()
                .mapToLong(entry -> {
                    int rank = entry.getKey(); // 키(등수) (예: 5)
                    long count = entry.getValue(); // 값(개수) (예: 1L)
                    long prizeMoney = LottoPrize.getPrizeByRank(rank);
                    return prizeMoney * count;
                })
                .sum();

        long inputAmount = (long) ticketCount * LOTTO_PRICE.getValue();
        profitRate = (100.0 * totalPrize) / inputAmount;
    }

}
