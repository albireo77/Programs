import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class FibonacciGenerator {

    private static final Map<Integer, BigInteger> memo = new HashMap<>();

    static {
        memo.put(0, BigInteger.ZERO);
        memo.put(1, BigInteger.ONE);
    }

    public BigInteger getNumber(int n) {
        if (!memo.containsKey(n)) {
            memo.put(n, getNumber(n - 2).add(getNumber(n - 1)));
        }
        return memo.get(n);
    }

    /**
     * This method utilizes Map.computeIfAbsent() but fails with ConcurrentModificationException for Java 9+
     * In Java 9+ Map implementations, map should not be modified during computation
     */
    public BigInteger getNumber8(int n) {
        return memo.computeIfAbsent(n, (k) -> getNumber8(k - 2).add(getNumber8(k - 1)));
    }

    public List<BigInteger> getNumbers(int n) {
        if (!memo.containsKey(n)) {
            getNumber(n);
        }
        return memo.values().stream()
                .limit(n + 1)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        FibonacciGenerator generator = new FibonacciGenerator();
        System.out.println(generator.getNumbers(9));
    }
}
