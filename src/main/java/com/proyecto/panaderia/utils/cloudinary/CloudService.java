package com.proyecto.panaderia.utils.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CloudService {
    private final Cloudinary cloudinary;
    public String uploadimg(MultipartFile file) {
        try {
            // Generar un nombre de archivo único usando UUID
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString();

            // Subir el archivo a Cloudinary con el nombre de archivo único
            return cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("public_id", uniqueFileName)).get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
