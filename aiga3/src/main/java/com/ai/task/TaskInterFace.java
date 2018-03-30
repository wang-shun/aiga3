package com.ai.task;

import org.springframework.stereotype.Component;

import com.ai.aiga.domain.ArchTaskPlan;

@Component
public interface TaskInterFace {
	public void taskDo(ArchTaskPlan param);
}
