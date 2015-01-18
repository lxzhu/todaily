package net.lxzhu.todaily.dto;

public class DropDownItemBase implements DropDownItem {

	public DropDownItemBase(int value, String text) {
		this.value = value;
		this.text = text;
	}

	protected int value;
	protected String text;

	public void setValue(int value) {
		this.value = value;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}

}
