package com.example.wholesaler.service.impl;


import com.example.wholesaler.api.Wholesalers;
import com.example.wholesaler.exception.UserDefinedException;
import com.example.wholesaler.model.Product;
import com.example.wholesaler.model.Wholesaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class WholesalerOperation implements Wholesalers {
    @Autowired
    RestTemplate restTemplate;
    public static ArrayList<Wholesaler> wholesalerList = new ArrayList<>();

    @Override
    public Wholesaler displayWholesaler(int wholesaleId){
        try{
            if (wholesalerList.isEmpty()){throw new UserDefinedException("\n Sorry !!No wholesaler to Display\n\n");}
            for(Wholesaler n : wholesalerList){
                    if(n.getWholesale_id() ==wholesaleId){
                        return n;
                }
                System.out.println("\n <--------------------------------------------------> \n");
            }
        }catch(UserDefinedException ud){
            System.out.println(ud.getMessage());
        }
        return null;
    }


    @Override
    public String addWholesaler(Wholesaler wholesaler) {
        wholesalerList.add(wholesaler);
        return "Successfully added the wholesaler";
    }

    @Override
    public String deleteWholesaler(int wholeSalerId) {
        if (wholesalerList.isEmpty()){return ("\n Sorry !!No wholesaler to delete\n\n");}
        int pos = 0;
        int trigger=-1;
        for(Wholesaler n : wholesalerList){
            if(n.getWholesale_id() ==wholeSalerId){
                trigger=pos;
            }pos++;
        }
        if(trigger>=0){
            wholesalerList.remove(trigger);
            return("delete Successfull");
        }else return("\n Sorry !!wholesale id not found enter an another id\n\n");
    }

    @Override
    public String allocateProductToWholesaler(int wholesalerId, int proid, int quantity, int price) {

            boolean wholesalerNotFound = true;
            boolean productNotFound = true;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            if (wholesalerList.isEmpty()){return("\n Sorry !!No wholesalers to allocate\n\n");}
            if (quantity<0){return ("\n Please enter a valid number for quantity.\n\n");}


            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/product/api/getProduct")
                    .queryParam("productId",proid).toUriString();

             Product pro = restTemplate.exchange(url, HttpMethod.GET, entity, Product.class).getBody();

            if (pro.getStock()< quantity){return("\n Stocks are low. kindly try a small number\n\n");}

            for(Wholesaler n : wholesalerList){
                if(n.getWholesale_id() == wholesalerId){
                    wholesalerNotFound =false;
                        if(pro.proId== proid){
                            productNotFound= false;
                            int billAmount =  pro.price*quantity;
//                            float discountPercentage = pro.discount.get(pro.discount.floorKey(quantity))/100f;
                            float discountPercentage=0;
                            float totalAfterOffer= billAmount - billAmount*discountPercentage;
                            float gstAmount = totalAfterOffer*(18/100f);
                            float grandBillAmount= totalAfterOffer+gstAmount;

                            System.out.print("\ngrand bill amount :: "+grandBillAmount);
                            Product pros = new Product(pro.proId, pro.proName, quantity, price,pro.gstPercentage);
                            n.setWholesale_products(pros);
                            quantity=pro.stock-quantity;


                             url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/product/api/updateProduct")
                                    .queryParam("productId",proid)
                                    .queryParam("quantity",quantity)
                                    .toUriString();
                             restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();



                            System.out.println("\n\n\n\t\t\t<------Bill Amount----->");
                            System.out.println("\n\t\tProduct :: "+ pro.proName + "\n\t\tQuantity :: "+quantity);
//                            System.out.print("\n\t\tBill amount :: "+billAmount+"\n\t\tOffer percentage :: "+pro.discount.get(pro.discount.floorKey(quantity))+"%");
                            System.out.print("\n\t\tGST Percentage :: 18%\n\t\tGST Amount :: "+gstAmount);
                            System.out.print("\n\t\tGrand bill amount :: "+grandBillAmount);

                            return("Sucessfully Purchased");

                    }

                }
            }
            if(wholesalerNotFound){ return("\n Sorry !!Wholesaler not found!\n\n"); }
            if(productNotFound){ return ("\n Sorry !!Products not found!\n\n"); }

            System.out.println("\n <--------------------------------------------------> \n");

         return("Exited due to an error");
    }

    @Override
    public String updateWholesalerProduct(int wholesalerId, int proid, int quantity) {
        boolean flag =false;
        for(Wholesaler whole: wholesalerList){
            if(whole.getWholesale_id() == wholesalerId){
                for(Product pro: whole.getWholesale_products()){
                    pro.setStock(quantity);
                    flag=true;
                }
            }
        }
        if(flag)return("update wholesaler Product detail updated Successfully");
        else return("no products or wholesaler found");
    }


}
