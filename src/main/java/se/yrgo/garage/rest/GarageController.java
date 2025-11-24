package se.yrgo.garage.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.garage.data.CustomerRepository;
import se.yrgo.garage.data.VehicleRepository;
import se.yrgo.garage.domain.Customer;
import se.yrgo.garage.domain.Vehicle;
import se.yrgo.garage.dto.CustomerDTO;
import se.yrgo.garage.dto.VehicleDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GarageController {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public GarageController(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    //Skapar en kund
    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestParam String name,
                                                 @RequestParam(required = false) String phoneNumber) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
    }
    //Skapar ett fordon
    @PostMapping("/vehicles")
    public ResponseEntity<String> createVehicle(@RequestParam String registrationNumber,
                                                @RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String model,
                                                @RequestParam(required = false) String productionYear) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setProductionYear(productionYear);
        vehicleRepository.save(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle created successfully");
    }
    //Koppla ett fordon till en kund
    @PostMapping("/connect")
    public ResponseEntity<String> connectVehicleToCustomer(@RequestParam Long customerId, @RequestParam Long vehicleId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if (customer != null && vehicle != null) {
            customer.getVehicles().add(vehicle);
            customerRepository.save(customer);
            vehicle.setOwner(customer);
            vehicleRepository.save(vehicle);
            return ResponseEntity.ok("Vehicle successfully connected to customer");
        } else {
            return ResponseEntity.badRequest().body("Invalid customer or vehicle");
        }
    }
    //returnera en lista av alla kunder med fordon
    @GetMapping("/customers")
    public List<CustomerDTO> allCustomersWithVehicles() {
        List<Object[]> customersAndVehicles = customerRepository.findAllCustomersWithVehicles();
        Map<Long, CustomerDTO> customerMap = new HashMap<>();

        for (Object[] result : customersAndVehicles) {
            Customer customer = (Customer) result[0];
            Vehicle vehicle = (Vehicle) result[1];

            CustomerDTO customerDTO = customerMap.getOrDefault(customer.getId(), new CustomerDTO());
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());

            if (vehicle != null) {
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setId(vehicle.getId());
                vehicleDTO.setRegistrationNumber(vehicle.getRegistrationNumber());
                vehicleDTO.setBrand(vehicle.getBrand());
                vehicleDTO.setModel(vehicle.getModel());
                vehicleDTO.setProductionYear(vehicle.getProductionYear());
                customerDTO.getVehicles().add(vehicleDTO);
            }
            customerMap.put(customer.getId(), customerDTO);
        }
        return new ArrayList<>(customerMap.values());
    }
    //returnerar en lista av alla fordon
    @GetMapping("/vehicles")
    public List<Vehicle> allVehicles() {
        List<Vehicle> all = vehicleRepository.findAll();
        return all;
    }
    //returnera alla fordon med deras märke
    @GetMapping("/vehicles-by-brand")
    public List<Vehicle> vehiclesByBrand(@RequestParam String brand) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByBrand(brand);
        return vehicles;
    }

    //tar kundens namn och returnerar id
    @GetMapping("/get-customer-id")
    public ResponseEntity<Long> getCustomerIdFromParameter(@RequestParam String name) {
        Customer customer = customerRepository.findByName(name);
        return ResponseEntity.ok(customer.getId());
    }
}
