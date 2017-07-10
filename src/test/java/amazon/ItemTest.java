package amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.ReadConfigFile;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by cima on 30/03/2017.
 */
public class ItemTest {

    private static final String PATH_CHROMEDRIVER = "ar.org.path.chromeDriver";
    private static final String XPATH_MAIN_TABLE = "ar.org.xpath.main_table";
    private static final String PRODUCT_LIST_URL = "ar.org.product.list.url";
    private static final String AMAZON_USERNAME = "ar.org.amazon.username";
    private static final String AMAZON_PASSWORD = "ar.org.amazon.password";
    private static final String STOP_UNTIL_PAGE = "ar.org.stop.until.page";
    private static final String DELAY = "ar.org.delay.seconds";
    private String DOMAIN_URL;
    private WebDriver driver;
    private ArrayList<String> tabs;
    private final List<StringBuilder>  results_with_out_TAX = new ArrayList<>();
    private final List<StringBuilder>  results_with_TAX = new ArrayList<>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @BeforeSuite(alwaysRun = true)
    public void initDriver() throws Exception {
        System.out.println("Before Suite");
    }

    @BeforeTest
    public void setUpDriver() {
        this.driver = getChromeDriver();
        System.out.println("Before Test");
    }

    private int hasSize() {
        String checkHasSize = this.driver.findElement(By.id("add-to-cart-button")).getAttribute("outerHTML");
        //print(checkHasSize);
        if (checkHasSize.contains("cursor: not-allowed")) {
            WebElement aux = this.driver.findElement(By.id("native_dropdown_selected_size_name"));
            List<WebElement> aux_list = aux.findElements(By.tagName("option"));
            int i = -1;
            for (WebElement webElement : aux_list) {
                //System.out.println("I: " + i);
                if (webElement.getAttribute("outerHTML").contains("dropdownAvailable") &&
                        !webElement.getAttribute("outerHTML").contains("value=\"-1\"")) {
                    //System.out.println("Return " + i + " size element");
                    return i;
                }
                i += 1;
            }
            return -1;
        }
        return -2;
    }

    //@Test
    public void testMenu2() {
        this.driver.get("https://www.amazon.es/gp/cart/view.html/ref=lh_cart_vc_btn");
        checkDelivery("BLA");
        /*JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('about:blank','_blank');");
        this.tabs = new ArrayList<>(this.driver.getWindowHandles());
        this.driver.switchTo().window(this.tabs.get(1));
        this.driver.get("https://www.google.com");
        this.driver.close();
        this.driver.switchTo().window(this.tabs.get(0));
        //closeAll();*/
    }

    @Test
    public void testMenu() throws URISyntaxException {
        print("====================================================================================================");
        print("========================= PARAMETERS =========================");
        print("STOP PAGE::: " + ReadConfigFile.getProperty(STOP_UNTIL_PAGE));
        print("START URL::: " + ReadConfigFile.getProperty(PRODUCT_LIST_URL));
        print("====================================================================================================");
        setAmazonDomain();
        this.driver.get(DOMAIN_URL);
        signin();
        emptyCart();
        this.driver.get(ReadConfigFile.getProperty(PRODUCT_LIST_URL));
        waitForLoad(driver);
        int pageListSize = this.driver.findElements(By.className("pagnCur")).size();
        //int stopPage = Integer.parseInt(ReadConfigFile.getProperty(STOP_UNTIL_PAGE));
        if (pageListSize != 0) {
            int pageCount = 1;
            while (this.driver.findElements(By.id("pagnNextLink")).size() != 0) {
                //System.out.println("BLA: " + stopPage + "bla2: " + pageCount);
                if(pageCount <= Integer.parseInt(ReadConfigFile.getProperty(STOP_UNTIL_PAGE))){
                    WebElement currentPage = this.driver.findElement(By.className("pagnCur"));
                    print("====================================================================================================");
                    print("========================= ACTUAL PAGE::: " + currentPage.getText() + " =========================");
                    print("====================================================================================================");
                    List<WebElement> products = getProducts();
                    evaluate_products(products);
                    String link = this.driver.findElement(By.id("pagnNextLink")).getAttribute("href");
                    this.driver.get(link);
                }else{
                    break;
                }
                pageCount++;
            }
            printResults();
        } else {
            print("====================================================================================================");
            print("=========================================== SIMPLE LIST ============================================");
            print("====================================================================================================");
            List<WebElement> products = getProducts();
            evaluate_products(products);
            printResults();
        }
        signout();
    }

