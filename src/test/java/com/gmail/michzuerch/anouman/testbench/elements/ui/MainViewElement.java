package com.gmail.michzuerch.anouman.testbench.elements.ui;

import com.gmail.michzuerch.anouman.testbench.elements.components.AppNavigationElement;
import com.vaadin.flow.component.applayout.testbench.AppLayoutElement;

public class MainViewElement extends AppLayoutElement {

	public AppNavigationElement getMenu() {
		return $(AppNavigationElement.class).first();
	}

}
