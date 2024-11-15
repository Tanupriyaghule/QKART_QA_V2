package QKART_TESTNG;
import QKART_SANITY_LOGIN.Module1.Checkout;
//import QKART_SANITY_LOGIN.Module1.Checkout;
//import QKART_TESTNG.pages.Checkout;
import QKART_TESTNG.pages.Home;
import QKART_TESTNG.pages.Login;
import QKART_TESTNG.pages.Register;
import QKART_TESTNG.pages.SearchResult;
//import static org.testng.Assert.assertTrue;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//mport org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.WebElement;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeoutException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class QKART_Tests {

    static RemoteWebDriver driver;
    public static String lastGeneratedUserName;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        // Launch Browser using Zalenium
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        System.out.println("createDriver()");
    }

    /*
     * Testcase01: Verify a new user can successfully register
     */ 
    @Test(description = "Verify registration happens correctly",dataProvider="data-provider",dataProviderClass=DProvider.class,priority = 1, groups = { "Sanity_test" })
   // @Parameters({ "testUser", "abc@123" })
    public void TestCase01(@Optional("TC1_username") String TC1_username, @Optional("TC1_password") String TC1_password)
            throws InterruptedException {
        Boolean status;

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("TC1_username","TC1_password", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        login.navigateToLoginPage();

        status = login.PerformLogin(lastGeneratedUserName,"TC1_password");

        assertTrue(status, "Failed to login with registered user");

        // Visit the home page and log out the logged in user
         Home home = new Home(driver);
    status = home.PerformLogout();
    }

    
     @Test(description = "Verify re-registering an already registered user fails",dataProvider="data-provider",dataProviderClass=DProvider.class, priority = 2, groups = {"Sanity_test" })
   // @Parameters({"testUser", "abc@123"})
    public void TestCase02(@Optional("TC2_username") String TC2_username, @Optional("TC2_password") String TC2_password)
            throws InterruptedException {
        Boolean status;
        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser(TC2_username,TC2_password, true);
        assertTrue(status, "User registration failed");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the Registration page and try to register using the previously
        // registered user's credentials
        registration.navigateToRegisterPage();
        status = registration.registerUser(lastGeneratedUserName,TC2_password, false);

        // If status is true, then registration succeeded, else registration has
        // failed. In this case registration failure means Success
        assertFalse(status, "Re-registration succeeded");
    }

 @Test(description = "Verify the functionality of search text box",dataProvider="data-provider",dataProviderClass=DProvider.class, priority = 3, groups = { "Sanity_test" })
  //  @Parameters("YONEX")
    public void TestCase03(@Optional("YONEX") String YONEX) throws InterruptedException {
        boolean status;

        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Search for the "yonex" product
        status = homePage.searchForProduct("YONEX");

        assertTrue(status, "Unable to search for given product - " + "YONEX");

        // Fetch the search results
        List<WebElement> searchResults = homePage.getSearchResults();

        // Verify the search results are available
        assertFalse(searchResults.size() == 0,
                "There were no results for the given search string - " + "YONEX");

        for (WebElement webElement : searchResults) {
            // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();
          assertTrue(elementText.toUpperCase().contains("YONEX"),
                  "Test Results contains un-expected values: " + elementText);
        }

        // Search for product
        status = homePage.searchForProduct("Gesundheit");

        // Verify no search results are found
        searchResults = homePage.getSearchResults();

        Boolean isResultsEmpty = searchResults.size() == 0;

        assertTrue(isResultsEmpty, "Expected: no results, Actual: Results were available");
    }

   
     @Test(description = "Verify the existence of size chart for certain items and validate contents of size chart",dataProvider="data-provider",dataProviderClass=DProvider.class, priority = 4, groups = {        "Regression_Test" } )
   // @Parameters("TC4_ProductNameToSearchFor")
    public void TestCase04(String TC4_ProductNameToSearchFor) throws InterruptedException {
        // Visit home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Search for product and get card content element of search results
        homePage.searchForProduct(TC4_ProductNameToSearchFor);
        List<WebElement> searchResults = homePage.getSearchResults();

        // Create expected values
        List<String> expectedTableHeaders = Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
        List<List<String>> expectedTableBody = Arrays.asList(Arrays.asList("6", "6", "40", "9.8"),
                Arrays.asList("7", "7", "41", "10.2"), Arrays.asList("8", "8", "42", "10.6"),
                Arrays.asList("9", "9", "43", "11"), Arrays.asList("10", "10", "44", "11.5"),
                Arrays.asList("11", "11", "45", "12.2"), Arrays.asList("12", "12", "46", "12.6"));

        // Verify size chart presence and content matching for each search result
        for (WebElement webElement : searchResults) {
            SearchResult result = new SearchResult(webElement);

            // Verify if the size chart exists for the search result
            Boolean isSizeChartExists = result.verifySizeChartExists();

            assertTrue(isSizeChartExists, "Size Chart Link does not exist");

            if (isSizeChartExists) {
                // Verify if size dropdown exists
                Boolean isSizeDropdownExist = result.verifyExistenceofSizeDropdown(driver);
                assertTrue(isSizeDropdownExist, "Size dropdown doesn't exist");

                // Open the size chart
                Boolean isSizeChartOpenSuccess = result.openSizechart();
                assertTrue(isSizeChartOpenSuccess, "Failed to open Size Chart");

                if (isSizeChartOpenSuccess) {
                    // Verify if the size chart contents matches the expected values
                    Boolean isChartContentMatching = result.validateSizeChartContents(expectedTableHeaders,
                            expectedTableBody, driver);
                    assertTrue(isChartContentMatching, "Failure while validating contents of Size Chart Link");

                    // Close the size chart modal
                    Boolean isSizeChartClosed = result.closeSizeChart(driver);
                    assertTrue(isSizeChartClosed, "Closing size chart failed");
                }

            }
        }
    }

   
    @Test(description = "Verify that a new user can add multiple products in to the cart and Checkout",dataProvider="data-provider",dataProviderClass=DProvider.class,priority = 5, groups = {"Sanity_test" })
//@Parameters({"TC5_ProductNameToSearchFor1","TC5_ProductNameToSearchFor2","TC5_AddressDetails"})
public void TestCase05(@Optional("TC5_ProductNameToSearchFor1") String TC5_ProductNameToSearchFor1, @Optional("TC5_ProductNameToSearchFor2") String TC5_ProductNameToSearchFor2,
@Optional("TC5_AddressDetails") String TC5_AddressDetails) throws InterruptedException {
Boolean status;

// Go to the Register page
Register registration = new Register(driver);
registration.navigateToRegisterPage();

// Register a new user
status = registration.registerUser("testUser", "abc@123", true);
assertTrue(status, "Registration failed");

// Save the username of the newly registered user
lastGeneratedUserName = registration.lastGeneratedUsername;

// Go to the login page
Login login = new Login(driver);
login.navigateToLoginPage();

// Login with the newly registered user's credentials
status = login.PerformLogin(lastGeneratedUserName, "abc@123");
assertTrue(status, "Login failed");

// Go to the home page
Home homePage = new Home(driver);
homePage.navigateToHome();

// Find required products by searching and add them to the user's cart
status = homePage.searchForProduct("YONEX");
homePage.addProductToCart("YONEX Smash Badminton Racquet");
status = homePage.searchForProduct("Tan");
homePage.addProductToCart("Tan Leatherette Weekender Duffle");

// Click on the checkout button
homePage.clickCheckout();

// Add a new address on the Checkout page and select it
Checkout checkoutPage = new Checkout(driver);
checkoutPage.addNewAddress("Addr line 1  addr Line 2  addr line 3");
checkoutPage.selectAddress("Addr line 1  addr Line 2  addr line 3");

// Place the order
checkoutPage.placeOrder();

//WebDriverWait wait = new WebDriverWait(driver, 30);
//wait.until(ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));

// Check if placing order redirected to the Thanks page
status = driver.getCurrentUrl().endsWith("/thanks");
assertTrue(status, "Placing order didn't redirect to Thanks page");

// Go to the home page
homePage.navigateToHome();

// Log out the user
homePage.PerformLogout();
}
 
    
     @Test(description = "Verify that the contents of the cart can be edited",dataProvider="data-provider",dataProviderClass=DProvider.class,priority = 6, groups = {
 "Regression_Test" })
   // @Parameters({"TC6_ProductNameToSearch1","TC6_ProductNameToSearch2"})
    public void TestCase06(@Optional("TC6_ProductNameToSearch1") String TC6_ProductNameToSearch1,@Optional("TC6_ProductNameToSearch2") String TC6_ProductNameToSearch2) throws InterruptedException {
                Boolean status;

                Home homePage = new Home(driver);
                Register registration = new Register(driver);
                Login login = new Login(driver);
        
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);
                assertTrue(status, "Registration failed");
        
                lastGeneratedUserName = registration.lastGeneratedUsername;
        
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");
                assertTrue(status, "Login failed");
        
                homePage.navigateToHome();
                status = homePage.searchForProduct("Xtend Smart Watch");
                homePage.addProductToCart("Xtend Smart Watch");
        
                status = homePage.searchForProduct("Yarine Floor Lamp");
                homePage.addProductToCart("Yarine Floor Lamp");
        
                // update watch quantity to 2
                homePage.changeProductQuantityinCart("Xtend Smart Watch", 2);
        
                // update table lamp quantity to 0
                homePage.changeProductQuantityinCart("Yarine Floor Lamp", 0);
        
                // update watch quantity again to 1
                homePage.changeProductQuantityinCart("Xtend Smart Watch", 1);
        
                homePage.clickCheckout();
        
                Checkout checkoutPage = new Checkout(driver);
                checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
                checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");
        
                checkoutPage.placeOrder();
        
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.urlToBe("https://crio-qkart-frontend-qa.vercel.app/thanks"));
        
                status = driver.getCurrentUrl().endsWith("/thanks");
                assertTrue(status, "Wasn't redirected to the Thanks page");
        
                homePage.navigateToHome();
                homePage.PerformLogout();
        
    }

 @Test(description = "Verify that the contents made to the cart are saved against the user's login details",dataProvider="data-provider",dataProviderClass=DProvider.class,priority = 7, groups = {
     "Regression_Test" })
