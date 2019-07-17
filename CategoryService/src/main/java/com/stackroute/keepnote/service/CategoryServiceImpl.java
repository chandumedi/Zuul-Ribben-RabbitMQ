package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {
		try {
			category.setCategoryCreationDate(new Date());
			Category existingCategory = getCategoryById(category.getId());
			if (existingCategory != null) {
				throw new CategoryNotCreatedException("CategoryNotCreatedException");
			}
		} catch (CategoryNotFoundException e) {

		}
		Category insertStatus=categoryRepository.insert(category);
		if(insertStatus==null) {throw new CategoryNotCreatedException("CategoryNotCreatedException");}
		return category;
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {
		Category category = null;
		try {
			category = getCategoryById(categoryId);
		} catch (CategoryNotFoundException e) {

		}
		if (category == null) {
			throw new CategoryDoesNoteExistsException("CategoryDoesNoteExistsException");
		}
		categoryRepository.delete(category);
		return true;
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
		try {
			Category existingCategory=getCategoryById(categoryId);
			category.setCategoryCreatedBy(existingCategory.getCategoryCreatedBy());
			category.setId(categoryId);
			categoryRepository.delete(existingCategory);
			categoryRepository.insert(category);
		} catch (CategoryNotFoundException e) {
			return null;
		}
		return category;
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		try {
			Optional<Category> category = categoryRepository.findById(categoryId);
			if (!category.isPresent()) {
				return null;
			}
			return category.get();
		} catch (Exception e) {
			throw new CategoryNotFoundException("CategoryNotFoundException");
		}

	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		List<Category> categoryList=categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
		return categoryList;
	}

	@Override
	public List<Category> getCategoriesByuserId(String userId) {
		// TODO Auto-generated method stub
		return categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
	}

}
