package today.parkh.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 이미지 저장
    @PostMapping
    public String saveImage(@RequestParam MultipartFile file) {
        String storedName = imageService.saveImage(file);

        return storedName;
    }

    // 이미지 조회
    @GetMapping(value = "/{storedName}",
            produces = {MediaType.IMAGE_JPEG_VALUE})
    public Resource getImage(@PathVariable("storedName") String storedName) {
        Resource file = imageService.readImage(storedName);

        return file;
    }
}
