package com.tananaev.jsonpatch.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tananaev.jsonpatch.JsonPath;

public class MoveOperation extends AbsOperation {

	private JsonPath from;

	public MoveOperation() {
		/* Empty default constructor */
	}

	public MoveOperation(JsonPath from, JsonPath path) {
		this.path = path;
		this.from = from;
	}

	@Override
	public String getOperationName() {
		return "move";
	}

	public JsonPath getFrom() {
		return from;
	}

	public void setFrom(JsonPath from) {
		this.from = from;
	}

	public JsonElement apply(JsonElement original, boolean remove) {
		JsonElement result = duplicate(original);

		JsonElement value = from.navigate(result);

		JsonElement source = from.head().navigate(result);
		JsonElement destination = path.head().navigate(result);

		if (remove) {
			if (source.isJsonObject()) {
				source.getAsJsonObject().remove(from.tail());
			} else if (source.isJsonArray()) {
				JsonArray array = source.getAsJsonArray();

				int index = (from.tail().equals("-")) ? array.size() : Integer.valueOf(from.tail());

				array.remove(index);
			}
		}

		if (destination.isJsonObject()) {
			destination.getAsJsonObject().add(path.tail(), value);
		} else if (destination.isJsonArray()) {

			JsonArray array = destination.getAsJsonArray();

			int index = (path.tail().equals("-")) ? array.size() : Integer.valueOf(path.tail());

			List<JsonElement> temp = new ArrayList<>();

			Iterator<JsonElement> iter = array.iterator();
			while (iter.hasNext()) {
				JsonElement stuff = iter.next();
				iter.remove();
				temp.add(stuff);
			}

			temp.add(index, value);

			for (JsonElement stuff : temp) {
				array.add(stuff);
			}
		}

		return result;
	}

	@Override
	public JsonElement apply(JsonElement original) {
		return apply(original, true);
	}

}
