import annotations.Driver;
import components.CoursesComponent;
import components.MenuComponent;
import data.Course;
import data.CoursesData;
import exceptions.PathEmptyException;
import extensions.UIExtension;
import listeners.MouseListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import pages.CoursePage;
import pages.MainPage;

import java.net.MalformedURLException;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@ExtendWith(UIExtension.class)
public class MaxValueTest {

  @Driver
  private WebDriver driver;

  @Test
  @DisplayName("Выбор курса с максимальной датой")
  public void maxValueTest() throws PathEmptyException, ParseException {
    MainPage mainPage = new MainPage(driver);
    mainPage.open();
    MenuComponent menuComponent = new MenuComponent(driver);
    List<Course> coursesWithDate = menuComponent.coursesWithDate();
    CoursesData coursesData = menuComponent.function(coursesWithDate, BinaryOperator.maxBy((p1,p2) -> p1.compareTo(p2))).getCoursesData();
    CoursePage coursePage = menuComponent.clickCourse(coursesData);
    menuComponent.checkTitlePage(coursePage, CoursesData.PHPDEVELOPER);
  }
}
