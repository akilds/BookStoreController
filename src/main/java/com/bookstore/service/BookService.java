package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.BookDTO;
import com.bookstore.exception.BookException;
import com.bookstore.model.BookData;
import com.bookstore.repository.BookRepository;
import com.bookstore.util.Response;
import com.bookstore.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService implements IBookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	//Returns all user data present
	@Override
	public List<BookData> getAllBooks(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<BookData> isPresent = bookRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Get All Book Data");
			List<BookData> getAllBooks = bookRepository.findAll();
			return getAllBooks;
		}else {
			log.error("Book Token Is Not valid");
			throw new BookException(400, "Book Token Is Not Valid");
		}	
	}
			
	//Adds a new user data
	@Override
	public Response addBook(BookDTO bookDTO) {
		Optional<BookData> isPresent = bookRepository.findByBookName(bookDTO.getBookName());
		if(isPresent.isPresent()) {
			log.error("Book Already Added");
			throw new BookException(400, "Book Already Added");
		}else {
			log.info("Add Book : " + bookDTO);
			BookData book = modelmapper.map(bookDTO, BookData.class);
			bookRepository.save(book);
			String token = tokenUtil.createToken(book.getBookId());
			return new Response(200, "Book Data Added Successfully", token);
		}	
	}
	
	//Updates an existing user data
	@Override
	public Response updateBook(String token, BookDTO bookDTO) {
		int id = tokenUtil.decodeToken(token);
		Optional<BookData> isPresent = bookRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Update Book : " + bookDTO);
			isPresent.get().updateBook(bookDTO);
			bookRepository.save(isPresent.get());
			return new Response(200, "Book Data Updated Successfully", token);
		}else {
			log.error("Book Doesnt Exist");
			throw new BookException(400, "Book Doesnt Exist");
		}	
	}

	//Deletes an existing user data
	@Override
	public Response deleteBook(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<BookData> isPresent = bookRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Book Data Deleted");
			bookRepository.delete(isPresent.get());
			return new Response(200, "Book Data Deleted Successfully", token);
		}else {
			log.error("Book Token Is Not Valid");
			throw new BookException(400, "Book Token Is Not Valid");
		}
	}
}
