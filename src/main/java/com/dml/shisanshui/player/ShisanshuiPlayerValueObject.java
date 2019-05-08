package com.dml.shisanshui.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.position.Position;

public class ShisanshuiPlayerValueObject {
	private String id;
	private Position position;
	/**
	 * 玩家手牌
	 */
	private Map<Integer, PukePai> allShoupai = new HashMap<>();
	private List<List<Integer>> shoupaiIdListForSortList;
	private int totalShoupai;
	/**
	 * 玩家乌龙出牌方案
	 */
	private List<Dao> wulongCandidates = new ArrayList<>();
	/**
	 * 玩家对子出牌方案
	 */
	private List<Dao> duiziCandidates = new ArrayList<>();
	/**
	 * 玩家两对出牌方案
	 */
	private List<Dao> liangduiCandidates = new ArrayList<>();
	/**
	 * 玩家三条出牌方案
	 */
	private List<Dao> santiaoCandidates = new ArrayList<>();
	/**
	 * 玩家顺子出牌方案
	 */
	private List<Dao> shunziCandidates = new ArrayList<>();
	/**
	 * 玩家同花出牌方案
	 */
	private List<Dao> tonghuaCandidates = new ArrayList<>();
	/**
	 * 玩家葫芦出牌方案
	 */
	private List<Dao> huluCandidates = new ArrayList<>();
	/**
	 * 玩家铁支出牌方案
	 */
	private List<Dao> tiezhiCandidates = new ArrayList<>();
	/**
	 * 玩家同花顺出牌方案
	 */
	private List<Dao> tonghuashunCandidates = new ArrayList<>();
	/**
	 * 玩家五枚出牌方案
	 */
	private List<Dao> wumeiCandidates = new ArrayList<>();
	/**
	 * 玩家出牌提示
	 */
	private List<PaixingSolution> chupaiSolutionForTips = new ArrayList<>();

	/**
	 * 最终出牌
	 */
	private PaixingSolution chupaiSolution;

	public ShisanshuiPlayerValueObject() {

	}

	public ShisanshuiPlayerValueObject(ShisanshuiPlayer player) {
		id = player.getId();
		position = player.getPosition();
		allShoupai.putAll(player.getAllShoupai());
		shoupaiIdListForSortList = new ArrayList<>(player.getShoupaiIdListForSortList());
		totalShoupai = allShoupai.size();
		for (Dao dao : player.getChupaiSolutionCandidates().values()) {
			List<PukePai> pukePaiList = dao.getPukePaiList();
			if (pukePaiList.size() != 5) {
				continue;
			}
			Paixing paixing = dao.getPaixing();
			if (Paixing.wulong.equals(paixing)) {
				if (wulongCandidates.size() > 10) {
					continue;
				}
				wulongCandidates.add(dao);
			} else if (Paixing.duizi.equals(paixing)) {
				if (duiziCandidates.size() > 10) {
					continue;
				}
				duiziCandidates.add(dao);
			} else if (Paixing.liangdui.equals(paixing)) {
				if (liangduiCandidates.size() > 10) {
					continue;
				}
				liangduiCandidates.add(dao);
			} else if (Paixing.santiao.equals(paixing)) {
				if (santiaoCandidates.size() > 10) {
					continue;
				}
				santiaoCandidates.add(dao);
			} else if (Paixing.shunzi.equals(paixing)) {
				if (shunziCandidates.size() > 10) {
					continue;
				}
				shunziCandidates.add(dao);
			} else if (Paixing.tonghua.equals(paixing)) {
				if (tonghuaCandidates.size() > 10) {
					continue;
				}
				tonghuaCandidates.add(dao);
			} else if (Paixing.hulu.equals(paixing)) {
				if (huluCandidates.size() > 10) {
					continue;
				}
				huluCandidates.add(dao);
			} else if (Paixing.tiezhi.equals(paixing)) {
				if (tiezhiCandidates.size() > 10) {
					continue;
				}
				tiezhiCandidates.add(dao);
			} else if (Paixing.tonghuashun.equals(paixing)) {
				if (tonghuashunCandidates.size() > 10) {
					continue;
				}
				tonghuashunCandidates.add(dao);
			} else if (Paixing.wumei.equals(paixing)) {
				if (wumeiCandidates.size() > 10) {
					continue;
				}
				wumeiCandidates.add(dao);
			}
		}
		chupaiSolutionForTips.addAll(player.getChupaiSolutionForTips());
		chupaiSolution = player.getChupaiSolution();
	}

