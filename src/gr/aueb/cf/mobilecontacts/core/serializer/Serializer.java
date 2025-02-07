package gr.aueb.cf.mobilecontacts.core.serializer;

import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;

public class Serializer {

    private Serializer () {}

    public static String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO.getId() + ", Firstname: " + readOnlyDTO.getFirstname() + ", Lastname: "
                + readOnlyDTO.getLastname() + ", Phone Number: " +readOnlyDTO.getPhoneNum();
    }
}
