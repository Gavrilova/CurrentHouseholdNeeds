
/**
 * Created by irinagavrilova on 4/28/18.
 */
public class YI {

  private String imageName;
  private String imageAlbum;
  private String imageUrl;


  public YI withImageName(String imageName) {
    this.imageName = imageName;
    return this;
  }

  public YI withImageAlbum(String imageAlbum) {
    this.imageAlbum = imageAlbum;
    return this;
  }

  public YI withImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public String getImageName() {
    return imageName;
  }

  public String getImageAlbum() {
    return imageAlbum;
  }


  public String getImageUrl() {
    return imageUrl;
  }
}
