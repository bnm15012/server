package com.dancestudio.erp.base;

import com.dancestudio.erp.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseManager<Entity, ID, Entry> {

    private final JpaRepository<Entity, ID> repository;
    private final String entityName;

    protected BaseManager(JpaRepository<Entity, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    protected abstract Entity toEntity(Entry entry, Entity existing) throws EntityNotFoundException;

    protected abstract Entry toEntry(Entity entity) throws EntityNotFoundException;

    public Entry add(Entry entry) throws EntityNotFoundException{
        Entity entity = toEntity(entry, null);
        Entity saved = repository.save(entity);
        return toEntry(saved);
    }

    public Entry update(ID id, Entry entry) throws EntityNotFoundException {
        Entity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));

        Entity updated = toEntity(entry, existing);
        Entity saved = repository.save(updated);
        return toEntry(saved);
    }

    public void delete(ID id) throws EntityNotFoundException {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));
        repository.deleteById(id);
    }

    public Entry getById(ID id) throws EntityNotFoundException {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));

        return toEntry(entity);
    }
}
