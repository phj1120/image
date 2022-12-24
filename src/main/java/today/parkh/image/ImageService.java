package today.parkh.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import today.parkh.image.dto.SaveImageDto;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    @Value("${basePath}")
    private String basePath;

    public String saveImage(SaveImageDto dto) {
        MultipartFile file = dto.getFile();
        String encodedImage = dto.getEncodedImage();
        if (file != null) {
            return saveImage(file);
        }
        if (encodedImage != null) {
            return saveImage(encodedImage);
        }

        throw new IllegalArgumentException("인자가 부족합니다");
    }

    private String saveImage(MultipartFile file) {
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

    private String saveImage(String encodedImage) {
        String storedName = UUID.randomUUID() + ".jpg";
        String imagePath = basePath + "/" + storedName;

        try {
            initFolder();
            FileSystemResource resource = new FileSystemResource(imagePath);
            resource.getOutputStream().write(Base64.getDecoder().decode(encodedImage.getBytes()));
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 저장 실패");
        }

        log.info("[Save] : byte -> {}", storedName);
        return storedName;
    }

    private void initFolder() {
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    private String generateStoredName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] data = originalFilename.split("[.]");
        String extension = data[data.length - 1];
        String storedName = UUID.randomUUID() + "." + extension;
        return storedName;
    }
}
