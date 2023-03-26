package com.kimsoar.fileserver.controller;

import com.kimsoar.fileserver.dto.ImageUploadResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@CrossOrigin
@Slf4j
public class FileController {

    private final String IMAGE_SERVER = "http://localhost:8080";
    private final String PATH = "D:/image/";

    @PostMapping("/upload")
    public ImageUploadResultDto uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString();

        int extractIndex = file.getOriginalFilename().lastIndexOf(".");
        String extractor = file.getOriginalFilename().substring(extractIndex);
        String saveFileName = uuid + extractor;

        log.info("============================================================");
        log.info(saveFileName);
        log.info("============================================================");

        File folder = new File(PATH);
        if (!folder.exists()) {
            folder.mkdir();
        }

        FileCopyUtils.copy(file.getBytes(), new File(PATH + saveFileName));

        String accessUrl = IMAGE_SERVER + "/image?name=" + saveFileName;

        ImageUploadResultDto imageUploadResultDto = new ImageUploadResultDto();
        imageUploadResultDto.setStatus(200);
        imageUploadResultDto.setImageAccessUrl(accessUrl);

        return imageUploadResultDto;

    }

    @GetMapping("/image")
    public ResponseEntity<Resource> downloadImage(HttpServletRequest request) {
        String imageName = request.getParameter("name");
        Resource resource = new FileSystemResource(PATH + imageName);

        HttpHeaders header = new HttpHeaders();
        try {
            Path filePath = Paths.get(PATH + imageName);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException io) {
            io.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

    }

}
