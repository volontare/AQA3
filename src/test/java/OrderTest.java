import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class OrderTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestOrderIfValidData() throws InterruptedException {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Евгения Смирнова");
        elements.get(1).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldTestIfIncorrectTel() throws InterruptedException {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Евгения Смирнова");
        elements.get(1).sendKeys("89991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestIfIncorrectName() throws InterruptedException {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Evgeniya Smirnova");
        elements.get(1).sendKeys("+79876543210");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldTestIfNoName() throws InterruptedException {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(1).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldTestIfNoTel() throws InterruptedException {
        driver.get("http://localhost:9999");
        Thread.sleep(5000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Евгения Смирнова");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        elements = driver.findElements(By.className("input__sub"));
        String text = elements.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    
}
