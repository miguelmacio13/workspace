package com.tem.aspire;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.api.complex.eb.enquiryhook.EnquiryContext;
import com.temenos.t24.api.complex.eb.enquiryhook.FilterCriteria;
import com.temenos.t24.api.hook.system.Enquiry;
import com.temenos.t24.api.records.customer.CustomerRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class NoFileIris extends Enquiry{

    @Override
    public List<String> setIds(List<FilterCriteria> filterCriteria, EnquiryContext enquiryContext) {
       
        List <String> noFileList = new ArrayList<String>();
        String sectorFilter = filterCriteria.get(0).getValue();
        DataAccess data = new DataAccess(this);
        String filter = " WITH SECTOR EQ " + sectorFilter;
        
        List<String> selectRecordList = data.selectRecords("", "CUSTOMER", "", filter);
        for(String i : selectRecordList){
            CustomerRecord customer = new CustomerRecord(data.getRecord("CUSTOMER", i));
            String sector = customer.getSector().getValue();
            String name = customer.getMnemonic().getValue();
            String marital = customer.getMaritalStatus().getValue();
            String currency = "";
            String employee = "";
            
            if(!(customer.getEmploymentStatus().isEmpty())){
                currency = customer.getEmploymentStatus().get(0).getCustomerCurrency().getValue();
                employee = customer.getEmploymentStatus().get(0).getEmploymentStatus().getValue();
            }
            
            noFileList.add(name + "/" + marital + "/" + sector + "/" + employee + "/" + currency);
        }
        
        return noFileList;
    }
    
}
