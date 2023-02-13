package com.pfm.response;

public class PFMResponse {
	private String id;
	private String name;

	public PFMResponse() {
	}

	public PFMResponse(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PFMResponse [id=" + id + ", name=" + name + "]";
	}

}
