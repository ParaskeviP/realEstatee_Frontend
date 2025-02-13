package gr.hua.dit.ds.grp41.rentalmanagement.rest;

import gr.hua.dit.ds.grp41.rentalmanagement.entities.Property;
import gr.hua.dit.ds.grp41.rentalmanagement.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyControllerRest {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/allProperties")
    public List<Property> getAllProperties(){
        return propertyService.getAllProperties();
    }

    @GetMapping("/getById/{propertyid}")
    public Property getById(@PathVariable Integer propertyid) {
//        if (propertyid == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Owner with ID: " + ownerid + "does not exist.");
//        }
        return propertyService.getById(propertyid);
    }

    @GetMapping("/getByRoomNumber/{roomNum}")
    public List<Property> getByRoomNumber(@PathVariable Integer roomNum) {
        return propertyService.getByRoomNumber(roomNum);
    }

    @GetMapping("/getByBedNumber/{bedNum}")
    public List<Property> getByBedNumber(@PathVariable Integer bedNum) {
        return propertyService.getByBedNumber(bedNum);
    }

    @GetMapping("/getByBathNumber/{bathNum}")
    public List<Property> getByBathNumber(@PathVariable Integer bathNum) {
        return propertyService.getByBathNumber(bathNum);
    }

    @GetMapping("/getByCity/{city}")
    public List<Property> getByCity(@PathVariable String city) {
        return propertyService.getByCity(city);
    }

    @GetMapping("/getBySize/{size}")
    public List<Property> getBySize(@PathVariable Integer size) {
        return propertyService.getBySize(size);
    }

    @GetMapping("/getByPrice/{price}")
    public List<Property> getByPrice(@PathVariable Long price) {
        return propertyService.getByPrice(price);
    }

    @GetMapping("/getByPetsAllowed/{petsAllowed}")
    public List<Property> getByPetsAllowed(@PathVariable Boolean petsAllowed) {
        return propertyService.getByPetsAllowed(petsAllowed);
    }

    @GetMapping("/getByHasGarden/{hasGarden}")
    public List<Property> getByHasGarden(@PathVariable Boolean hasGarden) {
        return propertyService.getByHasGarden(hasGarden);
    }

    @GetMapping("/getByHasBalcony/{hasBalcony}")
    public List<Property> getByHasBalcony(@PathVariable Boolean hasBalcony) {
        return propertyService.getByHasBalcony(hasBalcony);
    }

    @GetMapping("/getByHasHeating/{hasHeating}")
    public List<Property> getByHasHeating(@PathVariable Boolean hasHeating) {
        return propertyService.getByHasHeating(hasHeating);
    }

    @GetMapping("/getByIsFurnished/{isFurnished}")
    public List<Property> getByIsFurnished(@PathVariable Boolean isFurnished) {
        return propertyService.getByIsFurnished(isFurnished);
    }

    @GetMapping("/getByRenovYear/{renovationYear}")
    public List<Property> getByRenovationYear(@PathVariable Integer renovationYear) {
        return propertyService.getByRenovationYear(renovationYear);
    }

}
