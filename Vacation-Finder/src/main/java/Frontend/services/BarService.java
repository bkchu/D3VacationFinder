package Frontend.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jlutteringer on 1/21/16.
 */

@Service
public class BarService {
	public BarResponse bar(BarRequest request) {
		List<String> generatedWords = Lists.newArrayList();
		for (int i = 0; i < request.getRepeats(); i++) {
			generatedWords.add(request.value);
		}

		Map<String, String> attributes = Maps.newHashMap();
		if (request.getExtraKey() != null) {
			attributes.put(request.getExtraKey(), request.getExtraValue());
		}

		attributes.putAll(request.getAdditionalAttributes());

		return new BarResponse(generatedWords, attributes);
	}


	public static class BarRequest {
		private String value;
		private int repeats = 0;

		private String extraKey;
		private String extraValue;

		private Map<String, String> additionalAttributes = Maps.newHashMap();

		public BarRequest(String value, int repeats, String extraKey, String extraValue, Map<String, String> additionalAttributes) {
			this.value = value;
			this.repeats = repeats;
			this.extraKey = extraKey;
			this.extraValue = extraValue;
			this.additionalAttributes = additionalAttributes;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getRepeats() {
			return repeats;
		}

		public void setRepeats(int repeats) {
			this.repeats = repeats;
		}

		public String getExtraKey() {
			return extraKey;
		}

		public void setExtraKey(String extraKey) {
			this.extraKey = extraKey;
		}

		public String getExtraValue() {
			return extraValue;
		}

		public void setExtraValue(String extraValue) {
			this.extraValue = extraValue;
		}

		public Map<String, String> getAdditionalAttributes() {
			return additionalAttributes;
		}

		public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
			this.additionalAttributes = additionalAttributes;
		}
	}

	public static class BarResponse {
		private List<String> generatedWords = Lists.newArrayList();
		private Map<String, String> attributes = Maps.newHashMap();

		public BarResponse(List<String> generatedWords, Map<String, String> attributes) {
			this.generatedWords = generatedWords;
			this.attributes = attributes;
		}

		public List<String> getGeneratedWords() {
			return generatedWords;
		}

		public void setGeneratedWords(List<String> generatedWords) {
			this.generatedWords = generatedWords;
		}

		public Map<String, String> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}
	}
}
