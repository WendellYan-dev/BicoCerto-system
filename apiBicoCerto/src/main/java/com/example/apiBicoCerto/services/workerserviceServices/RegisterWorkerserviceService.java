package com.example.apiBicoCerto.services.workerserviceServices;


import com.example.apiBicoCerto.utils.GenerateLinkService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class RegisterWorkerserviceService {

    @Autowired
    private GenerateLinkService generateLinkService;

    public void registerService(MultipartFile image) throws IOException {
        String linkUrl = generateLinkService.uploadImage(image);
        System.out.println( linkUrl);
    }
}
