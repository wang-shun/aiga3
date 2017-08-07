package com.ai.am.constant;

public interface BusiConstant {
	
	//业务工程, 分页属性, 默认分页的页数为 0 .
	public static final int PAGE_DEFAULT = 0;
	//分页属性, 默认一页的数量为0
	public static final int PAGE_SIZE_DEFAULT =10;
	
	//an_auto_backup_deal.state
	enum DEAL_STATE {
		INIT((byte) 0), SUCCESS((byte) 1), FAIL((byte) 2), TEMP((byte) 3);
		public final byte value;

		private DEAL_STATE(byte value) {
			this.value = value;
		};
	};

}
