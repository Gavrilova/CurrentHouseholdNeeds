package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class NavigationHelper extends YandexHelperBase {

  public NavigationHelper(WebDriver driver) {
    super(driver);
  }


  public WebDriverWait wait = new WebDriverWait(driver, 10);

  public void gotoYandexMainPage() {
    driver.get("https://www.yandex.ru/");
    wait.until(visibilityOfElementLocated(By.cssSelector("div a.button")));
  }

  public void gotoPage(By locator) {
    click(locator);
  }

  public void gotoPage(By locatorToClick, String locatorToPresent) {
    click(locatorToClick);
    wait.until(presenceOfElementLocated(By.cssSelector(locatorToPresent)));
  }

  public void gotoPage(String url, By locator) {
    driver.navigate().to(url);
    wait.until(presenceOfElementLocated(locator));
    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

}
