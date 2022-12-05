package com.s_rafid.smartpot.Controller;

import com.s_rafid.smartpot.Model.Tree;

import java.util.ArrayList;
import java.util.List;

public interface IDashboardController {
    void OnLogout();
    boolean addTree(String name,String auth);
    ArrayList<Tree> getTree(List<String> list,List<String> listAuth);
}
