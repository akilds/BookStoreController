package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.BookData;
import com.bookstore.util.Response;

public interface IBookService {

	List<BookData> getAllBooks(String userToken);

	Response addBook(String userToken, BookDTO bookDTO);

	Response updateBook(String userToken, int bookId, BookDTO bookDTO);

	Response deleteBook(String userToken, int bookId);

	Response updateBookQuantity(String userToken, int bookId, long quantity);

	Response updateBookPrice(String userToken, int bookId, double price);

	boolean verifyBookId(int bookId);

}
