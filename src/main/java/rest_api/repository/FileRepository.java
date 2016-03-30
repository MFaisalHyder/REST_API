package rest_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rest_api.model.File;

public interface FileRepository extends MongoRepository<File, String> {
}
