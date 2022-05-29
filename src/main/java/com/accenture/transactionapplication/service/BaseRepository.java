package com.accenture.transactionapplication.service;

import com.accenture.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
}
