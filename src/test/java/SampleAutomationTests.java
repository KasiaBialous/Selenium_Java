import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SampleAutomationTests {

    static WebDriver driver;
    public static WebDriverWait wait;


    @BeforeAll
    static void openBrowser() {
        driver = new ChromeDriver();
        driver.get("http://www.automationpractice.pl/index.php");
        driver.manage().window().maximize();
    }

    void waitForLoading() {
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    void login(String email, String password) {

        driver.findElement(By.className("login")).click();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.name("passwd")).sendKeys(password);
        driver.findElement(By.id("SubmitLogin")).click();
    }

    static void addingProductToTheCartAndProceedingToCheckout() {
        //metoda dla dodawania produktu do koszyka i przechodzenia do niego

        WebElement categoryWomenButton =
                driver.findElement(By.xpath("//a[@title='Women']"));
        categoryWomenButton.click();

        WebElement blouseProduct =
                driver.findElement(By.xpath("//h5/a[@title='Blouse']"));
        blouseProduct.click();

        WebElement addToCart = driver.findElement(By.id("add_to_cart"));
        addToCart.click();

        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Proceed to checkout']")));

        WebElement proceedToCheckOut = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
        proceedToCheckOut.click();
    }

    @AfterEach
    void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }


    /*
    Zadanie 1
    Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano adresu email
    Kategoria: LOGIN
     */
    @Test
    void shouldNotSignInWithoutEmailAddress() {

        login("", "1qaz!QAZ");

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div[@class='alert alert-danger']")));

        WebElement emailAddressRequiredTitle =
                driver.findElement(By.xpath("//div/div/div[@class='alert alert-danger']"));
        Assertions.assertEquals("There is 1 error\nAn email address required.", emailAddressRequiredTitle.getText());
    }

    /*
    Zadanie 2.
    Napisz test, który zweryfikuje działanie aplikacji,
    gdy przy próbie logowania nie podano hasła.
    Kategoria: LOGIN
     */
    @Test
    void shouldNotSignInWithoutPassword() {

        login("testkasia@gmail.com", "");

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div/div[@class='alert alert-danger']")));

        WebElement passwordRequiredTitle =
                driver.findElement(By.xpath("//div/div/div[@class='alert alert-danger']"));
        Assertions.assertEquals("There is 1 error\nPassword is required.", passwordRequiredTitle.getText());
    }

    /*
    Zadanie 3.
    Sprawdź czy strona główna oraz strona logowania zawiera logo i pole wyszukiwania.
    Kategoria: MAIN PAGE, LOGIN PAGE
    */

    @Test
        //MAIN PAGE
    void shouldVerifyLogoDisplayOnTheMainPage() {

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='My Shop']")));

        WebElement logoOnTheMainPage =
                driver.findElement(By.xpath("//a[@title='My Shop']"));
        Assertions.assertEquals(true, logoOnTheMainPage.isDisplayed());
    }

    @Test
        //MAIN PAGE
    void shouldVerifySearchQueryDisplayOnTheMainPage() {

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_query_top")));

        WebElement searchQueryOnTheMainPage =
                driver.findElement(By.id("search_query_top"));
        Assertions.assertTrue(searchQueryOnTheMainPage.isDisplayed());
    }

    @Test
        //LOGIN PAGE
    void shouldVerifyLogoDisplayOnTheLoginPage() {

        driver.findElement(By.linkText("Sign in")).click();

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='My Shop']")));

        WebElement logoOnTheLoginPage =
                driver.findElement(By.xpath("//a[@title='My Shop']"));
        Assertions.assertTrue(logoOnTheLoginPage.isDisplayed());
    }

    @Test
        // LOGIN PAGE
    void shouldVerifySearchQueryDisplayOnTheLoginPage() {

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        //waitForLoading();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_query_top")));

        driver.findElement(By.linkText("Sign in")).click();

        WebElement searchQueryOnTheLoginPage =
                driver.findElement(By.id("search_query_top"));

        Assertions.assertTrue(searchQueryOnTheLoginPage.isDisplayed());
    }

    /*
    Zadanie 4.
    Napisz test sprawdzający przejście ze strony głównej do strony "Kontakt".
     */
    @Test
    void shouldRedirectFromMainPageToContactPage() {

        WebElement contactUsButton =
                driver.findElement(By.xpath("//a[@title='Contact us']"));
        contactUsButton.click();

        WebElement contactUsTitle =
                driver.findElement(By.xpath("//div/h1"));
        Assertions.assertEquals("CUSTOMER SERVICE - CONTACT US", contactUsTitle.getText());
    }

    /*
    Zadanie 5.
    Napisz test sprawdzający przejście ze strony logowania do strony głównej.
    */
    @Test
    void shouldRedirectFromLoginPageToTheMainPageThruLogo() {

        driver.findElement(By.linkText("Sign in")).click();

        WebElement toTheMainPageButton =
                driver.findElement(By.xpath("//a[@title='My Shop']"));
        toTheMainPageButton.click();

        Assertions.assertEquals("My Shop", driver.getTitle());
    }

     /*
    Zadanie 6.
    Napisz test, który dodaje produkt do koszyka.
    Zweryfikuj czy dodanie powiodlo się.
     */

    @Test
    void shouldVerifyProductAddedToTheCart() {

        addingProductToTheCartAndProceedingToCheckout();

        WebElement shoppingCartButton = driver.findElement(By.xpath("//div[@class='shopping_cart']"));
        shoppingCartButton.click();

        WebElement cartInfo = driver.findElement(By.className("heading-counter"));

        Assertions.assertEquals("Your shopping cart contains: 1 product", cartInfo.getText());

    }

    /*
    Zadanie 7.
    Napisz test, który dodaje produkt do koszyka, a nastepnie usuwa go.
    Zweryfikuj czy usunięcie powiodlo się.
     */

    @Test
    void shouldVerifyAddingThenRemovingProductFromTheCart() {

        addingProductToTheCartAndProceedingToCheckout();

        WebElement removeProductButton = driver.findElement(By.className("icon-trash"));
        removeProductButton.click();

        // W razie zbyt wolnego ładowania/nie przechodzenia testu, odkomentuj wait :)
        waitForLoading();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-warning")));

        WebElement emptyShoppingCartTitle = driver.findElement(By.className("alert-warning"));
        Assertions.assertEquals("Your shopping cart is empty.", emptyShoppingCartTitle.getText());
    }
}
