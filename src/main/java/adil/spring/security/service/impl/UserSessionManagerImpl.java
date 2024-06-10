package adil.spring.security.service.impl;

import adil.spring.security.DTO.RegisterRequestDTO;
import adil.spring.security.config.Element;
import adil.spring.security.service.UserSessionManagerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.logging.Logger;



@Service
public class UserSessionManagerImpl implements UserSessionManagerService {
    private static final Logger LOGGER = Logger.getLogger(UserSessionManagerImpl.class.getName());

    private static final Map<String, Element> dataStructure = new ConcurrentHashMap<>();
    private static final Map<String, RegisterRequestDTO> userRequests = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void addElement(String key, Element element) {
        dataStructure.put(key, element);
        LOGGER.info("Added element with key: " + key);
        scheduler.schedule(() -> removeElement(key), 3, TimeUnit.MINUTES);
    }

    @Override
    public Element searchElement(String key) {
        return dataStructure.get(key);
    }
public void getAll(){
    LOGGER.info("Current dataStructure: " + dataStructure);
    LOGGER.info("Current userRequests: " + userRequests);

}
    @Override
    public String searchKeyByElement(String element) {
        for (Map.Entry<String, Element> entry : dataStructure.entrySet()) {
            if (entry.getValue().getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void removeElement(String key) {
        dataStructure.remove(key);
        userRequests.remove(key);
        LOGGER.info("Removed element with key: " + key);

    }

    public void addUserRequest(String key, RegisterRequestDTO request) {
        userRequests.put(key, request);
    }

    public RegisterRequestDTO getUserRequest(String key) {
        return userRequests.get(key);
    }
}
