import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.By.cssSelector;


class OrderTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestOrderIfValidData() {
        WebElement form = driver.findElement(cssSelector("[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Евгения Смирнова");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type=button]")).click();
        String text = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestIfIncorrectTel() {
        WebElement form = driver.findElement(cssSelector("[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Евгения Смирнова");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("89991234567");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type=button]")).click();
        assertTrue(!form.findElements(cssSelector("[class='input input_type_tel input_view_default input_size_m input_width_available" +
                " input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).isEmpty());
    }

    @Test
    void shouldTestIfIncorrectName() {
        WebElement form = driver.findElement(cssSelector("[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Evgeniya Smirnova");
        form.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        form.findElement(cssSelector("[data-test-id=agreement]")).click();
        form.findElement(cssSelector("[type=button]")).click();
        assertTrue(!form.findElements(cssSelector("[class='input input_type_text input_view_default input_size_m input_width_available " +
                "input_has-label input_has-value input_invalid input_theme_alfa-on-white']")).isEmpty());
    }

    @Test
    void shouldTestIfNoName() {
        WebElement form = driver.findElement(By.cssSelector("[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        List<WebElement> elements = driver.findElements(cssSelector("[class=input__sub]"));
        String text = elements.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestIfNoTel() {
        WebElement form = driver.findElement(By.cssSelector("[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Евгения Смирнова");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        List<WebElement> elements = driver.findElements(cssSelector("[class=input__sub]"));
        String text = elements.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

}
