package ananas.lib.blueprint2.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import ananas.lib.blueprint2.dom.IAttr;

public class GridLayoutWrapper extends ObjectWrapper implements
		LayoutManagerWrapper {

	private IAttr mRows;
	private IAttr mColumns;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;

		} else if (name.equals("rows")) {
			this.mRows = attr;
		} else if (name.equals("columns")) {
			this.mColumns = attr;

		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	public void onTagBegin() {
		super.onTagBegin();
		GridLayout gl = (GridLayout) this.getTarget(true);
		int rows = this.intFromAttr(this.mRows);
		int cols = this.intFromAttr(this.mColumns);
		gl.setRows(rows);
		gl.setColumns(cols);
	}

	@Override
	public LayoutManager getLayoutManager(boolean create) {
		return (LayoutManager) this.getTarget(create);
	}

	@Override
	public void addComponentToContainer(Component component,
			Container container, String position) {

		container.add(component);
	}

}
