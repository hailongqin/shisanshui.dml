package com.dml.shisanshui.pan;

import java.util.List;

public abstract class PanResult {
	private long panFinishTime;
	private PanValueObject pan;

	public List<String> allPlayerIds() {
		return pan.allPlayerIds();
	}

}
