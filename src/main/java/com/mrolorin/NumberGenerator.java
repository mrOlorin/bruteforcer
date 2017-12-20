package com.mrolorin;

import com.mrolorin.bruteforcer.Generator;

import java.util.concurrent.atomic.AtomicLong;

public class NumberGenerator implements Generator {

    private AtomicLong i = new AtomicLong(0L);

    public String getNext() {
        return "" + this.i.getAndIncrement();
    }

}
