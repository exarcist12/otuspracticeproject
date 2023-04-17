package data;

import java.time.LocalDate;
import java.util.Date;

public class Course {
  LocalDate date;
  CoursesData coursesData;

  public Course(){
    this.date = date;
    this.coursesData = coursesData;
  }

  public LocalDate getDate() {
    return date;
  }

  public CoursesData getCoursesData() {
    return coursesData;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setCoursesData(CoursesData coursesData) {
    this.coursesData = coursesData;
  }
}
