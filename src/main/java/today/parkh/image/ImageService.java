package today.parkh.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    @Value("${basePath}")
    private String basePath;

    public String saveImage(MultipartFile file) {
        if (!isImage(file)) {
            throw new IllegalArgumentException("이미지 파일 아님");
        }
        String storedName = generateStoredName(file);
        String imagePath = basePath + "/" + storedName;

        try {
            initFolder();
            FileSystemResource resource = new FileSystemResource(imagePath);
            resource.getOutputStream().write(file.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 저장 실패");
        }

        log.info("[Save] : {} -> {}", file.getOriginalFilename(), storedName);
        return storedName;
    }

    public Resource readImage(String storedName) {
        String imagePath = basePath + "/" + storedName;
        FileSystemResource file = new FileSystemResource(imagePath);

        log.info("[Read] : {}", storedName);
        return file;
    }

    private void initFolder() {
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private boolean isImage(MultipartFile file) {
        return !"image/jpeg".equals(file.getContentType());
    }

    private String generateStoredName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] data = originalFilename.split("[.]");
        String extension = data[data.length - 1];
        String storedName = UUID.randomUUID() + "." + extension;
        return storedName;
    }
}
