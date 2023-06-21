package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Seller> findByIdDepartment(Department department) {
		List<Seller> sellers = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlShearch = "SELECT seller.*,department.Name as DepName  "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id  "
					+ "WHERE DepartmentId = ?  "
					+ "ORDER BY Name";

			ps = conn.prepareStatement(sqlShearch);
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department obj = map.get(department.getId());
				if(obj == null) {
					obj = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), obj);
				}
				
				sellers.add(instantiateSeller(rs, obj));
			}
			
			return sellers;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(ps);
		}
	}
	
	@Override
	public List<Seller> findAll() {
		List<Seller> objs = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlShearchAll = "SELECT seller.*,department.Name as DepName  "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id  "
					+ "ORDER BY Name";
			ps = conn.prepareStatement(sqlShearchAll);
			rs = ps.executeQuery();
			
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));
				if(department == null) {
					department = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}
				objs.add(instantiateSeller(rs, department));
			}
			
			return objs;
			
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDB.closeStatement(ps);
			ConnectionDB.closeResultSet(rs);
		}
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
