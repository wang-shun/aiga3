package com.ai.aiga.view.controller.archibaseline;

import java.util.Comparator;

import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdViewCenterItem;

public class MyComparator implements Comparator<ArchiThirdViewCenterItem> {
    @Override  
    public int compare(ArchiThirdViewCenterItem o1, ArchiThirdViewCenterItem o2) {  
        int i = o2.getItem().size() - o1.getItem().size() ;  
        if(i == 0){  
            return 1;
        }  
        return i;  
    }  
}
