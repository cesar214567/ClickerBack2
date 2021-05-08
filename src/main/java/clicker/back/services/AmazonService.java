package clicker.back.services;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {

    String uploadFile(MultipartFile multipartFile, String id, String dir);

    String deleteFileFromS3Bucket(String fileUrl);
}
