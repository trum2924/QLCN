package wee.budget.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoanDTO {
	private long id;
	private long lenderid;
	
	@NotNull
	private long borrowerid;
	private String borrowerName;

	@NotEmpty
	private String issueDate;

	@Min(1000)
	@Max(4294967295L)
	@NotNull
	private long amount;

	@Min(0)
	@Max(100)
	@NotNull
	private int rate;
	
	@Size(max=200)
	private String note;

	public LoanDTO() {
	}

	public LoanDTO(long id, long borrowerid, String borrowerName ,String issueDate, long amount, int rate) {
		this.id = id;
		this.borrowerid = borrowerid;
		this.borrowerName = borrowerName;
		this.issueDate = issueDate;
		this.amount = amount;
		this.rate = rate;
	}
	
	public LoanDTO(long borrowerid, String issueDate, long amount, int rate, String note) {
		this.borrowerid = borrowerid;
		this.issueDate = issueDate;
		this.amount = amount;
		this.rate = rate;
		this.note = note;
	}

	public LoanDTO(long id, long amount, String note) {
		this.id = id;
		this.amount = amount;
		this.note = note;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLenderid() {
		return lenderid;
	}

	public void setLenderid(long lenderid) {
		this.lenderid = lenderid;
	}

	public long getBorrowerid() {
		return borrowerid;
	}

	public void setBorrowerid(long borrowerid) {
		this.borrowerid = borrowerid;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	
}
