/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gmail.michzuerch.anouman.frontend;

import com.gmail.michzuerch.anouman.frontend.page.HomePage;
import com.gmail.michzuerch.anouman.frontend.page.PushTestPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import de.kaesdingeling.hybridmenu.HybridMenu;
import de.kaesdingeling.hybridmenu.components.*;
import de.kaesdingeling.hybridmenu.data.MenuConfig;
import de.kaesdingeling.hybridmenu.design.DesignItem;

@PageTitle("Anouman 1.0.0-SNAPSHOT")
@Push(PushMode.AUTOMATIC)
@Theme(Lumo.class)
@Viewport("width=device-width")
@BodySize(height = "100vh", width = "100vw")
@HtmlImport("shared-styles.html")
public class MainLayout extends HybridMenu {
    @Override
    public boolean init(VaadinSession vaadinSession, UI ui) {
        withConfig(MenuConfig.get().withDesignItem(DesignItem.getWhiteDesign()));

        TopMenu topMenu = getTopMenu();

        topMenu.add(HMButton.get()
                .withIcon(VaadinIcon.HOME)
                .withDescription("Home")
                .withNavigateTo(HomePage.class));

        getNotificationCenter()
                .setNotiButton(topMenu.add(HMButton.get()
                        .withDescription("Notifications")));


        LeftMenu leftMenu = getLeftMenu();

        Image logo = new Image("./frontend/button.svg", "Logo");

        leftMenu.add(HMLabel.get()
                .withCaption("<b>Anouman</b> Version 1.0.0")
                .withIcon(logo));

        getBreadCrumbs().setRoot(leftMenu.add(HMButton.get()
                .withCaption("Home")
                .withIcon(VaadinIcon.HOME)
                .withNavigateTo(HomePage.class)));

        HMSubMenu crud = leftMenu.add(HMSubMenu.get()
                .withCaption("CRUD")
                .withIcon(VaadinIcon.COMPILE));

//        crud.add(HMButton.get()
//                .withCaption("Blocks")
//                .withIcon(VaadinIcon.ENVELOPE)
//                .withNavigateTo(BlockPage.class));
//
//        crud.add(HMButton.get()
//                .withCaption("Locations")
//                .withIcon(VaadinIcon.LAPTOP)
//                .withNavigateTo(LocationPage.class));

        HMSubMenu tests = leftMenu.add(HMSubMenu.get()
                .withCaption("Test")
                .withIcon(VaadinIcon.TEETH));

//        tests.add(HMButton.get().withCaption("Upload").withIcon(VaadinIcon.UMBRELLA).withNavigateTo(UploadPage.class));
//        tests.add(HMButton.get().withIcon(VaadinIcon.VIMEO).withCaption("Database Test").withNavigateTo(DatabaseTestPage.class));
//        tests.add(HMButton.get().withIcon(VaadinIcon.OFFICE).withCaption("Dialog Test").withNavigateTo(DialogTestPage.class));
        tests.add(HMButton.get().withIcon(VaadinIcon.CLOCK).withCaption("Push Test").withNavigateTo(PushTestPage.class));

        return true; // build menu
    }
}
