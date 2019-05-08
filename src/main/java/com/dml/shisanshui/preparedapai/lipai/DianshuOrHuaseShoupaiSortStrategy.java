package com.dml.shisanshui.preparedapai.lipai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;

/**
 * 以点数或者花色为主的理牌策略
 * 
 * @author lsc
 *
 */
public class DianshuOrHuaseShoupaiSortStrategy implements ShoupaiSortStrategy {

	@Override
	public List<List<Integer>> sortShoupai(Map<Integer, PukePai> allShoupai) {
		List<List<Integer>> sortlists = new ArrayList<>();
		// 以点数为主的理牌
		List<Integer> dianshuSort = new ArrayList<>();
		sortlists.add(dianshuSort);
		List<PukePai> dianshuSortList = new LinkedList<>();
		for (PukePai pukePai : allShoupai.values()) {
			addPukePaiByDianshu(dianshuSortList, pukePai);
		}
		for (PukePai pukePai : dianshuSortList) {
			dianshuSort.add(pukePai.getId());
		}
		Collections.reverse(dianshuSort);
		// 以花色为主的理牌
		List<Integer> huaseSort = new ArrayList<>();
		sortlists.add(huaseSort);
		List<PukePai> huaseSortList = new LinkedList<>();
		for (PukePai pukePai : allShoupai.values()) {
			addPukePaiByDianshu(huaseSortList, pukePai);
		}
		for (PukePai pukePai : huaseSortList) {
			huaseSort.add(pukePai.getId());
		}
		return sortlists;
	}

	private void addPukePaiByDianshu(List<PukePai> sortList, PukePai pai) {
		if (sortList.isEmpty()) {
			sortList.add(pai);
		} else {
			for (int i = 0; i < sortList.size(); i++) {
				int compare = comparePaimian(pai, sortList.get(i));
				if (compare > 0) {
					if (i >= sortList.size() - 1) {
						sortList.add(pai);
						return;
					} else {
						continue;
					}
				} else {
					sortList.add(i, pai);
					return;
				}
			}
		}
	}

	private int comparePaimian(PukePai pai1, PukePai pai2) {
		int compare = pai1.getPaiMian().compareTo(pai2.getPaiMian());
		if (compare == 0) {
			return pai1.getId() - pai2.getId();
		} else {
			return compare;
		}
	}

	private void addPukePaiByHuase(List<PukePai> sortList, PukePai pai) {
		if (sortList.isEmpty()) {
			sortList.add(pai);
		} else {
			for (int i = 0; i < sortList.size(); i++) {
				int compare = compareHuase(pai, sortList.get(i));
				if (compare > 0) {
					if (i >= sortList.size() - 1) {
						sortList.add(pai);
						return;
					} else {
						continue;
					}
				} else {
					sortList.add(i, pai);
					return;
				}
			}
		}
	}

	private int compareHuase(PukePai pai1, PukePai pai2) {
		int compare = pai1.getPaiMian().huaSe().compareTo(pai2.getPaiMian().huaSe());
		if (compare == 0) {
			return pai1.getId() - pai2.getId();
		} else {
			return compare;
		}
	}

}
