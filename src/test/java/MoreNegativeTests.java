import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class MoreNegativeTests {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        open("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void wrongNameTest() {
        $("[data-test-id=name] input").setValue("English Name");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $("[role=button]").click();
        $("[data-test-id=name] .input__inner .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void wrongPhoneTest() {
        $("[data-test-id=name] input").setValue("Васильев Вася");
        $("[data-test-id=phone] input").setValue("dsfsdfqwfgq");
        $("[data-test-id=agreement]").click();
        $("[role=button]").click();
        $("[data-test-id=phone] .input__inner .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeAgreementTest() {
        $("[data-test-id=name] input").setValue("Васильев Вася");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[role=button]").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void emptyFormTest() {
        $("[role=button]").click();
        $("[data-test-id=name]  .input__inner .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void emptyFormPhoneTest() {
        $("[data-test-id=name] input").setValue("Васильев Вася");
        $("[role=button]").click();
        $("[data-test-id=phone]  .input__inner .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void spacesOnlyNameTest() {
        $("[data-test-id=name] input").setValue("               ");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $("[role=button]").click();
        $("[data-test-id=name]  .input__inner .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void hyphenOnlyNameBug() {
        $("[data-test-id=name] input").setValue("---------------");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
}