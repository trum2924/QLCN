package wee.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import wee.budget.dto.LoanDTO;
import wee.budget.form.EditLoanForm;

@Repository
public class LoanDAO extends JdbcDaoSupport {

	@Autowired
	public LoanDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	@Autowired
	private LoanLogDAO loanLogDAO;
	
	public boolean insert(LoanDTO loanDTO) throws Exception {
		boolean result = false;
		String sql = "INSERT INTO"
			+ "\n" + "    loan"
			+ "\n" + "    ("
			+ "\n" + "         lender_id"
			+ "\n" + "        ,borrower_id"
			+ "\n" + "        ,issue_date"
			+ "\n" + "        ,amount"
			+ "\n" + "        ,rate"
			+ "\n" + "        ,note"
			+ "\n" + "    )"
			+ "\n" + "VALUES"
			+ "\n" + "    (?, ?, ?, ?, ?, ?)";
		result = this.getJdbcTemplate().update(sql
										,loanDTO.getLenderid()
										,loanDTO.getBorrowerid()
										,loanDTO.getIssueDate()
										,loanDTO.getAmount()
										,loanDTO.getRate()
										,loanDTO.getNote()
										) == 1;
		if (result) {
			result = this.loanLogDAO.insertCreate(loanDTO);
		}
		return result;
	}
	
	public boolean update(EditLoanForm editLoanForm) throws Exception {
		boolean result = false;
		String sql = "UPDATE"
			+ "\n" + "    loan"
			+ "\n" + "SET"
			+ "\n" + "    amount = ?"
			+ "\n" + "   ,note = ?"
			+ "\n" + "WHERE"
			+ "\n" + "    id = ?";
		result = this.getJdbcTemplate().update(sql
										,editLoanForm.getAmount()
										,editLoanForm.getNote()
										,editLoanForm.getId()
										) == 1;
		if (result) {
			result = this.loanLogDAO.insertUpdate(editLoanForm);
		}
		return result;
	}
	
	public EditLoanForm getLoan(Long loanid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    amount"
			+ "\n" + "   ,note"
			+ "\n" + "FROM"
			+ "\n" + "    loan"
			+ "\n" + "WHERE"
			+ "\n" + "    id = ?";
		Object[] params = new Object[] { loanid };
		RowMapper<EditLoanForm> mapper = new RowMapper<EditLoanForm>() {
			@Override
			public EditLoanForm mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long amount = rs.getLong("amount");
				String note = rs.getString("note");
				return new EditLoanForm(loanid, amount, note);
			}
		};
		return this.getJdbcTemplate().queryForObject(sql, params ,mapper);
	}
	
	public List<LoanDTO> searchLoan(Long lenderid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    L.id"
			+ "\n" + "   ,L.borrower_id"
			+ "\n" + "   ,B.name AS borrower_name"
			+ "\n" + "   ,L.issue_date"
			+ "\n" + "   ,L.amount"
			+ "\n" + "   ,L.rate"
			+ "\n" + "FROM"
			+ "\n" + "    loan AS L"
			+ "\n" + "LEFT JOIN"
			+ "\n" + "    borrower AS B ON B.id = L.borrower_id"
			+ "\n" + "WHERE"
			+ "\n" + "    L.lender_id = ?";
		Object[] params = new Object[] { lenderid };
		RowMapper<LoanDTO> mapper = new RowMapper<LoanDTO>() {
			@Override
			public LoanDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long id = rs.getLong("id");
				Long borrowerid = rs.getLong("borrower_id");
				String borrowerName = rs.getString("borrower_name");
				String issuerDate = rs.getString("issue_date");
				Long amount = rs.getLong("amount");
				int rate = rs.getInt("rate");
				return new LoanDTO(id, borrowerid, borrowerName, issuerDate, amount, rate);
			}
		};
		return this.getJdbcTemplate().query(sql, params, mapper);
	}
	
}
