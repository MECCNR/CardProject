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

class CardTest {
    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void debitCardSiteTest() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Мигалкин Эдуард");
        $("[data-test-id=phone] input").setValue("+79872341234");
        $("[data-test-id=agreement]").click();
        $("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void invalidInputTest() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("English Name");
        $("[role=button]").click();
        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"));
    }
}