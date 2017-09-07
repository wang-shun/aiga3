package com.ai.aiga.view.controller.archibaseline.dto.syshealthreport;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@SuppressWarnings("serial")
@Data
public class SysReportGroupBelong implements Serializable {
	public String groupName;
	public List<SysHealthReportGroup> sysHealthReportGroups;
}
