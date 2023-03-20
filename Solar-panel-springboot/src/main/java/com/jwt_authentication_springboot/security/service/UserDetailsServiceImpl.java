package com.jwt_authentication_springboot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt_authentication_springboot.model.User;
import com.jwt_authentication_springboot.repository.UserRepositroy;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
  @Autowired
  UserRepositroy userRepository;

  //Ahhoz, hogy be tudjuk jelentkezni, a Spring Security alapértelmezett adatai: - felhasználói név: user
  //																			 - jelszó: rendom generált jelszó, ami megjelenik a konzol felületen.
  //Ezzel a fügvénnyel ezt az alap működést felül írom.
  //A felhasználó által megadott névvel megkeresem a felhasználót az adatbázisban.
  //Ha nem található akkor UsernameNotFoundException kivétel.
  //Ha megvan, akkor visszatérek egy UserDetailsImpl objektummal, ami tartalmazza: - id
  //																			   - felhasználói név
  //																			   - email cím
  //																			   - jelszó
  //																			   - szerepkörök
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

}