package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DB.ConnectionDB;
import DB.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {

	}

	@Override
	public void uptade(Seller seller) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {
		Seller obj = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlInnerJoin = "SELECT seller.*, department.Name as DepName "
							+ " FROM seller INNER JOIN department "
							+ " ON seller.DepartmentId = department.Id "
							+ " WHERE seller.Id = ?";
			ps = conn.prepareStatement(sqlInnerJoin);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Integer idSeller = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				Date dateBirth = rs.getDate(4);
				Double baseSalary = rs.getDouble(5);
				Integer departmentId = rs.getInt(6);
				String departmentName = rs.getString(7);
				
				Department department = new Department(departmentId, departmentName);
				
				obj = new Seller(idSeller, name, email, dateBirth, baseSalary, department);
			}
			
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(ps);
		}
		return obj;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

}
