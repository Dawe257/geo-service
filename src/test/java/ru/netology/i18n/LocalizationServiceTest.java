package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

public class LocalizationServiceTest {

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
