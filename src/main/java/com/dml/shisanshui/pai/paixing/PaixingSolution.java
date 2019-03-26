package com.dml.shisanshui.pai.paixing;

import java.util.SortedMap;
import java.util.TreeMap;

import com.dml.puke.pai.PukePai;

/**
 * 用于比较的牌型方案
 * 
 * @author lsc
 *
 */
public class PaixingSolution {

	/**
	 * 牌型索引
	 */
	private String paixingIndex;

	/**
	 * 头道
	 */
	private Dun toudao;

	/**
	 * 中道
	 */
	private Dun zhongdao;

	/**
	 * 尾道
	 */
	private Dun weidao;

	/**
	 * 计算牌型索引，牌的id按从小到大的顺序拼接，小于10的左边补零
	 */
	public void calculateIndex() {
		SortedMap<Integer, PukePai> map = new TreeMap<>();
		for (PukePai pukePai : toudao.getPukePaiSet()) {
			map.put(pukePai.getId(), pukePai);
		}
		for (PukePai pukePai : zhongdao.getPukePaiSet()) {
			map.put(pukePai.getId(), pukePai);
		}
		for (PukePai pukePai : weidao.getPukePaiSet()) {
			map.put(pukePai.getId(), pukePai);
		}
		StringBuilder sb = new StringBuilder();
		for (Integer id : map.keySet()) {
			if (id < 10) {
				sb.append('0');
			}
			sb.append(id);
		}
		paixingIndex = sb.toString();
	}

	/**
	 * 判断是否一条龙
	 */
	public boolean isYitiaolong() {
		int[] dianshuAmountArray = new int[15];
		for (PukePai pukePai : toudao.getPukePaiSet()) {
			dianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
		}
		for (PukePai pukePai : zhongdao.getPukePaiSet()) {
			dianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
		}
		for (PukePai pukePai : weidao.getPukePaiSet()) {
			dianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
		}
		for (int amount : dianshuAmountArray) {
			if (amount != 1) {
				return false;
			}
		}
		return true;
	}

	public String getPaixingIndex() {
		return paixingIndex;
	}

	public void setPaixingIndex(String paixingIndex) {
		this.paixingIndex = paixingIndex;
	}

	public Dun getToudao() {
		return toudao;
	}

	public void setToudao(Dun toudao) {
		this.toudao = toudao;
	}

	public Dun getZhongdao() {
		return zhongdao;
	}

	public void setZhongdao(Dun zhongdao) {
		this.zhongdao = zhongdao;
	}

	public Dun getWeidao() {
		return weidao;
	}

	public void setWeidao(Dun weidao) {
		this.weidao = weidao;
	}

}
