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
	 * 玩家对子出牌方案
	 */
	private List<List<PukePai>> duiziCandidates = new ArrayList<>();
	/**
	 * 玩家两对出牌方案
	 */
	private List<List<PukePai>> liangduiCandidates = new ArrayList<>();
	/**
	 * 玩家三条出牌方案
	 */
	private List<List<PukePai>> santiaoCandidates = new ArrayList<>();
	/**
	 * 玩家顺子出牌方案
	 */
	private List<List<PukePai>> shunziCandidates = new ArrayList<>();
	/**
	 * 玩家同花出牌方案
	 */
	private List<List<PukePai>> tonghuaCandidates = new ArrayList<>();
	/**
	 * 玩家葫芦出牌方案
	 */
	private List<List<PukePai>> huluCandidates = new ArrayList<>();
	/**
	 * 玩家铁支出牌方案
	 */
	private List<List<PukePai>> tiezhiCandidates = new ArrayList<>();
	/**
	 * 玩家同花顺出牌方案
	 */
	private List<List<PukePai>> tonghuashunCandidates = new ArrayList<>();
	/**
	 * 玩家五枚出牌方案
	 */
	private List<List<PukePai>> wumeiCandidates = new ArrayList<>();
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
		int[] dianshuArray = new int[15];
		for (PukePai pukePai : allShoupai.values()) {
			dianshuArray[pukePai.getPaiMian().dianShu().ordinal()]++;
		}
		for (int i = 0; i < dianshuArray.length; i++) {
			if (dianshuArray[i] == 2) {
				List<PukePai> duizi = new ArrayList<>();
				for (PukePai pukePai : allShoupai.values()) {
					if (pukePai.getPaiMian().dianShu().ordinal() == i) {
						duizi.add(pukePai);
					}
				}
				duiziCandidates.add(duizi);
			}
			if (dianshuArray[i] == 3) {
				List<PukePai> santiao = new ArrayList<>();
				for (PukePai pukePai : allShoupai.values()) {
					if (pukePai.getPaiMian().dianShu().ordinal() == i) {
						santiao.add(pukePai);
					}
				}
				santiaoCandidates.add(santiao);
			}
		}
		for (int m = 0; m < duiziCandidates.size() - 1; m++) {
			for (int n = m + 1; n < duiziCandidates.size(); n++) {
				List<PukePai> liangdui = new ArrayList<>();
				liangdui.addAll(duiziCandidates.get(m));
				liangdui.addAll(duiziCandidates.get(n));
				liangduiCandidates.add(liangdui);
			}
		}
		for (Dao dao : player.getChupaiSolutionCandidates().values()) {
			List<PukePai> pukePaiList = dao.getPukePaiList();
			if (pukePaiList.size() != 5) {
				continue;
			}
			Paixing paixing = dao.getPaixing();
			if (Paixing.shunzi.equals(paixing)) {
				if (shunziCandidates.size() > 10) {
					continue;
				}
				shunziCandidates.add(dao.getPukePaiList());
			} else if (Paixing.tonghua.equals(paixing)) {
				if (tonghuaCandidates.size() > 10) {
					continue;
				}
				tonghuaCandidates.add(dao.getPukePaiList());
			} else if (Paixing.hulu.equals(paixing)) {
				if (huluCandidates.size() > 10) {
					continue;
				}
				huluCandidates.add(dao.getPukePaiList());
			} else if (Paixing.tiezhi.equals(paixing)) {
				if (tiezhiCandidates.size() > 10) {
					continue;
				}
				tiezhiCandidates.add(dao.getPukePaiList());
			} else if (Paixing.tonghuashun.equals(paixing)) {
				if (tonghuashunCandidates.size() > 10) {
					continue;
				}
				tonghuashunCandidates.add(dao.getPukePaiList());
			} else if (Paixing.wumei.equals(paixing)) {
				if (wumeiCandidates.size() > 10) {
					continue;
				}
				wumeiCandidates.add(dao.getPukePaiList());
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

	public List<List<PukePai>> getDuiziCandidates() {
		return duiziCandidates;
	}

	public void setDuiziCandidates(List<List<PukePai>> duiziCandidates) {
		this.duiziCandidates = duiziCandidates;
	}

	public List<List<PukePai>> getLiangduiCandidates() {
		return liangduiCandidates;
	}

	public void setLiangduiCandidates(List<List<PukePai>> liangduiCandidates) {
		this.liangduiCandidates = liangduiCandidates;
	}

	public List<List<PukePai>> getSantiaoCandidates() {
		return santiaoCandidates;
	}

	public void setSantiaoCandidates(List<List<PukePai>> santiaoCandidates) {
		this.santiaoCandidates = santiaoCandidates;
	}

	public List<List<PukePai>> getShunziCandidates() {
		return shunziCandidates;
	}

	public void setShunziCandidates(List<List<PukePai>> shunziCandidates) {
		this.shunziCandidates = shunziCandidates;
	}

	public List<List<PukePai>> getTonghuaCandidates() {
		return tonghuaCandidates;
	}

	public void setTonghuaCandidates(List<List<PukePai>> tonghuaCandidates) {
		this.tonghuaCandidates = tonghuaCandidates;
	}

	public List<List<PukePai>> getHuluCandidates() {
		return huluCandidates;
	}

	public void setHuluCandidates(List<List<PukePai>> huluCandidates) {
		this.huluCandidates = huluCandidates;
	}

	public List<List<PukePai>> getTiezhiCandidates() {
		return tiezhiCandidates;
	}

	public void setTiezhiCandidates(List<List<PukePai>> tiezhiCandidates) {
		this.tiezhiCandidates = tiezhiCandidates;
	}

	public List<List<PukePai>> getTonghuashunCandidates() {
		return tonghuashunCandidates;
	}

	public void setTonghuashunCandidates(List<List<PukePai>> tonghuashunCandidates) {
		this.tonghuashunCandidates = tonghuashunCandidates;
	}

	public List<List<PukePai>> getWumeiCandidates() {
		return wumeiCandidates;
	}

	public void setWumeiCandidates(List<List<PukePai>> wumeiCandidates) {
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
