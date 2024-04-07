package com.alibou.security.config;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SaveUser {
    private static final Map<String, Element> dataStructure = new HashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public void addElement(String key, Element element) {
        dataStructure.put(key, element);
    }

    public Element searchElement(String key) {
        return dataStructure.get(key);
    }

    public void removeExpiredElements() {
        long currentTime = System.currentTimeMillis();
        dataStructure.entrySet().removeIf(entry -> (currentTime - entry.getValue().getTimestamp()) > 3 * 60 * 1000);
    }
    public String searchKeyByElement(String element) {
        for (Map.Entry<String, Element> entry : dataStructure.entrySet()) {
            if (entry.getValue().getValue().equals(element)) {
                return entry.getKey(); // Возвращаем ключ, соответствующий найденному элементу
            }
        }
        return null; // Если элемент не найден
    }
    public void removeElement(String key) {
        dataStructure.remove(key);
    }

}


