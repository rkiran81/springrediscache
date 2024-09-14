package com.spring.redis.cache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.redis.cache.entity.Product;
import com.spring.redis.cache.repository.ProductDao;

@RestController
@RequestMapping("product")
public class RedisController {
	
	@Autowired
	private ProductDao dao;
	
	@PostMapping
	public Product save(@RequestBody Product product) {
		return dao.save(product);
	}
	
	@GetMapping
	public List<Object> findAll(){
		return dao.findAll();
	}
	
	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "ProductCacheable", unless = "#result.quantity > 400")
	public Product findProductById(@PathVariable int id) {
		System.out.println("####Controller findProductById####");
		return dao.findProductById(id);
	}
	
	@DeleteMapping("/{id}")
	@Cacheable(key = "#id", value = "ProductCacheable")
	public String deleteProductById(@PathVariable int id) {
		return dao.deleteProductById(id);
	} 
}
