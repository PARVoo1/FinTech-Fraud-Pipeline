package net.parvkhandelwal.repository;

import net.parvkhandelwal.entity.AppCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppCacheRepository extends MongoRepository<AppCache, String> {
}
