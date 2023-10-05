package com.rentacar.contactmessage;

import com.rentacar.payload.request.ContactMessageRequest;
import com.rentacar.payload.response.ContactMessageResponse;
import com.rentacar.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/ContactMessage")
@RequiredArgsConstructor
public class ContactMessageController {
    private  ContactMessageService contactMessageService;

      //          save () *******
      @PostMapping("/save")  // http://localhost:8080/contactMessages/save    + POST
      public ResponseMessage<ContactMessageResponse> save(@RequestBody
                                                                  @Valid ContactMessageRequest contactMessageRequest) {
          return contactMessageService.save(contactMessageRequest);
      }


    //    getAll()  **********
    @GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll( @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "sort", defaultValue = "date") String sort,
                                                @RequestParam(value = "type", defaultValue = "desc") String type){
          return  contactMessageService.getAll(page,size,sort,type);

    }

    // not: searchByEmail() ****************************************************
    @GetMapping("/searchByEmail")  // http://localhost:8080/contactMessages/searchByEmail?email=xxx@yyy.com  +  GET
    public Page<ContactMessageResponse> searchByEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.searchByEmail(email,page,size,sort,type);
    }

    // not: searchBySubject() **************************************************
    @GetMapping("/searchBySubject") // http://localhost:8080/contactMessages/searchBySubject?subject=deneme  + GET

    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "date") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return contactMessageService.searchBySubject(subject,page,size,sort,type);
    }
    // not: deleteWithId() **************************************************
    String x = "Hello World";

}
