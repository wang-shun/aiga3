package com.ai.aiga.view.json;

/**
 * @ClassName: TeamEmployeeRelRequest
 * @author: liujinfang
 * @date: 2017年4月5日 下午5:22:18
 * @Description:
 * 
 */
public class TeamEmployeeRelRequest {
	private Long id;
    private Long teamId;
    private Long empId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "TeamEmployeeRelRequest [id=" + id + ", teamId=" + teamId + ", empId=" + empId + "]";
	}
    
}

