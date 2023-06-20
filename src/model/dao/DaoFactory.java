package model.dao;

import DB.ConnectionDB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDAO() {
		return new SellerDaoJDBC(ConnectionDB.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDAO() {
		return new DepartmentDaoJDBC(ConnectionDB.getConnection());
	}

}
