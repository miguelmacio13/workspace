package com.tem.aspire;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.customer.CustomerRecord;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class DemoClass extends RecordLifecycle {

    @Override
    public void defaultFieldValues(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        
        FundsTransferRecord fundsTransferRecord = new FundsTransferRecord();
        fundsTransferRecord.setTransactionType("AC");
        currentRecord.set(fundsTransferRecord.toStructure());
    }

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        
        CustomerRecord customerRecord = new CustomerRecord(currentRecord);
        // Get customer's information
        TField familyName = customerRecord.getFamilyName();
        TField givenName = customerRecord.getGivenNames();
        //checking if Familyname have numeric case
        
        for(Character c : familyName.getValue().toCharArray()){
            if(Character.isDigit(c)){
                familyName.setOverride("TRG.TK5.OVERR");
                break;
            }
        }      
        //Checking if GivenName have numeric case
        for(Character c : givenName.getValue().toCharArray()){
            if(Character.isDigit(c)){
                familyName.setOverride("TRG.TK5.OVERR");
                break;
            }
        }
        return customerRecord.getValidationResponse();
    }

    
    @Override
    public String checkId(String currentRecordId, TransactionContext transactionContext) {
        Set<Character> charSet = new HashSet<>();
        
        //First I check if the ID has unique digits with this loop
        for(char c : currentRecordId.toCharArray()){
            charSet.add(c);
        }
        // If not all digits are unique, then the next if won't 
        // affect the program. Therefore, the ID remains the same.
        if(currentRecordId.length() == 6 && charSet.size() == 6) {
            
            // Here I generate the new ID if the requirement is met
            Random ran = new Random();
            char randomChar = (char)(ran.nextInt(9) + '1');
            String newRecordId = new String(new char[6]).replace('\0', randomChar);
            currentRecordId = newRecordId;
        }

        return currentRecordId;
    }
    
  
    
    
}

