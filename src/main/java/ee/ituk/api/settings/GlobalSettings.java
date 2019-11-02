package ee.ituk.api.settings;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class GlobalSettings {

    @Id
    private String name;
    @NotNull
    private String value;

}
