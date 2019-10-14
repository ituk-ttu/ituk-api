package ee.ituk.api.domain;


import lombok.Data;

import java.time.LocalDate;

@Data
public class RecoveryKey {

  private Long id;
  private String key;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long userId;
}
