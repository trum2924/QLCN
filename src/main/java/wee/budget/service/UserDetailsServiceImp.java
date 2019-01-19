package wee.budget.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import wee.budget.dao.LenderDAO;
import wee.budget.dto.LenderDTO;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private LenderDAO lenderDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LenderDTO lenderDTO = this.lenderDAO.findLender(username);

		if (lenderDTO == null) {
			throw new UsernameNotFoundException("");
		}

		List<GrantedAuthority> grantList = new ArrayList<>();
		grantList.add(new SimpleGrantedAuthority(lenderDTO.getRole()));

		UserDetails userDetails = (UserDetails) new User(lenderDTO.getId().toString(), lenderDTO.getPassword(), grantList);

		return userDetails;
	}

}