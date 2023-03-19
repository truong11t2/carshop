package learn.carbackend.upload.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import learn.carbackend.upload.message.ResponseFile;
import learn.carbackend.upload.message.ResponseMessage;
import learn.carbackend.upload.model.FileDBEntity;
import learn.carbackend.upload.service.FileStorageService;
import net.minidev.json.JSONObject;

@Controller
public class FileController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        JSONObject json = new JSONObject();
        try {
            FileDBEntity savedFile = storageService.storeFile(file);
            //message = "Upload file successfully. Name: " + savedFile.getName() + " Id: " + savedFile.getId();
            //return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            
            json.put("Description", "Upload file successfully");
            json.put("Name", savedFile.getName());
            json.put("Id", savedFile.getId());
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (IOException e) {
            message = "Could not upload file: " + file.getOriginalFilename() + "!";
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping(value = "/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/files/")
            .path(dbFile.getId())
            .toUriString();

            return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping(value = "/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDBEntity fileDBEntity = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDBEntity.getName() + "\"")
                .body(fileDBEntity.getData());
    }

    @DeleteMapping(value = "/file/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) {
        String message = "";
        //Try to delete file from database
        try {
            //FileDBEntity fileDBEntity = storageService.deleteFile(id);
            storageService.deleteFile(id);
            message = "Delete file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