//@Parameters("TC7_ListOfProductsToAddToCart")
public void TestCase07(String TC7_ListOfProductsToAddToCart)throws InterruptedException {
Boolean status;

List<String> expectedResult = Arrays.asList(TC7_ListOfProductsToAddToCart.split(";"));

Register registration = new Register(driver);
Login login = new Login(driver);
Home homePage = new Home(driver);

registration.navigateToRegisterPage();
status = registration.registerUser("testUser", "abc@123", true);
assertTrue(status, "Registration failed");

lastGeneratedUserName = registration.lastGeneratedUsername;

login.navigateToLoginPage();
status = login.PerformLogin(lastGeneratedUserName, "abc@123");
assertTrue(status, "Login failed");

homePage.navigateToHome();
status = homePage.searchForProduct(expectedResult.get(0));
homePage.addProductToCart(expectedResult.get(0));

status = homePage.searchForProduct(expectedResult.get(1));
homePage.addProductToCart(expectedResult.get(1));

homePage.PerformLogout();

login.navigateToLoginPage();
status = login.PerformLogin(lastGeneratedUserName, "abc@123");

status = homePage.verifyCartContents(expectedResult);

homePage.PerformLogout();
assertTrue(status, "Verifying cart contents after logging out and logging in failed");
}

    //dataProvider="data-provider",dataProviderClass=DProvider.class,
     @Test(description = "Verify that insufficient balance error is thrown when the wallet balance is not enough", priority = 8, groups = {"Sanity_test" })
   @Parameters({"TC8_ProductName","TC8_Qty"})
    public void TestCase08(String TC8_ProductName, int TC8_Qty) throws InterruptedException {
        Boolean status;

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Registration failed");

        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        assertTrue(status, "Login failed");

        Home homePage = new Home(driver);
        homePage.navigateToHome();
        status = homePage.searchForProduct(TC8_ProductName);
        homePage.addProductToCart(TC8_ProductName);

        homePage.changeProductQuantityinCart(TC8_ProductName,TC8_Qty);

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 3");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 3");

        checkoutPage.placeOrder();
        Thread.sleep(3000);

        status = checkoutPage.verifyInsufficientBalanceMessage();
        assertTrue(status, "Insufficient balance message not shown");
    }
  
     @Test(description = "Verify that a product added to a cart is available when a new tab is added",priority = 10, dependsOnMethods = {"TestCase10" }, groups = { "Regression_Test" })
