package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CoursePage extends AbsBasePage<CoursePage> {

  private final String titleTemplate = "h1";
  public CoursePage(WebDriver driver) {
    super(driver);
  }

  public String getPageTitle() {
    return driver.findElement(By.cssSelector(titleTemplate)).getAttribute("innerText");
  }
}
