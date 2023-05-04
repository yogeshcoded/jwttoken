package com.nt.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.model.UserInfo;
import com.nt.repo.IUserRepository;
@Service
public class UserService implements IUserService{
	@Autowired
	private IUserRepository repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Override
	public String saveUserInfo(UserInfo info) {
				info.setPassword(encoder.encode(info.getPassword()));
			return "user save id "+repo.save(info).getUid();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> opt=repo.findByUsername(username);
		if(opt.isEmpty())
			throw new UsernameNotFoundException("user not present");
		else {
			UserInfo info=opt.get();
			User user=new User(info.getUsername(),
					info.getPassword(),
					info.getRole()
					.stream()
					.map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
			
			return user;
		}
	}

}
