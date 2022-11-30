package org.binar.eflightticket_b2.service;


import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;

public interface UserService {

    Users addUser(Users users);
    Users deleteUser(String username);
    Users getUserByUsername (String username);
    Users updateUser(Users users, String username);

    UsersDTO mapToDTO(Users users);
    Users mapToEntity(UsersDTO usersDTO);

    UserDetailRequest mapToUserDetailReq(Users users);
    Users mapToEntity(UserDetailRequest usersDTO);

}


