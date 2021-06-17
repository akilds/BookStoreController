package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.BookData;

public interface BookRepository extends JpaRepository<BookData, Integer>{

	Optional<BookData> findByBookName(String bookName);
	
}
