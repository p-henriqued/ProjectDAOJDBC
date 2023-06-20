package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	void insert(Department department);
	void uptade(Department department);
	void deleteById(Integer id);
	void findById(Integer id);
	List<Department> findAll();

}
