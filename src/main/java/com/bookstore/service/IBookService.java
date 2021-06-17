package com.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.BookData;
import com.bookstore.util.Response;

public interface IBookService {

	List<BookData> getAllBooks(String token);

	Response addBook(@Valid BookDTO bookDTO);

	Response updateBook(String token, @Valid BookDTO bookDTO);

	Response deleteBook(String token);

}
