package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.ConnectionDB;
import DB.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;
		
		try {
			String sqlInsert = "INSERT INTO department "
							+ "(Id, Name) "
							+ "VALUES (?, ?)";
			ps = conn.prepareStatement(sqlInsert);
			ps.setInt(1, department.getId());
			ps.setString(2, department.getName());
			
			ps.executeUpdate();

		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;
		
		try {
			String sqlUpdate = "UPDATE department SET Name = ? "
							+ "WHERE Id = ?";
			ps = conn.prepareStatement(sqlUpdate);
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			
			ps.executeUpdate();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			String sqlDelete = "DELETE FROM department WHERE id = ?";
			ps = conn.prepareStatement(sqlDelete);
			ps.setInt(1, id);
			
			Integer arrowsAffect = ps.executeUpdate();
			
			if(arrowsAffect == 0) {
				throw new DbException("ERROR! [Department ID not registered!]");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlInnerJoin = "SELECT * FROM department WHERE id = ?";
			ps = conn.prepareStatement(sqlInnerJoin);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Department obj = new Department();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				return obj;
			}else {
				throw new DbException("Unexpected ERROR! [No department was found with that ID]");
			}
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(ps);
		}
	}

	@Override
	public List<Department> findAll() {
		List<Department> departments = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlShearch = "SELECT * FROM department";
			ps = conn.prepareStatement(sqlShearch);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				departments.add(instantiateDepartment(rs));
			}
			
			return departments;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException{
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
