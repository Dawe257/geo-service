package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;

public class GeoServiceTest {

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
}
