package com.ainzson.usermanagementservice.mapper;

import com.ainzson.usermanagementservice.dto.AddressDTO;
import com.ainzson.usermanagementservice.entities.Address;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AddressMapper {

    public static Set<AddressDTO> toDto(Set<Address> address) {

        Set<AddressDTO> addressDTOS = new HashSet<>();

        for (Address addr : address) {
            AddressDTO dto = new AddressDTO();
            dto.setId(addr.getId());
            dto.setCity(addr.getCity());
            dto.setState(addr.getState());
            dto.setPostalCode(addr.getPostalCode());
            dto.setCountry(addr.getCountry());
            dto.setStreet(addr.getStreet());
            addressDTOS.add(dto);
        }
        return addressDTOS;
    }


    public static AddressDTO toSingleDto(Address address) {


        AddressDTO addressDTOS = new AddressDTO();
        addressDTOS.setId(address.getId());
        addressDTOS.setCity(address.getCity());
        addressDTOS.setState(address.getState());
        addressDTOS.setPostalCode(address.getPostalCode());
        addressDTOS.setCountry(address.getCountry());
        addressDTOS.setStreet(address.getStreet());

        return addressDTOS;
    }

    public static Set<Address> toEntity(Set<AddressDTO> addressDTO) {
        Set<Address> addresses = new HashSet<>();
        for (AddressDTO dto : addressDTO) {
            Address addr = new Address();
            addr.setCity(dto.getCity());
            addr.setState(dto.getState());
            addr.setPostalCode(dto.getPostalCode());
            addr.setCountry(dto.getCountry());
            addr.setStreet(dto.getStreet());
            addresses.add(addr);
        }
        return addresses;
    }
}
