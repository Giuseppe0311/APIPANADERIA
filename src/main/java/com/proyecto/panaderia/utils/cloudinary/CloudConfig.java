package com.proyecto.panaderia.utils.cloudinary;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CloudConfig {
    @Value("${CLOUDINARY_URL}")
    private String url;
    @Bean
    public Cloudinary cloudinaryConfig(){
        return new Cloudinary(url);
    }
}
