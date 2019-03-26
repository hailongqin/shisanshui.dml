package com.dml.shisanshui.ju;

import java.util.ArrayList;
import java.util.List;

import com.dml.shisanshui.gameprocess.CurrentPanFinishiDeterminer;
import com.dml.shisanshui.gameprocess.JuFinishiDeterminer;
import com.dml.shisanshui.pan.CurrentPanResultBuilder;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.pan.PanActionFrame;
import com.dml.shisanshui.pan.PanResult;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionCalculator;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ShisanshuiChupaiAction;
import com.dml.shisanshui.preparedapai.avaliablepai.AvaliablePaiFiller;
import com.dml.shisanshui.preparedapai.fapai.FapaiStrategy;
import com.dml.shisanshui.preparedapai.lluanpai.LuanpaiStrategy;

public class Ju {
	private Pan currentPan;
	private List<PanResult> finishedPanResultList = new ArrayList<>();
	private JuResult juResult;

	private CurrentPanFinishiDeterminer currentPanFinishiDeterminer;
	private JuFinishiDeterminer juFinishiDeterminer;

	private AvaliablePaiFiller avaliablePaiFiller;
	private LuanpaiStrategy luanpaiStrategyForFirstPan;
	private LuanpaiStrategy luanpaiStrategyForNextPan;
	private FapaiStrategy fapaiStrategyForFirstPan;
	private FapaiStrategy fapaiStrategyForNextPan;

	private ChupaiPaixingSolutionCalculator chupaiPaixingSolutionCalculator;
	private ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter;

	private CurrentPanResultBuilder currentPanResultBuilder;
	private JuResultBuilder juResultBuilder;

	public PanActionFrame chupai(String playerId, String paixingIndex, long actionTime) throws Exception {
		ShisanshuiChupaiAction action = currentPan.chupai(playerId, paixingIndex);
		if (currentPanFinishiDeterminer.determineToFinishCurrentPan(this)) {// 是否盘结束
			PanResult panResult = currentPanResultBuilder.buildCurrentPanResult(this, actionTime);
			finishedPanResultList.add(panResult);
			PanActionFrame panActionFrame = currentPan.recordPanActionFrame(action, actionTime);
			currentPan = null;
			if (juFinishiDeterminer.determineToFinishJu(this)) {// 是否局结束
				juResult = juResultBuilder.buildJuResult(this);
			}
			return panActionFrame;
		} else {
			return currentPan.recordPanActionFrame(action, actionTime);
		}
	}

