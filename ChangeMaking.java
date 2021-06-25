enum Coin {

    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    ONE(1);

    private final int amount;

    Coin(int amount) {
        this.amount = amount;
    }

    public int amount() {
        return amount;
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

        for (Coin coin : Coin.values()) {
            while (amount - coin.amount() >= 0) {
                amount -= coin.amount();
                change.add(coin.amount());
            }
        }

        return change.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
