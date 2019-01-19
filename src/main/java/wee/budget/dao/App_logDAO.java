package wee.budget.dao;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class App_logDAO extends JdbcDaoSupport {

	@Autowired
	public App_logDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public void insertLoginLog(String lenderid) {
		String sql = "INSERT INTO"
			+ "\n" + "    app_log"
			+ "\n" + "        ("
			+ "\n" + "             date"
			+ "\n" + "            ,lender_id"
			+ "\n" + "            ,action"
			+ "\n" + "            ,detail"
			+ "\n" + "        )"
			+ "\n" + "    VALUES"
			+ "\n" + "        (?, ?, ?, ?)";
		this.getJdbcTemplate().update(sql, new Date(), lenderid, "login", "");
	}
	
}
