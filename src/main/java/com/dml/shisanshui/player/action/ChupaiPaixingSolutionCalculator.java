package com.dml.shisanshui.player.action;

import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public interface ChupaiPaixingSolutionCalculator {

	List<PaixingSolution> generateAllPaixingSolution(Map<Integer, PukePai> allShoupai);

}
