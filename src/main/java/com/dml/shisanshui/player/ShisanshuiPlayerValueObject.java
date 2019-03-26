package com.dml.shisanshui.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.position.Position;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public class ShisanshuiPlayerValueObject {
	private String id;
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

	public ShisanshuiPlayerValueObject() {

	}

	public ShisanshuiPlayerValueObject(ShisanshuiPlayer player) {
		id = player.getId();
		position = player.getPosition();
		allShoupai.putAll(player.getAllShoupai());
		chupaiSolutionCandidates.putAll(player.getChupaiSolutionCandidates());
		chupaiSolutionForTips.addAll(player.getChupaiSolutionForTips());
		chupaiSolution = player.getChupaiSolution();
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
