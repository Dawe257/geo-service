package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {
    @Test
    @DisplayName("Должен отправить русский текст")
    public void shouldBeRussianMessage() {
        String ip = "172.123.12.19";
        Country country = Country.RUSSIA;

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("Moscow", country, "Pushkina", 100500));
        Mockito.when(localizationService.locale(country))
                .thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);
        String expected = "Добро пожаловать";

        Assertions.assertEquals(expected, message);
    }

    @Test
    @DisplayName("Должен отправить английский текст")
    public void shouldBeEnglishMessage() {
        String ip = "96.123.12.19";
        Country country = Country.USA;

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("New-york", country, "Street", 15));
        Mockito.when(localizationService.locale(country))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);
        String expected = "Welcome";

        Assertions.assertEquals(expected, message);
    }
}
