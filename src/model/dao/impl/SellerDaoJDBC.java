package model.dao.impl;

import java.sql.Connection;
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
				Department department = instantiateDepartment(rs);
				obj = instantiateSeller(rs, department);
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
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

}
