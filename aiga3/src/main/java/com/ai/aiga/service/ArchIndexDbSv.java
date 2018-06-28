package com.ai.aiga.service;

import com.ai.aiga.domain.AmCoreIndexTree;
import com.ai.aiga.service.ArchIndex.ArchitectureIndexSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectFlow;
import com.ai.aiga.view.controller.archiQuesManage.dto.DbConnectFlow;
import com.ai.aiga.view.controller.archiQuesManage.dto.DbConnectTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhuchao on 18-6-22.
 */
@Service
@Transactional
public class ArchIndexDbSv extends BaseService {
    @Autowired
    private ArchitectureIndexSv architectureIndexSv;
    
    public List<DbConnectTransfer> query2daynew(AmCoreIndexParams condition,List<AmCoreIndexTree>list)throws ParseException {
        String end = condition.getEndMonth();
        //获取上个月同一天时间字符串
        String nowday = end;
        String _nowday = nowday.replace("-", "");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = format.parse(nowday);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 31);
        Date before31Day = calendar.getTime();
        String start = simpleDateFormat.format(before31Day);
        String _start = start.replace("-", "");
        condition.setStartMonth(start);

        //获取昨日时间字符串
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTime(today);
        calendar2.set(Calendar.DAY_OF_YEAR, calendar2.get(Calendar.DAY_OF_YEAR) - 1);
        Date before1Day = calendar2.getTime();
        String yesterday = simpleDateFormat.format(before1Day);
        String _yesterday = yesterday.replace("-", "");
        List<ArchDbConnectFlow>connectList = architectureIndexSv.listDbConnects2Flow(condition);
        List<ArchDbConnectFlow>connectList2 = new ArrayList<ArchDbConnectFlow>(connectList);
        List key1List = new ArrayList();
        List<DbConnectTransfer> transfers = new ArrayList<DbConnectTransfer>();
        Iterator<ArchDbConnectFlow> iterator = connectList.iterator();
        List<DbConnectFlow> flows = new ArrayList<DbConnectFlow>();
        while(iterator.hasNext()){
            DbConnectTransfer transfer = new DbConnectTransfer();
            ArchDbConnectFlow base = iterator.next();
            long indexId = base.getIndexId();
			if(indexId==1030000){
				indexId = 10001;
			}else if(indexId==1031000){
				indexId = 10002;
			}else if(indexId==1032000){
				indexId = 10003;
			}else if(indexId==1033000){
				indexId = 10004;
			}
            if(!key1List.contains(indexId)){
                key1List.add(indexId);
                for(int i=0;i<list.size();i++){
                	if(indexId==list.get(i).getIndexId()){
                		transfer.setDb(list.get(i).getIndexGroup());
                		transfer.setMin(Long.parseLong(list.get(i).getExt1()));
                		transfer.setMax(Long.parseLong(list.get(i).getExt2()));
                	}
                }
                long fact = 0L;
                Iterator<ArchDbConnectFlow>iter = connectList2.iterator();
                while(iter.hasNext()){
                    ArchDbConnectFlow inne = iter.next();
                    if(inne.getIndexId()==indexId){
                        if(inne.getSettMonth().equals(_nowday)){
                            fact = Long.valueOf(inne.getResultValue());
                            transfer.setFact(fact);
                        }else if(inne.getSettMonth().equals(_yesterday)){
                            transfer.setFact1(Long.valueOf(inne.getResultValue()));
                        }else if(inne.getSettMonth().equals(_start)){
                            transfer.setFact31(Long.valueOf(inne.getResultValue()));
                        }
                    }
                }
                String health = "优秀";
                if(fact<20000*0.8){
                    health = "优秀";
                }else if((20000*0.8)<=fact && fact<=20000){
                    health = "良好";
                }else if(20000<=fact && fact<=25000){
                    health = "较差";
                }else if(fact>25000){
                    health = "危险";
                }
                transfer.setHealth(health);
                double dayrate = 0L;
                double monthrate = 0L;
                if(transfer.getFact()!=0){
                    dayrate = (transfer.getFact()-transfer.getFact1())*100/transfer.getFact();
                    monthrate = (transfer.getFact()-transfer.getFact31())*100/transfer.getFact();
                }
                transfer.setDayrate(dayrate);
                transfer.setMonthrate(monthrate);
                transfer.setTime(nowday);
                transfers.add(transfer);
            }
        }
        return transfers;
    }
}
