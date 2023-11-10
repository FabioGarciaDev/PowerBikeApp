package com.PowerBike.utils.moreUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    //@Value("$path.image-products")
    private String folder = "src/main/resources/images//";

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] fileBytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, fileBytes);
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    public void deleteImage(String fileName) {
        File file = new File(folder+fileName);
        file.delete();
    }


}
