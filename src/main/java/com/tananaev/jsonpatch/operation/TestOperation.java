package com.tananaev.jsonpatch.operation;

import java.util.Objects;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.tananaev.jsonpatch.JsonPath;

public class TestOperation extends AbsOperation {

	@SerializedName("value")
	public JsonElement data;

	public TestOperation() {
		/* Empty default contructor */
	}

	public TestOperation(JsonPath path, JsonElement data) {
		this.data = data;
		this.path = path;
	}

	@Override
	public String getOperationName() {
		return "test";
	}

	@Override
	public JsonElement apply(JsonElement original) {

		JsonElement result = duplicate(original);

		JsonElement item = path.navigate(result);

		if (Objects.equals(item, data)) {
			return result;
		}

		return null;
	}

}
