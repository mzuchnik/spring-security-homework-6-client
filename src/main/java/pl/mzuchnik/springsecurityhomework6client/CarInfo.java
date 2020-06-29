package pl.mzuchnik.springsecurityhomework6client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Service
public class CarInfo {

    private JwtGeneratorService jwtGeneratorService;

    @Autowired
    public CarInfo(JwtGeneratorService jwtGeneratorService) {
        this.jwtGeneratorService = jwtGeneratorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getCars()
    {
        //Tworze klienta
        RestTemplate client = new RestTemplate();

        //Addres z którego pobierane będa dane
        String url =  "http://localhost:8080/api/v1/cars";

        //Token użytkownika
        String jwt = jwtGeneratorService.generateSimpleJWT();

        //Dodanie tokenu do nagłówka HTTP
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", jwt);
        Base64.Encoder encoder = Base64.getEncoder();
        RSAPublicKey publicKey = RSAKeyUtil.getPublicKey();
        header.add("Certification", encoder.encodeToString(publicKey.getEncoded()));
        HttpEntity httpEntity = new HttpEntity(header);

        //Odebranie rządania i mapowanie na odpowiednia klase
        ResponseEntity<Car[]> cars = client.exchange(url, HttpMethod.GET, httpEntity, Car[].class);


        //Wyświetlenie rezultatów
        for (Car car : cars.getBody()) {
            System.out.println(car);
        }
    }

}
