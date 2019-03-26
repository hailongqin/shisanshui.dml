package com.dml.shisanshui.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.position.Position;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.player.action.ChupaiException;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;

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
	private Map<String, PaixingSolution> chupaiSolutionCandidates = new HashMap<>();
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

	public PaixingSolution chupai(String paixingIndex) throws Exception {
		PaixingSolution solution = chupaiSolutionCandidates.get(paixingIndex);
		if (solution == null) {
			throw new ChupaiException();
		}
		chupaiSolution = solution;
		chupaiSolutionCandidates.clear();
		chupaiSolutionForTips.clear();
		return chupaiSolution;
	}

	public void putChupaiSolutionCandidates(List<PaixingSolution> solutions) {
		solutions.forEach((solution) -> {
			chupaiSolutionCandidates.put(solution.getPaixingIndex(), solution);
		});
	}

	public void generateChupaiSolutionForTips(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
		chupaiSolutionForTips = chupaiPaixingSolutionFilter.filter(new ArrayList<>(chupaiSolutionCandidates.values()));
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

	public Map<String, PaixingSolution> getChupaiSolutionCandidates() {
		return chupaiSolutionCandidates;
	}

	public void setChupaiSolutionCandidates(Map<String, PaixingSolution> chupaiSolutionCandidates) {
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
