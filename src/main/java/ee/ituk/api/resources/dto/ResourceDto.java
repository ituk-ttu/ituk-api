package ee.ituk.api.resources.dto;

import lombok.Data;

@Data
public class ResourceDto {

    private Long id;
    private String name;
    private String comment;
    private String url;
    private Long authorId;
}
