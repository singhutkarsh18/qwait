package com.example.qwait.Services;

import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;
import com.example.qwait.DTOs.LocationDTO;
import com.example.qwait.DTOs.StoreDetailsDTO;
import com.example.qwait.Model.AppUser;
import com.example.qwait.Model.Customer;
import com.example.qwait.Model.Store;
import com.example.qwait.Repository.CustomerRepo;
import com.example.qwait.Repository.StoreRepository;
import com.example.qwait.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CustomerRepo customerRepo;
    private final UserRepository userRepository;

    public Customer addCustomer(String username,Long storeId)
    {
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(IllegalStateException::new);
        store.setPeople(store.getPeople());
        int minI=0,min=0;
        for(int i=0;i<3;i++)
        {
            if(store.getUser1()<min)
            {
                min=store.getUser1();
                minI=i;
            }
        }
        Customer customer=new Customer(appUser.getUsername(),appUser.getName(),minI,ZonedDateTime.now(ZoneId.of("Asia/Kolkata")),store);
        customer.setWaitingTime(store.getBillingTime()+min);
        store.setWaitingTime(store.getBillingTime()+min);
        storeRepository.save(store);
        return customerRepo.save(customer);
    }
//    public String removeCustomer(String username,Long storeId)
//    {
//        AppUser appUser = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
//        Store store = storeRepository.findById(storeId).orElseThrow(IllegalStateException::new);
//        long c=0;
//        for(var i:store.getCustomers())
//        {
//            if(i.getUsername().equals(appUser.getUsername()))
//                c=i.getId();
//
//        }
//        if(c==0)
//            throw new EntityNotFoundException("customer not found in queue");
//        int newPeopleCount=store.getPeopleCount()-1;
//        store.setPeopleCount(newPeopleCount);
//        ZonedDateTime old =customerRepo.findById(c).get().getEntryTime();
//        ChronoUnit unit=ChronoUnit.MINUTES;
//
//        store.setWaitingTime(waitingTime(store,(int)unit.between(old, ZonedDateTime.now(ZoneId.of("Asia/Kolkata")))));
//        storeRepository.save(store);
//        customerRepo.deleteById(c);
//        return "Customer removed";
//    }

//    public Integer waitingTime(Store store,Integer min)
//    {
//        return ( store.getWaitingTime() *(store.getPeopleCount())+min)/( store.getPeopleCount()+1);
//    }
    public List<Store> getStores(LocationDTO locationDTO)
    {

        List<Store> storeList = new ArrayList<>();
        for(Store store:storeRepository.findAll())
        {
            if(inRange(locationDTO,store)<2.0)
                storeList.add(store);
        }
        return storeList;
    }
    public double inRange(LocationDTO locationDTO,Store store)
    {

        var lon1 = Double.parseDouble(locationDTO.getLongitude())* Math.PI / 180;
        var lon2 = Double.parseDouble(store.getLongitude()) * Math.PI / 180;
        var lat1 = Double.parseDouble(locationDTO.getLatitude()) * Math.PI / 180;
        var lat2 = Double.parseDouble(store.getLatitude()) * Math.PI / 180;

        var dlon = lon2 - lon1;
        var dlat = lat2 - lat1;
        var a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        var c = 2 * Math.asin(Math.sqrt(a));

        var r = 6371;
        System.out.println((c * r));
        return (c * r);
    }
    public String addDetails( StoreDetailsDTO storeDetailsDTO, String username)
    {
        System.out.println(username);
        Store store = storeRepository.findById(userRepository.findByUsername(username).get().getStore().getId()).get();
        store.setName(storeDetailsDTO.getName());
        store.setBillingTime(storeDetailsDTO.getBillingTime());
        store.setCounter(storeDetailsDTO.getCounter());
        store.setLatitude(storeDetailsDTO.getLatitude());
        store.setLongitude(storeDetailsDTO.getLongitude());
        store.setFrm(storeDetailsDTO.getFrm());
        store.setTo(storeDetailsDTO.getTo());
        store.setAbout(storeDetailsDTO.getAbout());
        storeRepository.save(store);
        return "Details saved";
    }

    public Set<Customer> getCustomersInStore(Long storeId)
    {
        if(storeRepository.findById(storeId).isEmpty())
            throw new NoSuchElementException("Store not present");
        return storeRepository.findById(storeId).get().getCustomers();
    }

    public Store getStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(IllegalStateException::new);
        return store;
    }

    public List<List<Customer>> getQueue(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(IllegalStateException::new);
        List<List<Customer>> a= new ArrayList<>();
        for(int i=0;i<store.getCounter();i++)
        {
            a.add(new ArrayList<>());
        }
        for(Customer customer: store.getCustomers())
        {
            a.get(customer.getCounterNo()).add(customer);
        }
        return a;
    }

//    public List<Store> getCustomerQueue(String username) {
//
//    }
//    public List<Store> getUserInQueue(String username)
//    {
//        return customerRepo.findAllByUsername(username).orElseThrow(NoSuchElementException::new);
//    }
}
