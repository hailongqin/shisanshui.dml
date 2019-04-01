package com.dml.shisanshui.pan;

import java.util.List;

public abstract class PanResult {
	private long panFinishTime;
	private PanValueObject pan;

	public List<String> allPlayerIds() {
		return pan.allPlayerIds();
	}

	public long getPanFinishTime() {
		return panFinishTime;
	}

	public void setPanFinishTime(long panFinishTime) {
		this.panFinishTime = panFinishTime;
	}

	public PanValueObject getPan() {
		return pan;
	}

	public void setPan(PanValueObject pan) {
		this.pan = pan;
	}

}
