package wee.budget.dao;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import wee.budget.dto.BorrowerDTO;

@Repository
public class Borrower_logDAO extends JdbcDaoSupport {
	
	@Autowired
	public Borrower_logDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public boolean insertCreate(BorrowerDTO borrowerDTO) throws Exception {
		String sql = "INSERT INTO"
			+ "\n" + "    borrower_log"
			+ "\n" + "    ("
			+ "\n" + "         date"
			+ "\n" + "        ,borrower_id"
			+ "\n" + "        ,action"
			+ "\n" + "        ,detail"
			+ "\n" + "    )"
			+ "\n" + "SELECT"
			+ "\n" + "    ?, id, ?, ?"
			+ "\n" + "FROM"
			+ "\n" + "    borrower"
			+ "\n" + "WHERE"
			+ "\n" + "        card_num = ?"
			+ "\n" + "    AND lender_id = ?";
			
		return this.getJdbcTemplate().update(sql
								,new Date()
								,"insert"
								,borrowerDTO.toString()
								,borrowerDTO.getCardNum()
								,borrowerDTO.getLenderid()
								) == 1;
	}
	
	public boolean insertUpdate(BorrowerDTO borrowerDTO) throws Exception {
		String sql = "INSERT INTO"
			+ "\n" + "    borrower_log"
			+ "\n" + "        ("
			+ "\n" + "             date"
			+ "\n" + "            ,borrower_id"
			+ "\n" + "            ,action"
			+ "\n" + "            ,detail"
			+ "\n" + "        )"
			+ "\n" + "SELECT"
			+ "\n" + "    ?, ?, ?, ?";
			
		return this.getJdbcTemplate().update(sql
								,new Date()
								,borrowerDTO.getId()
								,"update"
								,borrowerDTO.toString()
								) == 1;
	}
}