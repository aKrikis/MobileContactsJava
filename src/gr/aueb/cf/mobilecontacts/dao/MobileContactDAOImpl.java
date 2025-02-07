package gr.aueb.cf.mobilecontacts.dao;


import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.ArrayList;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {

    private static final List<MobileContact> contacts = new ArrayList<>();
    private static Long id = 1L;



    @Override
    public MobileContact insert(MobileContact mobileContact) {
        mobileContact.setId(id++);
        contacts.add(mobileContact);
        return mobileContact;
    }

    @Override
    public MobileContact update(Long id, MobileContact mobileContact) {
        contacts.set(getIndexById(id), mobileContact);
        return mobileContact;
    }

    @Override
    public void deleteById(Long id) {
        //contacts.remove(getIndexById(id));
        contacts.removeIf(contact -> contact.getId().equals(id));
    }

    @Override
    public void deleteByPhoneNumber(String phoneNum) {
        contacts.removeIf(contact -> contact.getPhoneNum().equals(phoneNum));
    }

    @Override
    public MobileContact getById(Long id) {
        int positionToReturn = getIndexById(id);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public List<MobileContact> getAll() {

        return new ArrayList<>(contacts); // cause contacts is mutable !
    }

    @Override
    public MobileContact getByPhoneNumber(String phoneNum) {

        int positionToReturn = getIndexByPhoneNum(phoneNum);
        return (positionToReturn != -1) ? contacts.get(positionToReturn) : null;
    }

    @Override
    public boolean usedIdExists(Long id) {
        int position = getIndexById(id);
        return position != -1;

    }

    @Override
    public boolean phoneNumberExists(String phoneNum) {
        int position = getIndexByPhoneNum(phoneNum);
        return position != -1;
    }

    private int getIndexById(Long id){
        int positionToReturn = -1;

        for(int i = 0; i< contacts.size(); i++){
            if (contacts.get(i).getId().equals(id)) {
                positionToReturn = i;
                break;
            }
        } return positionToReturn;
    }

    private int getIndexByPhoneNum(String phoneNum){
        int positionToReturn = -1;

        for(int i = 0; i< contacts.size(); i++){
            if (contacts.get(i).getPhoneNum().equals(phoneNum)) {
                positionToReturn = i;
                break;
            }
        } return positionToReturn;
    }

}
