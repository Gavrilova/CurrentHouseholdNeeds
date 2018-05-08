package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class SessionHelper extends YandexHelperBase {

  public SessionHelper(WebDriver driver) {
    super(driver);
  }

  public WebDriverWait wait = new WebDriverWait(driver, 10);


  public void loginYandex(String username, String password) {
    type(By.name("login"), username);
    type(By.name("passwd"), password);
    click(By.className("passport-Button-Text"));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.mail-Logo-Pancakes")));
  }
}
