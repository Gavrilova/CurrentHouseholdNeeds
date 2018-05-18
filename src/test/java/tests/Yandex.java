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

/**
 * Created by irinagavrilova on 5/3/18.
 */
public class Yandex extends TestBase {


  public void AllImages(String user) throws InterruptedException {
    AllImages(user, 0);
  }

  public void AllImages(String user, int numberPagesStart) throws InterruptedException {
    boolean anotherOne = true;
    int j = numberPagesStart;
    while (anotherOne) {
      //URL, for example could be https://fotki.yandex.ru/users/magrat67/?&p=0
      String hrmUrl = "https://fotki.yandex.ru/users/" + user + "/?&p=" + j;
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
    AllImages("alyona-merletto", 6);
  }

  @Test
  public void testLiubovBrajuk() throws InterruptedException {
    AllImages("liubov-brajuk", 303);
  }
}



