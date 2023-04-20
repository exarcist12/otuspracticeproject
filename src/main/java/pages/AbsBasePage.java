package pages;

import annotations.Path;
import exceptions.PathEmptyException;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage<T> {

  protected WebDriver driver;

  private String baseUrl = System.getProperty("webdriver.base.url", "https://otus.ru");

  public AbsBasePage(WebDriver driver){
    this.driver = driver;
  }

  private String getPath() throws PathEmptyException{
    Class clazz = this.getClass();

    if (clazz.isAnnotationPresent(Path.class)){
      Path path = (Path)clazz.getAnnotation(Path.class);
      return path.value();

    }

    throw new PathEmptyException();
  }

  @Step("Открытие главной страницы")
  public T open() throws PathEmptyException {
    String url = baseUrl + getPath();
    driver.get(url);

    return (T)this;
  }
}
