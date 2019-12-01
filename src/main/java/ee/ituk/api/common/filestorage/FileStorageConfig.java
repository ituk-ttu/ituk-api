package ee.ituk.api.common.filestorage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@Getter
@Configuration
public class FileStorageConfig {

    private Path mentorProfileLocation;

    @Autowired
    public FileStorageConfig(FileStorageProperties properties) {
        this.mentorProfileLocation = getPath(properties.getBaseDir(), properties.getMentorProfileDir());
        try {
            if (!Files.exists(this.mentorProfileLocation)) {
                Files.createDirectories(this.mentorProfileLocation);
            }
        } catch (Exception ex) {
            log.info("Storage directories creation failed");
            log.info(Arrays.toString(ex.getStackTrace()));
        }
    }

    private static Path getPath(String basePath, String path) {
        return Paths.get(basePath, path).toAbsolutePath().normalize();
    }
}
