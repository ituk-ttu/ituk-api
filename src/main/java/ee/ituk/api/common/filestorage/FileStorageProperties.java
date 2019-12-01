package ee.ituk.api.common.filestorage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "file-storage")
public class FileStorageProperties {

    @Value("$base-dir")
    private String baseDir;
    @Value("$mentor-profile-dir")
    private String mentorProfileDir;

}