    private void emptyCart() {
        print("=================================== emptyCart ===================================");
        this.driver.get(DOMAIN_URL+"/gp/cart/view.html/ref=nav_cart");
        List<WebElement> elements = this.driver.findElements(By.cssSelector(".a-row.sc-list-item.sc-list-item-border.sc-java-remote-feature"));
        print("Items to remove from previous cart: " + elements.size());
        for (int i = elements.size(); i > 0; i--) {
            print("Deleting cart items: " + Integer.toString(i));
            //print(this.driver.findElement(By.xpath("//*[@id=\"activeCartViewForm\"]/div[2]/div[" + i + "]/div[4]/div/div[1]/div/div/div[2]/div/span[1]/span/input")).getText());
            this.driver.findElement(By.xpath("//*[@id=\"activeCartViewForm\"]/div[2]/div[" + i + "]/div[4]/div/div[1]/div/div/div[2]/div/span[1]/span/input")).click();
            sleep();
        }
    }

    private void setAmazonDomain() throws URISyntaxException {
        URI uri = new URI(ReadConfigFile.getProperty(PRODUCT_LIST_URL));
        DOMAIN_URL = "https://" + uri.getHost();
        print("Domain: " + DOMAIN_URL);
    }

    private void signin() {
        print("=================================== Login ===================================");
        String link_to_login = this.driver.findElement(By.xpath("//div[@id='nav-flyout-ya-signin']/a")).getAttribute("href");
        print(link_to_login);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", this.driver.findElement(By.xpath("//div[@id='nav-flyout-ya-signin']/a")));

        this.driver.findElement(By.id("ap_email")).sendKeys(ReadConfigFile.getProperty(AMAZON_USERNAME));
        this.driver.findElement(By.id("ap_password")).sendKeys(ReadConfigFile.getProperty(AMAZON_PASSWORD));
        this.driver.findElement(By.id("signInSubmit")).click();
        if (this.driver.findElements(By.id("cvf-page-content")).size() != 0) {
            sleep(30);
        }


        waitForLoad(driver);
    }

    private void signout() {
        print("=================================== LOGOUT ===================================");
        //this.driver.get(DOMAIN_URL+"/gp/flex/sign-out.html/ref=nav_youraccount_signout?ie=UTF8&action=sign-out&path=%2Fgp%2Fyourstore%2Fhome&signIn=1&useRedirectOnSuccess=1");
    }

    private void printResults() {
        print(ANSI_GREEN + "======================================================================================================" + ANSI_RESET);
        print(ANSI_GREEN + "======================================================================================================" + ANSI_RESET);
        print(ANSI_GREEN + "======================================== RESULTS WITH OUT TAX ========================================" + ANSI_RESET);
        for (StringBuilder results_with_out_tax : results_with_out_TAX) {
            System.out.println(results_with_out_tax.toString());

        }
        print(ANSI_RED + "======================================================================================================" + ANSI_RESET);
        print(ANSI_RED + "======================================================================================================" + ANSI_RESET);
        print(ANSI_RED + "==============================++========== RESULTS WITH TAX ++========================================" + ANSI_RESET);
        for (StringBuilder results_with_tax : results_with_TAX) {
            System.out.println(results_with_tax.toString());
        }
        print("====================================================================================================");
    }

    private void evaluate_products(List<WebElement> products) {
        System.out.println("=================================== evaluate_products ===================================");
        int i = 1;
        for (WebElement webElement : products) {
            System.out.println("Element: " + i);
            //if (i >= 5){
            String itemLink = webElement.findElement(By.cssSelector("a[class='a-link-normal s-access-detail-page  s-color-twister-title-link a-text-normal']")).getAttribute("href");
            evaluate_item(itemLink);
            //}
            i++;
        }
    }

