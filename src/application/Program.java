package application;

import DB.ConnectionDB;
import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO seller = DaoFactory.createSellerDAO();
		System.out.println(seller.findById(3));
		
		DepartmentDAO dep = DaoFactory.createDepartmentDAO();
		System.out.println(dep.findById(1));
		ConnectionDB.closeConnection();
		
	}

}
