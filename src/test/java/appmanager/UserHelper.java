package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class UserHelper extends YandexHelperBase {

  public UserHelper(WebDriver driver) {
    super(driver);
  }

  public ArrayList<String> getListOfAlbums(String user) {
    Select albums = new Select(driver.findElement(By.name("album_id")));
    ArrayList<String> albumId = new ArrayList<>();
    List<WebElement> listOptions = albums.getOptions();
    listOptions.forEach(e -> albumId.add(e.getAttribute("value")));
    return albumId;
  }

}


