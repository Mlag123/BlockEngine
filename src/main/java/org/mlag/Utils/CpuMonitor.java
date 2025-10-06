package org.mlag.Utils;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CpuMonitor {
    private static final OperatingSystemMXBean osBean =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    // Возвращает загрузку CPU в процентах (0–100)
    public static double getCpuLoad() {
        double load = osBean.getCpuLoad(); // 0.0 - 1.0
        if (load < 0) return 0;
        return load * 100;
    }
}
