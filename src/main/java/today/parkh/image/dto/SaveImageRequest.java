package today.parkh.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SaveImageRequest {
    private MultipartFile file;
    private String encodedImage;

    public SaveImageDto convert() {
        return new SaveImageDto(this.file, this.encodedImage);
    }

}
