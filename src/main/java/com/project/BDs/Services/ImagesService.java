package com.project.BDs.Services;

import com.project.BDs.Images;
import com.project.BDs.Repositories.ImagesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ImagesService {

    private final ImagesRepository imagesRepository;
    private final FileManager fileManager = new FileManager();

    @Autowired
    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Page<Images> getAllImages() {
        Pageable pageable = Pageable.unpaged();
        Page<Images> imagesPage = imagesRepository.findAll(pageable);
        return imagesPage;
    }

    public Images getImageById(Long id) {
        return imagesRepository.findById(id).orElse(null);
    }

    @Transactional(rollbackOn = {IOException.class})
    public Images uploadImage(MultipartFile file) throws IOException {
        String key = generateKey(file.getName());
        Images image = new Images();
        image.setName(file.getOriginalFilename());
        image.setKey(key);
        image.setSize(file.getSize());
        image.setDateUpload(LocalDate.now());
        imagesRepository.save(image);
        fileManager.upload(file.getBytes(), key);
        return image;
    }

    private String generateKey(String name) {
        String msg = name + LocalDateTime.now().toString();
        byte[] data = msg.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(data);
            return digest.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Chosen algorithm doesn't exist!");
            return null;
        }
    }
}
