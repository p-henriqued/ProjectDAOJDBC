package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDAO {
	
	void insert(Seller seller);
	void uptade(Seller seller);
	void deleteById(Integer id);
	void findById(Integer id);
	List<Seller> findAll();
}
