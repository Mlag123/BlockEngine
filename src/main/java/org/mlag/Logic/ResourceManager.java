package org.mlag.Logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ResourceManager {


    private static final List<Disposable> resources = new ArrayList<>();
    private static final Logger log = LogManager.getLogger(ResourceManager.class);

    public static void register(Disposable resource) {
        resources.add(resource);
    }

    public static void unregister(Disposable resource) {
        resources.remove(resource);
    }

    public static void cleanupAll() {
        for (Disposable res : resources) {
            try {
                res.cleanUP();
            } catch (Exception e) {
                System.err.println("Ошибка очистки ресурса: " + e.getMessage());
            }
        }
        log.info("Cleaned:= {}, resources",resources.size());

        resources.clear();
    }

}
