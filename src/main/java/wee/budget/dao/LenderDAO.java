package wee.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import wee.budget.dto.LenderDTO;

@Repository
public class LenderDAO extends JdbcDaoSupport {

	@Autowired
	public LenderDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public LenderDTO findLender(String username) {
		String sql = "SELECT"
			+ "\n" + "    id"
			+ "\n" + "   ,password"
			+ "\n" + "   ,role"
			+ "\n" + "FROM"
			+ "\n" + "    lender"
			+ "\n" + "WHERE"
			+ "\n" + "    is_enabled = '1'"
			+ "\n" + "    AND"
			+ "\n" + "    username = ?";

		Object[] params = new Object[] { username };
		
		RowMapper<LenderDTO> mapper = new RowMapper<LenderDTO>() {

			@Override
			public LenderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long id = rs.getLong("id");
				String password = rs.getString("password");
				String role = rs.getString("role");

				return new LenderDTO(id, password, role);
			}
		};
		
		try {
			LenderDTO lenderDTO = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return lenderDTO;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}