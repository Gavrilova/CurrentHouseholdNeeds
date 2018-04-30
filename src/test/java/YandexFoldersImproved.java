import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.lang.System.currentTimeMillis;
import static java.util.Collections.sort;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by irinagavrilova on 4/29/18.
 * This script was created for saving images on macOS form fotki.yandex service which was demolished and transformed further to disk.yandex;
 */

public class YandexFoldersImproved {
  private ChromeDriver driver;

  public void createFolders(String folderName, String user) {
    File dir = new File("/Users/irinagavrilova/Downloads/Images/" + user + "/Temp/" + folderName);
    if (!dir.exists()) {
      try {
        System.out.println("Folder " + folderName + " was created " + dir.mkdirs());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

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

  public String getUrl(String st) {
    driver.get(st);
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(visibilityOfElementLocated(By.cssSelector("img.image.js-image")));
    List<WebElement> elementListList = driver.findElements(By.cssSelector("img.image.js-image"));
    ArrayList<String> urlList = new ArrayList<>();
    for (WebElement e : elementListList) {
      urlList.add(e.getAttribute("src"));
    }
    //String imageURL = driver.findElement(By.cssSelector("img.image.js-image")).getAttribute("src");
    String imageURL = maxSizeOfX(urlList);
    //System.out.println(imageURL);
    return imageURL;
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

  public YI getUrlAndAlbum(String st) {
    YI data = new YI();
    driver.get(st);
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(visibilityOfElementLocated(By.cssSelector("img.image.js-image")));

    List<WebElement> elementListList = driver.findElements(By.cssSelector("img.image.js-image"));
    ArrayList<String> urlList = new ArrayList<>();
    for (WebElement e : elementListList) {
      urlList.add(e.getAttribute("src"));
    }

    String imageURL = maxSizeOfX(urlList);
    //add empty URL, Name, Album case!!!
    data.withImageUrl(imageURL);

    String albumName = driver.findElement(By.cssSelector("a.js-album-link")).getText();
    data.withImageAlbum(albumName);

    String imageName = driver.findElement(By.cssSelector("div.photo-title__value")).getText();
    data.withImageName(getJPGname(imageName));

    return data;
  }

  public void AllImages(String user) throws InterruptedException {
    AllImages(user, 0);
  }

  public void AllImages(String user, int numberPagesStart) throws InterruptedException {
    boolean anotherOne = true;
    int j = numberPagesStart;
    while (anotherOne) {

      //URL, for example could be https://fotki.yandex.ru/users/magrat67/?&p=0
      String hrmUrl = "https://fotki.yandex.ru/users/" + user + "/?&p=" + j;

      ArrayList<String> photos = new ArrayList<>(); //url to all photos in the page
      ArrayList<YI> imageData = new ArrayList<>();

      WebDriverWait wait = new WebDriverWait(driver, 10);
      driver.get(hrmUrl);
      wait.until(visibilityOfElementLocated(By.cssSelector("a.photo img")));
      anotherOne = doWeHaveAnotherPageToSave();
      List<WebElement> photoLinks = driver.findElements(By.cssSelector("a.photo"));
      for (WebElement partialLink : photoLinks) {
        String link = partialLink.getAttribute("href");
        photos.add(link);
      }

      //photos.forEach(System.out::println);
      photos.forEach(e -> imageData.add(getUrlAndAlbum(e)));

      for (int i = 0; i < photos.size(); i++) {
        try {
          //reading url
          String url = imageData.get(i).getImageUrl();
          String albumName = imageData.get(i).getImageAlbum();
          if (albumName.isEmpty()) {
            albumName = "noAlbumName";
          }
          String name = imageData.get(i).getImageName();
          if (name.isEmpty()) {
            name = Long.toString(currentTimeMillis());
          }
          //need to assert file.exist() for saved image and created folder
          createFolders(albumName, user);
          String imgPath = "/Users/irinagavrilova/Downloads/Images/" + user + "/Temp/" + albumName + "/" + name;
          //read url and retrieve an image
          BufferedImage image = ImageIO.read(new URL(url));
          if (image != null) {
            //download image to your workspace where the project is, save picture as pic.jpg to the folder imgPath
            ImageIO.write(image, "jpeg", new File(imgPath));
          }

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      System.out.println("Page " + j + " was saved");
      j++;

      //assert that there is no next page
    }

  }


  @Before
  public void start() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    options.addArguments("--start-maximized");
    driver = new ChromeDriver(options);
  }


  //cool-arina14, 1612, 350 альбомов, 32237 фото
  @Test
  public void testImagesFoldersCoolArina14() throws InterruptedException {
    AllImages("cool-arina14", 223);
  }

/*  @Test
  public void testCreationFolder() throws InterruptedException {
    createFolders("NewFolderTemp", "cool-arina14");
  }*/

/*  @Test
  public void testNextPage() throws InterruptedException {
    int j = 1611;
    String user = "cool-arina14";
    String hrmUrl = "https://fotki.yandex.ru/users/" + user + "/?&p=" + j;


    WebDriverWait wait = new WebDriverWait(driver, 10);
    driver.get(hrmUrl);
    wait.until(visibilityOfElementLocated(By.cssSelector("a.photo img")));
    System.out.println("\n" + doWeHaveAnotherPageToSave());
  }*/
/*
  /////////////////////////////////////////////////////////////////////////////////
  Her photos was already moved to yandex disk. Script will not be valid for it.
  ///oksuporova, 24 альбома, 7797 фото.
  @Test
  public void testOksuporova() throws InterruptedException{
    AllImages("oksuporova", 390);
  }
*/


  //3 483 альбома, 108605 фото, alyona-merletto", 5431
/*  @Test
  public void testMAlyonaMerletto() throws InterruptedException {
    AllImages("alyona-merletto");
  }


  //302 альбома, 5169 фото, "mitrofan-alyabjev", 259
  @Test
  public void testMitrofanAlyabjev() throws InterruptedException {
    AllImages("mitrofan-alyabjev");
  }*/


  @After
  public void stop() {
    driver.quit();
    driver = null;
  }
}
