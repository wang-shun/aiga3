package com.ai.aiga.service.auto;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.*;
import com.ai.aiga.domain.*;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.auto.enums.KeyValueEnum;
import com.ai.aiga.service.cases.CaseInterfaceSv;
import com.ai.aiga.service.cases.EsbInterfaceSv;
import com.ai.aiga.service.enums.AutoRunEnum;
import com.ai.aiga.service.enums.CaseEnum;
import com.ai.aiga.service.enums.GeneralEnum;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.auto.AutoCaseRequest;
import com.ai.aiga.view.json.auto.AutoUiCompRequest;
import com.ai.aiga.view.json.auto.AutoUiParamRequest;
import com.ai.aiga.view.json.cases.Factor;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自动化用例
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Service
@Transactional
public class AutoCaseSv {

    @Autowired
    private NaAutoCaseDao autoCaseDao;

    @Autowired
    private AutoTemplateSv autoTemplateSv;

    @Autowired
    private AutoUiCompSv autoUiCompSv;

    @Autowired
    private  AutoUiParamSv autoUiParamSv;

    @Autowired
    private NaTestCaseDao testCaseDao;

    @Autowired
    private NaUiComponentDao componentDao;

    @Autowired
    private NaUiCompCtrlDao compCtrlDao;

    @Autowired
    private NaUiControlDao controlDao;

    @Autowired
    private CaseInterfaceSv caseInterfaceSv;
    
