package com.PowerBike.utils.moreUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService {

    //@Value("$path.image-products")
    private String folder = "src/main/resources/static/productImages/";

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString();
            String fileExtension = StringUtils.getFilenameExtension(originalFileName);
            String newFileName = uniqueFileName + "." + fileExtension;
            byte[] fileBytes = file.getBytes();
            Path path = Paths.get(folder + newFileName);

            //Si la carpeta no esta creada la crea
            createDirFolder();

            //Guardo la imagen si no existe
            if (!Files.exists(path)) {
                try {
                    Files.write(path, fileBytes);
                    System.out.println("Imagen guardada exitosamente");
                    return newFileName;
                } catch (Exception e) {
                    System.err.println("Error al guardar la imagen: " + e.getMessage());
                }
            }
        }
        return "default.jpg";
    }

    public void deleteImage(String fileName) {
        File file = new File(folder + fileName);
        file.delete();
    }

    public void createDirFolder() {
        File dirFolder = new File(folder);
        if (!dirFolder.exists()) {
            dirFolder.mkdirs();
        }
    }

}
