package ee.ituk.api.common.filestorage;

import ee.ituk.api.common.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageConfig fileStorageConfig;

    public void uploadMentorPicture(String mentorId, MultipartFile file) {
        this.upload(file, this.fileStorageConfig.getMentorProfileLocation(), mentorId + ".png");
    }

    public Resource loadMentorPicture(String mentorId) {
        return this.load(this.fileStorageConfig.getMentorProfileLocation(), mentorId + ".png");
    }

    private void upload(MultipartFile file, Path path, String filename) {
        try {
            Path targetLocation = path.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.info(Arrays.toString(ex.getStackTrace()));
            throw new FileStorageException();
        }
    }

    public Resource load(Path path, String fileName) {
        try {
            Path filePath = path.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
            return null;
        } catch (MalformedURLException ex) {
            log.info(Arrays.toString(ex.getStackTrace()));
            throw new FileStorageException();
        }
    }
}
