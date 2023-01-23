package com.example.wholesaler.service.impl;


import com.example.wholesaler.api.Wholesalers;
import com.example.wholesaler.kafka.KafkaPublishService;
import com.example.wholesaler.model.*;
import com.example.wholesaler.repository.WholesalerInventoryRepository;
import com.example.wholesaler.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;


@Service
public class WholesalerOperation implements Wholesalers {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WholesalerRepository wholesalerRepository;

    @Autowired
    WholesalerInventoryOperation wholesalerInventoryOperation;

    @Autowired
    KafkaPublishService kafkaPublishService;


    public static ArrayList<Wholesaler> wholesalerList = new ArrayList<>();

    @Override
    @Cacheable(value="Wholesaler", key="#wholeSalerId")

    public Wholesaler displayWholesaler(int wholeSalerId) {
        return wholesalerRepository.findById(wholeSalerId);
    }

    @Override
    public ArrayList<Wholesaler> displayAllProduct() {
        return (ArrayList<Wholesaler>) wholesalerRepository.findAll();
    }


    @Override
    public String addWholesaler(Wholesaler wholesaler) {
        wholesalerRepository.save(wholesaler);
        return "Successfully added the wholesaler";
    }

    @Override
    @CacheEvict(value="Wholesaler", key="#wholeSalerId")

    public String deleteWholesaler(int wholeSalerId) {
        wholesalerRepository.deleteById(wholeSalerId);

        return("delete Successfull");
    }

    //    @Override
    //    public ResponseEntity<String> updateProductToWholesaler(int wholesalerId,String wholesalerName, String wholesalerMailId,String wholesalerMobileNo) {
    //        Wholesaler wholesaler = wholesalerRepository.findById(wholesalerId);
    //        wholesaler.setWarehouse_name(wholesalerName);
    //        wholesaler.setContact_mail(wholesalerMailId);
    //        wholesaler.setContact_mobile_no(wholesalerMobileNo);;
    //        wholesalerRepository.save(wholesaler);
    //        return new ResponseEntity<>( "Updated the wholesaler data", HttpStatus.OK);
    //
    //    }

    @Override
    public String allocateProductToWholesaler(int wholesalerId,int warehouseId, int proid, int quantity,int payingAmount, int price) {

            boolean wholesalerNotFound = true;
            boolean productNotFound = true;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

//            if (wholesalerList.isEmpty()){return("\n Sorry !!No wholesalers to allocate\n\n");}
            if (quantity<0){return ("\n Please enter a valid number for quantity.\n\n");}

            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/warehouse/api/getAllProductsByWarehouseId/"+warehouseId)
                    .toUriString();

             ArrayList<WarehouseInventory> listOfWarehouseInventory = restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<ArrayList<WarehouseInventory>>(){}).getBody();

             for(WarehouseInventory warehouseInventory: listOfWarehouseInventory) {
                 if(warehouseInventory.getProduct_id() ==proid) {
                     productNotFound= false;
                     if (warehouseInventory.getStock() < quantity) {
                         return ("\n Stocks are low. kindly try a small number\n\n");
                     }

                      url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/product/api/getProductById/"+proid)
                             .toUriString();

                     Product pro = restTemplate.exchange(url, HttpMethod.GET, entity, Product.class).getBody();

                     int billAmount =  warehouseInventory.getStock()*quantity;
                     float discountPercentage=0;
                     float totalAfterOffer= billAmount - billAmount*discountPercentage;
                     float gstAmount = totalAfterOffer*(pro.gstPercentage/100f);
                     float grandBillAmount= totalAfterOffer+gstAmount;


                     WholesalerInventory wholesalerInventory = new WholesalerInventory(1, warehouseId, wholesalerId, proid, quantity,price);

                     wholesalerInventoryOperation.addProductToWholesaler(wholesalerInventory);


                     int reducedQuantity = warehouseInventory.getStock()-quantity;


                     url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/warehouse/api/updateProductToWareHouse?inventoryId="+warehouseInventory.getId()+"&quantity="+reducedQuantity)
                             .toUriString();

                      restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();


                     System.out.print("\ngrand bill amount :: "+grandBillAmount);
                     Product pros = new Product(1,pro.getProId(), pro.getProName(), price,pro.getGstPercentage());


                     Bill bill = new Bill(true,wholesalerId, "A HUB", quantity, billAmount,Math.round(discountPercentage), gstAmount, grandBillAmount,pros);

                     String billid = RandomStringUtils.randomAlphanumeric(10);

                     long millis=System.currentTimeMillis();
                     java.sql.Date date=new java.sql.Date(millis);

                     Float due_amount = grandBillAmount-payingAmount;
                     if (due_amount<=0){ due_amount=0f; }

                     Finance finance = new Finance(1,billid,"WHOLESALER",wholesalerId, warehouseId,proid, grandBillAmount, due_amount, date);
                     kafkaPublishService.sendBillingInformation(bill);
                     kafkaPublishService.sendFinanceInformation(finance);

                     return ("Succesfully Purchased. \n Your bill id for reference is : "+billid);

                 }

             }
            if(productNotFound){ return ("\n Sorry !!Products not found!\n\n"); }

            System.out.println("\n <--------------------------------------------------> \n");

         return("Exited due to an error");
    }

    @Override
    public String payPendingDue(String billId, int amount) {
        kafkaPublishService.sendDueInformation(billId,amount);
        return ("Kafka request published ");
    }
//
//    @Override
//    public String updateWholesalerProduct(int wholesalerId, int proid, int quantity) {
//        boolean flag =false;
//        for(Wholesaler whole: wholesalerList){
//            if(whole.getWholesale_id() == wholesalerId){
//                for(Product pro: whole.getWholesale_products()){
//                    pro.setStock(quantity);
//                    flag=true;
//                }
//            }
//        }
//        if(flag)return("update wholesaler Product detail updated Successfully");
//        else return("no products or wholesaler found");
//    }


}
