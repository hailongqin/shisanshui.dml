package com.dml.shisanshui.pai.paixing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dml.shisanshui.pai.DianShu;
import com.dml.shisanshui.pai.HuaSe;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.wanfa.BianXingWanFa;

/**
 * 一墩牌
 * 
 * @author lsc
 *
 */
public class Dun {

	private Paixing paixing;

	private List<PukePai> pukePaiList = new ArrayList<>();

	private int size;

	private void sortedPukePai() {
		LinkedList<PukePai> sortedList = new LinkedList<>();
		for (PukePai pukePai : pukePaiList) {
			if (sortedList.isEmpty()) {
				sortedList.add(pukePai);
			} else {
				int length = sortedList.size();
				int i = 0;
				while (i < length) {
					int compare = sortedList.get(i).getPaiMian().compareTo(pukePai.getPaiMian());
					if (compare > 0) {
						sortedList.add(i, pukePai);
						break;
					}
					if (i == length - 1) {
						sortedList.add(pukePai);
					}
					i++;
				}
			}
		}
		pukePaiList = new ArrayList<>(sortedList);
	}

	/**
	 * 五枚（百变才有）＞一条龙＞同花顺>铁支>葫芦>同花>顺子>三条>两对>对子>乌龙.
	 */
	public void calculatePaixing(BianXingWanFa bx) {
		size = pukePaiList.size();
		sortedPukePai();
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
		} else if (shunzi && !tonghua && size == 5) {
			paixing = Paixing.shunzi;
		} else if (tonghua && !shunzi && size == 5) {
			paixing = Paixing.tonghua;
		} else if (tongdianshuCount == 3 && duiziCount == 1 && size == 5) {
			paixing = Paixing.hulu;
		} else if (tongdianshuCount == 4) {
			paixing = Paixing.tiezhi;
		} else if (tonghua && shunzi && size == 5) {
			paixing = Paixing.tonghuashun;
		} else if (tongdianshuCount == 4 && wangpaiCount == 1 && bx.equals(BianXingWanFa.baibian)) {
			paixing = Paixing.wumei;
		} else {
			paixing = Paixing.wulong;
		}
	}

	public boolean isShunzi() {
		DianShu dianshu = null;
		for (PukePai pukePai : pukePaiList) {
			DianShu newDianshu = pukePai.getPaiMian().dianShu();
			// 2、3、4、5、A算顺子，2、3、4、K、A不算
			if (dianshu == null || newDianshu.ordinal() - dianshu.ordinal() == 1) {
				dianshu = newDianshu;
			} else {
				if (newDianshu.equals(DianShu.A) && dianshu.equals(DianShu.wu)) {
					dianshu = newDianshu;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isTonghua() {
		HuaSe huase = null;
		for (PukePai pukePai : pukePaiList) {
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
		for (PukePai pukePai : pukePaiList) {
			DianShu dianshu = pukePai.getPaiMian().dianShu();
			if (dianshu.ordinal() > 12) {
				wangCount++;
			}
		}
		return wangCount;
	}

	public int countMaxTongdianshu() {
		int[] dianshuAmountArray = new int[15];
		for (PukePai pukePai : pukePaiList) {
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
		for (PukePai pukePai : pukePaiList) {
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

	public List<PukePai> getPukePaiList() {
		return pukePaiList;
	}

	public void setPukePaiList(List<PukePai> pukePaiList) {
		this.pukePaiList = pukePaiList;
	}

}
