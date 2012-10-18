package ananas.lib.blueprint2.awt;

import java.awt.Color;
import java.awt.Font;

import ananas.lib.blueprint2.AbstractElement;
import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.INode;

public class AwtBaseElement extends AbstractElement {

	@Override
	public boolean appendChild(INode child) {
		return super.appendChild(child);
	}

	private String _strFromAttr(IAttr attr) {
		if (attr == null)
			return null;
		return attr.getValue();
	}

	public String getLocalString(String s) {
		return s;
	}

	public String stringFromAttr(IAttr attr) {
		String s = this._strFromAttr(attr);
		return s;
	}

	public long longFromAttr(IAttr attr) {
		String s = this._strFromAttr(attr);
		if (s == null)
			return 0;
		return Long.parseLong(s);
	}

	public boolean booleanFromAttr(IAttr attr) {
		String s = this._strFromAttr(attr);
		if (s == null)
			return false;
		return Boolean.parseBoolean(s);
	}

	public int intFromAttr(IAttr attr) {
		String s = this._strFromAttr(attr);
		if (s == null)
			return 0;
		return Integer.parseInt(s);
	}

	public double doubleFromAttr(IAttr attr) {
		String s = this._strFromAttr(attr);
		if (s == null)
			return 0;
		return Double.parseDouble(s);
	}

	public Color colorFromAttr(IAttr attr) {
		String s = attr.getValue();
		if (s.startsWith("#")) {
			// s = s.substring(1);
		}
		return Color.decode(s);
	}

	public Font fontFromAttr(IAttr attr) {
		String s = attr.getValue();
		return Font.decode(s);
	}

}
