package today.parkh.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import today.parkh.image.dto.SaveImageRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    // 이미지 저장
    @PostMapping
    public String saveImage(SaveImageRequest request) {
        String imageUri = imageService.saveImage(request.convert());

        return imageUri;
    }

    // 이미지 조회
    @GetMapping(value = "/{storedName}",
            produces = {MediaType.IMAGE_JPEG_VALUE})
    public Resource getImage(@PathVariable("storedName") String storedName) {
        Resource file = imageService.readImage(storedName);

        return file;
    }
}
