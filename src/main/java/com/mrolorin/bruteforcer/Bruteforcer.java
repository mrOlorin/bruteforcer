package com.mrolorin.bruteforcer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Bruteforcer {

    private Tester tester;
    private Generator passwordGenerator;

    public Bruteforcer(Tester tester, Generator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
        this.tester = tester;
    }

    public String hack(int threadNumber) throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(threadNumber);
        CompletionService<String> ecs = new ExecutorCompletionService<>(e);
        List<Future<String>> futures = new ArrayList<>(threadNumber);
        String result = null;
        try {
            for (int i = 0; i < threadNumber; i++) {
                futures.add(ecs.submit(new BruteforcerThread(this.tester, this.passwordGenerator)));
            }
            for (int i = 0; i < threadNumber; ++i) {
                try {
                    String r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch (ExecutionException ignore) {
                }
            }
        } finally {
            futures.forEach((Future f) -> {
                f.cancel(true);
            });
            System.out.println("Shutting down..");
            e.shutdownNow();
        }
        return result;
    }

}
