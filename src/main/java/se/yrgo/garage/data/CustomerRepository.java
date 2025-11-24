package se.yrgo.garage.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.yrgo.garage.domain.Customer;
import se.yrgo.garage.domain.Vehicle;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByName(String name);

    @Query("SELECT c.vehicles FROM Customer c WHERE c.id = :customerId")
    public List<Vehicle> getCustomersVehicles(@Param("customerId") Long id);

    @Query("SELECT c, v FROM Customer c LEFT JOIN c.vehicles v")
    public List<Object[]> findAllCustomersWithVehicles();
}
