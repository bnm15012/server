package com.dancestudio.erp.base;

import com.dancestudio.erp.exception.EntityNotFoundException;

import org.springframework.beans.BeansException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseManager<Entity, ID, Entry> {

    private final JpaRepository<Entity, ID> repository;
    private final String entityName;

    protected BaseManager(JpaRepository<Entity, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    protected abstract Entity toEntity(Entry entry, Entity existing)
            throws EntityNotFoundException, BeansException, Exception;

    protected abstract Entry toEntry(Entity entity, String[] fields) throws EntityNotFoundException;

    public Entry add(Entry entry) throws EntityNotFoundException, BeansException, Exception {
        Entity entity = toEntity(entry, null);
        Entity saved = repository.save(entity);
        return toEntry(saved, new String[] {});
    }

    public Entry update(ID id, Entry entry) throws EntityNotFoundException, BeansException, Exception {
        Entity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));

        Entity updated = toEntity(entry, existing);
        Entity saved = repository.save(updated);
        return toEntry(saved, new String[] {});
    }

    public void delete(ID id) throws EntityNotFoundException {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));
        repository.deleteById(id);
    }

    public Entry getById(ID id) throws EntityNotFoundException {
        Entity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found"));

        return toEntry(entity, new String[] {});
    }
}
