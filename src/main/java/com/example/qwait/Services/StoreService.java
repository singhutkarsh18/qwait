package com.example.qwait.Services;

import com.example.qwait.DTOs.AddCustomerReq;
import com.example.qwait.Model.AppUser;
import com.example.qwait.Model.Customer;
import com.example.qwait.Model.Store;
import com.example.qwait.Repository.CustomerRepo;
import com.example.qwait.Repository.StoreRepository;
import com.example.qwait.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CustomerRepo customerRepo;
    private final UserRepository userRepository;

    public Customer addCustomer(AddCustomerReq addCustomerReq)
    {
        AppUser appUser = userRepository.findByUsername(addCustomerReq.getUsername()).orElseThrow(IllegalStateException::new);
        Store store = storeRepository.findById(addCustomerReq.getStoreId()).orElseThrow(IllegalStateException::new);
        Integer peopleCount = store.getPeopleCount()+1;
        Integer newCounter= ((peopleCount)%store.getCounter())==0?store.getCounter():(peopleCount)%store.getCounter();
        ZonedDateTime entryTime= ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        Customer customer = new Customer(addCustomerReq.getUsername(),appUser.getName(),newCounter,entryTime,store);

        int c=0;
        for(var i : store.getCustomers())
        {
            if(Objects.equals(i.getCounterNo(), newCounter))
                c++;
        }
        customer.setBillingTime(store.getBillingTime());
        customer.setWaitingTime((c+1)*(store.getBillingTime()));
        store.setPeopleCount(peopleCount);
        storeRepository.save(store);
        return customerRepo.save(customer);
    }
    public String removeCustomer(AddCustomerReq addCustomerReq)
    {
        AppUser appUser = userRepository.findByUsername(addCustomerReq.getUsername()).orElseThrow(IllegalStateException::new);
        Store store = storeRepository.findById(addCustomerReq.getStoreId()).orElseThrow(IllegalStateException::new);
        long c=0;
        for(var i:store.getCustomers())
        {
            if(i.getUsername().equals(appUser.getUsername()))
                c=i.getId();

        }
        if(c==0)
            throw new EntityNotFoundException("customer not found in queue");
        int newpeopleCount=store.getPeopleCount()-1;
        store.setPeopleCount(newpeopleCount);
        ZonedDateTime old =customerRepo.findById(c).get().getEntryTime();
        ChronoUnit unit=ChronoUnit.MINUTES;

        store.setWaitingTime(waitingTime(store,(int)unit.between(old, ZonedDateTime.now(ZoneId.of("Asia/Kolkata")))));
        storeRepository.save(store);
        customerRepo.deleteById(c);
        return "Customer removed";
    }
    public Integer waitingTime(Store store,Integer min)
    {
        return ( store.getWaitingTime() *(store.getPeopleCount())+min)/( store.getPeopleCount()+1);
    }
    public List<Store> getStores(String location)
    {
        var lon1 =  lon1 * Math.PI / 180;
        var lon2 = lon2 * Math.PI / 180;
        var lat1 = lat1 * Math.PI / 180;
        var lat2 = lat2 * Math.PI / 180;

        // Haversine formula
        var dlon = lon2 - lon1;
        var dlat = lat2 - lat1;
        var a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        var c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        var r = 6371;

        // calculate the result
        return (c * r);
    }
}
