package com.accenture.transactionapplication.service.user;

import com.accenture.transactionapplication.exception.FieldNotValidException;
import com.accenture.model.BaseEntity;
import com.accenture.transactionapplication.service.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public abstract class UserService<T extends BaseEntity, ID> {

    private BaseRepository<T, ID> repository;

    @Autowired
    public void setRepository(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public abstract boolean validateEntity(T baseEntity);

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public T update(T entity) {
        if (validateEntity(entity)) {
            return repository.save(entity);
        } else {
            throw new FieldNotValidException("message");
        }
    }

}
