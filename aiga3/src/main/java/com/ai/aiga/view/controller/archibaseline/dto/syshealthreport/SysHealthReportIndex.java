package com.ai.aiga.view.controller.archibaseline.dto.syshealthreport;
import java.io.Serializable;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class SysHealthReportIndex implements Serializable {
	public String indexName;
	public String indexValue;
}
