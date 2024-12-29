package jwt.spring.security.service.impl;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.ContactDto;
import jwt.spring.security.model.entities.Contact;
import jwt.spring.security.model.entities.User;
import jwt.spring.security.model.mapper.ContactMapper;
import jwt.spring.security.repository.ContactRepository;
import jwt.spring.security.repository.UserRepository;
import jwt.spring.security.service.ContactService;
import jwt.spring.security.util.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private ContactRepository contactRepository;
    private ContactMapper contactMapper;
    private UserRepository userRepository;
    private AuthenticatedUser authenticatedUser;

    @Autowired
    ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper, UserRepository userRepository, AuthenticatedUser authenticatedUser) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public CustomResponseEntity<ContactDto> createContact(ContactDto contactDto) {
        try {

            Long authUserId = authenticatedUser.getAuthUserId();
            Optional<User> userOptional = userRepository.findById(authUserId);
            if (userOptional.isEmpty()) {
                logger.warn("User with ID {} not found!", authUserId);
                return CustomResponseEntity.error("User not found!");
            }

            User user = userOptional.get();
            logger.info("Creating contact for User ID: {}", user.getId());
            contactDto.setCreatedAt(new Date());
            contactDto.setUserId(user.getId());

            Contact contact = contactMapper.dtoToEntity(contactDto);
            contact.setUser(user);

            Contact savedContact = contactRepository.save(contact);
            ContactDto responseDto = contactMapper.entityToDto(savedContact);

            logger.info("Contact created successfully for User ID: {}", user.getId());
            return new CustomResponseEntity<>(responseDto, "Contact created successfully.");

        } catch (Exception exception) {
            logger.error("An exception occurred while creating contact: {}", exception.getMessage(), exception);
            return CustomResponseEntity.error("An error occurred while creating the contact. Please try again later.");
        }
    }


    @Override
    public CustomResponseEntity<List<ContactDto>> fetchAllContacts() {
        try {
            Long userId = authenticatedUser.getAuthUserId();
            System.out.println("Authenticated User ID is ::: " + userId);

            List<Contact> userContacts = contactRepository.findByUserId(userId);

            List<ContactDto> contactDtos = userContacts.stream()
                    .map(contact -> {
                        ContactDto dto = contactMapper.entityToDto(contact);
                        dto.setUserId(contact.getUser().getId());
                        return dto;
                    })
                    .toList();

            return new CustomResponseEntity<>(contactDtos, "Contacts fetched successfully.");

        } catch (Exception exception) {
            logger.error("An error occurred while fetching all contacts.", exception);
            return CustomResponseEntity.error("An error occurred while fetching all contacts.");
        }
    }

    @Override
    public CustomResponseEntity<?> deleteContactById(Long contactId) {
        try {
            if (contactId == null) {
                return CustomResponseEntity.error("Please provide a valid contact ID in the parameter.");
            }
            Optional<Contact> optionalContact = contactRepository.findById(contactId);
            if (optionalContact.isEmpty()) {
                return CustomResponseEntity.error("Contact not found!");
            }
            Long authUserId = authenticatedUser.getAuthUserId();
            Contact contact = optionalContact.get();

            if (!authUserId.equals(contact.getUser().getId())) {
                return CustomResponseEntity.error("Sorry, you can't delete a contact of another user!");
            }

            contactRepository.deleteById(contact.getId());
            return new CustomResponseEntity<>(contactMapper.entityToDto(contact), "Contact deleted successfully.");
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("An error occurred during deleting contact!", exception);
            return CustomResponseEntity.error("An error occurred during contact deletion!");
        }
    }

    @Override
    public CustomResponseEntity<ContactDto> updateContact(Long contactId, ContactDto contactDto) {
        try {
            Long authUserId = authenticatedUser.getAuthUserId();

            Optional<Contact> optionalContact = contactRepository.findById(contactId);

            if (optionalContact.isEmpty()) {
                logger.error("Contact not found with id : " + contactId);
                return CustomResponseEntity.error("Contact not found!");
            }

            Contact contact = optionalContact.get();

            if (!contact.getUser().getId().equals(authUserId)) {
                logger.error("User " + authUserId + " is not authorized to update contact with id : " + contactId);
                return CustomResponseEntity.error("You are not authorized to update this contact.");
            }

            contact.setFirstName(contactDto.getFirstName());
            contact.setLastName(contactDto.getLastName());
            contact.setAbout(contactDto.getAbout());
            contact.setPhoneNo(contactDto.getPhoneNo());
            contact.setUpdatedAt(new Date());

            Contact updatedContact = contactRepository.save(contact);

            ContactDto updatedContactDto = contactMapper.entityToDto(updatedContact);
            return new CustomResponseEntity<>(updatedContactDto, "Contact updated successfully.");

        } catch (Exception exception) {
            logger.error("An error occurred during update contact!", exception);
            return CustomResponseEntity.error("An error occurred during update contact!");
        }
    }

    @Override
    public CustomResponseEntity<ContactDto> fetchContactDetailsById(Long contactId) {
        try {
            Long authUserId = authenticatedUser.getAuthUserId();

            Optional<Contact> optionalContact = contactRepository.findById(contactId);
            if (optionalContact.isEmpty()) {
                return CustomResponseEntity.error("Contact is empty!");
            }
            Contact contact = optionalContact.get();
            if (!contact.getUser().getId().equals(authUserId)) {
                return CustomResponseEntity.error("You are not authorized to get this contact!");
            }
            ContactDto contactDto = contactMapper.entityToDto(contact);
            return new CustomResponseEntity<>(contactDto, "Contact fetch successfully.");

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("an error occurred when fetch contact details");
            return CustomResponseEntity.error("an exception occurred during fetch contact details!");
        }
    }


}
