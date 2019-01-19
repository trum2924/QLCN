package wee.budget.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BorrowerDTO {
	private long id;
	private long lenderid;

	@NotEmpty
	@Pattern(regexp = "\\d{9}|\\d{12}")
	private String cardNum;

	@NotEmpty
	@Size(max = 40)
	private String name;

	@NotEmpty
	private String birthday;

	@NotEmpty
	@Size(max = 100)
	private String cardAddress;

	@NotEmpty
	private String issueDate;

	@NotEmpty
	@Size(max = 100)
	private String currentAddress;

	@NotEmpty
	@Pattern(regexp = "^0\\d{9}")
	private String phone;

	@Size(max = 200)
	private String note;
	private String action;

	public BorrowerDTO() {
	}

	public BorrowerDTO(String action) {
		this.action = action;
	}
	
	public BorrowerDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public BorrowerDTO(long id, String cardNum, String name, String phone, String note) {
		this.id = id;
		this.cardNum = cardNum;
		this.name = name;
		this.phone = phone;
		this.note = note;
	}

	public BorrowerDTO(long id, String cardNum, String name, String birthday, String cardAddress, String issueDate,
			String currentAddress, String phone, String note) {
		this.id = id;
		this.cardNum = cardNum;
		this.name = name;
		this.birthday = birthday;
		this.cardAddress = cardAddress;
		this.issueDate = issueDate;
		this.currentAddress = currentAddress;
		this.phone = phone;
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

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCardAddress() {
		return cardAddress;
	}

	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "cardNum=" + cardNum + ", name=" + name + ", birthday=" + birthday + ", cardAddress=" + cardAddress
				+ ", issueDate=" + issueDate + ", currentAddress=" + currentAddress + ", phone=" + phone + ", note="
				+ note;
	}

}
