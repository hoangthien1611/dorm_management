package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Action;
import com.doan.dormmanagement.model.Role;

public interface RoleService {
    boolean addRole(Role role);

    boolean delRole(Integer groupId, Integer actionId);

    boolean addActionToGroup(Integer groupId, Integer actionId);
}
