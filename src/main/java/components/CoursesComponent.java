package components;

import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CoursesComponent extends AbsBaseComponent<CoursesComponent> {
  public CoursesComponent(WebDriver driver) {
    super(driver);
  }


  public String getDateString(String dateString){
    List<String> listString= new ArrayList<>(Arrays.asList(dateString.split(" ")));
    if (listString.get(0).equals("")){
      listString.remove(0);
    }

    if (listString.get(0).equals("С")){
      listString.remove(0);
    }

    if (listString.size()>2){
      int i = 0;
      while ((i<5) && (listString.size()>3)) {
        i++;
        listString.remove(3);
      }

      if (listString.get(0).equals("В")){
        listString.set(0, "1");
      }

      for(int j = 3; j<listString.size(); j++){
        listString.remove(j);
      }

      if ((!(listString.get(2).equals("старта")))&&(Integer.valueOf(listString.get(2)) < 2022)) {
        listString.remove(2);
        listString.add("2023");
      }

      if (listString.get(1).equals("октябре")){
        listString.set(1, "октября");
      }

      if (listString.get(1).equals("сентябре")){
        listString.set(1, "сентября");
      }

      if (listString.get(2).equals("старта")){
        listString.set(0, "1");
        listString.set(1, "декабря");
      }
    }

    if ((listString.size()<3)) {
      listString.add("2023");
    } else if (listString.get(2).equals("старта")){
      listString.set(2, "2023");
    }

    String date = String.join(" ", listString);
    return date;
  }

  public LocalDate getDate(String dateString) throws ParseException {
    Date date;
    LocalDate localDate;
    try {
      DateFormat format = new SimpleDateFormat("dd MMMM yyyy", new Locale("ru"));
      date = format.parse(dateString);
      localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }
    catch(ParseException e) {
      DateFormat format = new SimpleDateFormat("dd MMMM yyyy", new Locale("en"));
      date = format.parse(dateString);
      localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    return localDate;
  }
}
