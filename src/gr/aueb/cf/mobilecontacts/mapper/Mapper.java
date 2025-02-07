package gr.aueb.cf.mobilecontacts.mapper;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

public class Mapper {

    /**
     * No instances of this class should be available
     */
    private Mapper  () {

    }

    public static MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNum());
    }

    public static MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNum());
    }


    public static MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(),
                mobileContact.getLastname(), mobileContact.getPhoneNum());

    }
//
//    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
//        return "ID: " + readOnlyDTO.getId() + ", Firstname: " + readOnlyDTO.getFirstname() + ", Lastname: "
//                + readOnlyDTO.getLastname() + ", Phone Number: " +readOnlyDTO.getPhoneNum();
//    }
}
