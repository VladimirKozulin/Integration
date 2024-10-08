package com.example.service.integration_app.service;

import com.example.service.integration_app.config.properties.AppCacheProperties;
import com.example.service.integration_app.entity.DatabaseEntity;
import com.example.service.integration_app.repository.DatabaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@CacheConfig(cacheManager = "redisCacheManger")
public class DatabaseEntityService implements Serializable {

    private final DatabaseEntityRepository databaseEntityRepository;

    @Cacheable(AppCacheProperties.cacheNames.DATABASE_ENTITIES)
    public List<DatabaseEntity> findAll() {
        return databaseEntityRepository.findAll();
    }


    @Cacheable(cacheNames = AppCacheProperties.cacheNames.DATABASE_ENTITIES_BY_ID, key = "#id")
    public DatabaseEntity findById(UUID id){
        return databaseEntityRepository.findById(id).orElseThrow();
    }



    @Cacheable("databaseByName")
    public DatabaseEntity findByName(String name) {
        DatabaseEntity prob = new DatabaseEntity();
        prob.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnorePaths("id", "date");

        Example<DatabaseEntity> example = Example.of(prob, matcher);

        return databaseEntityRepository.findOne(example).orElseThrow();
    }



    @CacheEvict(value = "databaseEntities", allEntries = true)
    public DatabaseEntity create(DatabaseEntity databaseEntity) {
        DatabaseEntity forSave = new DatabaseEntity();
        forSave.setName(databaseEntity.getName());
        forSave.setDate(databaseEntity.getDate());

        return databaseEntityRepository.save(forSave);
    }



//    @Caching(evict = {
//            @CacheEvict(value = "databaseEntities", allEntries = true),
//            @CacheEvict(value = "databaseByName", allEntries = true)
//    })
    @CacheEvict(cacheNames = AppCacheProperties.cacheNames.DATABASE_ENTITIES_BY_ID, key = "#id", beforeInvocation = true)
    public DatabaseEntity update(UUID id,DatabaseEntity databaseEntity) {
        DatabaseEntity entityForUpdate = findById(id);
        entityForUpdate.setName(databaseEntity.getName());
        entityForUpdate.setDate(databaseEntity.getDate());

        return databaseEntityRepository.save(entityForUpdate);
    }


//    @Caching(evict = {
//            @CacheEvict(value = "databaseEntities", allEntries = true),
//            @CacheEvict(value = "databaseByName", allEntries = true)
//    })
    @CacheEvict(cacheNames = AppCacheProperties.cacheNames.DATABASE_ENTITIES_BY_ID, key = "#id", beforeInvocation = true)
    public void deleteById(UUID id) {
        databaseEntityRepository.deleteById(id);
    }
}
