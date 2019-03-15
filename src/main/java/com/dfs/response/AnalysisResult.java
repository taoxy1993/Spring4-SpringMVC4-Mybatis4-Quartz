package com.dfs.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 算法分析结果
 * @author taoxy 2018/12/21
 */
public class AnalysisResult implements Serializable {
	private String content;
	private String imageUrl;
	private String result;
	private int score;
	private int wikiId;
	private List<Double> box;
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int getWikiId() {
		return wikiId;
	}

	public void setWikiId(int wikiId) {
		this.wikiId = wikiId;
	}

	public List<Double> getBox() {
		return box;
	}

	public void setBox(List<Double> box) {
		this.box = box;
	}
}
