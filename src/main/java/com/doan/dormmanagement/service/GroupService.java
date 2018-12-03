package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Group;

import java.util.List;

public interface GroupService {

    List<Group> getAllGroup();

    boolean addGroup(Group group);

    boolean editGroup(Group group);

    boolean delGroup(Integer groupId);
}
