package com.example.tcc_reddit.service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
public class MemoryService {
    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    public static void logMemoryUsage() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();
        long maxMemory = heapMemoryUsage.getMax();
        long committedMemory = heapMemoryUsage.getCommitted();

        System.out.println("Heap Memory Usage: " +
                "Used = " + (usedMemory / 1024 / 1024) + "MB, " +
                "Max = " + (maxMemory / 1024 / 1024) + "MB, " +
                "Committed = " + (committedMemory / 1024 / 1024) + "MB");
    }
}
