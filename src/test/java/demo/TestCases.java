package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v118.domstorage.model.Item;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {

    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void init() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // @AfterSuite
    // public void endTest() {
    // System.out.println("End Test: TestCases");
    // if (driver != null) {
    // driver.close();
    // driver.quit();
    // }
    // }

    @Test(priority = 0)
    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        driver.get("http://www.flipkart.com");
        dismissLoginPopup();
    }

    @Test(priority = 1)
    public void searchWashingMachine() {
        WebElement searchBox = waitForElement(By.name("q"));
        sendKeys(searchBox, "Washing Machine");
        searchBox.submit();
    }

    @Test(priority = 2)
    public void sortByPopularity() {
        WebElement sortBy = waitForElement(By.xpath("//span[contains(text(), 'Sort By')]"));
        click(sortBy);
        WebElement sortByPopularity = waitForElement(By.xpath("//div[contains(text(), 'Popularity')]"));
        click(sortByPopularity);
    }

    @Test(priority = 3)
    public void countItemsWithLowRatings() throws InterruptedException {
        waitForElement(By.xpath("//div[@class='XQDdHH']"));

        Thread.sleep(2000);

        List<WebElement> items = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='XQDdHH']")));

        int count = 0;
        for (WebElement item : items) {
                //WebElement ratingElement=item.findElement(By.xpath("//div[@class='XQDdHH']"));
            double rating = Double.parseDouble(item.getText());
            if (rating <= 4.0) {
                count++;
            }
        
    }
        System.out.println("Count of items with rating less than or equal to 4 stars: " + count);
        // Assert.assertTrue(count > 0, "No items found with rating less than or equal
        // to 4 stars.");
    }
    @Test(priority = 4)
    public void testCase02() {
        System.out.println("Start Test case: testCase01");
        driver.get("http://www.flipkart.com");
    }

    @Test(priority = 5)
    public void searchIphone() throws InterruptedException {
        WebElement searchBox = waitForElement(By.name("q"));
        
        Thread.sleep(3000);
        sendKeys(searchBox, "iPhone");
        searchBox.submit();
    }

    @Test(priority = 6)
    public void printIphoneDetailsWithDiscount() throws InterruptedException {
        //waitForElement(By.xpath("//div[@class='KzDlHZ']"));
        Thread.sleep(2000);
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='tUxRFH']"));
        int count=0;
        for (WebElement item : items) {
            try {

                // Find the title element within the current item
            WebElement titleElement = item.findElement(By.xpath(".//div[@class='KzDlHZ']"));

            // Find the discount element within the current item
            WebElement discountElement = item.findElement(By.xpath(".//div[@class='UkUFwK']"));

            // Extract and clean the discount text
            String discountText = discountElement.getText().replaceAll("[^0-9]", "");

            if (!discountText.isEmpty()) {
                int discount = Integer.parseInt(discountText);

                // Check if the discount is greater than 17%
                if (discount > 17) {
                    String title = titleElement.getText();
                    System.out.println("Title: " + title + ", Discount: " + discount + "%");
                    count++;
                }
            }
        } catch (Exception e) {
            // Handle cases where elements are not found within an item
            continue;
        }
    }
    System.out.println("Total count of items with discount greater than 17%: " + count);
                
}     

@Test(priority = 7)
    public void testCase03() {
        System.out.println("Start Test case: testCase01");
        driver.get("http://www.flipkart.com");
    }


    @Test(priority = 8)
    public void searchCoffeeMug() throws InterruptedException {
        WebElement searchBox = waitForElement(By.name("q"));
        searchBox.sendKeys("Coffee Mug");
        searchBox.submit();
        Thread.sleep(2000); // Wait for search results to load
    }
    @Test(priority = 9)
    public void filterBy4StarsAndAbove() throws InterruptedException {
        WebElement fourStarFilter = waitForElement(By.xpath("//div[contains(text(),'4★ & above')]"));
        fourStarFilter.click();
        Thread.sleep(2000); // Wait for the filter to be applied
    }
    @Test(priority = 10)
    public void printTop5HighestReviewedItems() throws InterruptedException {
        //waitForElement(By.xpath("//div[@class='_5OesEi afFzxY']"));

        Thread.sleep(3000);

        

        List<WebElement> items = driver.findElements(By.xpath("//div[@class='_5OesEi afFzxY']/span/div"));
        List<Item> itemList = new ArrayList<>();
        for (WebElement item : items) {
            try {

                WebElement titleElement = item.findElement(By.xpath(".//a[@class='wjcEIp']"));
                WebElement reviewsElement = item.findElement(By.xpath(".//span[@class='Wphh3N']"));
                WebElement imageElement = item.findElement(By.xpath(".//img[@class='DByuf4']"));

                String title = titleElement.getText();
                String reviewsText = reviewsElement.getText().split(" ")[0].replaceAll(",", "");
                int reviews = Integer.parseInt(reviewsText);
                String imageUrl = imageElement.getAttribute("src");
                itemList.add(new Item(title,reviews,imageUrl));
                

            } catch (Exception e) {
                // Skip the item if any element not found
            }
        }

        // Sort items by the number of reviews in descending order
        itemList.sort(Comparator.comparingInt(Item::getReviews).reversed());

        // Print the top 5 items
        itemList.stream().limit(5).forEach(item -> 
            System.out.println("Title: " + item.getTitle() + ", Image URL: " + item.getImageUrl()));
    }

        


    @AfterClass
    public void tearDown() {
        System.out.println("End Test case: tearDown");
        if (driver != null) {
            driver.quit();
        }
    }


    //Items class


    // Inner class to hold item details
    class Item {
        private String title;
        private int reviews;
        private String imageUrl;

        public Item(String title, int reviews, String imageUrl) {
            this.title = title;
            this.reviews = reviews;
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public int getReviews() {
            return reviews;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }


    // Wrapper methods

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    private void sendKeys(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    private void dismissLoginPopup() {
        try {
            WebElement closeButton = waitForElement(By.cssSelector("button._2KpZ6l._2doB4z"));
            click(closeButton);
        } catch (Exception e) {
            // Popup didn't appear
            System.out.println("Login popup not displayed.");
        }
    }
}