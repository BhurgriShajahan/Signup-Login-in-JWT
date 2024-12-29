package jwt.spring.security.service;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.ContactDto;

import java.util.List;

public interface ContactService {

    //Create Contact
    CustomResponseEntity<ContactDto> createContact(ContactDto contactDto);
    //Fetch all contacts
    CustomResponseEntity<List<ContactDto>> fetchAllContacts();
    //Delete contact
    CustomResponseEntity<?> deleteContactById(Long contactId);
    //Update contact
    CustomResponseEntity<ContactDto> updateContact(Long contactId,ContactDto contactDto);
    //Fetch contacts details by id
    CustomResponseEntity<ContactDto> fetchContactDetailsById(Long contactId);

}
