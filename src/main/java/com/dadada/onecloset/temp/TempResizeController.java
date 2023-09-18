package com.dadada.onecloset.temp;

import com.dadada.onecloset.global.S3Service;
import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TempResizeController {

    private final S3Service s3Service;

    @PostMapping("/api/resize")
    public ResizeResponseDto getImgAndResize(@RequestParam(value = "img") MultipartFile file) throws IOException {

        String url = s3Service.upload(file);
        String fileFormatName = Objects.requireNonNull(file.getContentType()).substring(file.getContentType().lastIndexOf("/") + 1);
        MultipartFile resizeImg = resizeAttachment(file.getName(), fileFormatName, file);
        String thumnailUrl = s3Service.upload(resizeImg);


        System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());

        System.out.println(resizeImg.getContentType());
        System.out.println(resizeImg.getSize());
        System.out.println(resizeImg.getName());
        System.out.println(resizeImg.getOriginalFilename());


        return new ResizeResponseDto(url, thumnailUrl);
//        return new ResizeResponseDto("2", "1");
    }

    private MultipartFile resizeAttachment(String fileName, String fileFormatName, MultipartFile multipartFile) {
        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());

            // 원하는 px로 Width와 Height 수정
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
            if (originWidth < 500 && originHeight < 500)
                return multipartFile;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", 500);
            scale.setAttribute("newHeight", 500);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();
            fileName = fileName + "." + fileFormatName;

            return new MockMultipartFile(fileName, fileName, multipartFile.getContentType(), baos.toByteArray());

        } catch (IOException e) {
            // 파일 리사이징 실패시 예외 처리
            System.out.println(e);
        }
        return multipartFile;
    }

}
