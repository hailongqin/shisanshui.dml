package com.dml.shisanshui.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.player.action.ChupaiException;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ToudaoDayuZhongdaoException;
import com.dml.shisanshui.player.action.ZhongdaoDayuWeidaoException;
import com.dml.shisanshui.position.Position;

public class ShisanshuiPlayer {
	/**
	 * 玩家id
	 */
	private String id;
	/**
	 * 玩家座位
	 */
	private Position position;
	/**
	 * 玩家手牌
	 */
	private Map<Integer, PukePai> allShoupai = new HashMap<>();
	/**
	 * 玩家出牌方案
	 */
	private Map<String, Dao> chupaiSolutionCandidates = new HashMap<>();
	/**
	 * 玩家出牌提示
	 */
	private List<PaixingSolution> chupaiSolutionForTips = new ArrayList<>();

	/**
	 * 最终出牌
	 */
	private PaixingSolution chupaiSolution;

	public void addShoupai(PukePai pukePai) {
		allShoupai.put(pukePai.getId(), pukePai);
	}

	public boolean chuwanpai() {
		return chupaiSolution != null;
	}

	public PaixingSolution chupai(String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
			DaoComparator daoComparator) throws Exception {
		Dao toudao = chupaiSolutionCandidates.get(toudaoIndex);
		Dao zhongdao = chupaiSolutionCandidates.get(zhongdaoIndex);
		Dao weidao = chupaiSolutionCandidates.get(weidaoIndex);
		PaixingSolution solution = new PaixingSolution();
		solution.setToudao(toudao);
		solution.setZhongdao(zhongdao);
		solution.setWeidao(weidao);
		if (toudao == null || zhongdao == null || weidao == null) {
			throw new ChupaiException();
		}
		if (daoComparator.compare(toudao, zhongdao) > 0) {
			throw new ToudaoDayuZhongdaoException();
		}
		if (daoComparator.compare(zhongdao, weidao) > 0) {
			throw new ZhongdaoDayuWeidaoException();
		}
		Map<Integer, PukePai> allPai = new HashMap<>();
		for (PukePai pukePai : toudao.getPukePaiList()) {
			if (!allShoupai.containsKey(pukePai.getId())) {
				throw new ChupaiException();
			}
			allPai.put(pukePai.getId(), pukePai);
		}
		for (PukePai pukePai : zhongdao.getPukePaiList()) {
			if (!allShoupai.containsKey(pukePai.getId())) {
				throw new ChupaiException();
			}
			allPai.put(pukePai.getId(), pukePai);
		}
		for (PukePai pukePai : weidao.getPukePaiList()) {
			if (!allShoupai.containsKey(pukePai.getId())) {
				throw new ChupaiException();
			}
			allPai.put(pukePai.getId(), pukePai);
		}
		if (allPai.size() != allShoupai.size()) {
			throw new ChupaiException();
		}
		chupaiSolution = solution;
		chupaiSolutionCandidates.clear();
		chupaiSolutionForTips.clear();
		return chupaiSolution;
	}

	public void putChupaiSolutionCandidates(List<Dao> daoList) {
		daoList.forEach((dao) -> {
			chupaiSolutionCandidates.put(dao.getIndex(), dao);
		});
	}

	public void generateChupaiSolutionForTips(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
		chupaiSolutionForTips = chupaiPaixingSolutionFilter.filter(allShoupai,
				new ArrayList<>(chupaiSolutionCandidates.values()));
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

	public Map<String, Dao> getChupaiSolutionCandidates() {
		return chupaiSolutionCandidates;
	}

	public void setChupaiSolutionCandidates(Map<String, Dao> chupaiSolutionCandidates) {
		this.chupaiSolutionCandidates = chupaiSolutionCandidates;
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

}
