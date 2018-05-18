package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class ApplicationManager {

  WebDriver driver;
  WebDriverWait wait;
  //ChromeDriver driver;

  private FileHelper fileHelper;
  private FolderHelper folderHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private UserHelper userHelper;
  private AlbumHelper albumHelper;
  private ImageHelper imageHelper;

  public void start() {

    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    options.addArguments("--start-maximized");
    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, 10);
    fileHelper = new FileHelper(driver);
    folderHelper = new FolderHelper(driver);
    navigationHelper = new NavigationHelper(driver);
    sessionHelper = new SessionHelper(driver);
    userHelper = new UserHelper(driver);
    albumHelper = new AlbumHelper(driver);
    imageHelper = new ImageHelper(driver);
  }

  public void stop() {
    driver.quit();
    driver = null;
  }

  public FolderHelper folder() {
    return folderHelper;
  }

  public FileHelper file() {
    return fileHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public SessionHelper signIn() {
    return sessionHelper;
  }

  public UserHelper user() {
    return userHelper;
  }

  public AlbumHelper album() {return albumHelper;}

  public ImageHelper image() {return imageHelper;}
}
