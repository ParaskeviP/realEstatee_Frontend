package gr.hua.dit.ds.grp41.rentalmanagement.services;

import gr.hua.dit.ds.grp41.rentalmanagement.entities.Property;
import gr.hua.dit.ds.grp41.rentalmanagement.entities.Tenant;
import gr.hua.dit.ds.grp41.rentalmanagement.repositories.PropertyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepo propertyRepository;

    @Transactional
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    @Transactional
    public void deleteProperty(Integer id){
        propertyRepository.deleteById(id);
    }

    @Transactional
    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    @Transactional
    public Property getById(Integer id){
        return propertyRepository.findById(id).get();
    }

    @Transactional
    public List<Property> getByRoomNumber(Integer roomNum){
        return propertyRepository.findByRoomNum(roomNum);
    }

    @Transactional
    public List<Property> getByBedNumber(Integer bedNum){
        return propertyRepository.findByBedNum(bedNum);
    }

    @Transactional
    public List<Property> getByBathNumber(Integer bathNum){
        return propertyRepository.findByBathNum(bathNum);
    }

    @Transactional
    public List<Property> getByCity(String city){
        return propertyRepository.findByCity(city);
    }

    @Transactional
    public List<Property> getBySize(Integer size){
        return propertyRepository.findBySize(size);
    }

    @Transactional
    public List<Property> getByPrice(Long price){
        return propertyRepository.findByPrice(price);
    }

    @Transactional
    public List<Property> getByPetsAllowed(Boolean petsAllowed){
        return propertyRepository.findByPetsAllowed(petsAllowed);
    }

    @Transactional
    public List<Property> getByHasGarden(Boolean hasGarden){
        return propertyRepository.findByHasGarden(hasGarden);
    }

    @Transactional
    public List<Property> getByHasBalcony(Boolean hasBalcony){
        return propertyRepository.findByHasBalcony(hasBalcony);
    }

    @Transactional
    public List<Property> getByIsFurnished(Boolean isFurnished){
        return propertyRepository.findByIsFurnished(isFurnished);
    }

    @Transactional
    public List<Property> getByHasHeating(Boolean hasHeating){
        return propertyRepository.findByHasHeating(hasHeating);
    }

    @Transactional
    public List<Property> getByRenovationYear(Integer renovationYear){
        return propertyRepository.findByRenovationYear(renovationYear);
    }

    @Transactional
    public List<Property> getPropertiesByTenant(Tenant tenant){
        return propertyRepository.getPropertiesByTenant(tenant);
    }


}
