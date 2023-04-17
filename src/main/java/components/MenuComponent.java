package components;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import data.CategoryData;
import data.Course;
import data.CoursesData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.CategoryPage;
import pages.CoursePage;
import pages.MainPage;
import waiters.StandartWaiter;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class MenuComponent extends AbsBaseComponent<MenuComponent> {

  public MenuComponent(WebDriver driver) {
    super(driver);
  }
  CoursesComponent coursesComponent = new CoursesComponent(driver);
  StandartWaiter standartWaiter = new StandartWaiter(driver);
  private final String menuItemByTitleSelectorTemplate = "#categories_id a[title=\"%s\"]";
  private final String menuCourseSelectorTemplate = "//div[contains(., '%s') and contains(@class, 'lessons__new-item-title')]";
  private final String catalogCategoriesCheckboxesSelectorTemplate = "//label[text()=\"%s\"]/..//div/input[@checked]";
  private final String logo = ".header3__logo-img";
  private final String buttonCoockiesTemplate = "button.cookies__button";
  private final String titleCourseTemplate = "div.lessons__new-item-title";
  private final String courseTemplate = "div.lessons__new-item-container";
  private final String dateStartTemplate = "div.lessons__new-item-start";
  private final String timeTemplate = "div.lessons__new-item-time";

  public CategoryPage clickCategory(CategoryData categoryData) {

    String selector = String.format(menuItemByTitleSelectorTemplate, categoryData.getName());
    driver.findElement(By.cssSelector(selector)).click();

    return new CategoryPage(driver);
  }

  public MenuComponent menuItemActive(CategoryData categoryData) {

    String selector = String.format(catalogCategoriesCheckboxesSelectorTemplate, categoryData.getName());
    WebElement element = driver.findElement(By.xpath(selector));
    List<WebElement> elements = driver.findElements(By.xpath(selector));
    assertThat("error", standartWaiter.waitForElementVisible(element), equalTo(true));

    return this;
  }

  public List<String> getCoursesStringFromMainPage() {

    List<WebElement> listTitleCourses = driver.findElements((By.cssSelector(titleCourseTemplate)));
    ArrayList<String> titleCourses = new ArrayList<>();
    String title;
    for(WebElement element : listTitleCourses) {
      title = element.getText();
      titleCourses.add(title);
    }

    return titleCourses;
  }

  public List<CoursesData> getCoursesDataFromMainPage(List<String> list) {

    List<CoursesData>  listEnums = Arrays.asList(CoursesData.values());

    List<CoursesData> newList = listEnums.stream().filter(s ->
            list.contains(s.getName())
      ).collect(Collectors.toList());

    return newList;
  }

  public CoursesData filterCourseName(String name) {

    List<String> courseString =  getCoursesStringFromMainPage();
    List<CoursesData> coursesData = getCoursesDataFromMainPage(courseString);
    List<CoursesData> coursesData2 = coursesData.stream().filter(s -> s.getName() == name).collect(Collectors.toList());

    return coursesData2.get(0);
  }


  public CoursePage clickCourse(CoursesData coursesData) {

    String selector = String.format(menuCourseSelectorTemplate, coursesData.getName());
    WebElement element = driver.findElement(By.xpath(selector));
    List<WebElement> buttonCoockies = driver.findElements(By.cssSelector(buttonCoockiesTemplate));
    if (buttonCoockies.size()>0){
      buttonCoockies.get(0).click();
    }
    new Actions(driver).moveToElement(element).click().perform();

    return new CoursePage(driver);
  }

  public CoursesComponent checkTitlePage(CoursePage coursePage, CoursesData coursesData) {


    assertThat("Тайтл не совпал", coursePage.getPageTitle(), equalTo(coursesData.getPageTitle()));

    return new CoursesComponent(driver);
  }


  public MainPage clickMainPage() {

    String selector = String.format(logo);
    driver.findElement(By.cssSelector(selector)).click();

    return new MainPage(driver);
  }

  public ArrayList<Course> coursesWithDate() throws ParseException {
    List<WebElement> elementsCourse = driver.findElements(By.cssSelector(courseTemplate));
    ArrayList<Course> coursesWithDate = new ArrayList<>();
    Course courseWithDate = new Course();
    CoursesData courseDataData = null;
    List<CoursesData> coursesDataList = Arrays.asList(CoursesData.values());


    for (WebElement elementCourse: elementsCourse){
      WebElement elementName = elementCourse.findElement(By.cssSelector(titleCourseTemplate));
      String nameCourse = elementName.getText();

      courseDataData = coursesDataList.stream().filter(p -> (p.getName().equals(nameCourse))).findFirst().get();

      List<WebElement> elementsDate = elementCourse.findElements(By.cssSelector(dateStartTemplate));
      if (elementsDate.size()==0){
        elementsDate = elementCourse.findElements(By.cssSelector(timeTemplate));
      }

      String dateString = elementsDate.get(0).getAttribute("innerText");

      LocalDate date = coursesComponent.getDate(coursesComponent.getDateString(dateString));

      courseWithDate.setCoursesData(courseDataData);
      courseWithDate.setDate(date);
      coursesWithDate.add(courseWithDate);
      courseWithDate = new Course();
    }

    return coursesWithDate;
  }


  public Course function(List<Course> courses, BinaryOperator<LocalDate> accumulator){

    List<LocalDate> dates = courses.stream().map(p1 -> p1.getDate()).collect(Collectors.toList());

    LocalDate date =dates.stream().reduce(accumulator).get();

    Course course = courses.stream().filter(p1 -> p1.getDate() == date).findFirst().get();

    return course;
  }
}
