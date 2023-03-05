package learn.carbackend.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learn.carbackend.upload.model.FileDB;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
    
}
