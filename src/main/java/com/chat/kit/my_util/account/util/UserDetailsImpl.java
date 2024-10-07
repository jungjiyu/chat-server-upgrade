package com.chat.kit.my_util.account.util;

import com.chat.kit.my_util.account.entity.Account;
import com.chat.kit.my_util.account.entity.Memberr;
import com.chat.kit.my_util.account.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(()-> account.getRole().getAuthority());
    }
    public String getNickname(){
        Memberr member = account.getMember();
        return member.getNickname();
    }

    public Memberr getMember(){
        return account.getMember();
    }

    public Long getId() {
        return account.getId();
    }

    public String getEmail(){
        return account.getEmail();
    }

    public String getPhoneNumber(){
        return account.getPhoneNumber();
    }

    public Role getRole() {
        return account.getRole();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        //return String.valueOf(member.getId());
        return String.valueOf(account.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
