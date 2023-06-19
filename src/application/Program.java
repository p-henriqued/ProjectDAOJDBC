package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		
		Seller obj2 = new Seller(7397, "Pelé", "pele2k23@gmail.com", new Date(), 3500.00, obj);
		
		System.out.println(obj2);
		
	}

}
