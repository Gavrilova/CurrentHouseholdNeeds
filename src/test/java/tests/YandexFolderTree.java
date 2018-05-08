package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * Created by irinagavrilova on 5/4/18.
 */
public class YandexFolderTree extends TestBase {

  private ChromeDriver driver;

  public void tree(String user) {

    app.goTo().gotoYandexMainPage();
    app.goTo().gotoPage((By.cssSelector("div a.button")), "div.passport-Domik");
    app.signIn().loginYandex("myYandexUserName", "myYandexPassword");
    String url = "https://m.fotki.yandex.ru/users/" + user + "/";
    app.goTo().gotoPage(url, By.name("album_id"));
    app.user().getListOfAlbums(user).forEach(e -> app.folder().getFolders(e, user));

  }

  @Test
  public void testCreatingAlbumTreeFolders() {
    app.goTo().gotoYandexMainPage();
    tree("nadezda-novitsenkova");
  }

  @Test
  public void testCreatingAlbumTreeFoldersLiubovBrajuk() {
    app.goTo().gotoYandexMainPage();
    tree("liubov-brajuk");
  }
}