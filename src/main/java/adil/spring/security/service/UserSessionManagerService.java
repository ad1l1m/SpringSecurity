package adil.spring.security.service;


import adil.spring.security.DTO.RegisterRequestDTO;
import adil.spring.security.config.Element;

public interface UserSessionManagerService {
    void addElement(String key, Element element);
    Element searchElement(String key);
//    void removeExpiredElements();
    String searchKeyByElement(String element);
    void removeElement(String key);
    void addUserRequest(String key, RegisterRequestDTO request);
    RegisterRequestDTO getUserRequest(String key);
    void getAll();
}
