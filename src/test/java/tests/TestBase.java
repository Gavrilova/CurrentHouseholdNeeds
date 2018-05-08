package tests;

import appmanager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Created by irinagavrilova on 5/7/18.
 */
public class TestBase {
  protected final ApplicationManager app = new ApplicationManager();

  @BeforeMethod
  public void setUp() throws Exception {
    app.start();
  }

  @AfterMethod
  public void tearDown() {
    app.stop();
  }
}
