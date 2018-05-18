package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by irinagavrilova on 5/17/18.
 */
public class ImageHelper extends YandexHelperBase {

  public ImageHelper (WebDriver driver) {super(driver);}
  public WebDriverWait wait = new WebDriverWait(driver, 10);

 /* public ArrayList<YI> getImageData() {
    //ArrayList<String> photos, ArrayList<YI> imageData
    ArrayList<String> photos = new ArrayList<>(); //url to all photos in the page
    ArrayList<YI> imageData = new ArrayList<>();
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
    return imageData;
  }*/
}

