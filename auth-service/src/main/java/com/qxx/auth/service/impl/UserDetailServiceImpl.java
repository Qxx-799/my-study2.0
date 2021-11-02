package com.qxx.auth.service.impl;

import com.qxx.auth.entity.User;
import com.qxx.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Assert.hasText(userName , "userName required !");
        User user = userRepository.findUserByName(userName);
        if (user == null){
            throw new UsernameNotFoundException(" 用户不存在 ");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return new org.springframework.security.core.userdetails.User(
                user.getName(), encoder.encode(user.getPassword()), AuthorityUtils.createAuthorityList(user.getHobby())
        );
    }
}
