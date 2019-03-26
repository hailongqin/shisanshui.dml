package com.dml.shisanshui.pai.paixing;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.shisanshui.pai.wanfa.BianXingWanFa;

/**
 * 一墩牌
 * 
 * @author lsc
 *
 */
public class Dun {

	private Paixing paixing;

	private Set<PukePai> pukePaiSet = new HashSet<>();

	/**
	 * 五枚（百变才有）＞一条龙＞同花顺>铁支>葫芦>同花>顺子>三条>两对>对子>乌龙.
	 */
	public void calculatePaixing(BianXingWanFa bx) {
		boolean tonghua = isTonghua();
		boolean shunzi = isShunzi();
		int wangpaiCount = countWangpai();
		int tongdianshuCount = countMaxTongdianshu();
		int duiziCount = countDuizi();
		if (duiziCount == 1) {
			paixing = Paixing.duizi;
		} else if (duiziCount == 2) {
			paixing = Paixing.liangdui;
		} else if (tongdianshuCount == 3) {
			paixing = Paixing.santiao;
		} else if (shunzi && !tonghua) {
			paixing = Paixing.shunzi;
		} else if (tonghua && !shunzi) {
			paixing = Paixing.tonghua;
		} else if (tongdianshuCount == 3 && duiziCount == 1) {
			paixing = Paixing.hulu;
		} else if (tongdianshuCount == 4) {
			paixing = Paixing.tiezhi;
		} else if (tonghua && shunzi) {
			paixing = Paixing.tonghuashun;
		} else if (tongdianshuCount == 4 && wangpaiCount == 1 && bx.equals(BianXingWanFa.baibian)) {
			paixing = Paixing.wumei;
		} else {
			paixing = Paixing.wulong;
		}
	}

	public boolean isShunzi() {
		SortedMap<Integer, PukePai> map = new TreeMap<>();
		for (PukePai pukePai : pukePaiSet) {
			map.put(pukePai.getId(), pukePai);
		}
		DianShu dianshu = null;
		for (PukePai pukePai : map.values()) {
			DianShu newDianshu = pukePai.getPaiMian().dianShu();
			if (dianshu == null || newDianshu.ordinal() - dianshu.ordinal() == 1) {
				dianshu = newDianshu;
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isTonghua() {
		HuaSe huase = null;
		for (PukePai pukePai : pukePaiSet) {
			HuaSe newHuase = pukePai.getPaiMian().huaSe();
			if (newHuase == null) {
				return false;
			} else if (huase == null) {
				huase = newHuase;
			} else if (!huase.equals(newHuase)) {
				return false;
			}
		}
		return true;
	}

	public int countWangpai() {
		int wangCount = 0;
		for (PukePai pukePai : pukePaiSet) {
			DianShu dianshu = pukePai.getPaiMian().dianShu();
			if (dianshu.ordinal() > 12) {
				wangCount++;
			}
		}
		return wangCount;
	}

	public int countMaxTongdianshu() {
		int[] dianshuAmountArray = new int[15];
		for (PukePai pukePai : pukePaiSet) {
			DianShu dianshu = pukePai.getPaiMian().dianShu();
			dianshuAmountArray[dianshu.ordinal()]++;
		}
		int max = 0;
		for (int amount : dianshuAmountArray) {
			if (amount > max) {
				max = amount;
			}
		}
		return max;
	}

	public int countDuizi() {
		int[] dianshuAmountArray = new int[15];
		for (PukePai pukePai : pukePaiSet) {
			DianShu dianshu = pukePai.getPaiMian().dianShu();
			dianshuAmountArray[dianshu.ordinal()]++;
		}
		int duiziCount = 0;
		for (int amount : dianshuAmountArray) {
			if (amount > 1) {
				duiziCount++;
			}
		}
		return duiziCount;
	}

	public Paixing getPaixing() {
		return paixing;
	}

	public void setPaixing(Paixing paixing) {
		this.paixing = paixing;
	}

	public Set<PukePai> getPukePaiSet() {
		return pukePaiSet;
	}

	public void setPukePaiSet(Set<PukePai> pukePaiSet) {
		this.pukePaiSet = pukePaiSet;
	}

}
