/**
 *
 */
package com.gmail.michzuerch.anouman.ui.crud;

import com.gmail.michzuerch.anouman.app.security.CurrentUser;
import com.gmail.michzuerch.anouman.backend.data.entity.Order;
import com.gmail.michzuerch.anouman.backend.service.OrderService;
import com.gmail.michzuerch.anouman.ui.views.storefront.StorefrontView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PresenterFactory {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public EntityPresenter<Order, StorefrontView> orderEntityPresenter(OrderService crudService, CurrentUser currentUser) {
		return new EntityPresenter<>(crudService, currentUser);
	}

}
