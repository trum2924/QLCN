package wee.budget.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditLoanForm {

	@NotNull
	private long id;
	
	@NotNull
	@Min(0)
	@Max(4294967295L)
	private long amount;
	
	@Size(max=200)
	private String note;

	public EditLoanForm() {
	}
	
	public EditLoanForm(long id, long amount, String note) {
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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
