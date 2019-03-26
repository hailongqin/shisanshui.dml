package com.dml.shisanshui.pan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.position.Position;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.player.PlayerNotFoundException;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionCalculator;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ShisanshuiChupaiAction;

public class Pan {

	private int no;

	private Map<String, ShisanshuiPlayer> playerIdPlayerMap = new HashMap<>();

	private Map<Position, String> positionPlayerIdMap = new HashMap<>();

	private List<PukePai> avaliablePaiList = new ArrayList<>();

	private List<PanActionFrame> actionFrameList = new ArrayList<>();

	public PanActionFrame recordPanActionFrame(ShisanshuiChupaiAction action, long actionTime) {
		PanActionFrame frame = new PanActionFrame(action, new PanValueObject(this), actionTime);
		frame.setNo(actionFrameList.size());
		actionFrameList.add(frame);
		return frame;
	}

	public void addPlayer(String playerId) {
		ShisanshuiPlayer player = new ShisanshuiPlayer();
		player.setId(playerId);
		playerIdPlayerMap.put(playerId, player);
	}

	public void generateAllChupaiPaixingSolution(ChupaiPaixingSolutionCalculator chupaiPaixingSolutionCalculator) {
		for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
			player.putChupaiSolutionCandidates(
					chupaiPaixingSolutionCalculator.generateAllPaixingSolution(player.getAllShoupai()));
		}
	}

	public void generateChupaiPaixingSolutionsForTips(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
		for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
			player.generateChupaiSolutionForTips(chupaiPaixingSolutionFilter);
		}
	}

	public void updatePlayerPositionByPlayerId(String playerId, Position position) throws PlayerNotFoundException {
		if (!playerIdPlayerMap.containsKey(playerId)) {
			throw new PlayerNotFoundException();
		}
		ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
		player.setPosition(position);
		positionPlayerIdMap.put(position, playerId);
	}

	public ShisanshuiChupaiAction chupai(String playerId, String paixingIndex) throws Exception {
		if (!playerIdPlayerMap.containsKey(playerId)) {
			throw new PlayerNotFoundException();
		}
		ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
		PaixingSolution solution = player.chupai(paixingIndex);
		ShisanshuiChupaiAction action = new ShisanshuiChupaiAction(playerId, solution);
		return action;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Map<String, ShisanshuiPlayer> getPlayerIdPlayerMap() {
		return playerIdPlayerMap;
	}

	public void setPlayerIdPlayerMap(Map<String, ShisanshuiPlayer> playerIdPlayerMap) {
		this.playerIdPlayerMap = playerIdPlayerMap;
	}

	public Map<Position, String> getPositionPlayerIdMap() {
		return positionPlayerIdMap;
	}

	public void setPositionPlayerIdMap(Map<Position, String> positionPlayerIdMap) {
		this.positionPlayerIdMap = positionPlayerIdMap;
	}

	public List<PukePai> getAvaliablePaiList() {
		return avaliablePaiList;
	}

	public void setAvaliablePaiList(List<PukePai> avaliablePaiList) {
		this.avaliablePaiList = avaliablePaiList;
	}

	public List<PanActionFrame> getActionFrameList() {
		return actionFrameList;
	}

	public void setActionFrameList(List<PanActionFrame> actionFrameList) {
		this.actionFrameList = actionFrameList;
	}

}
