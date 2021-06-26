import java.util.*;

enum Coin {

    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50);

    private final int amount;

    Coin(int amount) {
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }
    
    public static Coin[] valuesAmountDesc() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(Coin::amount).reversed())
                .toArray(Coin[]::new);
    }
}

public class ChangeMaking {
  
    public static void main(String[] args) {
        int[] change = changeMoney(17);
        Arrays.stream(change)
          .forEach(System.out::println);
    }

    private static int[] changeMoney(int amount) {

        List<Integer> change = new ArrayList<>();

        for (Coin coin : Coin.valuesAmountDesc()) {
            while (amount - coin.amount() >= 0) {
                amount -= coin.amount();
                change.add(coin.amount());
            }
        }

        return change.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
