package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Returns all book data present
	@Override
	public List<BookData> getAllBooks(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isUserIdPresent) {
			log.info("Get All Book Data");
			List<BookData> getAllBooks = bookRepository.findAll();
			return getAllBooks;
		}else {
			log.error("Book Token Is Not valid");
			throw new BookException(400, "Book Token Is Not Valid");
		}	
	}
			
	//Adds a new book data
	@Override
	public Response addBook(String userToken, BookDTO bookDTO) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		Optional<BookData> isPresent = bookRepository.findByBookName(bookDTO.getBookName());
		if(isPresent.isPresent() && isUserIdPresent==false) {
			log.error("Book Already Added/ USer Doesnt Exist");
			throw new BookException(400, "Book Already Added / User Doesnt Exist");
		}else {
			log.info("Add Book : " + bookDTO);
			BookData book = modelmapper.map(bookDTO, BookData.class);
			bookRepository.save(book);
			return new Response(200, "Book Data Added Successfully", userToken);
		}	
	}
	
	//Verifies a book based on bookId
	@Override
	public boolean verifyBookId(int bookId) {
		List<BookData> books = bookRepository.findAll();
		return books.stream().anyMatch(book -> book.getBookId()==bookId);
	}
		
	//Updates an existing book data
	@Override
	public Response updateBook(String userToken, int bookId, BookDTO bookDTO) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		Optional<BookData> isPresent = bookRepository.findById(bookId);
		if(isUserIdPresent && isPresent.isPresent()) {
			log.info("Update Book : " + bookDTO);
			isPresent.get().updateBook(bookDTO);
			bookRepository.save(isPresent.get());
			return new Response(200, "Book Data Updated Successfully", userToken);
		}else {
			log.error("Book/User Doesnt Exist");
			throw new BookException(400, "Book/User Doesnt Exist");
		}	
	}

	//Updates the quantity of the book
	@Override
	public Response updateBookQuantity(String userToken, int bookId,  long quantity) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		Optional<BookData> isPresent = bookRepository.findById(bookId);
		if(isPresent.isPresent() && isUserIdPresent) {
			log.info("Update Book Quantity");
			isPresent.get().setBookQuantity(isPresent.get().getBookQuantity() + quantity);
			bookRepository.save(isPresent.get());
			return new Response(200, "Book Quantity Updated Successfully", userToken);
		}else {
			log.error("Book/User Doesnt Exist");
			throw new BookException(400, "Book/User Doesnt Exist");
		}	
	}

	//Updates the price of the book
	@Override
	public Response updateBookPrice(String userToken, int bookId, double price) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		Optional<BookData> isPresent = bookRepository.findById(bookId);
		if(isPresent.isPresent() && isUserIdPresent) {
			log.info("Update Book Price ");
			isPresent.get().setBookPrice(price);;
			bookRepository.save(isPresent.get());
			return new Response(200, "Book Price Updated Successfully", userToken);
		}else {
			log.error("Book/User Doesnt Exist");
			throw new BookException(400, "Book/User Doesnt Exist");
		}	
	}
	
	//Deletes an existing book data
	@Override
	public Response deleteBook(String userToken, int bookId) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://bookstore-user/user/verifyuserid/" + userId;
		boolean isUserIdPresent = restTemplate.getForObject(uri, Boolean.class);
		Optional<BookData> isPresent = bookRepository.findById(bookId);
		if(isPresent.isPresent() && isUserIdPresent) {
			log.info("Book Data Deleted");
			bookRepository.delete(isPresent.get());
			return new Response(200, "Book Data Deleted Successfully", userToken);
		}else {
			log.error("Book/User Doesnt Exist");
			throw new BookException(400, "Book/User Doesnt Exist");
		}
	}
}
