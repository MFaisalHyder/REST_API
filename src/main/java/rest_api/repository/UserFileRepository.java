package rest_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import rest_api.model.UserFile;

public interface UserFileRepository extends MongoRepository<UserFile, String>{	
	
}