	public List<List<Integer>> getShoupaiIdListForSortList() {
		return shoupaiIdListForSortList;
	}

	public void setShoupaiIdListForSortList(List<List<Integer>> shoupaiIdListForSortList) {
		this.shoupaiIdListForSortList = shoupaiIdListForSortList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Map<Integer, PukePai> getAllShoupai() {
		return allShoupai;
	}

	public void setAllShoupai(Map<Integer, PukePai> allShoupai) {
		this.allShoupai = allShoupai;
	}

	public List<Dao> getWulongCandidates() {
		return wulongCandidates;
	}

	public void setWulongCandidates(List<Dao> wulongCandidates) {
		this.wulongCandidates = wulongCandidates;
	}

	public List<Dao> getDuiziCandidates() {
		return duiziCandidates;
	}

	public void setDuiziCandidates(List<Dao> duiziCandidates) {
		this.duiziCandidates = duiziCandidates;
	}

	public List<Dao> getLiangduiCandidates() {
		return liangduiCandidates;
	}

	public void setLiangduiCandidates(List<Dao> liangduiCandidates) {
		this.liangduiCandidates = liangduiCandidates;
	}

	public List<Dao> getSantiaoCandidates() {
		return santiaoCandidates;
	}

	public void setSantiaoCandidates(List<Dao> santiaoCandidates) {
		this.santiaoCandidates = santiaoCandidates;
	}

	public List<Dao> getShunziCandidates() {
		return shunziCandidates;
	}

	public void setShunziCandidates(List<Dao> shunziCandidates) {
		this.shunziCandidates = shunziCandidates;
	}

	public List<Dao> getTonghuaCandidates() {
		return tonghuaCandidates;
	}

	public void setTonghuaCandidates(List<Dao> tonghuaCandidates) {
		this.tonghuaCandidates = tonghuaCandidates;
	}

	public List<Dao> getHuluCandidates() {
		return huluCandidates;
	}

	public void setHuluCandidates(List<Dao> huluCandidates) {
		this.huluCandidates = huluCandidates;
	}

	public List<Dao> getTiezhiCandidates() {
		return tiezhiCandidates;
	}

	public void setTiezhiCandidates(List<Dao> tiezhiCandidates) {
		this.tiezhiCandidates = tiezhiCandidates;
	}

	public List<Dao> getTonghuashunCandidates() {
		return tonghuashunCandidates;
	}

	public void setTonghuashunCandidates(List<Dao> tonghuashunCandidates) {
		this.tonghuashunCandidates = tonghuashunCandidates;
	}

	public List<Dao> getWumeiCandidates() {
		return wumeiCandidates;
	}

	public void setWumeiCandidates(List<Dao> wumeiCandidates) {
		this.wumeiCandidates = wumeiCandidates;
	}

	public List<PaixingSolution> getChupaiSolutionForTips() {
		return chupaiSolutionForTips;
	}

	public void setChupaiSolutionForTips(List<PaixingSolution> chupaiSolutionForTips) {
		this.chupaiSolutionForTips = chupaiSolutionForTips;
	}

	public PaixingSolution getChupaiSolution() {
		return chupaiSolution;
	}

	public void setChupaiSolution(PaixingSolution chupaiSolution) {
		this.chupaiSolution = chupaiSolution;
	}

	public int getTotalShoupai() {
		return totalShoupai;
	}

	public void setTotalShoupai(int totalShoupai) {
		this.totalShoupai = totalShoupai;
	}

}