	public PanActionFrame startFirstPan(List<String> allPlayerIds, long startTime) throws Exception {
		currentPan = new Pan();
		currentPan.setNo(1);
		allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));

		avaliablePaiFiller.fillAvaliablePai(this);

		// 先乱牌，再发牌
		luanpaiStrategyForFirstPan.luanpai(this);
		fapaiStrategyForFirstPan.fapai(this);
		// 生成所有合理的出牌方案
		currentPan.generateAllChupaiPaixingSolution(chupaiPaixingSolutionCalculator);
		// 出牌提示
		currentPan.generateChupaiPaixingSolutionsForTips(chupaiPaixingSolutionFilter);
		return currentPan.recordPanActionFrame(null, startTime);
	}

	public void startNextPan() throws Exception {
		currentPan = new Pan();
		currentPan.setNo(countFinishedPan() + 1);
		PanResult latestFinishedPanResult = findLatestFinishedPanResult();
		List<String> allPlayerIds = latestFinishedPanResult.allPlayerIds();
		allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));

		avaliablePaiFiller.fillAvaliablePai(this);

		// 先乱牌，再发牌，再理牌，再组队
		luanpaiStrategyForNextPan.luanpai(this);
		fapaiStrategyForNextPan.fapai(this);
		// 生成所有合理的出牌方案
		currentPan.generateAllChupaiPaixingSolution(chupaiPaixingSolutionCalculator);
		// 出牌提示
		currentPan.generateChupaiPaixingSolutionsForTips(chupaiPaixingSolutionFilter);
		currentPan.recordPanActionFrame(null, System.currentTimeMillis());
	}

	public int countFinishedPan() {
		return finishedPanResultList.size();
	}

	public PanResult findLatestFinishedPanResult() {
		if (!finishedPanResultList.isEmpty()) {
			return finishedPanResultList.get(finishedPanResultList.size() - 1);
		} else {
			return null;
		}
	}

	public Pan getCurrentPan() {
		return currentPan;
	}

	public void setCurrentPan(Pan currentPan) {
		this.currentPan = currentPan;
	}

	public List<PanResult> getFinishedPanResultList() {
		return finishedPanResultList;
	}

	public void setFinishedPanResultList(List<PanResult> finishedPanResultList) {
		this.finishedPanResultList = finishedPanResultList;
	}

	public JuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(JuResult juResult) {
		this.juResult = juResult;
	}

	public CurrentPanFinishiDeterminer getCurrentPanFinishiDeterminer() {
		return currentPanFinishiDeterminer;
	}

	public void setCurrentPanFinishiDeterminer(CurrentPanFinishiDeterminer currentPanFinishiDeterminer) {
		this.currentPanFinishiDeterminer = currentPanFinishiDeterminer;
	}

	public JuFinishiDeterminer getJuFinishiDeterminer() {
		return juFinishiDeterminer;
	}

	public void setJuFinishiDeterminer(JuFinishiDeterminer juFinishiDeterminer) {
		this.juFinishiDeterminer = juFinishiDeterminer;
	}

	public AvaliablePaiFiller getAvaliablePaiFiller() {
		return avaliablePaiFiller;
	}

	public void setAvaliablePaiFiller(AvaliablePaiFiller avaliablePaiFiller) {
		this.avaliablePaiFiller = avaliablePaiFiller;
	}

	public LuanpaiStrategy getLuanpaiStrategyForFirstPan() {
		return luanpaiStrategyForFirstPan;
	}

	public void setLuanpaiStrategyForFirstPan(LuanpaiStrategy luanpaiStrategyForFirstPan) {
		this.luanpaiStrategyForFirstPan = luanpaiStrategyForFirstPan;
	}

	public LuanpaiStrategy getLuanpaiStrategyForNextPan() {
		return luanpaiStrategyForNextPan;
	}

	public void setLuanpaiStrategyForNextPan(LuanpaiStrategy luanpaiStrategyForNextPan) {
		this.luanpaiStrategyForNextPan = luanpaiStrategyForNextPan;
	}

	public FapaiStrategy getFapaiStrategyForFirstPan() {
		return fapaiStrategyForFirstPan;
	}

	public void setFapaiStrategyForFirstPan(FapaiStrategy fapaiStrategyForFirstPan) {
		this.fapaiStrategyForFirstPan = fapaiStrategyForFirstPan;
	}

	public FapaiStrategy getFapaiStrategyForNextPan() {
		return fapaiStrategyForNextPan;
	}

	public void setFapaiStrategyForNextPan(FapaiStrategy fapaiStrategyForNextPan) {
		this.fapaiStrategyForNextPan = fapaiStrategyForNextPan;
	}

	public ChupaiPaixingSolutionCalculator getChupaiPaixingSolutionCalculator() {
		return chupaiPaixingSolutionCalculator;
	}

	public void setChupaiPaixingSolutionCalculator(ChupaiPaixingSolutionCalculator chupaiPaixingSolutionCalculator) {
		this.chupaiPaixingSolutionCalculator = chupaiPaixingSolutionCalculator;
	}

	public ChupaiPaixingSolutionFilter getChupaiPaixingSolutionFilter() {
		return chupaiPaixingSolutionFilter;
	}

	public void setChupaiPaixingSolutionFilter(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
		this.chupaiPaixingSolutionFilter = chupaiPaixingSolutionFilter;
	}

	public CurrentPanResultBuilder getCurrentPanResultBuilder() {
		return currentPanResultBuilder;
	}

	public void setCurrentPanResultBuilder(CurrentPanResultBuilder currentPanResultBuilder) {
		this.currentPanResultBuilder = currentPanResultBuilder;
	}

	public JuResultBuilder getJuResultBuilder() {
		return juResultBuilder;
	}

	public void setJuResultBuilder(JuResultBuilder juResultBuilder) {
		this.juResultBuilder = juResultBuilder;
	}

}
