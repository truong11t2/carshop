package learn.carbackend.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import learn.carbackend.persistence.UserEntity;
import learn.carbackend.persistence.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	
	private UserDetails userDetails;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepo.findByUsername(username);
		if(user.isPresent()) {
			UserEntity currentUser = user.get();
			userDetails = UserDetailsImpl.build(currentUser);
		} else {
			throw new UsernameNotFoundException("User not found");
		}
		return userDetails;
	}
}
