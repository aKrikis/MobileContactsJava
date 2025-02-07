package gr.aueb.cf.mobilecontacts.service;


import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

import static gr.aueb.cf.mobilecontacts.mapper.Mapper.mapInsertDTOToContact;
import static gr.aueb.cf.mobilecontacts.mapper.Mapper.mapUpdateDTOToContact;

public class MobileContactServiceImpl implements IMobileContactService {

    private final IMobileContactDAO dao;

    public MobileContactServiceImpl (IMobileContactDAO dao){
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO mobileContactInsertDto) throws PhoneNumberAlreadyExistsException {

        MobileContact mobileContact;

        try {
            // take care to not insert 2 same numbers
            if (dao.phoneNumberExists(mobileContactInsertDto.getPhoneNum())) {
                throw new PhoneNumberAlreadyExistsException("User with phone number " + mobileContactInsertDto.getPhoneNum() + " already exists.");

            }

            mobileContact = mapInsertDTOToContact(mobileContactInsertDto);
            System.err.printf("User Logger: %s was inserted. \n", mobileContact);
            return dao.insert(mobileContact);

        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("User with phone number %s already exists.\n", mobileContactInsertDto.getPhoneNum());
            throw e;

        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws PhoneNumberAlreadyExistsException, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;

        try {

            if(!dao.usedIdExists(dto.getId())){
                throw new PhoneNumberAlreadyExistsException("Contact with id: " + dto.getId() + "not found");

            }

            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNum().equals(dto.getPhoneNum());
            boolean isPhoneNumberExists = dao.phoneNumberExists(dto.getPhoneNum());

            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new ContactNotFoundException("Contact with phone number " + dto.getPhoneNum() + " already exists.");

            }

            newContact = mapUpdateDTOToContact(dto);
            System.err.printf("User Logger %s was updated to %s.\n", mobileContact, newContact);
            return dao.update(dto.getId(), newContact);

        } catch (PhoneNumberAlreadyExistsException | ContactNotFoundException e) {
            System.err.println(e.getMessage());
            throw e;

        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {

        try {
            if (!dao.usedIdExists(id)) {
                throw new ContactNotFoundException("Contact with id: "+ id+ " already exists.");
            }

            System.err.printf("User with id: %d was deleted.\n" , id);
            dao.deleteById(id);

        } catch (ContactNotFoundException e) {
            System.err.printf("User with id: %d not found for delete.\n", id);
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Logger: Contact with id: "+ id+ " not found.");

            }

            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("Contact with id: %d was not found.\n", id);
            throw e;

        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    @Override
    public MobileContact getContactByPhoneNum(String phoneNum) throws ContactNotFoundException {

        MobileContact mobileContact;

        try {
            mobileContact = dao.getByPhoneNumber(phoneNum);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Logger: Contact with phone number: "+ phoneNum+ " not found.");

            }

            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("Contact with phone number: %s was not found.\n", phoneNum);
            throw e;

        }
    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNum) throws ContactNotFoundException {
        try {
            if (!dao.phoneNumberExists(phoneNum)) {
                throw new ContactNotFoundException("Contact with phone number: "+ phoneNum+ " already exists.");
            }

            System.err.printf("User with phone number: %s was deleted.\n" , phoneNum);
            dao.deleteByPhoneNumber(phoneNum);

        } catch (ContactNotFoundException e) {
            System.err.printf("User with phone number: %s not found for delete.\n", phoneNum);
            throw e;
        }
    }

//    private MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
//        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNum());
//    }
//
//    private MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
//        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNum());
//    }
}
