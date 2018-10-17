package com.tananaev.jsonpatch.operation;

import com.google.gson.JsonElement;

public class CopyOperation extends MoveOperation {

	@Override
	public String getOperationName() {
		return "copy";
	}

	@Override
	public JsonElement apply(JsonElement original) {
		return apply(original, false);
	}

}