    @Autowired
    private EsbInterfaceSv esbInterfaceSv;
    /**
     * 保存操作(唯一入口)
     * @param autoCase 自动化用例对象
     * @return NaAutoCase
     */
    public NaAutoCase save(NaAutoCase autoCase){
        if (autoCase == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (StringUtils.isBlank(autoCase.getAutoName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoName");
        }
        if (autoCase.getEnvironmentType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "EnvironmentType");
        }
        if (autoCase.getCaseType() == null) {
            autoCase.setCaseType(CaseEnum.CaseType_UI.getValue());//默认ui类
        }
        if (autoCase.getImportant()==null){
            autoCase.setImportant((short) 4);//默认为4级
        }
        if (autoCase.getStatus() == null) {
            autoCase.setStatus(GeneralEnum.Status_able.getValue());//默认可用
        }
        if (autoCase.getHasAuto() == null) {
            autoCase.setHasAuto(GeneralEnum.Logic_no.getValue());//默认未实现自动化
        }
        if (autoCase.getParamLevel() == null) {
            autoCase.setParamLevel(1L);//默认为1级
        }
        //校验是否存在名称一样的用例
        if (autoCase.getAutoId() != null ? (this.isExisting(autoCase.getAutoName(),autoCase.getAutoId()))  :  (autoCaseDao.findByAutoName(autoCase.getAutoName())!=null)){
            BusinessException.throwBusinessException("autoName already existing! please change......");
        }
//       if (autoCase.getAutoId()==null)autoCase.setCreatorId();
//        autoCase.setUpdateId();
        autoCase.setUpdateTime(DateUtil.getCurrentTime());
        return autoCaseDao.save(autoCase);
    }


    /**
     * 根据传递的请求参数信息保存用例、组件、参数(带有autoId则修改，不带则新增)
     * @param autoCaseRequest 页面请求参数
     * @return NaAutoCase
     */
    public NaAutoCase saveAutoCaseCompParam(AutoCaseRequest autoCaseRequest) throws Exception {
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException("AutoCaseRequest is null！");
        }
        if (autoCaseRequest.getAutoName() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoName");
        }
        if (autoCaseRequest.getEnvironmentType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "environmentType");
        }
        NaAutoCase autoCase=null;
        //是否新增
        boolean isAdd = autoCaseRequest.getAutoId() == null;
        //是否根据自动化用例模板ID生成自动化用例
        boolean isTemp = autoCaseRequest.getTempId() != null;
        //是否根据测试用例ID生成自动化用例
        boolean isTest = autoCaseRequest.getTestId() != null;
        if(isAdd&&isTemp){
                autoCase = this.copyCaseByTempId(autoCaseRequest.getTempId());
        }else if(isAdd&&isTest){
                autoCase = this.copyCaseByTestId(autoCaseRequest.getTestId());
        }else if(!isAdd){
            autoCase=this.findById(autoCaseRequest.getAutoId());
            //删除现有组件和参数关系
            autoUiCompSv.deleteByAutoId(autoCase.getAutoId());
            autoUiParamSv.deleteByAutoId(autoCase.getAutoId());
        }else{
            BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST);
        }
        //填充自动化用例名称与环境属性
        autoCase.setEnvironmentType(autoCaseRequest.getEnvironmentType());
        autoCase.setAutoName(autoCaseRequest.getAutoName());
        autoCase=this.save(autoCase);
        List<AutoUiCompRequest> compRequestList=autoCaseRequest.getCompList();
        //是否带有组件
        boolean isCompList=compRequestList!=null&&compRequestList.size()>0;
        //是否接口类用例
        boolean isInterface=autoCase.getCaseType().equals(CaseEnum.CaseType_interface.getValue());
        //如果带有组件信息且 （不是接口类 或  是接口但不是根据自动化用例模板生成用例）
        if (isCompList && (!isInterface || (isInterface && !isTemp))) {
            autoUiCompSv.saveCompList(compRequestList, autoCase);
        }
        //如果带有组件信息、接口类、根据自动化用例模板生成用例
        if(isCompList&&isInterface&&isTemp){
            //从组件列表中筛选出非自定义组件
            List<AutoUiCompRequest> compList = this.getRuleCompFromList(compRequestList);
            autoUiCompSv.saveCompList(compList,autoCase);
            //从组件列表中筛选出自定义组件
            AutoUiCompRequest customComp=this.getCustomCompFromList(compRequestList);
            //生成接口类组件信息
            this.createInterfaceCompList(customComp,autoCase);
        }
        return autoCase;
    }

    /**
     * 先复制数据到删除备份表，在删除原数据（唯一入口）
     * @param autoId 自动化用例ID
     */
    public void delete(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        autoCaseDao.copyDataToDel(autoId);
        autoCaseDao.delete(autoId);
    }

    /**
     * 通过请求参数删除
     * @param autoCaseRequest 页面请求参数
     */
    public void delete(AutoCaseRequest autoCaseRequest){
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(autoCaseRequest.getAutoId());
    }

    /**
     * 根据主键ID查询自动化用例信息（唯一入口）
     * @param autoId 自动化用例ID
     * @return NaAutoCase
     */
    public NaAutoCase findById(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        NaAutoCase autoCase = autoCaseDao.findOne(autoId);
        if (autoCase == null) {
            BusinessException.throwBusinessException("can not found autoCase !please make sure the autoId:"+autoId);
        }
         return autoCase;
    }

    /**
     * 根据用例名称查询
     * @param autoName 自动化用例名称
     * @return NaAutoCase
     */
    public NaAutoCase findByAutoName(String autoName){
        if (StringUtils.isBlank(autoName)) {
                  BusinessException.throwBusinessException(ErrorCode.Parameter_null, autoName);
        }
        NaAutoCase autoCase = autoCaseDao.findByAutoName(autoName);
        if (autoCase == null) {
            BusinessException.throwBusinessException("can not found autoCase !please make sure the autoName:"+autoName);
        }
        return autoCase;
    }

    /**
     * 根据主键ID查询自动化用例信息（包括用例信息、组件、参数）
     * @param autoCaseRequest 页面请求参数
     * @return 查询到具体信息
     */
    public AutoCaseRequest findById(AutoCaseRequest autoCaseRequest){
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoCaseRequest.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        AutoCaseRequest response=BeanMapper.map(this.findById(autoCaseRequest.getAutoId()),AutoCaseRequest.class);
        //填入组件
        response.setCompList(this.autoUiCompSv.findByAutoIdRequest(autoCaseRequest.getAutoId()));
        return response;
    }

    /**
     * 根据原生SQL分页查询自动化用例信息(包括属性ID的描述信息)
     * @param condition 查询条件对象
     * @param pageNumber 页数
     * @param pageSize 每页数量
     * @return 分页的数据
     */
    public Object listbyNativeSQL(AutoCaseRequest condition,int pageNumber, int pageSize){
        StringBuilder nativeSql=new StringBuilder("select a.auto_id,a.test_id,a.temp_id,a.test_type,a.case_type,a.auto_name,\n" +
                "a.sys_id,a.sys_sub_id,a.busi_id,a.fun_id,a.sc_id,a.important,a.environment_type,\n" +
                "a.status,a.has_auto,a.auto_desc,a.param_level,a.creator_id,a.update_id,a.update_time,\n" +
                "b.temp_name,c.test_name,d.sys_name,e.sys_name as sysSubName,f.sys_name as funName,g.name\n" +
                " from na_auto_case a\n" +
                " left join Na_Auto_Template b on a.temp_id=b.temp_id\n" +
                " left join na_test_case c on a.test_id=c.test_id\n" +
                " left join Aiga_System_Folder d on a.sys_Id=d.sys_Id\n" +
                " left join Aiga_Sub_Sys_Folder e on a.sys_Sub_Id=e.subsys_Id\n" +
                " left join Aiga_Fun_Folder f on a.fun_Id=f.fun_Id\n" +
                " left join Aiga_Staff g on a.update_id=g.staff_Id where 1=1 ");
        List<String> keyList=new ArrayList<String>();
        keyList.add("autoId");
        keyList.add("testId");
        keyList.add("tempId");
        keyList.add("testType");
        keyList.add("caseType");
        keyList.add("autoName");
        keyList.add("sysId");
        keyList.add("sysSubId");
        keyList.add("busiId");
        keyList.add("funId");
        keyList.add("scId");
        keyList.add("important");
        keyList.add("environmentType");
        keyList.add("status");
        keyList.add("hasAuto");
        keyList.add("autoDesc");
        keyList.add("paramLevel");
        keyList.add("creatorId");
        keyList.add("updateId");
        keyList.add("updateTime");
        keyList.add("tempName");
        keyList.add("testName");
        keyList.add("sysName");
        keyList.add("sysSubName");
        keyList.add("funName");
        keyList.add("staffName");
        if(condition != null){
            if(StringUtils.isNotBlank(condition.getAutoName())){
                nativeSql.append(" and a.auto_name like '%").append(condition.getAutoName()).append("%'");
            }

            if(condition.getSysId() != null){
                nativeSql.append(" and a.sys_id=").append(condition.getSysId());
            }

            if(condition.getSysSubId() != null){
                nativeSql.append(" and a.sys_sub_id=").append(condition.getSysSubId());
            }

            if(condition.getFunId() != null){
                nativeSql.append(" and a.fun_id=").append(condition.getFunId());
            }

            if(condition.getBusiId() != null){
                nativeSql.append(" and a.busi_id=").append(condition.getBusiId());
            }

            if(condition.getScId() != null){
                nativeSql.append(" and a.sc_id=").append(condition.getScId());
            }

            if(condition.getImportant() != null){
                nativeSql.append(" and a.important=").append(condition.getImportant());
            }
            if (condition.getStatus() != null) {
                nativeSql.append(" and a.status=").append(condition.getStatus());
            }
            if(condition.getEnvironmentType() !=null){
                nativeSql.append(" and a.environment_type=").append(condition.getEnvironmentType());
            }
            if(condition.getCaseType() !=null){
                nativeSql.append(" and a.case_type=").append(condition.getCaseType());
            }
        }
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return autoCaseDao.searchByNativeSQL(nativeSql.toString(),pageable,keyList);
    }


    /**
     * 根据任务ID和用例名称返回json信息
     * @param autoName 任务ID_自动化用例名称
     * @return JSON串
     */
    public String getCaseByAutoNameToJson(String autoName){
        if (StringUtils.isBlank(autoName)) {
                  BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoName");
        }
        Map<String,String> json=new HashMap<String,String>();
        Map<String, String> scripts = new HashMap<String, String>();
        Map<String, String> elements = new HashMap<String, String>();
        List<String> steps=new ArrayList<String>();
        NaAutoCase autoCase=this.findByAutoName(autoName);
        List<NaAutoUiComp> autoUiCompList=this.autoUiCompSv.findByAutoIdOrderByCompOrderAsc(autoCase.getAutoId());
        if (autoUiCompList == null || autoUiCompList.size() == 0) {
            BusinessException.throwBusinessException("getCaseByCaseNameToJson could not found the autoCaseComp......");
        }
        for (NaAutoUiComp autoUiComp:autoUiCompList){
            //获取组件脚本信息
            NaUiComponent uiComponent=this.componentDao.findOne(autoUiComp.getCompId());
            if(uiComponent !=null){
                steps.add(uiComponent.getCompName());
                scripts.put(uiComponent.getCompName(), uiComponent.getCompScript());
            }
            //获取控件XPATH信息
            List<NaUiCompCtrl> ctrlList=this.compCtrlDao.findByCompId(autoUiComp.getCompId());
            if (ctrlList != null && ctrlList.size() > 0) {
                for (NaUiCompCtrl compCtrl:ctrlList){
                    NaUiControl control=controlDao.findOne(compCtrl.getCtrlId());
                    if (control != null) {
                        elements.put(control.getCtrlName(),control.getCtrlXpath());
                    }
                }
            }
        }
        json.put("name", autoName);
        json.put("steps", JsonUtil.listToJson(steps));
        json.put("scripts", JsonUtil.mapToJson(scripts));
        json.put("elements", JsonUtil.mapToJson(elements));
        return JsonUtil.mapToJson(json);
    }

    /**
     * 获取自动化用例的参数值
     * @param taskIdAutoId 传入参数为taskId_autoId格式字符串，需解析出autoId
     * @return JSON串
     */
    public String getParamValueByautoIdToJson(String taskIdAutoId){
        if (StringUtils.isBlank(taskIdAutoId)) {
                  BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskIdAutoId");
        }
        Long autoId = Long.parseLong(taskIdAutoId.split("_")[1]);
        List<NaAutoUiComp> compList=this.autoUiCompSv.findByAutoIdOrderByCompOrderAsc(autoId);
        Map<String,String> json=new HashMap<String, String>();
        Map<String,String> arguments=new HashMap<String, String>();
        if (compList == null || compList.size()==0) {
            BusinessException.throwBusinessException("getCaseByCaseNameToJson could not found the autoCaseComp......");
        }
        //获取组件
        for (NaAutoUiComp comp:compList){
            List<NaAutoUiParam> paramList=this.autoUiParamSv.findByAutoComp(autoId,comp.getCompId(),comp.getCompOrder());
            Map<String,String> compMap=new HashMap<String, String>();
            //获取组件下的参数
            if (paramList != null && paramList.size() > 0) {
                for (NaAutoUiParam param:paramList){
                    Map<String,String> args=new HashMap<String, String>();
                    args.put("value",param.getParamValue());
                    args.put("expect",param.getParamExpect());
                    compMap.put(param.getParamName(),JsonUtil.mapToJson(args));
                }
            }
            arguments.put(comp.getCompOrder().toString(),JsonUtil.mapToJson(compMap));
        }
        json.put("name",taskIdAutoId);
        json.put("arguments",JsonUtil.mapToJson(arguments));
        return JsonUtil.mapToJson(json);
    }


    /**
     * 判断是否存在重复名称的自动化用例(存在返回true，不存在false)
     * @param autoName 自动化用例名称
     * @param autoId 自动化用例ID
     * @return boolean
     */
    private boolean isExisting(String autoName,Long autoId){
        NaAutoCase autoCase=autoCaseDao.findByAutoName(autoName);
        return !(autoCase != null && autoCase.getAutoId().equals(autoId));
    }

    /**
     * 根据自动化用例模板ID复制数据生成自动化用例基础信息(只复制用例基础属性，不复制组件参数)
     * @param tempId  自动化用例模板ID
     * @return NaAutoCase
     */
    private NaAutoCase copyCaseByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        NaAutoTemplate autoTemplate=autoTemplateSv.findById(tempId);
        NaAutoCase autoCase= BeanMapper.map(autoTemplate,NaAutoCase.class);
        autoCase.setAutoName(autoTemplate.getTempName()+DateUtil.getCurrTimeString());//默认根据模板名称填充，需由后续调用方法者覆盖
        autoCase.setEnvironmentType(1L);//默认验收环境，需由后续调用方法者覆盖
        //保存操作
        return this.save(autoCase);
    }

    /**
     * 根据自动化用例模板ID复制用例数据和组件参数信息
     * @param tempId 自动化用例模板ID
     */
    private NaAutoCase copyCaseCompByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        //先复制用例
        NaAutoCase autoCase=this.copyCaseByTempId(tempId);
        //复制组件
        autoUiCompSv.copyCompList(autoCase);
        return autoCase;
    }

    /**
     * 通过测试用例主键复制数据生成自动化用例基础信息
     * @param testId 测试用例ID
     * @return NaAutoCase
     */
    private NaAutoCase copyCaseByTestId(Long testId){
        if (testId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "testId");
        }
        NaTestCase testCase=testCaseDao.findOne(testId);
        if (testCase == null) {
            BusinessException.throwBusinessException("can not found the testCase! please make sure the testId:"+testId);
        }
        NaAutoCase autoCase=BeanMapper.map(testCase,NaAutoCase.class);
        autoCase.setAutoName(testCase.getTestName()+DateUtil.getCurrTimeString());//默认根据测试用例名称填充，需由后续调用方法者覆盖
        autoCase.setEnvironmentType(AutoRunEnum.EnvironmentType_acceptance.getValue());//默认验收环境，需由后续调用方法者覆盖
        //保存操作
        return this.save(autoCase);
    }

    /**
     * 生成接口类组件
     * @param customComp 自定义组件，compId为0
     * @param autoCase 自动化用例对象
     */
    private void createInterfaceCompList(AutoUiCompRequest customComp,NaAutoCase autoCase) throws Exception {
            //获取用例接口信息
            NaCaseInterface caseInterface = this.getCaseInterfaceByTempId(autoCase.getTempId());
            //是否ESB类型
            boolean isESB=caseInterface.getInterfaceType().equals(CaseEnum.InterfaceType_ESB.getValue());
            //是否CBOSS类型
            boolean isCBOSS=caseInterface.getInterfaceType().equals(CaseEnum.InterfaceType_CBOSS.getValue());
            if(isESB){
                //生成ESB组件(接口测试.HTTP接口测试.esb接口测试多规则校验)
                NaAutoUiComp autoUiComp = this.autoUiCompSv.createAutoUiCompByCompNameAutoId(AutoRunEnum.InterfaceTest_esb.getShow(), autoCase.getAutoId());
                //获取ESB报文对象
                AigaEsbInterface esbInterface=this.esbInterfaceSv.findById(caseInterface.getMessageId());
                //获取填充好的ESB报文信息
                String esbXml=this.getEsbXml(customComp.getParamList(),esbInterface);
                //生成sAddress参数
                this.autoUiParamSv.createAutoUiParamByNameValue(AutoRunEnum.Custom_sAddress.getShow(), caseInterface.getAddress(), autoUiComp);
                //生成inputXml1参数
                this.autoUiParamSv.createAutoUiParamByNameValue(AutoRunEnum.Custom_inputXmlOne.getShow(), esbXml, autoUiComp);
                //根据必填校验参数生成组件
                this.createCompFromValidParam(autoCase);
            }
            if (isCBOSS){
                
            }
    }

    /**
     * 从集合中筛选出自定义组件
     * @param compRequestList 组件集合
     * @return 自定义组件
     */
    private AutoUiCompRequest getCustomCompFromList(List<AutoUiCompRequest> compRequestList){
        AutoUiCompRequest customComp=new AutoUiCompRequest();
        for (AutoUiCompRequest compRequest:compRequestList){
            boolean isCustom = compRequest.getCompId().equals(AutoRunEnum.Custom_component.getValue());
            if (isCustom){
                customComp=compRequest;
            }
        }
        return customComp;
    }

    /**
     * 从集合中筛选出非自定义组件
     * @param compRequestList 组件集合
     * @return 非自定义组件集合
     */
    private List<AutoUiCompRequest> getRuleCompFromList(List<AutoUiCompRequest> compRequestList){
        List<AutoUiCompRequest> compList = new ArrayList<AutoUiCompRequest>();
        for (AutoUiCompRequest compRequest:compRequestList){
            boolean isCustom = compRequest.getCompId().equals(AutoRunEnum.Custom_component.getValue());
            if (!isCustom) {
                compList.add(compRequest);
            }
        }
        return compList;
    }

    /**
     * 根据自动化用例模板ID查询接口类用例的接口类型
     * @param tempId 自动化用例模板ID
     * @return 用例接口类型对象
     */
    private NaCaseInterface getCaseInterfaceByTempId(Long tempId){
        NaAutoTemplate autoTemplate=this.autoTemplateSv.findById(tempId);
        return this.caseInterfaceSv.findByCaseId(autoTemplate.getCaseId());
    }

    /**
     * 根据报文模板与参数值拼装ESB报文
     * @param paramRequestList 参数集合
     * @param esbInterface 报文对象
     * @return ESB报文内容
     * @throws Exception 异常
     */
    private String getEsbXml(List<AutoUiParamRequest> paramRequestList ,AigaEsbInterface esbInterface)throws Exception{
        //XML字符串必须要有根元素，否则报错
        String inputSoap = CaseEnum.xmlRoot_header.getShow() + esbInterface.getInputSoap() + CaseEnum.xmlRoot_footer.getShow();
        Document document= DocumentHelper.parseText(inputSoap);
        //将参数集合转换成迭代器，并按顺序填充到该XML的叶子节点值上
        this.listNodesToAssignment(document.getRootElement(),paramRequestList.iterator());
        //返回拼装后的ESB报文
        return this.integrationEsbXml(document.asXML(),esbInterface.getEsbName());
    }

    /**
     * 根据传入的XML根节点和参数值迭代器给每个叶子节点赋值
     * @param node 根节点
     * @param iterator 参数值迭代器
     */
    private void listNodesToAssignment(Element node,Iterator iterator){
        List<Element> elements=node.elements();
        //是否叶子节点
        boolean isLeaf = elements.size() == 0;
        //按顺序将值塞入叶子节点中
        if (isLeaf && iterator.hasNext()) {
            AutoUiParamRequest paramRequest=(AutoUiParamRequest) iterator.next();
            node.setText(paramRequest.getParamValue());
        }
        //递归子节点
        for (Element element:elements){
            this.listNodesToAssignment(element,iterator);
        }
    }

    /**
     * 根据必填校验参数生成组件
     * @param autoCase 自动化用例
     * @throws Exception 异常
     */
    private void createCompFromValidParam(NaAutoCase autoCase) throws Exception {
        NaCaseInterface caseInterface = this.getCaseInterfaceByTempId(autoCase.getTempId());
        //获取参数校验组件名称
        Map<String,String> validCompMap=this.getValidCompMap();
        //解析必填校验参数
        Map<String,String> params=this.parseValidParamToMap(caseInterface.getValidParam());
        for (String key:params.keySet()){
            String compName=validCompMap.get(key);
            if (StringUtils.isBlank(compName)) {
                return;
            }
            //新增组件
            NaAutoUiComp autoUiComp=this.autoUiCompSv.createAutoUiCompByCompNameAutoId(compName,autoCase.getAutoId());
            //新增参数
            this.autoUiParamSv.createAutoUiParamByNameValue(AutoRunEnum.Custom_result.getShow(), params.get(key), autoUiComp);
        }
    }

    /**
     * 返回参数校验组件MAP集合
     * @return Map
     */
    private Map<String,String> getValidCompMap(){
        Map<String, String> compMap = new HashMap<String,String>();
        compMap.put(KeyValueEnum.ValidParam_date.getKey(),KeyValueEnum.ValidParam_date.getValue());
        compMap.put(KeyValueEnum.ValidParam_enum.getKey(),KeyValueEnum.ValidParam_enum.getValue());
        compMap.put(KeyValueEnum.ValidParam_equals.getKey(),KeyValueEnum.ValidParam_equals.getValue());
        compMap.put(KeyValueEnum.ValidParam_float.getKey(),KeyValueEnum.ValidParam_float.getValue());
        compMap.put(KeyValueEnum.ValidParam_httpInterface.getKey(),KeyValueEnum.ValidParam_httpInterface.getValue());
        compMap.put(KeyValueEnum.ValidParam_length.getKey(),KeyValueEnum.ValidParam_length.getValue());
        compMap.put(KeyValueEnum.ValidParam_maxLength.getKey(),KeyValueEnum.ValidParam_maxLength.getValue());
        compMap.put(KeyValueEnum.ValidParam_notNull.getKey(),KeyValueEnum.ValidParam_notNull.getValue());
        compMap.put(KeyValueEnum.ValidParam_number.getKey(),KeyValueEnum.ValidParam_number.getValue());
        compMap.put(KeyValueEnum.ValidParam_numValue.getKey(),KeyValueEnum.ValidParam_numValue.getValue());
        return compMap;
    }
    
    /**
     * 根据报文体和ESB报文名称拼装ESB报文
     * @param bodyMsg 报文体
     * @param esbName ESB报文名称
     * @return ESB报文
     */
    private String integrationEsbXml(String bodyMsg,String esbName){
        //获取根节点头部位置
        int headerIndex=bodyMsg.indexOf(CaseEnum.xmlRoot_header.getShow())+CaseEnum.xmlRoot_header.getValue().intValue();
        //获取根节点尾部位置
        int footerIndex=bodyMsg.indexOf(CaseEnum.xmlRoot_footer.getShow());
        //截取掉根节点，保留正文
        bodyMsg=bodyMsg.substring(headerIndex,footerIndex);
        String header = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Header xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "</soap:Header>"
                + "<soapenv:Body>"
                + "<esb:" + esbName + "   xmlns:esb=\"http://esb-sc.yw.zj.chinamobile.com\">"
                + "<reqXml id=\"string\" href=\"http://www.company.org/cum/sonoras\" xsi:type=\"soapenc:string\""
                + "  xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<REQ_PARAM>";
        String footer = "</REQ_PARAM>]]>"
                + "</reqXml> "
                + "</esb:" + esbName + ">"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";
        return header+bodyMsg+footer;
    }

    /**
     * 解析必填校验参数存入map集合
     * @param validParam 必填校验参数
     * @return  map集合
     * @throws UnsupportedEncodingException  字符编码解析异常
     */
    private Map<String,String> parseValidParamToMap(String validParam) throws UnsupportedEncodingException {
        Pattern pattern = Pattern.compile("^<([\\w|:]+)([ ]+)(validType[ ]*=[ ]*\'|validType[ ]*=[ ]*\")(NotNull|Enum|Date|Number|Float|Length|NumValue|Equals|MaxLength)(\"|\')>(.*)<\\/([\\w|:]+)>$");
        validParam = validParam.trim().replaceAll("[\\f\\n\\r\\t\\v]*", "");
        String[] mustCompareArr = validParam.split("\\|");
        Map<String, String> hashMap = new HashMap<String, String>();
        for (String compare:mustCompareArr) {
            compare = compare.trim();
            if(!"".equals(compare)){
                Matcher matcher = pattern.matcher(compare);
                boolean rs = matcher.find();
                if (rs){
                    String validType = matcher.group(4);
                    String text = matcher.group(6);
                    String startNoteName = matcher.group(1);
                    String endNoteName = matcher.group(7);

                    if("NumValue".equals(validType)){//数值校验
                        text = java.net.URLEncoder.encode(text.trim(),   "utf-8");
                    }

                    //封装 xml报文校验组件的参数
                    StringBuilder sb = new StringBuilder(hashMap.get(validType));
                    if (StringUtils.isBlank(sb.toString())){
                        sb.append("<").append(startNoteName).append(">")
                                .append(text).append("</").append(endNoteName).append(">");
                    }else{
                        sb.append("|<").append(startNoteName).append(">")
                                .append(text).append("</").append(endNoteName).append(">");
                    }
                    hashMap.put(validType, sb.toString());
                }else{
                    BusinessException.throwBusinessException("compare parameter is error!"+compare);
                }

            }
        }
        return hashMap;
    }
    
    public static void main(String[]args)throws Exception{
        String inputSoap="<root><PUB_INFO><SYS_OP_ID></SYS_OP_ID><SYS_PASSWORD></SYS_PASSWORD><OP_ID></OP_ID><OP_ORG_ID></OP_ORG_ID></PUB_INFO><BUSI_INFO><PHONE_NO></PHONE_NO><CITY_CODE></CITY_CODE><CHANNEL_ID></CHANNEL_ID><CHANNELADIVID></CHANNELADIVID><PRODUCT_TYPE></PRODUCT_TYPE><ORG_ID></ORG_ID><OPER_ID></OPER_ID><OTHER_INFO></OTHER_INFO></BUSI_INFO></root>";
        Document document= DocumentHelper.parseText(inputSoap);
        String bodyMsg=document.asXML();
        //根节点头部位置
        int headerIndex=bodyMsg.indexOf(CaseEnum.xmlRoot_header.getShow())+CaseEnum.xmlRoot_header.getValue().intValue();
        //根节点尾部位置
        int footerIndex=bodyMsg.indexOf(CaseEnum.xmlRoot_footer.getShow());
        bodyMsg=bodyMsg.substring(headerIndex,footerIndex);
        System.out.println("-------defaultekey------- xml value is :"+bodyMsg+" ------ class name : AutoCaseSv  ------ method name : main");
    } 
}
