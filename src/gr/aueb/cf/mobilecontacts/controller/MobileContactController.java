package gr.aueb.cf.mobilecontacts.controller;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.mapper.Mapper;
import gr.aueb.cf.mobilecontacts.model.MobileContact;
import gr.aueb.cf.mobilecontacts.service.IMobileContactService;
import gr.aueb.cf.mobilecontacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobilecontacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

import static gr.aueb.cf.mobilecontacts.core.serializer.Serializer.serializeDTO;

public class MobileContactController {

    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {

        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            //Validate DTO input data
            String error = ValidationUtil.validateDTO(insertDTO);
            if (!error.isEmpty()) {
                return "Error.\n" + "Validation error\n" + error;

            }

            //if validation is ok
            mobileContact = service.insertMobileContact(insertDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);

            // Return back to the client success code and details
            return "Ok\n" + serializeDTO(readOnlyDTO) + "\n";

        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\n Number already exists.\n";
        }


    }

    public String updateContact (MobileContactUpdateDTO updateDTO) {

        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            //Validate DTO input data
            String error = ValidationUtil.validateDTO(updateDTO);
            if (!error.isEmpty()) {
                return "Error.\n " + "Validation error\n" + error;

            }

            //if validation is ok, update contact
            mobileContact = service.updateMobileContact(updateDTO);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);

            // Return back to the client success code and details
            return "Ok\n" + serializeDTO(readOnlyDTO) + "\n";

        } catch (ContactNotFoundException e) {
            return "Error.\n Number not found.\n";
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error.\n Number already exists.\n";
        }

    }

    public String deleteContactById (Long id) {

        try {
            service.deleteContactById(id);
            return "Ok\n User has been deleted.\n";
        } catch (ContactNotFoundException e) {
            return "Error.\n Contact not found.\n";
        }
    }

    public String getContactById (Long id) {

        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            mobileContact = service.getContactById(id);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "Ok\n"+ serializeDTO(readOnlyDTO);
        } catch (ContactNotFoundException e) {
            return "Error.\n Contact not found.\n";
        }
    }

    public List<String> getAllContacts() {
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();

        contacts = service.getAllContacts();

        for (MobileContact contact : contacts) {
            String serialized = serializeDTO(Mapper.mapMobileContactToDTO(contact));
            serializedList.add(serialized);
        }
        return serializedList;
    }

    public String getContactByPhoneNumber (String phoneNumber) {

        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            mobileContact = service.getContactByPhoneNum(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            return "Ok\n"+ serializeDTO(readOnlyDTO);
        } catch (ContactNotFoundException e) {
            return "Error.\n Contact not found.\n";
        }
    }

    public String deleteContactByPhoneNumber (String phoneNumber) {

        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
            mobileContact = service.getContactByPhoneNum(phoneNumber);
            readOnlyDTO = Mapper.mapMobileContactToDTO(mobileContact);
            service.deleteContactByPhoneNumber(phoneNumber);
            return "Ok\n User has been deleted. "+ serializeDTO(readOnlyDTO)+"\n";
        } catch (ContactNotFoundException e) {
            return "Error.\n Contact not found.\n";
        }
    }










//    private MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
//        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(),
//                mobileContact.getLastname(), mobileContact.getPhoneNum());
//
//    }

//    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
//        return "ID: " + readOnlyDTO.getId() + ", Firstname: " + readOnlyDTO.getFirstname() + ", Lastname: "
//                + readOnlyDTO.getLastname() + ", Phone Number: " +readOnlyDTO.getPhoneNum();
//    }


}