//@Parameters("TC9_ProductNameToSearchFor")
            public void TestCase09() throws InterruptedException
            { boolean status;

            Register registration = new Register(driver);
            registration.navigateToRegisterPage();
            status = registration.registerUser("testUser", "abc@123", true);
            assertTrue(status, "Registration failed");
    
            String lastGeneratedUserName = registration.lastGeneratedUsername;
    
            Login login = new Login(driver);
            login.navigateToLoginPage();
            status = login.PerformLogin(lastGeneratedUserName, "abc@123");
            assertTrue(status, "Login failed");
    
            Home homePage = new Home((RemoteWebDriver) driver);
            homePage.navigateToHome();
    
            status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
            assertTrue(status, "Search for product failed");
    
            homePage.addProductToCart("YONEX Smash Badminton Racquet");
    
            String currentURL = driver.getCurrentUrl();
    
            driver.findElement(By.linkText("Privacy policy")).click();
            Set<String> handles = driver.getWindowHandles();
            driver.switchTo().window(new ArrayList<>(handles).get(1));
    
            driver.get(currentURL);
            Thread.sleep(2000);
    
            List<String> expectedResult = Arrays.asList("YONEX Smash Badminton Racquet");
            status = homePage.verifyCartContents(expectedResult);
    
            assertTrue(status, "Verification for product in cart when a new tab is opened failed");
    
            driver.close();
            driver.switchTo().window(new ArrayList<>(handles).get(0));
    
          
        
    }

    
 @Test(description = "Verify that privacy policy and about us links are working fine", priority = 9, groups = {
          "Regression_Test" })
   // @Parameters("YONEX Smash Badminton Racquet")
            public void TestCase10(@Optional("YONEX") String YONEX) throws InterruptedException {
                Boolean status = false;
                Register registration = new Register(driver);
                registration.navigateToRegisterPage();
                status = registration.registerUser("testUser", "abc@123", true);
                assertTrue(status, YONEX);
        
                lastGeneratedUserName = registration.lastGeneratedUsername;
        
                Login login = new Login(driver);
                login.navigateToLoginPage();
                status = login.PerformLogin(lastGeneratedUserName, "abc@123");
               assertTrue(status, YONEX);
        
                Home homePage = new Home(driver);
                homePage.navigateToHome();
        
                String basePageURL = driver.getCurrentUrl();
        
                driver.findElement(By.linkText("Privacy policy")).click();
                status = driver.getCurrentUrl().equals(basePageURL);
                assertTrue(status, "Parent page url changed on privacy policy link click");
        
                Set<String> handles = driver.getWindowHandles();
                driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
                WebElement PrivacyPolicyHeading = driver.findElement(By.xpath("//*[@id=\'root\']/div/div[2]/h2"));
                status = PrivacyPolicyHeading.getText().equals("Privacy Policy");
                assertTrue(status, "New tab opened doesn't have Privacy Policy page heading content");
        
                driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
                driver.findElement(By.linkText("Terms of Service")).click();
        
                handles = driver.getWindowHandles();
                driver.switchTo().window(handles.toArray(new String[handles.size()])[2]);
                WebElement TOSHeading = driver.findElement(By.xpath("//*[@id=\'root\']/div/div[2]/h2"));
                status = TOSHeading.getText().equals("Terms of Service");
                assertTrue(status, "New tab opened doesn't have Terms of Service page heading content");
        
                driver.close();
                driver.switchTo().window(handles.toArray(new String[handles.size()])[1]).close();
                driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
    }

      @Test(description = "Verify that the contact us dialog works fine",dataProvider="data-provider",dataProviderClass=DProvider.class, priority = 11, groups = { "Regression_Test" })
    //@Parameters({"TC11_ContactusUserName","TC11_ContactUsEmail","TC11_QueryContent"})
    public void TestCase11(String TC11_ContactusUserName, String TC11_ContactUsEmail, String TC11_QueryContent)
            throws InterruptedException {
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        driver.findElement(By.xpath("//*[text()='Contact us']")).click();

        WebElement name = driver.findElement(By.xpath("//input[@placeholder='Name']"));
        name.sendKeys(TC11_ContactusUserName);
        WebElement email = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        email.sendKeys(TC11_ContactUsEmail);
        WebElement message = driver.findElement(By.xpath("//input[@placeholder='Message']"));
        message.sendKeys(TC11_QueryContent);

        WebElement contactUs = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/section/div/div/div/form/div/div/div[4]/div/button"));

        contactUs.click();
    }

   
    @Test(description = "Ensure that the Advertisement Links on the QKART page are clickable",priority = 12, groups= {"Sanity_test" })
    @Parameters({"TC12_ProductNameToSearch"})
    //dataProvider="data-provider",dataProviderClass=DProvider.class,
    public void TestCase12(String TC12_ProductNameToSearch) throws InterruptedException {
        boolean status = false;
    
        // Registration and Login
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Registration failed");
        
        String lastGeneratedUserName = registration.lastGeneratedUsername;
        
        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        assertTrue(status, "Login failed");
    
        // Home Page and Product Handling
        Home homePage = new Home(driver);
        homePage.navigateToHome();
        status = homePage.searchForProduct(TC12_ProductNameToSearch);
        assertTrue(status, "Search for product failed");
        homePage.addProductToCart(TC12_ProductNameToSearch);
        homePage.changeProductQuantityinCart(TC12_ProductNameToSearch, 1);
        homePage.clickCheckout();
    
        // Checkout Process
        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1  addr Line 2  addr line 3");
        checkoutPage.selectAddress("Addr line 1  addr Line 2  addr line 3");
        checkoutPage.placeOrder();
        Thread.sleep(3000);
    
        String currentURL = driver.getCurrentUrl();
    
        // Advertisement Handling
        List<WebElement> advertisements = driver.findElements(By.xpath("//iframe"));
        status = advertisements.size() == 3;
        assertTrue(status, "Exactly 3 ads with iframes weren't available");
    
        for (int i = 1; i <= 2; i++) {
            WebElement advertisement = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/iframe[" + i + "]"));
            driver.switchTo().frame(advertisement);
            driver.findElement(By.xpath("//button[text()='Buy Now']")).click();
            driver.switchTo().parentFrame();
            
            status = driver.getCurrentUrl().equals(currentURL);
            assertFalse(status, "Clicking on the 'Buy now' button in the ad should redirect the main page");
            driver.get(currentURL); // Return to the original URL
        }
    }
    

    private void assertTrue(Boolean status, String string) {}

    private void assertFalse(Boolean status, String string) {}

    @AfterSuite
    public static void quitDriver() {
        System.out.println("quit()");
        driver.quit();
    }
}
