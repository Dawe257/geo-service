import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

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

    @ParameterizedTest
    @DisplayName("Должен определить Россию")
    @ValueSource(strings = {"172.123.12.19", "172.125.125.12", "172.65.123.75"})
    public void shouldBeRussia(String argument) {
        GeoService geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(argument).getCountry();
        Country expected = Country.RUSSIA;

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Должен определить США")
    @ValueSource(strings = {"96.123.12.19", "96.125.125.12", "96.65.123.75"})
    public void shouldBeUSA(String argument) {
        GeoService geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(argument).getCountry();
        Country expected = Country.USA;

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Проверка возвращаемого текста")
    @EnumSource(Country.class)
    public void testReturnText(Country country) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(country);

        String expected;
        if (country == Country.RUSSIA) {
            expected = "Добро пожаловать";
        } else {
            expected = "Welcome";
        }
        Assertions.assertEquals(expected, actual);
    }
}
