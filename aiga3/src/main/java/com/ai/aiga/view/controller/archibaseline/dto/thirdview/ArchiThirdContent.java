package com.ai.aiga.view.controller.archibaseline.dto.thirdview;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArchiThirdContent implements Serializable {
	private ArchiThirdLevelView SaaS;
	private ArchiThirdPaaSView PaaS;
	public ArchiThirdPaaSView getPaaS() {
		return PaaS;
	}
	public void setPaaS(ArchiThirdPaaSView paaS) {
		PaaS = paaS;
	}
	public ArchiThirdLevelView getSaaS() {
		return SaaS;
	}
	public void setSaaS(ArchiThirdLevelView saaS) {
		SaaS = saaS;
	}
}
