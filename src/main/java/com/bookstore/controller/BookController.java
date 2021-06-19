package com.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.BookData;
import com.bookstore.service.IBookService;
import com.bookstore.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

	@Autowired
	private IBookService bookService;
	
	@GetMapping("getallbooks")
	public ResponseEntity<List<?>> getAllBooks(@RequestHeader String userToken) {
		log.info("Get All Book Data");
		List<BookData> response = bookService.getAllBooks(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Response> addBookData(@RequestHeader String userToken,
												@RequestBody BookDTO bookDTO) {
		log.info("Create Book Data : " + bookDTO);
		Response response  = bookService.addBook(userToken,bookDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/verifybookid/{bookId}")
	public boolean verifyBookId(@PathVariable int bookId){
		boolean userCheck = bookService.verifyBookId(bookId);
		return userCheck;
	}
		
	@PutMapping("/update/{bookId}")
	public ResponseEntity<Response> updateBookData(@RequestHeader String userToken,
												   @PathVariable int bookId,
			                                       @Valid @RequestBody BookDTO userDTO) {
		log.info("Update Book Data : " + userDTO);
		Response response  = bookService.updateBook(userToken, bookId, userDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/updatequantity/{bookId}")
	public ResponseEntity<Response> updateBookQuantity(@RequestHeader String userToken,
			 										   @PathVariable int bookId,
			                                       	   @RequestParam long quantity) {
		log.info("Update Book Quantity");
		Response response  = bookService.updateBookQuantity(userToken, bookId, quantity);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/updateprice/{bookId}")
	public ResponseEntity<Response> updateBookPrice(@RequestHeader String userToken,
			    									@PathVariable int bookId,
													@RequestParam double price) {
		log.info("Update Book Price ");
		Response response  = bookService.updateBookPrice(userToken, bookId, price);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteBookData(@RequestHeader String userToken,
			 									   @PathVariable int bookId) {
		log.info("Book Data Deleted");
		Response response  = bookService.deleteBook(userToken, bookId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
