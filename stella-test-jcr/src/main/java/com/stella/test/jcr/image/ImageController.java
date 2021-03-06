package com.stella.test.jcr.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    public Image uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String name){

        try {
            return imageService.addImage(name, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    @GetMapping("/list")
    public List<Image> queryImage(String name){
        return imageService.listImage(name);
    }
}
