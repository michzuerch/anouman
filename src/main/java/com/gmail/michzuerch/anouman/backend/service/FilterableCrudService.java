package com.gmail.michzuerch.anouman.backend.service;

import com.gmail.michzuerch.anouman.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

    Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

    long countAnyMatching(Optional<String> filter);

}
