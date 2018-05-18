package tests;

import model.YI;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

//import org.junit.Test;

/**
 * Created by irinagavrilova on 5/3/18.
 */
public class Yandex extends TestBase {


 // private ChromeDriver driver;

/*  public String maxSizeOfX(ArrayList<String> arrayList) {
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
  }*/

  public void AllImages(String user) throws InterruptedException {
    AllImages(user, 0);
  }

  public void AllImages(String user, int numberPagesStart) throws InterruptedException {
    boolean anotherOne = true;
    int j = numberPagesStart;
    while (anotherOne) {

      //URL, for example could be https://fotki.yandex.ru/users/magrat67/?&p=0
      String hrmUrl = "https://fotki.yandex.ru/users/" + user + "/?&p=" + j;

   /*   ArrayList<String> photos = new ArrayList<>(); //url to all photos in the page
      ArrayList<YI> imageData = new ArrayList<>();*/

/*    WebDriverWait wait = new WebDriverWait(driver, 10);
      driver.get(hrmUrl);
      wait.until(presenceOfElementLocated(By.cssSelector("a.photo img")));*/
      app.goTo().gotoPage(hrmUrl, (By.cssSelector("a.photo img")));
      ArrayList<String> photos = app.album().getPhotos();
      anotherOne = app.album().doWeHaveAnotherPageToSave();

      ArrayList<YI> imageData = app.album().getImageData(photos);


      for (int i = 0; i < photos.size(); i++) {
        try {
          //reading url
          String url = imageData.get(i).getImageUrl();
          String albumName = imageData.get(i).getImageParentAlbumLink();
          if (albumName.isEmpty()) {
            albumName = "noAlbumName";
          }
          String name = imageData.get(i).getImageName();
          if (name.isEmpty()) {
            name = Long.toString(currentTimeMillis());
          }
          //assert file.exist() for saved images and created folders
          app.folder().createFolderTree(albumName, user);
          String imgPath = "/Users/irinagavrilova/Downloads/Images/" + user + "/Temp1/" + albumName + "/" + name;
          if ((new File(imgPath)).exists()) {
            imgPath = "/Users/irinagavrilova/Downloads/Images/" + user + "/Temp1/" + albumName + "/" + Long.toString(currentTimeMillis()) + name;
          }
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
    }

  }

/*  private void getImageData(ArrayList<String> photos, ArrayList<YI> imageData) {
    List<WebElement> photoLinks = driver.findElements(By.cssSelector("a.photo"));
    for (WebElement partialLink : photoLinks) {
      String link = partialLink.getAttribute("href");
      photos.add(link);
    }

    photos.forEach(e -> imageData.add(app.album().getUrlAndAlbum(e)));

    ArrayList<String> parentAlbumTemp = new ArrayList<>();
    imageData.forEach(e -> parentAlbumTemp.add(e.getImageParentAlbumLink()));

    imageData.get(0).withImageParentAlbumLink(app.album().getParentAlbumString(imageData.get(0), wait));

    for (int i = 1; i < imageData.size(); i++) {
      if (imageData.get(i).getImageParentAlbumLink().equals(parentAlbumTemp.get(i - 1))) {
        imageData.get(i).withImageParentAlbumLink(imageData.get(i - 1).getImageParentAlbumLink());
      } else {
        imageData.get(i).withImageParentAlbumLink(app.album().getParentAlbumString(imageData.get(i), wait));
      }
    }
  }*/

/*  public String getParentAlbumString(YI element, WebDriverWait wait) {
    driver.get(element.getImageParentAlbumLink());
    wait.until(presenceOfElementLocated(By.cssSelector("span.title")));
    String result = driver.findElement(By.cssSelector("span.title")).getText().replace("«", "").replace("»", "");
    if ((result.substring(0, 7)).equals("Альбом ")) {
      result = result.substring(7);
    }
    return result;
  }*/

  @Test
  public void testsnezinka11() throws InterruptedException {
    AllImages("snezinka11", 145);
  }

  @Test
  public void testnataButencko2010() throws InterruptedException {
    AllImages("nata-butencko2010");
  }

  @Test
  public void testolgavadimov() throws InterruptedException {
    AllImages("olgavadimov", 6);
  }

  @Test
  public void testAlyonaMerletto() throws InterruptedException {
    AllImages("alyona-merletto");
  }

  @Test
  public void testLiubovBrajuk() throws InterruptedException {
    AllImages("liubov-brajuk", 303);
    //253
  }
}



