package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class FolderHelper extends YandexHelperBase {
  public FolderHelper(WebDriver driver) {
    super(driver);
  }

  public void createFolderTree(String folderName, String user) {
    File dir = new File("/Users/irinagavrilova/Downloads/Images/" + user + "/Temp1/" + folderName);
    if (!dir.exists()) {
      try {
        System.out.println("Folder " + folderName + " was created " + dir.mkdirs());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void getFolders(String folders, String user) {
    if (!folders.isEmpty()) {
      driver.get("https://fotki.yandex.ru/users/" + user + "/album/" + folders);
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(visibilityOfElementLocated(By.cssSelector("div.header-bar")));
      //div.information p

      if (driver.findElements(By.cssSelector("span.title")).size() > 0) {
        String result = driver.findElement(By.cssSelector("span.title")).getText().replace("«", "").replace("»", "");
        if ((result.substring(0, 7)).equals("Альбом ")) {
          result = result.substring(7);
        }
        createFolderTree(result, user);
      }
    }
  }
}