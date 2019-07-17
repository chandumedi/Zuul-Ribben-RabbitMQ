package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.stackroute.keepnote.config.RabbitMQSender;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController

@CrossOrigin(origins = { "http://localhost:4200" }, allowedHeaders = "*", allowCredentials = "true")
public class CategoryController {

	/*
	 * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	@Autowired
	private CategoryService categoryService;
	@Autowired
    Environment environment;
	@Autowired
	RabbitMQSender rabbitMQSender;
	@Autowired
    RestTemplate restTemplate;
	@LoadBalanced
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@GetMapping("/api/producer")
	public String producer() {
	
	Category category=new Category();
	category.setCategoryCreatedBy("CHANDU");
	category.setCategoryCreationDate(new Date());
		rabbitMQSender.send(category);

		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}
	@GetMapping("/server-location")
    public String serverLocation() {
		String url=new String("http://reminder-service/api/v1/ribbon");
		 String result = restTemplate.getForObject(url,String.class);
		 
        return result;
    }
	
	@GetMapping("/api/zuul")
    public String zuulConfigTest() {
		return "Zuul Gate way returned!";
    }
	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * database. Please note that the careatorId has to be unique.This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the category
	 * 2. 409(CONFLICT) - In case of duplicate categoryId
	 *
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP POST
	 * method".
	 */
	@PostMapping("api/v1/category")
	public ResponseEntity<?> createCategory(@RequestBody Category category, HttpSession session) {
		try {
			category.setCategoryCreatedBy("chandu");
			// category.setCategoryCreatedBy((String)
			// session.getAttribute("LoggedInUserId"));
			Category newCategory = categoryService.createCategory(category);
			return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
		}

		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}

	}

	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid categoryId without {}
	 */
	@DeleteMapping("/api/v1/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") String categoryId, HttpSession session) {

		try {
			boolean deleteFlag = categoryService.deleteCategory(categoryId);
			return new ResponseEntity<String>("Category deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. This handler method should map to the URL
	 * "/api/v1/category/{id}" using HTTP PUT method.
	 */
	@PutMapping("/api/v1/category/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable("id") String categoryId, @RequestBody Category category,
			HttpSession session) {

		try {
			Category categoryStatus = categoryService.updateCategory(category, categoryId);
			if (categoryStatus == null) {
				return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Category>(categoryStatus, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully.
	 * 
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP GET
	 * method
	 */
	@GetMapping("/api/v1/category/{userId}/{id}")
	public ResponseEntity<?> getSpecificCategory(@PathVariable("userId") String userId,
			@PathVariable("id") String categoryId, HttpSession session) {

		Category category = null;
		try {
			category = categoryService.getCategoryById(categoryId);
		} catch (CategoryNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);

	}

	@GetMapping("/api/v1/category/{userId}")
	public ResponseEntity<?> getSpecificUserCategories(@PathVariable("userId") String userId, HttpSession session) {

		Category category = null;
		try {
			List<Category> categories = categoryService.getCategoriesByuserId(userId);
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
}
