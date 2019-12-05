package com.gmail.michzuerch.anouman.ui.views;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

public interface HasConfirmation {

    ConfirmDialog getConfirmDialog();

    void setConfirmDialog(ConfirmDialog confirmDialog);
}
