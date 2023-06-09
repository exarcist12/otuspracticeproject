import annotations.Driver;
import components.MenuComponent;
import data.Course;
import data.CoursesData;
import exceptions.PathEmptyException;
import extensions.UIExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.CoursePage;
import pages.MainPage;

import java.net.MalformedURLException;
import java.net.URI;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

@ExtendWith(UIExtension.class)
public class MinValueTest {

  @Driver
  private WebDriver driver;
  @BeforeEach
  void setupTest() throws MalformedURLException {
    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);
    selenoidOptions.put("enableVideo", true);
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("browserName", "chrome");
    capabilities.setCapability("browserVersion", "109.0");
    capabilities.setCapability("selenoid:options", selenoidOptions);
    driver = new RemoteWebDriver(
      URI.create("http://127.0.0.1:8080/wd/hub").toURL(),
      capabilities
    );
  }
  //@Test
  public void minValueTest() throws PathEmptyException, ParseException {
    MainPage mainPage = new MainPage(driver);
    mainPage.open();
    MenuComponent menuComponent = new MenuComponent(driver);
    List<Course> coursesWithDate = menuComponent.coursesWithDate();
    CoursesData coursesData = menuComponent.function(coursesWithDate, BinaryOperator.minBy((p1, p2) -> p1.compareTo(p2))).getCoursesData();
    CoursePage coursePage = menuComponent.clickCourse(coursesData);
    menuComponent.checkTitlePage(coursePage, CoursesData.MACHINELEARNING);
  }
}
