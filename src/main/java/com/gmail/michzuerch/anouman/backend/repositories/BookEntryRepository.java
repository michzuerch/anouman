package com.gmail.michzuerch.anouman.backend.repositories;

import com.gmail.michzuerch.anouman.backend.data.entity.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEntryRepository extends JpaRepository<BookEntry, Long> {
}
