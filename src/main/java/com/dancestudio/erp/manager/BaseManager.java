package com.dancestudio.erp.manager;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseManager<Entity, ID> {

    protected final JpaRepository<Entity, ID> repository;
    protected final String entityName;

    protected BaseManager(JpaRepository<Entity, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public Entity add(Entity entry) {
        return repository.save(entry);
    }

    @Transactional
    public Entity update(ID id, Entity entry) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception(entityName + " not found for update: " + id);
        }
        return repository.save(entry);
    }

    public void delete(ID id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception(entityName + " not found for delete: " + id);
        }
        repository.deleteById(id);
    }

    public Entity getById(ID id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception(entityName + " not found: " + id));
    }
}
