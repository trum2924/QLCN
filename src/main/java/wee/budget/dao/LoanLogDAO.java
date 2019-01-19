package wee.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import wee.budget.dto.LoanDTO;
import wee.budget.dto.LoanLogDTO;
import wee.budget.form.EditLoanForm;

@Repository
public class LoanLogDAO extends JdbcDaoSupport {
	
	@Autowired
	public LoanLogDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public boolean insertCreate(LoanDTO loanDTO) throws Exception {
		String sql = "INSERT INTO"
			+ "\n" + "    loan_log"
			+ "\n" + "    ("
			+ "\n" + "         date"
			+ "\n" + "        ,loan_id"
			+ "\n" + "        ,amount"
			+ "\n" + "        ,action"
			+ "\n" + "    )"
			+ "\n" + "SELECT"
			+ "\n" + "    ?, id, ?, ?"
			+ "\n" + "FROM"
			+ "\n" + "    loan"
			+ "\n" + "WHERE"
			+ "\n" + "        lender_id = ?"
			+ "\n" + "    AND borrower_id = ?"
			+ "\n" + "ORDER BY"
			+ "\n" + "    id DESC"
			+ "\n" + "LIMIT 1";
			
		return this.getJdbcTemplate().update(sql
								,new Date()
								,loanDTO.getAmount()
								,"insert"
								,loanDTO.getLenderid()
								,loanDTO.getBorrowerid()
								) == 1;
	}
	
	public boolean insertUpdate(EditLoanForm editLoanForm) throws Exception {
		String sql = "INSERT INTO"
			+ "\n" + "    loan_log"
			+ "\n" + "    ("
			+ "\n" + "         date"
			+ "\n" + "        ,loan_id"
			+ "\n" + "        ,amount"
			+ "\n" + "        ,action"
			+ "\n" + "    )"
			+ "\n" + "VALUES"
			+ "\n" + "    (?, ?, ?, ?)";
			
		return this.getJdbcTemplate().update(sql
								,new Date()
								,editLoanForm.getId()
								,editLoanForm.getAmount()
								,"update"
								) == 1;
	}
	
	public List<LoanLogDTO> searchLoan(Long loanid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    date"
			+ "\n" + "   ,amount"
			+ "\n" + "FROM"
			+ "\n" + "    loan_log"
			+ "\n" + "WHERE"
			+ "\n" + "    loan_id = ?";
		Object[] params = new Object[] { loanid };
		RowMapper<LoanLogDTO> mapper = new RowMapper<LoanLogDTO>() {
			@Override
			public LoanLogDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				String date = rs.getString("date");
				Long amount = rs.getLong("amount");
				return new LoanLogDTO(date, amount);
			}
		};
		return this.getJdbcTemplate().query(sql, params, mapper);
	}
	
}
