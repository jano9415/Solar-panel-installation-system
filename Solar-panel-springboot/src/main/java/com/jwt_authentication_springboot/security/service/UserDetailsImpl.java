package com.jwt_authentication_springboot.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt_authentication_springboot.model.User;

//Implementálom a UserDetails interfészt, így meg tudom mondani a Spring Security-nak, hogy honnan vegye ki a felhasználói nevet, jelszót
//és szerepköröket.
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  //Ez tartalmazza a szerepköröket.
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(), 
        user.getUsername(), 
        user.getEmail(),
        user.getPassword(), 
        authorities);
  }

  //A szerepköröket a user szerepköreiből vedd ki.
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  //A bejelentkezéshez szükséges jelszó a user jelszava.
  @Override
  public String getPassword() {
    return password;
  }

  //A bejelentkezéshez szükséges felhasználói név a user felhasználói neve.
  @Override
  public String getUsername() {
    return username;
  }

  //Lejárt a felhasználói account?
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //Zárolva van a felhasználói account?
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  //Lejárt a hitelesítés?
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //Engedélyezve van a felhasználói account? Ezzel lehet email alapú regisztrációt csinálni. Regisztráció után a felhasználó ugyan létre jön
  //az adatbázisban, de ez a változója false értékű lesz, így nem tud bejelentkezni. Ha rákattint az email-ben kapott linkre, akkor ez az érték
  //true-ra változik, így utána már be tud jelentkezni a felhasználói fiókjába.
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
