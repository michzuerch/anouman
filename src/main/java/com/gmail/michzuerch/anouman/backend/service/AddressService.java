package com.gmail.michzuerch.anouman.backend.service;

import com.gmail.michzuerch.anouman.backend.data.entity.Address;
import com.gmail.michzuerch.anouman.backend.data.entity.User;
import com.gmail.michzuerch.anouman.backend.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.function.BiConsumer;

public class AddressService implements CrudService<Address> {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        super();
        this.addressRepository = addressRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public Address saveAddress(User currentUser, Long id, BiConsumer<User, Address> addressFiller) {
        Address address;
        if (id == null) {
            address = new Address();
        } else {
            address = load(id);
        }
        addressFiller.accept(currentUser, address);
        return addressRepository.save(address);
    }

    @Transactional(rollbackOn = Exception.class)
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public JpaRepository<Address, Long> getRepository() {
        return addressRepository;
    }

    @Override
    @Transactional
    public Address createNew(User currentUser) {
        Address address = new Address();
        return address;
    }
}