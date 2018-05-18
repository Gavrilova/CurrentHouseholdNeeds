package appmanager;

import model.YI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.util.Collections.sort;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by irinagavrilova on 5/10/18.
 */
public class AlbumHelper extends YandexHelperBase {

  public AlbumHelper(WebDriver driver) {
    super(driver);
  }

  public WebDriverWait wait = new WebDriverWait(driver, 10);

  public String maxSizeOfX(ArrayList<String> arrayList) {
    int max = 0;
    char charX = 'X';
    String st = arrayList.get(0);
    for (String e : arrayList) {
      //need to find how many X chars in url String;
      int xCount = 0;
      for (int i = 0; i < e.length(); i++) {
        if (e.charAt(i) == charX) {
          xCount++;
        }
      }
      if (xCount > max) {
        max = xCount;
        st = e;
      }
    }
    return st;
  }


  public String getJPGname(String altName) {
    //get max resolution image from all its links
    String finalName = altName;
    char charX = '.';
    int dotCount = 0;
    for (int i = 0; i < altName.length(); i++) {
      if (altName.charAt(i) == charX) {
        dotCount++;
      }
    }
    if (dotCount == 0) {
      finalName = altName + ".jpg";
    }
    return finalName;
  }


  public YI getUrlAndAlbum(String st) {
    YI data = new YI();
    driver.get(st);
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(presenceOfElementLocated(By.cssSelector("img.image.js-image")));

    List<WebElement> elementListList = driver.findElements(By.cssSelector("img.image.js-image"));
    ArrayList<String> urlList = new ArrayList<>();
    for (WebElement e : elementListList) {
      urlList.add(e.getAttribute("src"));
    }

    String imageURL = maxSizeOfX(urlList);
    data.withImageUrl(imageURL);
    String imageName = driver.findElement(By.cssSelector("div.photo-title__value")).getText();
    data.withImageName(getJPGname(imageName));
    String albumName = driver.findElement(By.cssSelector("a.js-album-link")).getText();
    data.withImageAlbum(albumName);
    String parentAlbumLink = driver.findElement(By.cssSelector("a.js-album-link")).getAttribute("href");
    data.withImageParentAlbumLink(parentAlbumLink);
    return data;
  }


  public boolean doWeHaveAnotherPageToSave() {
    Boolean bool;
    ArrayList<Integer> pages = new ArrayList<>();

    driver.findElements(By.cssSelector("a.b-pager__page"))
            .forEach(e -> pages.add(valueOf(e.getText().replace("…", "0"))));
    sort(pages);
    if (pages.get(pages.size() - 1) > (valueOf(driver.findElement(By.cssSelector("b.b-pager__current")).getText()) - 1)) {
      bool = true;
    } else {
      bool = false;
      System.out.println("There is no other page to save");
    }
    return bool;
  }


  public String getParentAlbumString(YI element, WebDriverWait wait) {
    driver.get(element.getImageParentAlbumLink());
    wait.until(presenceOfElementLocated(By.cssSelector("span.title")));
    String result = driver.findElement(By.cssSelector("span.title")).getText().replace("«", "").replace("»", "");
    if ((result.substring(0, 7)).equals("Альбом ")) {
      result = result.substring(7);
    }
    return result;
  }

  public String getUrl(String st) {
    driver.get(st);
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(presenceOfElementLocated(By.cssSelector("img.image.js-image")));
    List<WebElement> elementListList = driver.findElements(By.cssSelector("img.image.js-image"));
    ArrayList<String> urlList = new ArrayList<>();
    for (WebElement e : elementListList) {
      urlList.add(e.getAttribute("src"));
    }
    String imageURL = maxSizeOfX(urlList);
    return imageURL;
  }


  public ArrayList<YI> getImageData(ArrayList<String> photos) {
    ArrayList<YI> imageData = new ArrayList<>();
    photos.forEach(e -> imageData.add(getUrlAndAlbum(e)));

    ArrayList<String> parentAlbumTemp = new ArrayList<>();
    imageData.forEach(e -> parentAlbumTemp.add(e.getImageParentAlbumLink()));
    imageData.get(0).withImageParentAlbumLink(getParentAlbumString(imageData.get(0), wait));

    for (int i = 1; i < imageData.size(); i++) {
      if (imageData.get(i).getImageParentAlbumLink().equals(parentAlbumTemp.get(i - 1))) {
        imageData.get(i).withImageParentAlbumLink(imageData.get(i - 1).getImageParentAlbumLink());
      } else {
        imageData.get(i).withImageParentAlbumLink(getParentAlbumString(imageData.get(i), wait));
      }
    }
    return imageData;
  }

  public ArrayList<String> getPhotos() {
    ArrayList<String> photos = new ArrayList<>(); //url to all photos in the page
    List<WebElement> photoLinks = driver.findElements(By.cssSelector("a.photo"));
    for (WebElement partialLink : photoLinks) {
      String link = partialLink.getAttribute("href");
      photos.add(link);
    }
    return photos;
  }
}
