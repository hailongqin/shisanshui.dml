package com.dml.shisanshui.player.action;

import java.util.List;

import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public interface ChupaiPaixingSolutionFilter {

	List<PaixingSolution> filter(List<Dao> daoList);
}
