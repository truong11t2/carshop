package learn.carbackend.upload.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import learn.carbackend.upload.model.FileDBEntity;
import learn.carbackend.upload.repository.FileDBRepository;

@Service
public class FileStorageService {
    
    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDBEntity storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDBEntity fileDB = new FileDBEntity(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(fileDB);
    }

    public FileDBEntity getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDBEntity> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    // public FileDBEntity deleteFile(String id) {
    //     FileDBEntity fileDBEntity = fileDBRepository.findById(id).get();
    //     fileDBRepository.deleteById(id);
    //     return fileDBEntity;
    // }

    public void deleteFile(String id) {
        fileDBRepository.deleteById(id);
    }
}
