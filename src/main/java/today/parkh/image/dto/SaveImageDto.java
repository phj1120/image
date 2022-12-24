package today.parkh.image.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SaveImageDto {
    private MultipartFile file;
    private String encodedImage;

    public SaveImageDto(MultipartFile file, String encodedImage) {
        this.file = file;
        this.encodedImage = encodedImage;
    }
}
