
package View.Sites.EditSite.J_Contacts;

import Model.DataModels.Sites.Contact;


public class ContactTableEntry {
    private EnumContactType contactType;
    private Contact contact;
    
    public ContactTableEntry( EnumContactType contactType, Contact contact ){
        this.contactType = contactType;
        this.contact = contact;
    }
    
    public EnumContactType getContactType(){
        return contactType;
    }
    
    public void setContactType( EnumContactType contactType ){
        this.contactType = contactType;
    }
    
    public Contact getContact(){
        return contact;
    }
    
    public void setContact( Contact contact){
        this.contact = contact;
    }
   
}
