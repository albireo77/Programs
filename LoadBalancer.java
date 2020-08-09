import org.springframework.http.HttpRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import static java.util.Comparator.comparingDouble;

enum Mode {
    ROUND_ROBIN, BY_LOAD
}

interface Host {
    double getLoad();
    void handleRequest(HttpRequest request);
}

public class LoadBalancer {

    private final List<Host> hosts;
    private final Mode mode;
    private final AtomicInteger rrCount = new AtomicInteger();

    private final IntBinaryOperator OP = (actual, size) -> actual == size ? 0 : (actual + 1);

    public LoadBalancer(List<Host> hosts, Mode mode) {
        this.hosts = hosts;
        this.mode = mode;
    }

    public void handleRequest(HttpRequest request) {
        switch (mode)  {
            case ROUND_ROBIN:
                handleRoundRobin(request);
                break;
            case BY_LOAD:
                handleByLoad(request);
                break;
        }
    }

    private void handleRoundRobin(HttpRequest request) {
        if (hosts.isEmpty()) {
            return;
        }
        int i = rrCount.getAndAccumulate(hosts.size()-1, OP);
        hosts.get(i).handleRequest(request);
    }

    private void handleByLoad(HttpRequest request) {
        Optional<Host> optHost = hosts.stream()
                .filter(h -> h.getLoad() < 0.75)
                .findFirst();
        if (!optHost.isPresent()) {
            optHost = hosts.stream()
                    .min(comparingDouble(Host::getLoad));
        }
        optHost.ifPresent(h -> h.handleRequest(request));
    }
}
