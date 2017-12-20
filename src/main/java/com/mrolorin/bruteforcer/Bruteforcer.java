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
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        Collection<Callable<String>> solvers = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            solvers.add(new BruteforcerThread(this.tester, this.passwordGenerator));
        }
        return this.solve(executor, solvers);
    }

    private String solve(ExecutorService e, Collection<Callable<String>> solvers) throws InterruptedException {
        CompletionService<String> ecs = new ExecutorCompletionService<>(e);
        int n = solvers.size();
        List<Future<String>> futures = new ArrayList<>(n);
        String result = null;
        try {
            for (Callable<String> s : solvers) {
                futures.add(ecs.submit(s));
            }
            for (int i = 0; i < n; ++i) {
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
            for (Future<String> f : futures) {
                f.cancel(true);
            }
            System.out.println("Shutting down..");
            e.shutdownNow();
        }
        return result;
    }

}
