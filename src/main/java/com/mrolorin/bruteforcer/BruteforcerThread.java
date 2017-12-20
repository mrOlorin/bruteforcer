package com.mrolorin.bruteforcer;

import java.util.concurrent.Callable;

public class BruteforcerThread implements Callable<String> {

    private Tester tester;
    private Generator passwordGenerator;

    BruteforcerThread(Tester tester, Generator passwordGenerator) {
        this.tester = tester;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public String call() {
        Thread current = Thread.currentThread();
        String password = null;
        while (!current.isInterrupted()) {
            password = this.passwordGenerator.getNext();
            try {
                boolean isSucess = this.tester.test(password);
                if (isSucess) {
                    break;
                }
                System.out.println("Tried \"" + password + "\"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return password;
    }

}
