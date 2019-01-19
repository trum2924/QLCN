package wee.budget.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import wee.budget.dto.BorrowerDTO;

@Repository
public class BorrowerDAO extends JdbcDaoSupport {

	@Autowired
	public BorrowerDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	@Autowired
	private Borrower_logDAO borrower_logDAO;

	public boolean insertBorrower(BorrowerDTO borrowerDTO) throws Exception {
		boolean result = false;
		String sql = "INSERT INTO"
			+ "\n" + "    borrower"
			+ "\n" + "    ("
			+ "\n" + "         lender_id"
			+ "\n" + "        ,card_num"
			+ "\n" + "        ,name"
			+ "\n" + "        ,birthday"
			+ "\n" + "        ,card_address"
			+ "\n" + "        ,issue_date"
			+ "\n" + "        ,current_address"
			+ "\n" + "        ,phone"
			+ "\n" + "        ,note"
			+ "\n" + "    )"
			+ "\n" + "VALUES"
			+ "\n" + "    (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		result = this.getJdbcTemplate().update(sql
										,borrowerDTO.getLenderid()
										,borrowerDTO.getCardNum()
										,borrowerDTO.getName()
										,borrowerDTO.getBirthday()
										,borrowerDTO.getCardAddress()
										,borrowerDTO.getIssueDate()
										,borrowerDTO.getCurrentAddress()
										,borrowerDTO.getPhone()
										,borrowerDTO.getNote()
										) == 1;
		if (result) {
			result = this.borrower_logDAO.insertCreate(borrowerDTO);
		}
		return result;
	}
	
	public boolean updateBorrower(BorrowerDTO borrowerDTO) throws Exception {
		boolean result = false;
		String sql = "UPDATE"
			+ "\n" + "    borrower"
			+ "\n" + "SET"
			+ "\n" + "   card_num = ?"
			+ "\n" + "   ,name = ?"
			+ "\n" + "   ,birthday = ?"
			+ "\n" + "   ,card_address = ?"
			+ "\n" + "   ,issue_date = ?"
			+ "\n" + "   ,current_address = ?"
			+ "\n" + "   ,phone = ?"
			+ "\n" + "   ,note = ?"
			+ "\n" + "WHERE"
			+ "\n" + "    id = ?";
		result = this.getJdbcTemplate().update(sql
										,borrowerDTO.getCardNum()
										,borrowerDTO.getName()
										,borrowerDTO.getBirthday()
										,borrowerDTO.getCardAddress()
										,borrowerDTO.getIssueDate()
										,borrowerDTO.getCurrentAddress()
										,borrowerDTO.getPhone()
										,borrowerDTO.getNote()
										,borrowerDTO.getId()
										) == 1;
		if (result) {
			result = this.borrower_logDAO.insertUpdate(borrowerDTO);
		}
		return result;
	}
	
	public BorrowerDTO getBorrower(Long borrowerid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    card_num"
			+ "\n" + "   ,name"
			+ "\n" + "   ,birthday"
			+ "\n" + "   ,card_address"
			+ "\n" + "   ,issue_date"
			+ "\n" + "   ,current_address"
			+ "\n" + "   ,phone"
			+ "\n" + "   ,note"
			+ "\n" + "FROM"
			+ "\n" + "    borrower"
			+ "\n" + "WHERE"
			+ "\n" + "    id = ?";
		Object[] params = new Object[] { borrowerid };
		RowMapper<BorrowerDTO> mapper = new RowMapper<BorrowerDTO>() {
			@Override
			public BorrowerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				String cardNum = rs.getString("card_num");
				String name = rs.getString("name");
				String birthday = rs.getString("birthday");
				String cardAddress = rs.getString("card_address");
				String issueDate = rs.getString("issue_date");
				String currentAddress = rs.getString("current_address");
				String phone = rs.getString("phone");
				String note = rs.getString("note");
				return new BorrowerDTO(borrowerid, cardNum, name, birthday, cardAddress, issueDate, currentAddress, phone, note);
			}
		};
		return this.getJdbcTemplate().queryForObject(sql, params ,mapper);
	}
	
	public List<BorrowerDTO> searchBorrower(Long lenderid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    id"
			+ "\n" + "   ,card_num"
			+ "\n" + "   ,name"
			+ "\n" + "   ,phone"
			+ "\n" + "   ,note"
			+ "\n" + "FROM"
			+ "\n" + "    borrower"
			+ "\n" + "WHERE"
			+ "\n" + "    lender_id = ?";
		Object[] params = new Object[] { lenderid };
		RowMapper<BorrowerDTO> mapper = new RowMapper<BorrowerDTO>() {
			@Override
			public BorrowerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long id = rs.getLong("id");
				String cardNum = rs.getString("card_num");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String note = rs.getString("note");
				return new BorrowerDTO(id, cardNum, name, phone, note);
			}
		};
		return this.getJdbcTemplate().query(sql, params, mapper);
	}
	
	public List<BorrowerDTO> getComboboxData(Long lenderid) throws Exception {
		String sql = "SELECT"
			+ "\n" + "    id"
			+ "\n" + "   ,name"
			+ "\n" + "FROM"
			+ "\n" + "    borrower"
			+ "\n" + "WHERE"
			+ "\n" + "    lender_id = ?";
		Object[] params = new Object[] { lenderid };
		RowMapper<BorrowerDTO> mapper = new RowMapper<BorrowerDTO>() {
			@Override
			public BorrowerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				return new BorrowerDTO(id, name);
			}
		};
		return this.getJdbcTemplate().query(sql, params, mapper);
	}	
}
