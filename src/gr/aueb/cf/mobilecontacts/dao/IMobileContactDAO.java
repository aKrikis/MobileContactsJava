package gr.aueb.cf.mobilecontacts.dao;

import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;
/**
 * CRUD
 */

/**
 * To DAO pairnei eisodo model entity kai vgazei eksw model entity
 */
public interface IMobileContactDAO {
    MobileContact insert (MobileContact mobileContact);
    MobileContact update (Long id, MobileContact mobileContact);
    void deleteById (Long id);
    void deleteByPhoneNumber(String phoneNum);
    // h get gia to select
    MobileContact getById(Long id);
    List<MobileContact> getAll();



    MobileContact getByPhoneNumber(String phoneNum);
    boolean usedIdExists(Long id);
    boolean phoneNumberExists(String phoneNum);




}
