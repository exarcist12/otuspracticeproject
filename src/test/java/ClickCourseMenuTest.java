import annotations.Driver;
import components.MenuComponent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(UIExtension.class)
public class ClickCourseMenuTest {

  @Driver
  private WebDriver driver;

  @Test
  public void clickCourseMenuTest() throws PathEmptyException {

    String courseName = "Apache Kafka";
    MainPage mainPage = new MainPage(driver);
    mainPage.open();
    MenuComponent menuComponent = new MenuComponent(driver);
    CoursesData coursesData = menuComponent.filterCourseName(courseName);
    CoursePage coursePage = menuComponent.clickCourse(coursesData);
    menuComponent.checkTitlePage(coursePage, coursesData);
  }
}
