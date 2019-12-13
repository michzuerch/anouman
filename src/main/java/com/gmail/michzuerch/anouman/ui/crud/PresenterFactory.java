/**
 *
 */
package com.gmail.michzuerch.anouman.ui.crud;

import com.gmail.michzuerch.anouman.app.security.CurrentUser;
import com.gmail.michzuerch.anouman.ui.views.address.AddressView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PresenterFactory {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public EntityPresenter<Order, AddressView> addressEntityPresenter(AddressService crudService, CurrentUser currentUser) {
        return new EntityPresenter<>(crudService, currentUser);
    }

}
