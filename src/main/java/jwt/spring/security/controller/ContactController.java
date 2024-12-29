package jwt.spring.security.controller;

import jakarta.validation.Valid;
import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.ContactDto;
import jwt.spring.security.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contact/")
public class ContactController {

    ContactService contactService;
    @Autowired
    ContactController(ContactService contactService){
        this.contactService=contactService;
    }

    //create contact
    @PostMapping
    public CustomResponseEntity<ContactDto> createNewContact(@Valid @RequestBody ContactDto contactDto){
       return contactService.createContact(contactDto);
    }

    //fetch all contacts of user
    @GetMapping
    public CustomResponseEntity<List<ContactDto>> fetchAllContacts(){
        return contactService.fetchAllContacts();
    }

    //delete contact of user by id
    @DeleteMapping
    public CustomResponseEntity<?> deleteContact(@RequestParam(required = false)  Long contactId){
        return contactService.deleteContactById(contactId);
    }

    //delete contact of user by id
    @PutMapping("/{contactId}")
    public CustomResponseEntity<?> updateContact(@PathVariable  Long contactId,@Valid @RequestBody ContactDto contactDto){
        return contactService.updateContact(contactId,contactDto);
    }

    //fetch contact details
    @GetMapping("/{contactId}")
    public CustomResponseEntity<?> updateContact(@PathVariable  Long contactId){
        return contactService.fetchContactDetailsById(contactId);
    }

}
