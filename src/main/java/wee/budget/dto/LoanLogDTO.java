package wee.budget.dto;

public class LoanLogDTO {

	private String date;
	private long amount;

	public LoanLogDTO(String date, long amount) {
		this.date = date;
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

}
