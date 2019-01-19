package wee.budget.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import wee.budget.dao.BorrowerDAO;
import wee.budget.dao.LoanDAO;
import wee.budget.dao.LoanLogDAO;
import wee.budget.dto.BorrowerDTO;
import wee.budget.dto.LoanDTO;
import wee.budget.form.EditLoanForm;

@Controller
public class MainController implements ErrorController {

	private static Logger LOGGER;
	
	private static void logging(String logger, String mes) {
		MainController.LOGGER = LoggerFactory.getLogger(logger);
		MainController.LOGGER.error(mes);
	}

	@Autowired
	private BorrowerDAO borrowerDAO;
	
	@Autowired
	private LoanDAO LoanDAO;
	
	@Autowired
	private LoanLogDAO loanLogDAO;

	@Override
    public String getErrorPath() {
        return "/error";
    }
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String loginPage() {
		if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			return "login";
		}
		return "redirect:/loan/loanList/0";
	}

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error() {
		logging("/error", "STW");
		return "redirect:/login";
	}
	
	
	
	@RequestMapping(value = { "/loan/loanList/{loanid}" }, method = RequestMethod.GET)
	public String loanListPage(Model model, @PathVariable long loanid) {
		Long lenderid = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			model.addAttribute("loans", this.LoanDAO.searchLoan(lenderid));
			if (loanid == 0) {
				model.addAttribute("editLoanForm", null);
				model.addAttribute("loanLogs", null);
			} else {
				model.addAttribute("editLoanForm", this.LoanDAO.getLoan(loanid));
				model.addAttribute("loanLogs", this.loanLogDAO.searchLoan(loanid));
			}
		} catch (Exception e) {
			logging("/loan/loanList/id", e.getMessage());
		}
		return "loanList";
	}
	
	@RequestMapping(value = { "/loan/addLoanPage" }, method = RequestMethod.GET)
	public String loanAddPage(Model model) {
		Long lenderid = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			LoanDTO loanForm = new LoanDTO();
			loanForm.setAmount(1000000);
			model.addAttribute("loanDTO", loanForm);
			model.addAttribute("borrowers", this.borrowerDAO.getComboboxData(lenderid));
		} catch (Exception e) {
			logging("/loan/addLoanPage", e.getMessage());
		}
		return "addLoan";
	}
	
	@RequestMapping(value = { "/loan/saveLoan" }, method = RequestMethod.POST)
	public String saveLoan(Model model, @Valid LoanDTO loanDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/loan/addLoanPage";
		}
		Long lenderid = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		loanDTO.setLenderid(lenderid);
		try {
			if (!this.LoanDAO.insert(loanDTO)) {
				logging("/loan/saveLoan", "STW");
			}
		} catch (Exception e) {
			logging("/loan/saveLoan", e.getMessage());
		}
		
		return "redirect:/loan/addLoanPage";
	}

	@RequestMapping(value = { "/loan/editLoan" }, method = RequestMethod.POST)
	public String editLoan(Model model, @Valid EditLoanForm editLoanForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/loan/loanList/" + editLoanForm.getId();
		}
		
		try {
			if (!this.LoanDAO.update(editLoanForm)) {
				logging("/loan/editLoan", "STW");
			}
		} catch (Exception e) {
			logging("/loan/editLoan", e.getMessage());
		}
		
		return "redirect:/loan/loanList/" + editLoanForm.getId();
	}
	
	@RequestMapping(value = { "/borrower/borrowerList/{borrowerid}" }, method = RequestMethod.GET)
	public String borrowerListPage(Model model, @PathVariable long borrowerid) {
		try {
			Long lenderid = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
			List<BorrowerDTO> borrowers = this.borrowerDAO.searchBorrower(lenderid);
			model.addAttribute("borrowers", borrowers);
			if (borrowerid == 0) {
				model.addAttribute("borrowerDTO", new BorrowerDTO());
			} else {
				model.addAttribute("borrowerDTO", this.borrowerDAO.getBorrower(borrowerid));
			}

		} catch (Exception e) {
			logging("/borrower/borrowerList/id", e.getMessage());
		}
		return "borrowerList";
	}

	@RequestMapping(value = { "/borrower/addBorrower" }, method = RequestMethod.GET)
	public String addBorrowerPage(Model model) {
		model.addAttribute("borrowerDTO", new BorrowerDTO("insert"));

		return "borrower";
	}
	
	@RequestMapping(value = { "/borrower/saveBorrower" }, method = RequestMethod.POST)
	public String saveBorrower(Model model, @Valid BorrowerDTO borrowerDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "borrower";
		}

		Long lenderid = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		borrowerDTO.setLenderid(lenderid);
		if (borrowerDTO.getId() == 0) {
			try {
				if (!this.borrowerDAO.insertBorrower(borrowerDTO)) {
					logging("/borrower/saveBorrower_insert", "STW");
				}
			} catch (Exception e) {
				logging("/borrower/saveBorrower_insert", e.getMessage());
			}
			return "redirect:/borrower/borrowerList/0";
		} else {
			try {
				if (!this.borrowerDAO.updateBorrower(borrowerDTO)) {
					logging("/borrower/saveBorrower_update", "STW");
				}
			} catch (Exception e) {
				logging("/borrower/saveBorrower_update", e.getMessage());
			}
			return "redirect:/borrower/borrowerList/" + borrowerDTO.getId();
		}
	}

}
