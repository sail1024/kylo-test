package com.stella.test.jcr.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * image controller.
 *
 * @author sail
 * @date 16:31 2019-11-11.
 * @since 1.0
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public Image uploadImage(@RequestParam("file") MultipartFile file){

        try {
            return imageService.addImage(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
