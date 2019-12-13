package com.gmail.michzuerch.anouman.ui.views.address;

import com.gmail.michzuerch.anouman.app.HasLogger;
import com.gmail.michzuerch.anouman.app.security.CurrentUser;
import com.gmail.michzuerch.anouman.backend.data.Role;
import com.gmail.michzuerch.anouman.backend.data.entity.Address;
import com.gmail.michzuerch.anouman.backend.service.AddressService;
import com.gmail.michzuerch.anouman.backend.repositories.AddressRepository;
import com.gmail.michzuerch.anouman.ui.MainView;
import com.gmail.michzuerch.anouman.ui.i18n.I18nConst;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static com.gmail.michzuerch.anouman.ui.i18n.I18nConst.PAGE_ADDRESSES;

@Route(value = PAGE_ADDRESSES, layout = MainView.class)
@RouteAlias(value = I18nConst.PAGE_ROOT, layout = MainView.class)
@PageTitle(I18nConst.TITLE_ADDRESSES)
@Secured(Role.ADMIN)
public class AddressView extends Div implements HasLogger {
    @Autowired
    public AddressView(AddressRepository addressRepository, CurrentUser currentUser) {
        Grid<Address> grid = new Grid<>(Address.class);
        //grid.addColumns("id", "localeCode", "continentCode", "continentName", "countryIsoCode", "countryName", "cityName");
        grid.setItems(addressRepository.findAll());
        add(grid);
    }


}