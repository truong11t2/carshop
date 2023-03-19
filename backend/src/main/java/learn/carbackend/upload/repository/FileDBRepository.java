package learn.carbackend.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learn.carbackend.upload.model.FileDBEntity;

public interface FileDBRepository extends JpaRepository<FileDBEntity, String> {
    
}
