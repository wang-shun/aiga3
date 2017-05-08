package com.ai.aiga.service.cases;

import com.ai.aiga.cache.AigaFunFolderCacheCmpt;
import com.ai.aiga.cache.AigaSubSysFolderCacheCmpt;
import com.ai.aiga.cache.AigaSystemFolderCacheCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaCaseFactorDao;
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.*;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.CaseEnum;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.NaCaseTemplateResponse;
import com.ai.aiga.view.controller.cases.dto.CaseTmeplateResponse;
import com.ai.aiga.view.controller.cases.dto.Factor;
import com.ai.aiga.view.controller.cases.dto.TemplateRequest;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CaseTemplateSv extends BaseService{
	
	@Autowired
	private NaCaseTemplateDao caseTemplateDao;
	@Autowired
	private NaCaseFactorDao caseFactorDao;
	
	@Autowired
	private AigaSystemFolderCacheCmpt sysCache;
	@Autowired
	private AigaSubSysFolderCacheCmpt subSysCache;
	@Autowired
	private AigaFunFolderCacheCmpt funSysCache;
	
	@Autowired
	private EsbInterfaceSv esbInterfaceSv;
	
	@Autowired
	private CaseInterfaceSv caseInterfaceSv;
	
	
	public Page<NaCaseTemplateResponse> listTmeplate(NaCaseTemplate condition, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			if(StringUtils.isNotBlank(condition.getCaseName())){
				cons.add(new Condition("caseName", "%".concat(condition.getCaseName()).concat("%"), Condition.Type.LIKE));
			}
			
			if(condition.getSysId() != null){
				cons.add(new Condition("sysId", condition.getSysId(), Condition.Type.EQ));
			}
			
			if(condition.getSysSubId() != null){
				cons.add(new Condition("sysSubId", condition.getSysSubId(), Condition.Type.EQ));
			}
			
			if(condition.getFunId() != null){
				cons.add(new Condition("funId", condition.getFunId(), Condition.Type.EQ));
			}
			
			if(condition.getBusiId() != null){
				cons.add(new Condition("busiId", condition.getBusiId(), Condition.Type.EQ));
			}
			
			if(condition.getScId() != null){
				cons.add(new Condition("scId", condition.getScId(), Condition.Type.EQ));
			}
			
			
			if(condition.getImportant() != null){
				cons.add(new Condition("important", condition.getImportant(), Condition.Type.EQ));
			}
		}
		
		cons.add(new Condition("states", 1, Condition.Type.EQ));
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		
		
		Page<NaCaseTemplate> result = caseTemplateDao.search(cons, pageable);
		List<NaCaseTemplate> content = result.getContent();
		
		List<NaCaseTemplateResponse> list = new ArrayList<NaCaseTemplateResponse>();
		if(content != null){
			for(NaCaseTemplate one : content){
				NaCaseTemplateResponse rep = BeanMapper.map(one, NaCaseTemplateResponse.class);
				if(one.getSysId() != null){
					AigaSystemFolder asf = sysCache.getSysFolder(one.getSysId());
					if(asf == null){
						rep.setSysName(String.valueOf(one.getSysId()));
					}else{
						rep.setSysName(asf.getSysName());
					}
					
				}
				
				if(one.getSysSubId() != null){
					AigaSubSysFolder assf = subSysCache.getSubSys(one.getSysSubId());
					if(assf == null){
						rep.setSysSubName(String.valueOf(one.getSysSubId()));
					}else{
						rep.setSysSubName(assf.getSysName());
					}
				}
				
				if(one.getFunId() != null){
					AigaFunFolder fun = funSysCache.getFunsByFunid(one.getFunId());
					
					if(fun == null){
						rep.setFunName(String.valueOf(one.getFunId()));
					}else{
						rep.setFunName(fun.getBusiLabel());
					}
				}
				
				list.add(rep);
			}
		}
		
		
		return new PageImpl<NaCaseTemplateResponse>(list, pageable, result.getTotalElements());
	}
	

	public void saveTmeplate(TemplateRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}

		NaCaseTemplate template = BeanMapper.map(request, NaCaseTemplate.class);
		template.setStates((byte) 1);
		template.setSysSubId(request.getSubSysId());

		template=caseTemplateDao.save(template);
		//如果为接口类，则保存接口信息
		if(template.getCaseType().equals(CaseEnum.CaseType_interface.getValue())) {
			NaCaseInterface caseInterface = BeanMapper.map(request, NaCaseInterface.class);
			caseInterface.setCaseId(template.getCaseId());
			caseInterfaceSv.save(caseInterface);
		}
		
		//保存因子
		//List<NaCaseFactor> factorList = structureCaseFactor(template.getCaseId(), request.getFactorName(), request.getRemark());
		//caseFactorDao.save(factorList);
		
		List<Factor> factorList = structureCaseFactor(request.getFactors());
		if(factorList != null && factorList.size() > 0){
			List<NaCaseFactor> list = BeanMapper.mapList(factorList, Factor.class, NaCaseFactor.class);
			List<NaCaseFactor> saveList = new ArrayList<NaCaseFactor>();
			for(NaCaseFactor one : list){
				if(StringUtils.isNotBlank(one.getFactorName())){
					one.setCaseId(template.getCaseId());
					saveList.add(one);
				}
			}
			
			caseFactorDao.save(saveList);
		}
		
	}


	private List<NaCaseFactor> structureCaseFactor(long caseId, List<String> factorName, List<String> remark) {
		List<NaCaseFactor> list = new ArrayList<NaCaseFactor>();
		if(factorName != null){
			for(int i = 0; i < factorName.size(); i++){
				
				if(StringUtils.isNoneBlank(factorName.get(i))){
					NaCaseFactor cf = new NaCaseFactor();
					cf.setCaseId(caseId);
					cf.setFactorName(factorName.get(i));
					
					if(remark != null && remark.size() >= (i + 1)){
						cf.setRemark(remark.get(i));
					}
					
					list.add(cf);
				}
				
			}
		}
		return list;
	}


	public void delTmeplate(Long caseId) {
		if(caseId == null || caseId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		NaCaseTemplate temp = caseTemplateDao.findOne(caseId);
		if(temp != null){
			temp.setStates((byte) 0);
			caseTemplateDao.save(temp);
			
			//caseTemplateDao.delete(caseId);
			caseFactorDao.deleteByCaseId(caseId);
		}
		
	}


	public CaseTmeplateResponse getTmeplate(Long caseId) {
	
		if(caseId == null || caseId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		NaCaseTemplate temp = caseTemplateDao.findOne(caseId);
		
		if(temp == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "caseId");
		}
		
		List<NaCaseFactor> facs = caseFactorDao.findByCaseId(caseId);
		
		CaseTmeplateResponse response = BeanMapper.map(temp, CaseTmeplateResponse.class);
		//填充因子信息
		response.setFactors(facs);
		//如果为接口类，则查询接口信息填充
		if(response.getCaseType().equals(CaseEnum.CaseType_interface.getValue())){
			NaCaseInterface caseInterface=this.caseInterfaceSv.findByCaseId(response.getCaseId());
			response.setCaseInterface(caseInterface);
		}		
		return response;
	}
	
//	public NaCaseTemplate getSimpleTmeplate(Long caseId) {
//		
//		if(caseId == null || caseId < 0){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
//		}
//		
//		NaCaseTemplate temp = caseTemplateDao.findOne(caseId);
//		
//		if(temp == null){
//			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "caseId");
//		}
//		
//		return temp;
//	}


	public void updateTmeplate(TemplateRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		
		if(request.getCaseId() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		NaCaseTemplate template = caseTemplateDao.findOne(request.getCaseId());
		
		if(template == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "caseId");
		}
		
		template.setCaseName(request.getCaseName());
		template.setImportant(request.getImportant());
		template.setSysId(request.getSysId());
		template.setSysSubId(request.getSubSysId());
		template.setFunId(request.getFunId());
		template.setBusiId(request.getBusiId());
		
		caseTemplateDao.save(template);
		caseFactorDao.deleteByCaseId(template.getCaseId());
		//保存因子
		List<Factor> factorList = structureCaseFactor(request.getFactors());
		
		if(factorList != null && factorList.size() > 0){
			List<NaCaseFactor> list = BeanMapper.mapList(factorList, Factor.class, NaCaseFactor.class);
			for(NaCaseFactor one : list){
				one.setCaseId(template.getCaseId());
			}
			caseFactorDao.save(list);
		}
		
		
		
	}

	/**
	 * 根据模板ID和因子顺序排序返回因子集合
	 * @param caseId 模板ID
	 * @return 返回因子集合
	 */
	public List<NaCaseFactor> getFactorByCaseIdOrderByFactorDesc(Long caseId){
		if (caseId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		return this.caseFactorDao.findByCaseIdOrderByFactorOrderAsc(caseId);
	}
	
	/**
	 * 根据接口信息获取因子
	 * @param messageId 接口主键ID
	 * @param interfaceType 接口类型
	 * @return Factor集合
	 */
	public List<Factor> getFactor(Long messageId,Long interfaceType)throws Exception{
		List<Factor> factorList=new ArrayList<Factor>();
		if (messageId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "messageId");
		}
		if (interfaceType == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "interfaceType");
		}
		//是否ESB接口
		boolean isESB=interfaceType.equals(CaseEnum.InterfaceType_ESB.getValue());
		//是否CBOSS接口
		boolean isCBOSS=interfaceType.equals(CaseEnum.InterfaceType_CBOSS.getValue());
		if (isESB) {
			AigaEsbInterface esbInterface=this.esbInterfaceSv.findById(messageId);
			//XML字符串必须要有根元素，否则报错
			String inputSoap = CaseEnum.xmlRoot_header.getShow() + esbInterface.getInputSoap() + CaseEnum.xmlRoot_footer.getShow();
			Document document= DocumentHelper.parseText(inputSoap);
			//获取所有叶子节点并存入因子集合中
			factorList=this.listNodesIntoFactorList(document.getRootElement(),factorList,CaseEnum.factorType_one.getValue());
		}
		if (isCBOSS) {
			
		}
		return factorList;
	}

	/**
	 * 递归获取该节点下的所有属性节点
	 * @param node 节点
	 * @param factorList 存放因子的集合
	 * @param factorType 因子类型
	 * @return Factor集合
	 */
	private List<Factor> listNodesIntoFactorList(Element node,List<Factor> factorList,Long factorType){
		List<Element> elements=node.elements();
		//是否叶子节点
		boolean isLeaf = elements.size() == 0;
		if(isLeaf){
			Factor factor=new Factor();
			factor.setFactorName(node.getName());
			factor.setFactorType(factorType);
			//获取因子顺序
			Integer factorOrder = factorList.size() == 0 ? 1 : factorList.get(factorList.size() - 1).getFactorOrder() + 1;
			factor.setFactorOrder(factorOrder);
			//将因子塞入集合
			factorList.add(factor);
		}
		//递归子节点
		for (Element element:elements){
			this.listNodesIntoFactorList(element,factorList,factorType);
		}
		return factorList;
	}
	
	private List<Factor> structureCaseFactor(String factors) {
		
		List<Factor> list = new ArrayList<Factor>();
		
		if(factors != null){
			
			list = JsonUtil.jsonToList(factors, Factor.class);
			
		}
		
		return list;
	}

}
