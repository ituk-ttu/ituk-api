package ee.ituk.api.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Resource {

  private Long id;
  private String name;
  private String comment;
  private String url;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long authorId;
}
