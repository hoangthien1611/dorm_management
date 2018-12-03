package com.doan.dormmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group implements Serializable {

    private Integer id;
    @NotNull
    private String name;
    private List<Action> actions;
    private Integer totalAction;

    public Integer getTotalAction() {
        return actions != null ? actions.size() : 0;
    }

    public void setTotalAction(Integer totalAction) {
        this.totalAction = totalAction;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