    private void sleep() {
        sleep(Integer.parseInt(ReadConfigFile.getProperty(DELAY)));
    }

    private void sleep(int seconds) {
        //print("Sleep call");
        try {
            Thread.sleep((seconds) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void evaluate_item(String itemLink) {
        //System.out.println("=================================== evaluate_item ===================================");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('about:blank','_blank');");
        this.tabs = new ArrayList<>(this.driver.getWindowHandles());
        this.driver.switchTo().window(this.tabs.get(1));
        //print("Evaluating this item: " + itemLink);
        this.driver.get(itemLink);
        sleep();
        int size = hasSize();
        //System.out.println("Size::: " + size);
        if (size != -1) {
            if (size != -2) {
                Select select = new Select(this.driver.findElement(By.id("native_dropdown_selected_size_name")));
                select.selectByIndex(size);
                sleep();
            }
            this.driver.findElement(By.id("add-to-cart-button")).click();
            sleep();
            //waitForLoad(this.driver);
            if(this.driver.findElements(By.id("siNoCoverage-announce")).size() != 0) {
                this.driver.findElement(By.id("siNoCoverage-announce")).click();
                sleep();
            }
            this.driver.findElement(By.id("hlb-view-cart-announce")).click();
            //waitForLoad(this.driver);
            sleep();
            System.out.println("=================================== checkDelivery ===================================");
            print("Evaluating this item: " + itemLink);
            if (!checkDelivery(itemLink)) {
                print("Second checkDelivery");
                checkDelivery(itemLink);
            }
            //Delete item from cart after check
            print("Deleted item from cart");
            this.driver.findElement(By.xpath("//*[@id=\"activeCartViewForm\"]/div[2]/div/div[4]/div/div[1]/div/div/div[2]/div/span[1]/span/input")).click();
            sleep();
        }
        this.driver.close();
        this.driver.switchTo().window(this.tabs.get(0));
    }

    private boolean checkDelivery(String productLink) {
        boolean canCheck = (this.driver.findElements(By.cssSelector(".a-box.sc-flc.sc-invisible-when-no-js")).size() != 0);
        if (canCheck){
            this.driver.findElement(By.cssSelector(".a-box.sc-flc.sc-invisible-when-no-js")).click();
            sleep();
            List<WebElement> elementList = this.driver.findElements(By.cssSelector(".a-row.sc-subtotal-detail"));
            if (elementList.size() != 0) {
                WebElement temp = this.driver.findElement(By.cssSelector(".a-row.sc-subtotal-detail"));
                //print(temp.getAttribute("outerHTML"));
                String html = temp.getAttribute("outerHTML");
                if (html.contains("a-row sc-item-quantity-subtotal")) {
                    StringBuilder builder = new StringBuilder();
                    WebElement aux = temp.findElement(By.cssSelector(".a-row.sc-item-quantity-subtotal"));
                    builder.append("--------------------------------------------------------------------------------");
                    builder.append(System.lineSeparator());
                    //print("--------------------------------------------------------------------------------");
                    builder.append(ANSI_BLUE  + "PRICE: " + aux.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                    builder.append(System.lineSeparator());
                    //print(aux.findElement(By.cssSelector("span")).getText());
                    WebElement aux3 = temp.findElement(By.cssSelector(".a-row.sc-shipping-cost"));
                    WebElement aux4 = aux3.findElement(By.cssSelector(".a-column.a-span3.a-text-right.a-span-last.sc-value"));
                    builder.append(ANSI_CYAN  + "SHIPPING: " + aux4.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                    builder.append(System.lineSeparator());
                    if (html.contains("a-row sc-importfee")) {
                        WebElement aux1 = temp.findElement(By.cssSelector(".a-row.sc-importfee"));
                        builder.append(ANSI_RED  + "IMPORT: " + aux1.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                        builder.append(System.lineSeparator());
                        WebElement aux2 = temp.findElement(By.cssSelector(".a-row.sc-grand-total"));
                        builder.append(ANSI_YELLOW  + "TOTAL: " + aux2.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                        builder.append(System.lineSeparator());
                        builder.append(ANSI_RED  + "Item with TAX: " + ANSI_RESET);
                        builder.append(productLink);
                        builder.append(System.lineSeparator());
                        builder.append("--------------------------------------------------------------------------------");
                        results_with_TAX.add(builder);
                    } else {
                        //WebElement aux3 = temp.findElement(By.cssSelector(".a-row.sc-shipping-cost"));
                        //builder.append(ANSI_CYAN  + "SHIPPING: " + aux3.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                        //builder.append(System.lineSeparator());
                        WebElement aux2 = temp.findElement(By.cssSelector(".a-row.sc-grand-total"));
                        builder.append(ANSI_GREEN  + "TOTAL: " + aux2.findElement(By.cssSelector("span")).getText() + ANSI_RESET);
                        builder.append(System.lineSeparator());
                        builder.append(ANSI_GREEN  + "Item with OUT TAX: " + ANSI_RESET);
                        builder.append(System.lineSeparator());
                        builder.append(productLink);
                        builder.append(System.lineSeparator());
                        print("--------------------------------------------------------------------------------");
                        results_with_out_TAX.add(builder);
                    }
                    //print(productLink);
                    System.out.println(builder.toString());
                    return true;
                }
            }
        }
        return false;
    }

    private void print(String s) {
        System.out.println(s);
    }

    @AfterMethod
    public void closeAll() {
        this.tabs = new ArrayList<>(driver.getWindowHandles());
        if (this.tabs.size()!= 0){
            for (String tab : tabs) {
                this.driver.switchTo().window(tab);
                this.driver.close();
            }
        }
        System.out.println("AfterMethod");
        //this.driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void quitDriver() throws Exception {
        System.out.println("AfterSuite");
    }

    private WebDriver getChromeDriver() {
        System.out.println("=================================== getChromedriver ===================================");
        // ########################################################################################################
        // ### LIST OF ARGUMENTS FOR CHROMEDRIVE
        // #### http://www.assertselenium.com/java/list-of-chrome-driver-command-line-arguments/
        // ########################################################################################################

        // SETTING CAPABILITES AND ARGUMENTS
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeDriverService cds = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(ReadConfigFile.getProperty(PATH_CHROMEDRIVER)))
                .build();
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("disable-infobars");
        chromeOption.addArguments("no-sandbox");
        chromeOption.addArguments("user-data-dir=" + System.getProperty("user.dir") + "\\target\\chromeData");
        chromeOption.addArguments("start-maximized");
        chromeOption.addArguments("disable-session-crashed-bubble");

        Map<String, Object> prefs = new LinkedHashMap<>();
        prefs.put("credentials_enable_service", Boolean.FALSE);
        prefs.put("profile.password_manager_enabled", Boolean.FALSE);
        chromeOption.setExperimentalOption("prefs", prefs);

        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOption);

        this.driver = new ChromeDriver(cds, capabilities);
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.tabs = new ArrayList<>(driver.getWindowHandles());
        return driver;
    }

    private void waitForLoad(WebDriver driver) {
        //System.out.println("=================================== waitForLoad ===================================");
        new WebDriverWait(driver, 10).until((ExpectedCondition<Boolean>) wd -> {
            assert wd != null;
            return ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete");
        });
    }

    private List<WebElement> getProducts() {
        System.out.println("=================================== getProducts ===================================");
        WebElement tableProduct = this.driver.findElement(By.id(ReadConfigFile.getProperty(XPATH_MAIN_TABLE)));
        List<WebElement> products = tableProduct.findElements(By.tagName("li"));
        List<WebElement> filtered_products = new ArrayList<>();
        for (WebElement product : products) {
            if (!product.getAttribute("outerHTML").contains("s-result-item s-item-placeholder"))
                filtered_products.add(product);
        }
        System.out.println("Get products list size: " + filtered_products.size());
        return filtered_products;
    }
}