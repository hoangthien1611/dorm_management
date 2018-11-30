package com.doan.dormmanagement.service;

import com.doan.dormmanagement.model.Action;

import java.util.List;

public interface ActionService {
    List<Action> getAllActionsByUserName(String username);

    List<Action> getAllActions();
}
