---------------------------------------------
-- Export file for user AIAM               --
-- Created by Danny on 2018/8/20, 12:29:40 --
---------------------------------------------

spool DB.log

prompt
prompt Creating table AIGA_AUTHOR
prompt ==========================
prompt
create table AIAM.AIGA_AUTHOR
(
  ROLE_AUTHOR_ID NUMBER not null,
  ROLE_ID        NUMBER,
  STAFF_ID       NUMBER
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_AUTHOR
  add primary key (ROLE_AUTHOR_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_FUNCTION
prompt ============================
prompt
create table AIAM.AIGA_FUNCTION
(
  FUNC_ID     NUMBER(12) not null,
  FUNC_CODE   VARCHAR2(80) not null,
  NAME        VARCHAR2(80) not null,
  NOTES       VARCHAR2(100),
  PARENT_ID   NUMBER(12) not null,
  FUNC_LEVEL  NUMBER(2),
  FUN_SEQ     NUMBER(3),
  VIEWNAME    VARCHAR2(1000),
  DLL_PATH    VARCHAR2(1000),
  FUNC_IMG    VARCHAR2(1000),
  FUNC_ARG    VARCHAR2(1000),
  FUNC_TYPE   CHAR(1),
  STATE       NUMBER(2),
  DONE_CODE   NUMBER(12),
  CREATE_DATE DATE,
  DONE_DATE   DATE,
  VALID_DATE  DATE,
  EXPIRE_DATE DATE,
  OP_ID       NUMBER(12),
  ORG_ID      NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_FUNCTION
  add constraint PK_AIGA_FUNCTION primary key (FUNC_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_ORGANIZE
prompt ============================
prompt
create table AIAM.AIGA_ORGANIZE
(
  ORGANIZE_ID        NUMBER(12) not null,
  PARENT_ORGANIZE_ID NUMBER(12),
  ORGANIZE_NAME      VARCHAR2(200),
  CODE               VARCHAR2(100),
  ORG_ROLE_TYPE_ID   NUMBER(12),
  DISTRICT_ID        VARCHAR2(40),
  SHORT_NAME         VARCHAR2(2000),
  ENGLISH_NAME       VARCHAR2(2000),
  MEMBER_NUM         NUMBER(6),
  MANAGER_NAME       VARCHAR2(50),
  EMAIL              VARCHAR2(50),
  PHONE_ID           VARCHAR2(50),
  FAX_ID             VARCHAR2(50),
  ORG_ADDRESS        VARCHAR2(250),
  CONTACT_NAME       VARCHAR2(50),
  CONTACT_CARD_TYPE  NUMBER(2),
  CONTACT_CARD_ID    VARCHAR2(40),
  POSTCODE           NUMBER(6),
  CONTACT_BILL_ID    VARCHAR2(40),
  POST_PROVINCE      NUMBER(12),
  POST_CITY          NUMBER(12),
  POST_ADDRESS       VARCHAR2(255),
  POST_POSTCOD       NUMBER(6),
  NOTES              VARCHAR2(255),
  STATE              NUMBER(2),
  DONE_CODE          NUMBER(38),
  CREATE_DATE        DATE,
  DONE_DATE          DATE,
  VALID_DATE         DATE,
  EXPIRE_DATE        DATE,
  OP_ID              NUMBER(12),
  ORG_ID             NUMBER(12),
  OLD_CODE           VARCHAR2(255),
  COUNTY_ID          VARCHAR2(255),
  OLD_PARENT_CODE    VARCHAR2(255),
  EXT1               VARCHAR2(255),
  EXT2               VARCHAR2(255),
  EXT3               VARCHAR2(255),
  S_LEAF             VARCHAR2(1)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.AIGA_ORGANIZE.ORGANIZE_NAME
  is '组织名称';
comment on column AIAM.AIGA_ORGANIZE.CODE
  is '编码';
comment on column AIAM.AIGA_ORGANIZE.ORG_ROLE_TYPE_ID
  is '组织类型';
comment on column AIAM.AIGA_ORGANIZE.DISTRICT_ID
  is '归属地区';
comment on column AIAM.AIGA_ORGANIZE.SHORT_NAME
  is '简称';
comment on column AIAM.AIGA_ORGANIZE.ENGLISH_NAME
  is '英文名称';
comment on column AIAM.AIGA_ORGANIZE.MEMBER_NUM
  is '人数';
comment on column AIAM.AIGA_ORGANIZE.MANAGER_NAME
  is '负责人名称';
comment on column AIAM.AIGA_ORGANIZE.EMAIL
  is 'EMAIL';
comment on column AIAM.AIGA_ORGANIZE.PHONE_ID
  is '联系电话';
comment on column AIAM.AIGA_ORGANIZE.FAX_ID
  is '传真';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_NAME
  is '联系人名称';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_CARD_TYPE
  is '联系人证件类型';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_CARD_ID
  is '联系人证件号码';
comment on column AIAM.AIGA_ORGANIZE.POSTCODE
  is '联系人手机';
alter table AIAM.AIGA_ORGANIZE
  add constraint IDS primary key (ORGANIZE_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_ROLE_FUNC
prompt =============================
prompt
create table AIAM.AIGA_ROLE_FUNC
(
  FUNC_ROLE_TRLAT_ID NUMBER not null,
  FUNC_ID            NUMBER,
  ROLE_ID            NUMBER
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_ROLE_FUNC
  add primary key (FUNC_ROLE_TRLAT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_STAFF
prompt =========================
prompt
create table AIAM.AIGA_STAFF
(
  STAFF_ID              NUMBER(12) not null,
  CODE                  VARCHAR2(20),
  NAME                  VARCHAR2(200),
  PASSWORD              VARCHAR2(48),
  BILL_ID               VARCHAR2(50),
  CARD_TYPE_ID          NUMBER(2),
  CARD_NO               VARCHAR2(40),
  EMAIL                 VARCHAR2(50),
  RECENT_PASSWORD       VARCHAR2(300),
  RECENT_PASS_TIMES     NUMBER(2),
  MIN_PASSWD_LENGTH     NUMBER(2),
  ALLOW_CHANGE_PASSWORD CHAR(1),
  ACCT_EFFECT_DATE      DATE,
  ACCT_EXPIRE_DATE      DATE,
  MULTI_LOGIN_FLAG      CHAR(1),
  LAST_LOGIN_LOG_ID     NUMBER(12),
  TRY_TIMES             NUMBER(3),
  LOCK_FLAG             CHAR(1),
  IS_LOGIN              CHAR(1),
  IS_SUPER_USER         CHAR(1),
  NOTES                 VARCHAR2(400),
  PASSWD_VALID_DAYS     NUMBER(10),
  CANCEL_DAYS           NUMBER(3),
  PASSWORD_VALID_DATE   DATE,
  CHG_PASSWD_ALARM_DAYS NUMBER(10),
  DONE_CODE             NUMBER(38),
  CREATE_DATE           DATE,
  DONE_DATE             DATE,
  VALID_DATE            DATE,
  EXPIRE_DATE           DATE,
  ORG_ID                NUMBER(12),
  OP_ID                 NUMBER(12),
  STATE                 NUMBER(2),
  OLD_CODE              VARCHAR2(20),
  OP_TYPE               NUMBER(3),
  EXT1                  VARCHAR2(40),
  EXT2                  VARCHAR2(40),
  EXT3                  VARCHAR2(40),
  OP_LVL                NUMBER(3),
  BAND_TYPE             NUMBER(3)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_STAFF
  add constraint AIGA_PK_STAFF primary key (STAFF_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_STAFF_ORG_RELAT
prompt ===================================
prompt
create table AIAM.AIGA_STAFF_ORG_RELAT
(
  STAFF_ORG_RELAT_ID NUMBER(12) not null,
  ORGANIZE_ID        NUMBER(12),
  STAFF_ID           NUMBER(12),
  IS_ADMIN_STAFF     CHAR(1),
  IS_BASE_ORG        CHAR(1),
  NOTES              VARCHAR2(255),
  DONE_CODE          NUMBER(38),
  CREATE_DATE        DATE,
  DONE_DATE          DATE,
  VALID_DATE         DATE,
  EXPIRE_DATE        DATE,
  OP_ID              NUMBER(12),
  ORG_ID             NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_STAFF_ORG_RELAT
  add constraint AIGA_PK_STAFF_ORG primary key (STAFF_ORG_RELAT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AIGA_STAFF_ROLE
prompt ==============================
prompt
create table AIAM.AIGA_STAFF_ROLE
(
  STAFF_ID      NUMBER(12) not null,
  CODE          VARCHAR2(20),
  NAME          VARCHAR2(200),
  PASSWORD      VARCHAR2(48),
  ORGANIZE_ID   NUMBER(12),
  ORGANIZE_NAME VARCHAR2(200),
  BILL_ID       VARCHAR2(50),
  EMAIL         VARCHAR2(50),
  ROLE_ID       VARCHAR2(12) not null,
  ROLE_NAME     VARCHAR2(60) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AIGA_STAFF_ROLE
  add constraint PK_AIGA_STAFF_ROLE primary key (STAFF_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AI_SCHE_CENTER
prompt =============================
prompt
create table AIAM.AI_SCHE_CENTER
(
  CENTER_CODE VARCHAR2(255),
  CENTER_NAME VARCHAR2(255),
  CENTER_DESC VARCHAR2(1024)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AI_SCHE_TASK_GROUP
prompt =================================
prompt
create table AIAM.AI_SCHE_TASK_GROUP
(
  GROUP_NAME  VARCHAR2(50),
  GROUP_CODE  VARCHAR2(50),
  GROUP_DESC  VARCHAR2(50),
  CREATE_TIME TIMESTAMP(6),
  CENTER_CODE VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AI_SCHE_TASK_INFO
prompt ================================
prompt
create table AIAM.AI_SCHE_TASK_INFO
(
  TASK_CODE            VARCHAR2(50),
  TASK_NAME            VARCHAR2(255),
  TASK_DESC            VARCHAR2(255),
  TASK_TYPE            VARCHAR2(20),
  TASK_GROUP_CODE      VARCHAR2(255),
  VERSION              NUMBER(11),
  START_TIME           VARCHAR2(100),
  END_TIME             VARCHAR2(50),
  SCAN_INTERVAL_TIME   NUMBER(11),
  SCAN_NUM             NUMBER(11),
  EXECUTE_NUM          NUMBER(11),
  IDLE_SLEEP_TIME      NUMBER(11),
  THREAD_NUM           NUMBER(11),
  SPLIT_REGION         NUMBER(11),
  ITEMS                VARCHAR2(255),
  PROCESS_CLASS        VARCHAR2(255),
  FAULT_PROCESS_METHOD VARCHAR2(10),
  STATE                VARCHAR2(10),
  CREATE_TIME          TIMESTAMP(6),
  PRIORITY             NUMBER(11),
  IS_LOG               NUMBER(11),
  MARATHON_MEM         NUMBER(11),
  TERM_TYPE            VARCHAR2(10),
  DISPATCH_TYPE        VARCHAR2(10),
  REGION_CODE          VARCHAR2(50),
  SERVER_NAME          VARCHAR2(255),
  MULTI_FLAG           VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AM_CORE_INDEX
prompt ============================
prompt
create table AIAM.AM_CORE_INDEX
(
  INDEX_ID     NUMBER(12) not null,
  INDEX_NAME   VARCHAR2(100) not null,
  INDEX_GROUP  CHAR(50) not null,
  SCH_ID       VARCHAR2(40) not null,
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  STATE        CHAR(1) not null,
  CREATE_DATE  DATE not null,
  CREATE_OP_ID NUMBER(12),
  GROUP_ID     NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AM_CORE_INDEX
  add constraint PK_AM_INDEX primary key (INDEX_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AM_CORE_INDEX_EXT
prompt ================================
prompt
create table AIAM.AM_CORE_INDEX_EXT
(
  INDEX_ID     NUMBER(12) not null,
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  QUERY_SQL    VARCHAR2(4000),
  STATE        CHAR(1) not null,
  CREATE_DATE  DATE not null,
  CREATE_OP_ID NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.AM_CORE_INDEX_EXT
  add constraint PK_AM_INDEX_EXT primary key (INDEX_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table AM_CORE_INDEX_TREE
prompt =================================
prompt
create table AIAM.AM_CORE_INDEX_TREE
(
  INDEX_ID    NUMBER(12) not null,
  INDEX_NAME  VARCHAR2(100) not null,
  INDEX_GROUP CHAR(50) not null,
  SCH_ID      VARCHAR2(40) not null,
  STATE       CHAR(1) not null,
  CREATE_DATE DATE not null,
  GROUP_ID    NUMBER(12),
  EXT_1       VARCHAR2(100),
  EXT_2       VARCHAR2(100),
  EXT_3       VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHITECTURE_FIRST
prompt =================================
prompt
create table AIAM.ARCHITECTURE_FIRST
(
  ID_FIRST        NUMBER(10) not null,
  NAME            VARCHAR2(255) not null,
  SHORT_NAME      VARCHAR2(255),
  DESCRIPTION     VARCHAR2(255),
  CODE            VARCHAR2(50) not null,
  BELONG_LEVEL    VARCHAR2(20),
  STATE           VARCHAR2(20),
  APPLY_ID        NUMBER(10),
  APPLY_USER      VARCHAR2(255),
  CREATE_DATE     DATE not null,
  MODIFY_DATE     DATE,
  IDENTIFIED_INFO VARCHAR2(255),
  FILE_INFO       VARCHAR2(500),
  EXT_1           VARCHAR2(255),
  EXT_2           VARCHAR2(255),
  EXT_3           VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCHITECTURE_FIRST.ID_FIRST
  is '一级域编号';
comment on column AIAM.ARCHITECTURE_FIRST.NAME
  is '一级域名称
（1：业务支撑域；
2：信管域；
3：BOMC域；
4：大数据域；
5：安全域；
6：公共域；
7：网络域；
8：地市域；
9：开放域；）';
comment on column AIAM.ARCHITECTURE_FIRST.SHORT_NAME
  is '简称';
comment on column AIAM.ARCHITECTURE_FIRST.DESCRIPTION
  is '描述';
comment on column AIAM.ARCHITECTURE_FIRST.CODE
  is '编码
（1：业务支撑域 B；
2：信管域 M；
3：BOMC域 C；
4：大数据域 D；
5：安全域 S；
6：公共域 P；
7：网络域 O；
8：地市域 R；
9：开放域 N；）';
comment on column AIAM.ARCHITECTURE_FIRST.BELONG_LEVEL
  is '所属分层';
comment on column AIAM.ARCHITECTURE_FIRST.STATE
  is '流程状态
（1：申请；
2：审批通过；
3：审批不通过；）';
comment on column AIAM.ARCHITECTURE_FIRST.APPLY_ID
  is '申请编号';
comment on column AIAM.ARCHITECTURE_FIRST.APPLY_USER
  is '申请人';
comment on column AIAM.ARCHITECTURE_FIRST.CREATE_DATE
  is '创建时间';
comment on column AIAM.ARCHITECTURE_FIRST.MODIFY_DATE
  is '修改时间';
comment on column AIAM.ARCHITECTURE_FIRST.IDENTIFIED_INFO
  is '认定信息';
comment on column AIAM.ARCHITECTURE_FIRST.FILE_INFO
  is '归档信息';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_1
  is '扩展字段1';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_2
  is '扩展字段2';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_3
  is '扩展字段3';
alter table AIAM.ARCHITECTURE_FIRST
  add constraint PK_ARCHITECTURE_FIRST primary key (ID_FIRST)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHITECTURE_GRADING
prompt ===================================
prompt
create table AIAM.ARCHITECTURE_GRADING
(
  APPLY_ID        NUMBER(10) not null,
  IDENTIFIED_INFO VARCHAR2(255),
  SYS_ID          NUMBER(10) not null,
  NAME            VARCHAR2(255) not null,
  SYSTEM_FUNCTION VARCHAR2(2048),
  DESCRIPTION     VARCHAR2(255),
  CODE            VARCHAR2(50),
  ID_BELONG       NUMBER(10),
  BELONG_LEVEL    VARCHAR2(20),
  DEPARTMENT      VARCHAR2(255),
  PROJECT_INFO    VARCHAR2(255),
  DESIGN_INFO     VARCHAR2(255),
  SYS_STATE       VARCHAR2(20),
  STATE           VARCHAR2(20) not null,
  RANK_INFO       VARCHAR2(255),
  APPLY_USER      VARCHAR2(255) not null,
  APPLY_TIME      DATE,
  MODIFY_DATE     DATE,
  CREATE_DATE     DATE,
  EXT_1           VARCHAR2(255),
  EXT_2           VARCHAR2(255),
  EXT_3           VARCHAR2(255),
  ONLYSYS_ID      NUMBER(10),
  IDENTIFY_USER   VARCHAR2(255),
  FILE_ID         NUMBER(20),
  DEVELOPER       VARCHAR2(255),
  CLOUD_ORDER_ID  VARCHAR2(255),
  APPLY_USER_INFO VARCHAR2(255),
  PRINCIPAL       VARCHAR2(255),
  BACK_MESSAGE    VARCHAR2(2048)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_ID
  is '申请编号';
comment on column AIAM.ARCHITECTURE_GRADING.IDENTIFIED_INFO
  is '认定信息';
comment on column AIAM.ARCHITECTURE_GRADING.SYS_ID
  is '系统编号
';
comment on column AIAM.ARCHITECTURE_GRADING.NAME
  is '名称
（1：订单中心系统；
2：账户中心系统；
3：融合计费系统；
4：实时账务系统；
5：渠道中心系统；
6：稽核中心系统；
7：统一酬金系统；
--以业务支撑域为例）';
comment on column AIAM.ARCHITECTURE_GRADING.SYSTEM_FUNCTION
  is '系统功能';
comment on column AIAM.ARCHITECTURE_GRADING.DESCRIPTION
  is '描述';
comment on column AIAM.ARCHITECTURE_GRADING.CODE
  is '编码';
comment on column AIAM.ARCHITECTURE_GRADING.ID_BELONG
  is '所属上级域编号';
comment on column AIAM.ARCHITECTURE_GRADING.BELONG_LEVEL
  is '所属分层
（1：交互应用层SaaS；
2：核心业务中心BPaaS；
3：通用服务中心UPaaS；
4：应用集成平台服务IPaaS；
5：通用技术组件平台服务TPaaS；
6：基础设施服务:IaaS；）';
comment on column AIAM.ARCHITECTURE_GRADING.DEPARTMENT
  is '责任部门';
comment on column AIAM.ARCHITECTURE_GRADING.PROJECT_INFO
  is '项目立项信息';
comment on column AIAM.ARCHITECTURE_GRADING.DESIGN_INFO
  is '规划设计信息';
comment on column AIAM.ARCHITECTURE_GRADING.SYS_STATE
  is '系统建设状态';
comment on column AIAM.ARCHITECTURE_GRADING.STATE
  is '流程状态
（1：申请；
2：审批通过；
3：审批不通过；）';
comment on column AIAM.ARCHITECTURE_GRADING.RANK_INFO
  is '系统等级信息';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_USER
  is '申请人';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_TIME
  is '申请时间';
comment on column AIAM.ARCHITECTURE_GRADING.MODIFY_DATE
  is '修改时间';
comment on column AIAM.ARCHITECTURE_GRADING.CREATE_DATE
  is '创建时间';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_1
  is '扩展信息1   系统层级（1：一级域 2：二级子域 3：三级系统）';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_2
  is '扩展信息2   建设状态（时间）';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_3
  is '扩展信息3   （app .pc）';
comment on column AIAM.ARCHITECTURE_GRADING.ONLYSYS_ID
  is '系统识别编号';
comment on column AIAM.ARCHITECTURE_GRADING.IDENTIFY_USER
  is '认定人';
comment on column AIAM.ARCHITECTURE_GRADING.FILE_ID
  is '文件编号';
comment on column AIAM.ARCHITECTURE_GRADING.DEVELOPER
  is '开发厂商';
comment on column AIAM.ARCHITECTURE_GRADING.CLOUD_ORDER_ID
  is '云管平台创建业务系统订单编号';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_USER_INFO
  is '申请人附属信息';
comment on column AIAM.ARCHITECTURE_GRADING.PRINCIPAL
  is '责任人';
comment on column AIAM.ARCHITECTURE_GRADING.BACK_MESSAGE
  is '审批意见回复';
alter table AIAM.ARCHITECTURE_GRADING
  add constraint PK_ARCHITECTURE_GRADING primary key (APPLY_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHITECTURE_SECOND
prompt ==================================
prompt
create table AIAM.ARCHITECTURE_SECOND
(
  ID_SECOND       NUMBER(10) not null,
  NAME            VARCHAR2(255) not null,
  SHORT_NAME      VARCHAR2(255),
  DESCRIPTION     VARCHAR2(255),
  CODE            VARCHAR2(50) not null,
  ID_FIRST        NUMBER(10) not null,
  BELONG_LEVEL    VARCHAR2(20) not null,
  STATE           VARCHAR2(20),
  APPLY_ID        NUMBER(10),
  APPLY_USER      VARCHAR2(255),
  CREATE_DATE     DATE not null,
  MODIFY_DATE     DATE,
  IDENTIFIED_INFO VARCHAR2(255),
  FILE_INFO       VARCHAR2(500),
  EXT_1           VARCHAR2(255),
  EXT_2           VARCHAR2(255),
  EXT_3           VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCHITECTURE_SECOND.ID_SECOND
  is '二级子域编号';
comment on column AIAM.ARCHITECTURE_SECOND.NAME
  is '二级子域名称
（1：业务受理中心；
2：运营管理中心；
3：热线渠道中心；
4：电子渠道中心；
5：订单中心；
6：账户中心；
7：计费账务中心；
--以部分业务支撑域为例
）';
comment on column AIAM.ARCHITECTURE_SECOND.SHORT_NAME
  is '简称';
comment on column AIAM.ARCHITECTURE_SECOND.DESCRIPTION
  is '描述';
comment on column AIAM.ARCHITECTURE_SECOND.CODE
  is '编码
（1：业务受理中心 BSC；
2：运营管理中心 OPM；
3：热线渠道中心 HTL；
4：电子渠道中心 ECC；
5：订单中心 ORD；
6：账户中心 ACC；
7：计费账务中心 AMS；
--以部分业务支撑域为例
）';
comment on column AIAM.ARCHITECTURE_SECOND.ID_FIRST
  is '所属一级域编号';
comment on column AIAM.ARCHITECTURE_SECOND.BELONG_LEVEL
  is '所属分层
（1：交互应用层SaaS；
2：核心业务中心BPaaS；
3：通用服务中心UPaaS；
4：应用集成平台服务IPaaS；
5：通用技术组件平台服务TPaaS；
6：基础设施服务:IaaS；）';
comment on column AIAM.ARCHITECTURE_SECOND.STATE
  is '流程状态
（1：申请；
2：审批通过；
3：审批不通过；）
';
comment on column AIAM.ARCHITECTURE_SECOND.APPLY_ID
  is '申请编号';
comment on column AIAM.ARCHITECTURE_SECOND.APPLY_USER
  is '申请人';
comment on column AIAM.ARCHITECTURE_SECOND.CREATE_DATE
  is '创建时间';
comment on column AIAM.ARCHITECTURE_SECOND.MODIFY_DATE
  is '修改时间';
comment on column AIAM.ARCHITECTURE_SECOND.IDENTIFIED_INFO
  is '认定信息';
comment on column AIAM.ARCHITECTURE_SECOND.FILE_INFO
  is '归档信息';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_1
  is '扩展字段1';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_2
  is '扩展字段2';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_3
  is '扩展字段3';
alter table AIAM.ARCHITECTURE_SECOND
  add constraint PK_ARCHITECTURE_SECOND primary key (ID_SECOND)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHITECTURE_STATIC_DATA
prompt =======================================
prompt
create table AIAM.ARCHITECTURE_STATIC_DATA
(
  DATA_ID    NUMBER(10) not null,
  CODE_TYPE  VARCHAR2(255),
  CODE_VALUE VARCHAR2(255),
  CODE_NAME  VARCHAR2(255),
  CODE_DESC  VARCHAR2(255),
  EXT1       VARCHAR2(255),
  EXT2       VARCHAR2(255),
  EXT3       VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCHITECTURE_STATIC_DATA
  add constraint PK_ARCHITECTURE_STATIC_DATA primary key (DATA_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHITECTURE_THIRD
prompt =================================
prompt
create table AIAM.ARCHITECTURE_THIRD
(
  ONLYSYS_ID      NUMBER(10) not null,
  ID_THIRD        NUMBER(10) not null,
  NAME            VARCHAR2(255) not null,
  SYSTEM_CODE     VARCHAR2(255),
  SYSTEM_FUNCTION VARCHAR2(500),
  DESCRIPTION     VARCHAR2(255),
  CODE            VARCHAR2(50),
  ID_SECOND       NUMBER(10) not null,
  BELONG_LEVEL    VARCHAR2(20) not null,
  DEPARTMENT      VARCHAR2(255),
  PROJECT_INFO    VARCHAR2(255),
  DESIGN_INFO     VARCHAR2(255),
  RANK_INFO       VARCHAR2(255),
  SYS_STATE       VARCHAR2(255) not null,
  STATE           VARCHAR2(20),
  APPLY_ID        NUMBER(10),
  APPLY_USER      VARCHAR2(255),
  CREATE_DATE     DATE not null,
  MODIFY_DATE     DATE,
  IDENTIFIED_INFO VARCHAR2(255),
  FILE_INFO       VARCHAR2(500),
  EXT_1           VARCHAR2(255),
  EXT_2           VARCHAR2(255),
  EXT_3           VARCHAR2(255),
  DEVELOPER       VARCHAR2(255),
  CLOUD_ORDER_ID  VARCHAR2(255),
  PRINCIPAL       VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCHITECTURE_THIRD.ID_THIRD
  is '三级系统编号
';
comment on column AIAM.ARCHITECTURE_THIRD.SYSTEM_FUNCTION
  is '系统功能';
comment on column AIAM.ARCHITECTURE_THIRD.DESCRIPTION
  is '描述';
comment on column AIAM.ARCHITECTURE_THIRD.CODE
  is '编码';
comment on column AIAM.ARCHITECTURE_THIRD.BELONG_LEVEL
  is '所属分层
（1：交互应用层SaaS；
2：核心业务中心BPaaS；
3：通用服务中心UPaaS；
4：应用集成平台服务IPaaS；
5：通用技术组件平台服务TPaaS；
6：基础设施服务:IaaS；）';
comment on column AIAM.ARCHITECTURE_THIRD.DEPARTMENT
  is '责任部门';
comment on column AIAM.ARCHITECTURE_THIRD.PROJECT_INFO
  is '项目立项信息';
comment on column AIAM.ARCHITECTURE_THIRD.DESIGN_INFO
  is '规划设计信息';
comment on column AIAM.ARCHITECTURE_THIRD.STATE
  is '流程状态
（1：申请；
2：审批通过；
3：审批不通过；）';
comment on column AIAM.ARCHITECTURE_THIRD.APPLY_ID
  is '申请编号';
comment on column AIAM.ARCHITECTURE_THIRD.APPLY_USER
  is '申请人';
comment on column AIAM.ARCHITECTURE_THIRD.CREATE_DATE
  is '创建时间';
comment on column AIAM.ARCHITECTURE_THIRD.MODIFY_DATE
  is '修改时间';
comment on column AIAM.ARCHITECTURE_THIRD.IDENTIFIED_INFO
  is '认定信息';
comment on column AIAM.ARCHITECTURE_THIRD.FILE_INFO
  is '归档信息';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_1
  is '扩展信息1';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_2
  is '扩展信息2';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_3
  is '扩展信息3';
comment on column AIAM.ARCHITECTURE_THIRD.DEVELOPER
  is '开发厂商';
comment on column AIAM.ARCHITECTURE_THIRD.CLOUD_ORDER_ID
  is '云管平台创建业务系统订单编号';
comment on column AIAM.ARCHITECTURE_THIRD.PRINCIPAL
  is '责任人';
alter table AIAM.ARCHITECTURE_THIRD
  add constraint PK_ARCHITECTURE_THIRD primary key (ONLYSYS_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHI_ONLINE_PLAN
prompt ================================
prompt
create table AIAM.ARCHI_ONLINE_PLAN
(
  ONLINE_TIME DATE not null,
  CREATE_TIME DATE,
  EXT_1       VARCHAR2(255),
  EXT_2       VARCHAR2(255),
  EXT_3       VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCHI_ONLINE_PLAN
  add constraint PK_ARCHI_ONLINE_PLAN primary key (ONLINE_TIME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHI_SYS_HEALTH_REPORT
prompt ======================================
prompt
create table AIAM.ARCHI_SYS_HEALTH_REPORT
(
  REPORT_ID  NUMBER(12) not null,
  ONLYSYS_ID NUMBER(12),
  TIME       DATE,
  TYPE       VARCHAR2(10),
  KEY        VARCHAR2(255),
  VALUE      VARCHAR2(255),
  CREAT_TIME DATE,
  EXT_1      VARCHAR2(255),
  EXT_2      VARCHAR2(255),
  EXT_3      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCHI_SYS_HEALTH_REPORT
  add constraint PK_ARCHI_SYS_HEALTH_REPORT primary key (REPORT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCHI_TOP_LIST
prompt =============================
prompt
create table AIAM.ARCHI_TOP_LIST
(
  INDEX_ID     NUMBER(10) not null,
  INDEX_SEQ    NUMBER(10),
  INDEX_NAME   VARCHAR2(100),
  INDEX_GROUP  VARCHAR2(100),
  SETT_MONTH   VARCHAR2(10),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE NUMBER(10),
  IMG_SRC      VARCHAR2(100),
  STAR_NUM     NUMBER(2),
  CREATE_DATE  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCHI_TOP_LIST.INDEX_ID
  is '编号';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_SEQ
  is '序列';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_NAME
  is '名称';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_GROUP
  is '分组';
comment on column AIAM.ARCHI_TOP_LIST.SETT_MONTH
  is '采集时间';
comment on column AIAM.ARCHI_TOP_LIST.KEY_1
  is '键1';
comment on column AIAM.ARCHI_TOP_LIST.KEY_2
  is '键2';
comment on column AIAM.ARCHI_TOP_LIST.KEY_3
  is '键3';
comment on column AIAM.ARCHI_TOP_LIST.RESULT_VALUE
  is '值';
comment on column AIAM.ARCHI_TOP_LIST.IMG_SRC
  is '头像路径';
comment on column AIAM.ARCHI_TOP_LIST.STAR_NUM
  is '颗星数量';
comment on column AIAM.ARCHI_TOP_LIST.CREATE_DATE
  is '创建时间';
alter table AIAM.ARCHI_TOP_LIST
  add constraint PK_ARCHI_TOP_LIST primary key (INDEX_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_APPID_SYSTEM_RELATION
prompt =========================================
prompt
create table AIAM.ARCH_APPID_SYSTEM_RELATION
(
  ID       NUMBER(10),
  INDEX_ID NUMBER(10),
  APPID    VARCHAR2(256),
  CENTER   VARCHAR2(32),
  MODULE   VARCHAR2(32),
  DB       VARCHAR2(16),
  EXT      VARCHAR2(256)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_BACKSTAGE_PROCESS
prompt =====================================
prompt
create table AIAM.ARCH_BACKSTAGE_PROCESS
(
  CENTER_CODE VARCHAR2(255),
  CENTER_NAME VARCHAR2(255),
  TERM_TYPE   VARCHAR2(255),
  SERVER_NAME VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_BUSI_ERRCODE_MAP
prompt ====================================
prompt
create table AIAM.ARCH_BUSI_ERRCODE_MAP
(
  INSERT_TIME       DATE,
  PERSON            VARCHAR2(256),
  CENTER            VARCHAR2(256),
  DATA_RESOURCE     VARCHAR2(256),
  ERRCODE_MAP_ID    NUMBER(12),
  CSF_SERVICE_CODE  VARCHAR2(128),
  I18N_ERRCODE      VARCHAR2(128),
  I18N_ERRCODE_DESC VARCHAR2(256),
  ESB_ERRCODE       VARCHAR2(128),
  ESB_ERRCODE_DESC  VARCHAR2(256),
  CSF_ERRCODE       VARCHAR2(128),
  CSF_ERRCODE_DESC  VARCHAR2(256),
  CREATE_DATE       DATE,
  STATE_DATE        DATE,
  STATE             CHAR(1),
  REMARKS           VARCHAR2(512),
  CHECK_RESULT      VARCHAR2(128)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_CAPABILITY_PLANNING
prompt =======================================
prompt
create table AIAM.ARCH_CAPABILITY_PLANNING
(
  ID               NUMBER(10),
  REGULATIONS_NAME VARCHAR2(255),
  REGULATIONS_CODE VARCHAR2(255),
  SECOND_NAME      VARCHAR2(255),
  SECOND_CODE      VARCHAR2(255),
  THIRD_NAME       VARCHAR2(255),
  THIRD_CODE       VARCHAR2(255),
  THIRD_DESC       VARCHAR2(512),
  STANDARD         VARCHAR2(255),
  EXT1             VARCHAR2(255),
  EXT2             VARCHAR2(255),
  EXT3             VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_CONNECT_RESOURCE
prompt ====================================
prompt
create table AIAM.ARCH_CONNECT_RESOURCE
(
  FROM_SYS_NAME VARCHAR2(256),
  TOTAL         NUMBER(10),
  REMARK        VARCHAR2(32),
  SETT_MONTH    VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_CSF_ERRCODE_REPORT
prompt ======================================
prompt
create table AIAM.ARCH_CSF_ERRCODE_REPORT
(
  ID                 NUMBER not null,
  MONTH_DATE         VARCHAR2(32),
  CENTER_NAME        VARCHAR2(256),
  ERRCODE_CFG_NUM    NUMBER,
  CFG_CSF_NUM        NUMBER,
  TOTLA_CSF_NUM      NUMBER,
  ERRCODE_COVER_RATE VARCHAR2(32),
  ERRCODE_SPEC_RATE  VARCHAR2(32),
  COLLECTION_ORIGIN  VARCHAR2(128),
  PM_OF_CHINAMOBILE  VARCHAR2(128),
  PM_OF_ASIAINFO     VARCHAR2(128),
  INSERT_DATE        DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DB_CONNECT
prompt ==============================
prompt
create table AIAM.ARCH_DB_CONNECT
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table AIAM.ARCH_DB_CONNECT
  is '架构治理-数据库连接指标';
create index AIAM.IDX_20180711 on AIAM.ARCH_DB_CONNECT (INDEX_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DB_CONNECT_20170826
prompt =======================================
prompt
create table AIAM.ARCH_DB_CONNECT_20170826
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DB_SESSION
prompt ==============================
prompt
create table AIAM.ARCH_DB_SESSION
(
  CREATE_DATE DATE,
  KEY1        VARCHAR2(256),
  KEY2        VARCHAR2(256),
  KEY3        VARCHAR2(256),
  KEY4        VARCHAR2(256),
  KEY5        VARCHAR2(256),
  KEY6        VARCHAR2(256),
  KEY7        VARCHAR2(256),
  KEY8        VARCHAR2(256),
  KEY9        VARCHAR2(256)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DB_SESSION_20180726
prompt =======================================
prompt
create table AIAM.ARCH_DB_SESSION_20180726
(
  BATCH_ID        VARCHAR2(32),
  INSERT_DATE     DATE,
  SID             VARCHAR2(256),
  OSUSER          VARCHAR2(256),
  MACHINE         VARCHAR2(256),
  MODULE          VARCHAR2(256),
  USERNAME        VARCHAR2(256),
  SCHEMANAME      VARCHAR2(256),
  CLIENT_INFO     VARCHAR2(256),
  STATUS          VARCHAR2(8),
  DB_NAME         VARCHAR2(16),
  FROM_SYS_NAME   VARCHAR2(64),
  FROM_SYS_MODULE VARCHAR2(64),
  REMARK          VARCHAR2(256),
  KEY_1           VARCHAR2(256),
  KEY_2           VARCHAR2(256)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DCOS_DATA
prompt =============================
prompt
create table AIAM.ARCH_DCOS_DATA
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_DCOS_DATA_INDEX1 on AIAM.ARCH_DCOS_DATA (KEY_1)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_DCOS_DATA_INDEX2 on AIAM.ARCH_DCOS_DATA (SETT_MONTH)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_DCOS_DATA_INDEX3 on AIAM.ARCH_DCOS_DATA (RESULT_VALUE)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DCOS_DATA_20180813
prompt ======================================
prompt
create table AIAM.ARCH_DCOS_DATA_20180813
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_DCOS_NUM
prompt ============================
prompt
create table AIAM.ARCH_DCOS_NUM
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_EARLY_WARNING
prompt =================================
prompt
create table AIAM.ARCH_EARLY_WARNING
(
  INDEX_ID        NUMBER(12) not null,
  INDEX_NAME      VARCHAR2(100) not null,
  INDEX_GROUP     VARCHAR2(100),
  GROUP_ID        VARCHAR2(255) not null,
  EMAIL_ADDRESS   VARCHAR2(2048) not null,
  EMAIL_CCLIST    VARCHAR2(2048),
  EMAIL_HEAD      VARCHAR2(255) not null,
  EMAIL_CONTENT   VARCHAR2(4000),
  DATA_SOURCE     VARCHAR2(100) not null,
  CONDITION_SQL   VARCHAR2(4000) not null,
  CONDITION_TYPE  VARCHAR2(100) not null,
  CONDITION_VALUE VARCHAR2(100) not null,
  POST_URL        VARCHAR2(2048),
  CONTENT_SQL     VARCHAR2(4000),
  FILE_SQL        VARCHAR2(4000),
  SCH_ID          VARCHAR2(40),
  STATE           CHAR(1),
  CREATE_DATE     DATE,
  EXT1            VARCHAR2(2048),
  EXT2            VARCHAR2(2048),
  EXT3            VARCHAR2(2048)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_EARLY_WARNING
  add constraint PK_ARCH_EARLY_WARNING primary key (INDEX_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_FUNCTION_RECORD
prompt ===================================
prompt
create table AIAM.ARCH_FUNCTION_RECORD
(
  RECORD_ID   NUMBER(10) not null,
  MENU_URL    VARCHAR2(255) not null,
  MENU_CODE   VARCHAR2(255),
  MENU_NAME   VARCHAR2(255),
  USER_CODE   VARCHAR2(255),
  USER_NAME   VARCHAR2(255),
  RECORD_TIME DATE,
  EXT_1       VARCHAR2(255),
  EXT_2       VARCHAR2(255),
  EXT_3       VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_FUNCTION_RECORD
  add constraint PK_ARCH_FUNCTION_RECORD primary key (RECORD_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_HEAT_BASE
prompt =============================
prompt
create table AIAM.ARCH_HEAT_BASE
(
  INDEX_NAME    VARCHAR2(256),
  MODULE        VARCHAR2(256),
  VESSEL_NUM    NUMBER(10),
  MIN_IDLE      NUMBER(10),
  MAX_IDLE      NUMBER(10),
  GTMIN_IDLE    NUMBER(10),
  TOTAL_SESSION NUMBER(10),
  PERSENTAGE    NUMBER(10),
  INSERT_TIME   DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_MONTH_INDEX
prompt ===============================
prompt
create table AIAM.ARCH_MONTH_INDEX
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table AIAM.ARCH_MONTH_INDEX
  is '架构治理-月度指标';

prompt
prompt Creating table ARCH_NOTICE_TITLE
prompt ================================
prompt
create table AIAM.ARCH_NOTICE_TITLE
(
  ID                 NUMBER(12) not null,
  NOTICE_TITLE       VARCHAR2(100),
  NOTICE_DESCRIPTION VARCHAR2(200),
  NOTICE_DETAILS     VARCHAR2(3999)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_NOTICE_TITLE
  add primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SESSION_CONNECT_RESOURCE
prompt ============================================
prompt
create table AIAM.ARCH_SESSION_CONNECT_RESOURCE
(
  FROM_SYS_NAME VARCHAR2(256),
  TOTAL         NUMBER(10),
  REMARK        VARCHAR2(32),
  DB_NAME       VARCHAR2(32),
  SETT_MONTH    VARCHAR2(10),
  BATCH_ID      VARCHAR2(32)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SRV_MANAGE
prompt ==============================
prompt
create table AIAM.ARCH_SRV_MANAGE
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12),
  INSERT_TIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SRV_MANAGE_20170826
prompt =======================================
prompt
create table AIAM.ARCH_SRV_MANAGE_20170826
(
  INDEX_ID     NUMBER(12),
  SETT_MONTH   VARCHAR2(12),
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  RESULT_VALUE VARCHAR2(100),
  GROUP_ID     NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_STAFF_GRAD
prompt ==============================
prompt
create table AIAM.ARCH_STAFF_GRAD
(
  APPLY_ID       NUMBER(12) not null,
  STAFF_CODE     VARCHAR2(255) not null,
  STAFF_NAME     VARCHAR2(255) not null,
  STAFF_PASSWORD VARCHAR2(255) not null,
  EMAIL          VARCHAR2(255) not null,
  BILL_ID        VARCHAR2(255) not null,
  ROLE_ID        VARCHAR2(255),
  ORGANIZE_ID    VARCHAR2(255),
  ORGANIZE_NAME  VARCHAR2(255),
  STATE          VARCHAR2(50),
  GRADING_PERSON VARCHAR2(255),
  CREATE_DATE    DATE,
  MODIFY_DATE    DATE,
  EXT1           VARCHAR2(500),
  EXT2           VARCHAR2(500),
  EXT3           VARCHAR2(500)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_STAFF_GRAD
  add constraint PK_ARCH_STAFF_GRAD primary key (APPLY_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SVN_DBCP
prompt ============================
prompt
create table AIAM.ARCH_SVN_DBCP
(
  CENTER       VARCHAR2(32),
  MODULE       VARCHAR2(32),
  DB           VARCHAR2(20),
  INITIAL_SIZE NUMBER(10),
  MAX_ACTIVE   NUMBER(10),
  MAX_IDLE     NUMBER(10),
  MIN_IDLE     NUMBER(10),
  MAX_WAIT     NUMBER(10),
  INSERT_TIME  DATE,
  IS_CHANGE    VARCHAR2(4)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_SVN_DBCP_INDEX1 on AIAM.ARCH_SVN_DBCP (CENTER)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_SVN_DBCP_INDEX2 on AIAM.ARCH_SVN_DBCP (MODULE)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.ARCH_SVN_DBCP_INDEX3 on AIAM.ARCH_SVN_DBCP (DB)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SYNCHRO_APPID
prompt =================================
prompt
create table AIAM.ARCH_SYNCHRO_APPID
(
  INDEX_ID     NUMBER(12) not null,
  INDEX_NAME   VARCHAR2(100) not null,
  INDEX_GROUP  CHAR(50) not null,
  SCH_ID       VARCHAR2(40) not null,
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  STATE        CHAR(1) not null,
  CREATE_DATE  DATE not null,
  CREATE_OP_ID NUMBER(12),
  GROUP_ID     NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SYS_QRY_SQL_CFG
prompt ===================================
prompt
create table AIAM.ARCH_SYS_QRY_SQL_CFG
(
  QRYSQL_CFG_ID      VARCHAR2(16) not null,
  REPORT_MODULE_CODE VARCHAR2(32) not null,
  DATA_CODE          VARCHAR2(16) not null,
  DATA_NAME          VARCHAR2(1024) not null,
  QRY_SQL            VARCHAR2(4000) not null,
  REMARK             VARCHAR2(1024),
  MODIFY_DATE        DATE default sysdate
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table AIAM.ARCH_SYS_QRY_SQL_CFG
  is '新业务系统运行状态巡检报告指标查询SQL配置表，用于配置报告报表中所需参数的查询SQL';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.DATA_CODE
  is '原始计算数据ID，与配置表中的参数ID对应';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.DATA_NAME
  is '原始计算数据名称';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.QRY_SQL
  is '参数对应查询SQL';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.REMARK
  is '备注';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.MODIFY_DATE
  is '配置修改时间';
create index AIAM.ARCH_SYS_QRY_SQL_CFG_ID on AIAM.ARCH_SYS_QRY_SQL_CFG (QRYSQL_CFG_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SYS_REPORT_CFG
prompt ==================================
prompt
create table AIAM.ARCH_SYS_REPORT_CFG
(
  REPORT_CFG_ID        VARCHAR2(16) not null,
  REPORT_SHOW_ORDER    NUMBER(16) default 0 not null,
  REPORT_MODULE_CODE   VARCHAR2(32) not null,
  MODULE_SHOW_ORDER    NUMBER(16) default 0 not null,
  MODULE_TYPE          VARCHAR2(32) not null,
  STATE                VARCHAR2(4),
  REPORT_CONTENT_CFG_1 VARCHAR2(4000) not null,
  REPORT_CONTENT_CFG_2 VARCHAR2(4000),
  REPORT_CONTENT_CFG_3 VARCHAR2(4000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table AIAM.ARCH_SYS_REPORT_CFG
  is '新业务系统运行状态巡检报告内容配置表，用于配置报告各个模块的展示顺序，正文内容，变量获取对应关系';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CFG_ID
  is '巡检报告配置主键ID';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_SHOW_ORDER
  is '内容子模块展示顺序编号';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_MODULE_CODE
  is '内容子模块编码';
comment on column AIAM.ARCH_SYS_REPORT_CFG.MODULE_SHOW_ORDER
  is '子模块内部展示顺序编号';
comment on column AIAM.ARCH_SYS_REPORT_CFG.MODULE_TYPE
  is '子模块内部内容类型，如标题，正文，采集说明等';
comment on column AIAM.ARCH_SYS_REPORT_CFG.STATE
  is '配置有效性状态,U为生效，X为已下线';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_1
  is '巡检报告内容配置字段1';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_2
  is '巡检报告内容配置字段2';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_3
  is '巡检报告内容配置字段3';
create index AIAM.IDX_ARCH_SYS_REPORT_CFG_ID on AIAM.ARCH_SYS_REPORT_CFG (REPORT_CFG_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SYS_REPORT_DATA
prompt ===================================
prompt
create table AIAM.ARCH_SYS_REPORT_DATA
(
  DATA_ID     VARCHAR2(16) not null,
  CJ_DATE     VARCHAR2(16) not null,
  DATA_CODE   VARCHAR2(16) not null,
  DATA_VALUE  VARCHAR2(4000) not null,
  INSERT_DATE DATE not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on table AIAM.ARCH_SYS_REPORT_DATA
  is '新业务系统运行状态巡检原始数据结果表，通过采集计算将报告所需值沉淀至本表中，配置表通过正文中的变量ID关联从本表获取结果数据';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_ID
  is '巡检报告原始计算结果数据主键序列';
comment on column AIAM.ARCH_SYS_REPORT_DATA.CJ_DATE
  is '报告采集展示日期';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_CODE
  is '原始计算数据ID，与配置表中的参数ID对应';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_VALUE
  is '原始计算数据结果值';
comment on column AIAM.ARCH_SYS_REPORT_DATA.INSERT_DATE
  is '数据创建时间';
create index AIAM.IDX_ARCH_SYS_REPORT_DATA_ID on AIAM.ARCH_SYS_REPORT_DATA (DATA_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_SYS_REPORT_RESULT
prompt =====================================
prompt
create table AIAM.ARCH_SYS_REPORT_RESULT
(
  RESULT_ID          VARCHAR2(16) not null,
  CJ_DATE            VARCHAR2(16) not null,
  REPORT_SHOW_ORDER  NUMBER(16) default 0 not null,
  REPORT_MODULE_CODE VARCHAR2(32) not null,
  MODULE_SHOW_ORDER  NUMBER(16) default 0 not null,
  MODULE_TYPE        VARCHAR2(32) not null,
  REPORT_CONTENT_1   VARCHAR2(4000) not null,
  REPORT_CONTENT_2   VARCHAR2(4000),
  REPORT_CONTENT_3   VARCHAR2(4000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.ARCH_SYS_REPORT_RESULT.RESULT_ID
  is '主键序列';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.CJ_DATE
  is '报告采集展示日期';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_SHOW_ORDER
  is '内容子模块展示顺序编号';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_MODULE_CODE
  is '内容子模块编码';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.MODULE_SHOW_ORDER
  is '子模块内部展示顺序编号';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.MODULE_TYPE
  is '子模块内部内容类型，如标题，正文，采集说明等';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_1
  is '巡检报告结果正文1';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_2
  is '巡检报告结果正文2';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_3
  is '巡检报告结果正文3';
create index AIAM.IDX_ARCH_SYS_REPORT_RESULT_ID on AIAM.ARCH_SYS_REPORT_RESULT (RESULT_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_TASK_CONTROL
prompt ================================
prompt
create table AIAM.ARCH_TASK_CONTROL
(
  TASK_INS       VARCHAR2(255) not null,
  PRE_FIRE_TIME  VARCHAR2(255),
  FIRE_TIME      VARCHAR2(255),
  NEXT_FIRE_TIME VARCHAR2(255),
  CLASS_PATH     VARCHAR2(1000),
  CRONEXPRESSION VARCHAR2(255),
  PARAM1         VARCHAR2(500),
  PARAM2         VARCHAR2(500),
  PARAM3         VARCHAR2(500),
  PARAM4         VARCHAR2(500),
  PARAM5         VARCHAR2(500),
  STATE          CHAR(1),
  EXT1           VARCHAR2(500),
  EXT2           VARCHAR2(500),
  EXT3           VARCHAR2(500)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_TASK_CONTROL
  add constraint PK_ARCH_TASK_CONTROL primary key (TASK_INS)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_TASK_PLAN
prompt =============================
prompt
create table AIAM.ARCH_TASK_PLAN
(
  TASK_ID        NUMBER(10) not null,
  TASK_NAME      VARCHAR2(255) not null,
  TASK_DESC      VARCHAR2(1000),
  CLASS_PATH     VARCHAR2(1000) not null,
  CRONEXPRESSION VARCHAR2(255) not null,
  PARAM1         VARCHAR2(500),
  PARAM2         VARCHAR2(500),
  PARAM3         VARCHAR2(500),
  PARAM4         VARCHAR2(500),
  PARAM5         VARCHAR2(500),
  CREATE_DATE    DATE not null,
  STATE          CHAR(1),
  EXT1           VARCHAR2(500),
  EXT2           VARCHAR2(500),
  EXT3           VARCHAR2(500)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ARCH_TASK_PLAN
  add constraint PK_ARCH_TASK_PLAN primary key (TASK_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ARCH_WORK_PLAN
prompt =============================
prompt
create table AIAM.ARCH_WORK_PLAN
(
  ID                NUMBER(10) not null,
  NAME              VARCHAR2(20) not null,
  MATTERS           VARCHAR2(30),
  CLASSIFICATION    VARCHAR2(10),
  JOBCONTENT        VARCHAR2(700),
  COMPLETION        VARCHAR2(500),
  PROJECTCOMPLETION VARCHAR2(10),
  SUBMITTIMELY      VARCHAR2(20),
  FILLQUALITY       VARCHAR2(20),
  QUALITY           VARCHAR2(100),
  BEGAINTIME        DATE,
  ENDTIME           DATE,
  WORKSTATE         VARCHAR2(10),
  PERSON            VARCHAR2(20),
  PRIORITY          VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BOMC_ONLINEMANAGE
prompt ================================
prompt
create table AIAM.BOMC_ONLINEMANAGE
(
  NAME        VARCHAR2(100),
  ONLINETIME  NUMBER(15),
  SYSCATEGORY VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BP_TEMPLATE
prompt ==========================
prompt
create table AIAM.BP_TEMPLATE
(
  TEMPLATE_CODE       VARCHAR2(255),
  TEMPLATE_NAME       VARCHAR2(255),
  TEMPLATE_GROUP_CODE VARCHAR2(255),
  FTP_ID              NUMBER(14),
  FORMAT_ID           NUMBER(14),
  SERVER_NAME         VARCHAR2(255),
  PRIORITY            NUMBER(6),
  SCAN_ITV_TIME       NUMBER(14),
  SCAN_ROWNUM         NUMBER(14),
  DATA_GROUP_COUNT    NUMBER(14),
  PROCESS_OP_ID       NUMBER(14),
  PROCESS_ORG_ID      NUMBER(14),
  PROCESS_REGION_ID   VARCHAR2(6),
  PROCESS_CLASSNAME   VARCHAR2(1000),
  BUSI_PARA_URL       VARCHAR2(1000),
  STATE               CHAR(1),
  REMARKS             VARCHAR2(255),
  IDEMPOTENT_FLAG     VARCHAR2(10),
  TRANSACTIONAL_FLAG  VARCHAR2(10),
  ORDER_LIMIT_NUM     NUMBER(14),
  BUSI_THREAD_NUM     NUMBER(14)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BP_TEMPLATE_GROUP
prompt ================================
prompt
create table AIAM.BP_TEMPLATE_GROUP
(
  TEMPLATE_GROUP_CODE VARCHAR2(255),
  TEMPLATE_GROUP_DESC VARCHAR2(255),
  CENTER_CODE         VARCHAR2(255),
  STATE               CHAR(1),
  REMARKS             VARCHAR2(255),
  LIMIT_NUM           NUMBER(14)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BS_BUSI_PARAM_MAP
prompt ================================
prompt
create table AIAM.BS_BUSI_PARAM_MAP
(
  BUSI_PARAM_MAP_ID    NUMBER(12) not null,
  INTERFACE_ID         NUMBER(12),
  BUSI_PARAM_ID        NUMBER(12),
  PARAM_VALUE          VARCHAR2(512),
  PARAM_VALUE_DESC     VARCHAR2(256),
  PARAM_VALUE_MAP      VARCHAR2(512),
  PARAM_VALUE_MAP_DESC VARCHAR2(256),
  CREATE_DATE          DATE,
  STATE_DATE           DATE,
  STATE                CHAR(1),
  REMARKS              VARCHAR2(512)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BS_F_BOMC_ESB
prompt ============================
prompt
create table AIAM.BS_F_BOMC_ESB
(
  BASEID                VARCHAR2(50) not null,
  BASESCHEMA            VARCHAR2(255),
  BASENAME              VARCHAR2(255),
  BASESN                VARCHAR2(255),
  BASEENTRYID           VARCHAR2(50),
  BASECREATEDATE        NUMBER(15),
  BASESENDDATE          NUMBER(15),
  BASEACCEPTDATE        NUMBER(15),
  BASEFINISHDATE        NUMBER(15),
  BASECLOSEDATE         NUMBER(15),
  BASESTATUS            VARCHAR2(255),
  BASETPLID             VARCHAR2(255),
  BASEISARCHIVE         VARCHAR2(255),
  BASEISTRUECLOSE       VARCHAR2(255),
  BASEWORKFLOWFLAG      NUMBER(15),
  BASECATAGORYNAME      VARCHAR2(255),
  BASECATAGORYID        VARCHAR2(255),
  BASECREATORFULLNAME   VARCHAR2(255),
  BASECREATORLOGINNAME  VARCHAR2(255),
  BASECREATORCONNECTWAY VARCHAR2(255),
  BASECREATORCORP       VARCHAR2(255),
  BASECREATORCORPID     VARCHAR2(255),
  BASECREATORDEP        VARCHAR2(255),
  BASECREATORDEPID      VARCHAR2(255),
  BASECREATORDN         VARCHAR2(255),
  BASECREATORDNID       VARCHAR2(255),
  P_SERVICE_ID          VARCHAR2(150),
  P_REF_ID              VARCHAR2(50),
  P_SERVICE_DESC        VARCHAR2(1000),
  P_TEST_RESULT         VARCHAR2(50),
  P_PRINCIPAL           VARCHAR2(50),
  P_DESIGN_DOC          VARCHAR2(100),
  ACTIONID              VARCHAR2(50),
  P_REMARK              VARCHAR2(1000),
  P_STORY_ID            VARCHAR2(50),
  P_CHECK               VARCHAR2(50),
  P_INTERFACE           VARCHAR2(50),
  P_CHANGE_AFFECT       VARCHAR2(50),
  P_SERVICE_NAME        VARCHAR2(200),
  P_ASSEST_TYPE         VARCHAR2(50),
  P_SERVICE_BTN         VARCHAR2(150),
  P_CHANGE_TYPE         VARCHAR2(100),
  P_DEV_ID              VARCHAR2(100),
  SER_IMPORTPARAM       CLOB,
  P_PARAM_DOC           VARCHAR2(100),
  P_PERFORMANCE         VARCHAR2(50),
  STATUS                VARCHAR2(50),
  P_REQ                 VARCHAR2(50),
  BUSDEID               VARCHAR2(100),
  P_ONLINE_TYPE         VARCHAR2(50),
  P_CHANGE_CAUSE        VARCHAR2(50),
  P_RESULT_ANALYZE      VARCHAR2(1000),
  P_MAX_AMOUNT          VARCHAR2(50),
  P_ERROR_CODE          VARCHAR2(2000),
  P_ONLINE_TIME         NUMBER(15),
  P_SERVICE_CHANGE_TYPE VARCHAR2(200),
  OWNER                 VARCHAR2(50),
  P_TEST_DESC           VARCHAR2(2000),
  P_ID                  VARCHAR2(50),
  P_CONN_SYSTEM         VARCHAR2(50),
  P_DESIGN_TIME         VARCHAR2(50),
  P_ADD_CAUSE           VARCHAR2(50),
  TASKID                VARCHAR2(50),
  P_REQ_YIJIAN          VARCHAR2(1000),
  P_SERVICE_PROVIDER    VARCHAR2(50),
  P_SERVICE_MANAGER     VARCHAR2(50),
  BASESUMMARY           VARCHAR2(100),
  P_CHANGE_CONTENT      VARCHAR2(50),
  PFYXBZ                VARCHAR2(50),
  P_AVG_TIME            VARCHAR2(50),
  P_SERVICE_SN          VARCHAR2(100),
  P_MAX_CONCURRENCY     VARCHAR2(50),
  SER_OUTPUTPARAM       CLOB,
  P_SERVICE_SYSTEM      VARCHAR2(300),
  SWDEID                VARCHAR2(100),
  PFTESTM               VARCHAR2(50),
  PFTEST                VARCHAR2(50),
  ATTACHMENT            VARCHAR2(50),
  P_SERVICE_VERSION     VARCHAR2(50),
  P_SERVICE_CHECK       VARCHAR2(50),
  P_ISTEST              VARCHAR2(50),
  P_SYS                 VARCHAR2(50),
  P_SERVICE_CLASS       VARCHAR2(50),
  P_CONTRAST            VARCHAR2(10),
  P_ISONLINE            VARCHAR2(10),
  P_OFF_CAUSE           VARCHAR2(50),
  P_OFF_AFFECT          VARCHAR2(50),
  REQMANAGER            VARCHAR2(50),
  DEVMANAGER            VARCHAR2(50),
  REQTITLE              VARCHAR2(500),
  SOFTREQID             VARCHAR2(100),
  OWNERID               VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CENTER_PROJECT_MANAGER_CFG
prompt =========================================
prompt
create table AIAM.CENTER_PROJECT_MANAGER_CFG
(
  CENTER_CODE          VARCHAR2(32),
  CENTER_NAME          VARCHAR2(128),
  PM_OF_CHINAMOBILE    VARCHAR2(32),
  EMAIL_OF_CHINAMOBILE VARCHAR2(256),
  PM_OF_ASIAINFO       VARCHAR2(32),
  EMAIL_OF_ASIAINFO    VARCHAR2(256),
  UPDATE_DATE          DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_BO_MASK
prompt ==========================
prompt
create table AIAM.CFG_BO_MASK
(
  BO_NAME             VARCHAR2(255) not null,
  ATTR_NAME           VARCHAR2(255) not null,
  MASK_FUNCTION_CLASS VARCHAR2(255) not null,
  STATE               CHAR(1) not null,
  REMARKS             VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_BO_MASK
  add constraint PK_CFG_BO_MASK primary key (BO_NAME, ATTR_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_CLIENT_TIMEOUT
prompt =================================
prompt
create table AIAM.CFG_CLIENT_TIMEOUT
(
  SERVER_NAME         VARCHAR2(100) not null,
  INTERFACE_CLASSNAME VARCHAR2(300) not null,
  METHOD_NAME         VARCHAR2(100) not null,
  PARAMETER_COUNT     NUMBER(6) not null,
  TIMEOUT_SECOND      NUMBER(6) not null,
  STATE               CHAR(1) not null,
  REMARKS             VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_CLIENT_TIMEOUT
  add constraint PK_CFG_CLIENT_TIMEOUT primary key (INTERFACE_CLASSNAME, METHOD_NAME, PARAMETER_COUNT)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_DB_ACCT
prompt ==========================
prompt
create table AIAM.CFG_DB_ACCT
(
  DB_ACCT_CODE     VARCHAR2(255) not null,
  USERNAME         VARCHAR2(255),
  PASSWORD         VARCHAR2(255),
  HOST             VARCHAR2(255),
  PORT             NUMBER(5),
  SID              VARCHAR2(255),
  DEFAULT_CONN_MIN NUMBER(3),
  DEFAULT_CONN_MAX NUMBER(3),
  STATE            CHAR(1),
  REMARKS          VARCHAR2(1000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_DB_ACCT
  add primary key (DB_ACCT_CODE)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_DB_ACCT_1 on AIAM.CFG_DB_ACCT (USERNAME)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_DB_JDBC_PARAMETER
prompt ====================================
prompt
create table AIAM.CFG_DB_JDBC_PARAMETER
(
  PARAMETER_ID NUMBER(12) not null,
  DB_ACCT_CODE VARCHAR2(255) not null,
  SERVER_NAME  VARCHAR2(255) not null,
  NAME         VARCHAR2(255) not null,
  VALUE        VARCHAR2(255) not null,
  STATE        CHAR(1) not null,
  REMARKS      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_DB_JDBC_PARAMETER
  add primary key (PARAMETER_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_DB_JDBC_PARAMETER_1 on AIAM.CFG_DB_JDBC_PARAMETER (SERVER_NAME)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_DB_JDBC_PARAMETER_2 on AIAM.CFG_DB_JDBC_PARAMETER (DB_ACCT_CODE)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_DB_RELAT
prompt ===========================
prompt
create table AIAM.CFG_DB_RELAT
(
  DB_ACCT_CODE VARCHAR2(255) not null,
  URL_NAME     VARCHAR2(255),
  SERVER_NAME  VARCHAR2(255) not null,
  STATE        CHAR(1),
  REMARKS      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_DB_RELAT
  add primary key (DB_ACCT_CODE, SERVER_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_DB_RELAT_1 on AIAM.CFG_DB_RELAT (SERVER_NAME)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_DB_URL
prompt =========================
prompt
create table AIAM.CFG_DB_URL
(
  NAME    VARCHAR2(255) not null,
  URL     VARCHAR2(4000),
  STATE   CHAR(1),
  REMARKS VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_DB_URL
  add primary key (NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_DYNC_TABLE_SPLIT
prompt ===================================
prompt
create table AIAM.CFG_DYNC_TABLE_SPLIT
(
  GROUP_NAME      VARCHAR2(255) not null,
  TABLE_NAME      VARCHAR2(255) not null,
  TABLE_NAME_EXPR VARCHAR2(255) not null,
  CONVERT_CLASS   VARCHAR2(255) not null,
  STATE           CHAR(1) not null,
  REMARKS         VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_DYNC_TABLE_SPLIT
  add primary key (GROUP_NAME, TABLE_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_I18N_RESOURCE
prompt ================================
prompt
create table AIAM.CFG_I18N_RESOURCE
(
  RES_KEY VARCHAR2(15) not null,
  ZH_CN   VARCHAR2(1000) not null,
  EN_US   VARCHAR2(1000),
  STATE   CHAR(1) not null,
  REMARK  VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_I18N_RESOURCE
  add primary key (RES_KEY)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_ID_GENERATOR
prompt ===============================
prompt
create table AIAM.CFG_ID_GENERATOR
(
  TABLE_NAME             VARCHAR2(100) not null,
  DOMAIN_ID              NUMBER(6) not null,
  GENERATOR_TYPE         CHAR(1) not null,
  SEQUENCE_NAME          VARCHAR2(60),
  MAX_ID                 NUMBER(12),
  START_VALUE            NUMBER(12),
  MIN_VALUE              NUMBER(12),
  MAX_VALUE              NUMBER(12),
  INCREMENT_VALUE        NUMBER(6),
  CYCLE_FLAG             CHAR(1),
  CACHE_SIZE             NUMBER(6),
  SEQUENCE_CREATE_SCRIPT VARCHAR2(1000),
  HIS_TABLE_NAME         VARCHAR2(100),
  HIS_SEQUENCE_NAME      VARCHAR2(60),
  HIS_DATA_FLAG          CHAR(1),
  HIS_MAX_ID             NUMBER(12),
  HIS_DONECODE_FLAG      CHAR(1),
  STEP_BY                NUMBER(6),
  HIS_STEP_BY            NUMBER(6)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_ID_GENERATOR
  add primary key (TABLE_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_ID_GENERATOR_WRAPPER
prompt =======================================
prompt
create table AIAM.CFG_ID_GENERATOR_WRAPPER
(
  TABLE_NAME                 VARCHAR2(100) not null,
  TABLE_SEQ_WRAPPER_IMPL     VARCHAR2(1000),
  HIS_TABLE_SEQ_WRAPPER_IMPL VARCHAR2(1000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_ID_GENERATOR_WRAPPER
  add primary key (TABLE_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_METHOD_CENTER
prompt ================================
prompt
create table AIAM.CFG_METHOD_CENTER
(
  SERVICE_IMPL_CLASSNAME VARCHAR2(255) not null,
  METHOD_NAME            VARCHAR2(255) not null,
  PARAMETER_COUNT        NUMBER(6) not null,
  PARAMETER_INDEX        NUMBER(6) not null,
  PARAMETER_FUNCTION     VARCHAR2(255) not null,
  CENTER_TYPE            VARCHAR2(255) not null,
  STATE                  CHAR(1) not null,
  REMARKS                VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_METHOD_CENTER
  add primary key (SERVICE_IMPL_CLASSNAME, METHOD_NAME, PARAMETER_COUNT)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_SERVICE_CONTROL
prompt ==================================
prompt
create table AIAM.CFG_SERVICE_CONTROL
(
  SERVER_NAME  VARCHAR2(1000) not null,
  SERVICE_NAME VARCHAR2(1000) not null,
  METHOD_NAME  VARCHAR2(1000),
  LIMIT_COUNT  NUMBER(6) not null,
  STATE        CHAR(1) not null,
  REMARKS      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_TABLE_SPLIT
prompt ==============================
prompt
create table AIAM.CFG_TABLE_SPLIT
(
  TABLE_NAME      VARCHAR2(255) not null,
  TABLE_NAME_EXPR VARCHAR2(255) not null,
  STATE           CHAR(1) not null,
  REMARKS         VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_TABLE_SPLIT
  add primary key (TABLE_NAME)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_TABLE_SPLIT_MAPPING
prompt ======================================
prompt
create table AIAM.CFG_TABLE_SPLIT_MAPPING
(
  MAPPING_ID           NUMBER(12) not null,
  TABLE_NAME           VARCHAR2(255) not null,
  COLUMN_NAME          VARCHAR2(255) not null,
  COLUMN_CONVERT_CLASS VARCHAR2(255) not null,
  STATE                CHAR(1) not null,
  REMARKS              VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_TABLE_SPLIT_MAPPING
  add primary key (MAPPING_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_TABLE_SPLIT_MAPPING_1 on AIAM.CFG_TABLE_SPLIT_MAPPING (TABLE_NAME, COLUMN_NAME)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_TASK
prompt =======================
prompt
create table AIAM.CFG_TASK
(
  CFG_TASK_ID        NUMBER(12) not null,
  TASK_NAME          VARCHAR2(255) not null,
  CFG_TASK_TYPE_CODE VARCHAR2(255) not null,
  BUSINESS_CLASS     VARCHAR2(255) not null,
  TASK_METHOD        CHAR(1) not null,
  TASK_EXPR          VARCHAR2(255),
  SORT_ID            NUMBER(12),
  STAFF_ID           NUMBER(12),
  CREATE_DATE        DATE not null,
  STATE_DATE         DATE not null,
  STATE              CHAR(1) not null,
  REMARKS            VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.CFG_TASK.CFG_TASK_ID
  is '任务配置ID';
comment on column AIAM.CFG_TASK.TASK_NAME
  is '任务名称';
comment on column AIAM.CFG_TASK.CFG_TASK_TYPE_CODE
  is '任务配置类型';
comment on column AIAM.CFG_TASK.BUSINESS_CLASS
  is '接口的业务实现类';
comment on column AIAM.CFG_TASK.TASK_METHOD
  is '任务执行方式(F,为固定时间;C,为cron周期执行;I,为立即执行)';
comment on column AIAM.CFG_TASK.TASK_EXPR
  is '任务配置表达式';
comment on column AIAM.CFG_TASK.SORT_ID
  is '任务优先级别(越大优先级别越高) ';
comment on column AIAM.CFG_TASK.STAFF_ID
  is '配置任务的员工';
comment on column AIAM.CFG_TASK.CREATE_DATE
  is '任务创建时间';
comment on column AIAM.CFG_TASK.STATE_DATE
  is '任务配置的状态更新时间';
comment on column AIAM.CFG_TASK.STATE
  is '状态(U,为正常;E,为删除;F,为执行完成;X,为异常执行完成)';
comment on column AIAM.CFG_TASK.REMARKS
  is '备注';
alter table AIAM.CFG_TASK
  add primary key (CFG_TASK_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_TASK_1 on AIAM.CFG_TASK (CFG_TASK_TYPE_CODE)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CFG_TASK_PARAM_VALUE
prompt ===================================
prompt
create table AIAM.CFG_TASK_PARAM_VALUE
(
  ID          NUMBER(12) not null,
  CFG_TASK_ID NUMBER(12) not null,
  PARAM_ID    NUMBER(12) not null,
  PARAM_VALUE VARCHAR2(255),
  REMARKS     VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.CFG_TASK_PARAM_VALUE
  add constraint PK_CFG_TASK_PARAM_VALUE primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.IDX_CFG_TASK_PARAM_VALUE_1 on AIAM.CFG_TASK_PARAM_VALUE (CFG_TASK_ID)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CLOUD_DATA
prompt =========================
prompt
create table AIAM.CLOUD_DATA
(
  CLOUD_NAME VARCHAR2(255),
  CLOUD_ID   VARCHAR2(255),
  THIRD_NAME VARCHAR2(255),
  THIRD_ID   VARCHAR2(255),
  FIR_NAME   VARCHAR2(255),
  FIR_ID     VARCHAR2(255),
  SEC_NAME   VARCHAR2(255),
  SEC_ID     VARCHAR2(255),
  EXT_1      VARCHAR2(255),
  EXT_2      VARCHAR2(255),
  EXT_3      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CLOUD_DATA_MAPPED
prompt ================================
prompt
create table AIAM.CLOUD_DATA_MAPPED
(
  OGNIZATION    VARCHAR2(255),
  OGNIZATION_ID VARCHAR2(255),
  CON_PERSON    VARCHAR2(255),
  CON_PERSON_ID VARCHAR2(255),
  CLOUD_NAME    VARCHAR2(255),
  CLOUD_ID      VARCHAR2(255),
  THIRD_NAME    VARCHAR2(255),
  THIRD_ID      VARCHAR2(255),
  FIR_NAME      VARCHAR2(255),
  FIR_ID        VARCHAR2(255),
  SEC_NAME      VARCHAR2(255),
  SEC_ID        VARCHAR2(255),
  EXT_1         VARCHAR2(255),
  EXT_2         VARCHAR2(255),
  EXT_3         VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CLOUD_DATA_SELECT
prompt ================================
prompt
create table AIAM.CLOUD_DATA_SELECT
(
  CLOUD_NAME VARCHAR2(255),
  THIRD_NAME VARCHAR2(255),
  EXT_1      VARCHAR2(255),
  EXT_2      VARCHAR2(255),
  EXT_3      VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table COPY_AM_CORE_INDEX
prompt =================================
prompt
create table AIAM.COPY_AM_CORE_INDEX
(
  INDEX_ID     NUMBER(12) not null,
  INDEX_NAME   VARCHAR2(100) not null,
  INDEX_GROUP  CHAR(50) not null,
  SCH_ID       VARCHAR2(40) not null,
  KEY_1        VARCHAR2(100),
  KEY_2        VARCHAR2(100),
  KEY_3        VARCHAR2(100),
  STATE        CHAR(1) not null,
  CREATE_DATE  DATE not null,
  CREATE_OP_ID NUMBER(12),
  GROUP_ID     NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table CSF_CENTER_INFO
prompt ==============================
prompt
create table AIAM.CSF_CENTER_INFO
(
  CENTER_CODE VARCHAR2(64),
  CENTER_NAME VARCHAR2(64),
  NUMBERS     NUMBER(2)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table DB_SESSION_COUNT
prompt ===============================
prompt
create table AIAM.DB_SESSION_COUNT
(
  ID               NUMBER(10) not null,
  SYSTEM_NAME      VARCHAR2(128),
  SYSTEM_SUBDOMAIN VARCHAR2(128),
  NAME             VARCHAR2(128),
  BUSINESS_INFO    VARCHAR2(30),
  CREATE_TIME      VARCHAR2(20)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FIRST
prompt ====================
prompt
create table AIAM.FIRST
(
  FIRST_CATEGORY NUMBER(10) not null,
  NAME           VARCHAR2(50) not null,
  QUES_TYPE      NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.FIRST
  add constraint PK_FIRST primary key (FIRST_CATEGORY)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FIRST_CATEGORY
prompt =============================
prompt
create table AIAM.FIRST_CATEGORY
(
  FIRST_ID NUMBER(10) not null,
  NAME     VARCHAR2(50) not null,
  ROOT_ID  NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.FIRST_CATEGORY
  add constraint PK_FIRST_CATEGORY primary key (FIRST_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table INSPECT_RADAR_ERROR
prompt ==================================
prompt
create table AIAM.INSPECT_RADAR_ERROR
(
  ERR_ID        NUMBER(10) not null,
  RULE_ID       NUMBER(10),
  SYS_ID        NUMBER(10),
  INDEX_NAME    VARCHAR2(255),
  INDEX_INFO    VARCHAR2(2000),
  PARANT_NAME   VARCHAR2(255),
  INDEX_MARK    VARCHAR2(255),
  INDEX_VALUE   VARCHAR2(255),
  INDEX_TIME    DATE,
  RULE_TYPE     VARCHAR2(255),
  RULE_FUNCTION VARCHAR2(255),
  RULE_VALUE    VARCHAR2(255),
  CREATE_TIME   DATE,
  EXT1          VARCHAR2(255),
  EXT2          VARCHAR2(255),
  EXT3          VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.INSPECT_RADAR_ERROR.ERR_ID
  is '错误编号（序列）';
comment on column AIAM.INSPECT_RADAR_ERROR.RULE_ID
  is '规则编号';
comment on column AIAM.INSPECT_RADAR_ERROR.SYS_ID
  is '系统编号';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_NAME
  is '指标名称';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_INFO
  is '指标信息说明';
comment on column AIAM.INSPECT_RADAR_ERROR.PARANT_NAME
  is '所属八大军规';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_MARK
  is '指标得分';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_VALUE
  is '指标数值';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_TIME
  is '指标更新时间';
comment on column AIAM.INSPECT_RADAR_ERROR.CREATE_TIME
  is '创建时间';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT1
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT2
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT3
  is '拓展字段';

prompt
prompt Creating table INSPECT_RADAR_RESULT
prompt ===================================
prompt
create table AIAM.INSPECT_RADAR_RESULT
(
  RESULT_ID   NUMBER(10) not null,
  SYS_ID      NUMBER(10),
  TOTAL_MARK  VARCHAR2(255),
  AQ_MARK     VARCHAR2(255),
  RL_MARK     VARCHAR2(255),
  JK_MARK     VARCHAR2(255),
  GKY_MARK    VARCHAR2(255),
  RXKY_MARK   VARCHAR2(255),
  PZ_MARK     VARCHAR2(255),
  RZ_MARK     VARCHAR2(255),
  FC_MARK     VARCHAR2(255),
  CREATE_TIME DATE,
  SPONSOR     VARCHAR2(255),
  EXT1        VARCHAR2(255),
  EXT2        VARCHAR2(255),
  EXT3        VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.INSPECT_RADAR_RESULT.RESULT_ID
  is '结果编号（序列）';
comment on column AIAM.INSPECT_RADAR_RESULT.SYS_ID
  is '系统编号';
comment on column AIAM.INSPECT_RADAR_RESULT.TOTAL_MARK
  is '总得分';
comment on column AIAM.INSPECT_RADAR_RESULT.AQ_MARK
  is '安全';
comment on column AIAM.INSPECT_RADAR_RESULT.RL_MARK
  is '容量';
comment on column AIAM.INSPECT_RADAR_RESULT.JK_MARK
  is '监控';
comment on column AIAM.INSPECT_RADAR_RESULT.GKY_MARK
  is '高可用';
comment on column AIAM.INSPECT_RADAR_RESULT.RXKY_MARK
  is '柔性可用';
comment on column AIAM.INSPECT_RADAR_RESULT.PZ_MARK
  is '配置';
comment on column AIAM.INSPECT_RADAR_RESULT.RZ_MARK
  is '日志';
comment on column AIAM.INSPECT_RADAR_RESULT.FC_MARK
  is '分层';
comment on column AIAM.INSPECT_RADAR_RESULT.CREATE_TIME
  is '创建时间';
comment on column AIAM.INSPECT_RADAR_RESULT.SPONSOR
  is '发起人';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT1
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT2
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT3
  is '拓展字段';

prompt
prompt Creating table INSPECT_RADAR_RULE
prompt =================================
prompt
create table AIAM.INSPECT_RADAR_RULE
(
  RULE_ID       NUMBER(10) not null,
  TEMP_ID       NUMBER(10),
  SYS_ID        NUMBER(10),
  INDEX_VALUE   VARCHAR2(255),
  INDEX_TIME    DATE,
  INDEX_SQL     VARCHAR2(2000),
  INDEX_MARK    VARCHAR2(255),
  STATE         CHAR(1),
  RULE_TYPE     VARCHAR2(255),
  RULE_FUNCTION VARCHAR2(255),
  RULE_VALUE    VARCHAR2(255),
  CREATE_TIME   DATE,
  EXT1          VARCHAR2(255),
  EXT2          VARCHAR2(255),
  EXT3          VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.INSPECT_RADAR_RULE.RULE_ID
  is '规则编号（序列）';
comment on column AIAM.INSPECT_RADAR_RULE.TEMP_ID
  is '模板编号';
comment on column AIAM.INSPECT_RADAR_RULE.SYS_ID
  is '系统编号';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_VALUE
  is '指标采集数值';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_TIME
  is '指标采集时间';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_SQL
  is '指标采集sql';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_MARK
  is '指标得分';
comment on column AIAM.INSPECT_RADAR_RULE.STATE
  is '状态';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_TYPE
  is '规则类型; 常规、正则、特殊';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_FUNCTION
  is '判断方法';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_VALUE
  is '规则判断值';
comment on column AIAM.INSPECT_RADAR_RULE.CREATE_TIME
  is '创建时间';
comment on column AIAM.INSPECT_RADAR_RULE.EXT1
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_RULE.EXT2
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_RULE.EXT3
  is '拓展字段';

prompt
prompt Creating table INSPECT_RADAR_TEMP
prompt =================================
prompt
create table AIAM.INSPECT_RADAR_TEMP
(
  TEMP_ID      NUMBER(10) not null,
  INDEX_NAME   VARCHAR2(255),
  INDEX_INFO   VARCHAR2(2000),
  INDEX_TYPE   VARCHAR2(255),
  PARENT_ID    NUMBER(10),
  INDEX_WEIGHT VARCHAR2(255),
  STATE        CHAR(1),
  CREATE_TIME  DATE,
  EXT1         VARCHAR2(255),
  EXT2         VARCHAR2(255),
  EXT3         VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.INSPECT_RADAR_TEMP.TEMP_ID
  is '模板编号（序列）';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_NAME
  is '指标名称';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_INFO
  is '指标信息说明';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_TYPE
  is '指标类型（此处主要区别八大军规和子项）';
comment on column AIAM.INSPECT_RADAR_TEMP.PARENT_ID
  is '指标对应八大军规分类';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_WEIGHT
  is '指标权重';
comment on column AIAM.INSPECT_RADAR_TEMP.STATE
  is '状态';
comment on column AIAM.INSPECT_RADAR_TEMP.CREATE_TIME
  is '创建时间';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT1
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT2
  is '拓展字段';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT3
  is '拓展字段';

prompt
prompt Creating table ISEE_CSFTHREAD_INFO_20180718
prompt ===========================================
prompt
create table AIAM.ISEE_CSFTHREAD_INFO_20180718
(
  INS_NAME           VARCHAR2(255) not null,
  INS_ID             VARCHAR2(255),
  POOL_ID            VARCHAR2(255),
  INS_TYPE           VARCHAR2(255),
  ACTIVE_COUNT       VARCHAR2(255),
  THREAD_PERCENT     VARCHAR2(255),
  TOTAL_THREAD_COUNT VARCHAR2(255),
  DATA_TIME          TIMESTAMP(6),
  CJ_TIME            VARCHAR2(255),
  EXT1               VARCHAR2(255),
  EXT2               VARCHAR2(255),
  EXT3               VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ISEE_DATASOURCE_INFO_20180712
prompt ============================================
prompt
create table AIAM.ISEE_DATASOURCE_INFO_20180712
(
  INS_NAME     VARCHAR2(255) not null,
  INS_ID       VARCHAR2(255),
  DATA_SOURCE  VARCHAR2(255),
  INS_TYPE     VARCHAR2(255),
  NUM_ACTIVE   VARCHAR2(255),
  USED_PERCENT VARCHAR2(255),
  NUM_PHYSICAL VARCHAR2(255),
  NUM_IDLE     VARCHAR2(255),
  MAX_ACTIVE   VARCHAR2(255),
  DATA_TIME    TIMESTAMP(6),
  CJ_TIME      VARCHAR2(255),
  EXT1         VARCHAR2(255),
  EXT2         VARCHAR2(255),
  EXT3         VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_AUTO_DB_ACCT
prompt ==============================
prompt
create table AIAM.NA_AUTO_DB_ACCT
(
  DB_ID            NUMBER(14) not null,
  ENVIRONMENT      VARCHAR2(64) not null,
  DB_ACCT_CODE     VARCHAR2(255) not null,
  REGION           VARCHAR2(64) not null,
  USERNAME         VARCHAR2(255) not null,
  PASSWORD         VARCHAR2(255),
  HOST             VARCHAR2(255) not null,
  PORT             NUMBER(5) not null,
  SID              VARCHAR2(255) not null,
  DEFAULT_CONN_MIN NUMBER(3),
  DEFAULT_CONN_MAX NUMBER(3),
  STATE            CHAR(1) not null,
  REMARKS          VARCHAR2(1000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.NA_AUTO_DB_ACCT
  add constraint PK_NA_AUTO_DB_ACCT primary key (DB_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.AUTO_CFG_DB_ACCT_1 on AIAM.NA_AUTO_DB_ACCT (ENVIRONMENT, DB_ACCT_CODE, REGION)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_BUSINESS
prompt ==========================
prompt
create table AIAM.NA_BUSINESS
(
  BUSI_ID     NUMBER(14) not null,
  BUSI_NAME   VARCHAR2(40),
  BUSI_TYPE   NUMBER(14),
  BUSI_DESC   VARCHAR2(1000),
  IMPORTANT   NUMBER(3),
  INVALID     NUMBER(3),
  CREATOR_ID  NUMBER(14),
  CREATE_TIME DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.NA_BUSINESS
  add primary key (BUSI_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_CASE_IMPL_REPORT
prompt ==================================
prompt
create table AIAM.NA_CASE_IMPL_REPORT
(
  REPORT_ID         NUMBER(14) not null,
  ONLINE_PLAN_ID    NUMBER(14),
  ONLINE_PLAN_NAME  VARCHAR2(200),
  PLAN_DATE         DATE,
  SYS_NAME          VARCHAR2(2000),
  ENVIRONMENT       VARCHAR2(20),
  CASE_TOTAL_COUNT  NUMBER,
  MANUAL_CASE_COUNT NUMBER,
  AUTO_CASE_COUNT   NUMBER,
  FIR_SUC_RATE      VARCHAR2(20),
  FIR_EXEC_TIME     VARCHAR2(20),
  SEC_SUC_RATE      VARCHAR2(20),
  SEC_EXEC_TIME     VARCHAR2(20),
  THIRD_SUC_RATE    VARCHAR2(20),
  THIRD_EXEC_TIME   VARCHAR2(20),
  FLAG              NUMBER(2),
  EXT1              VARCHAR2(200),
  EXT2              VARCHAR2(200),
  EXT3              VARCHAR2(200)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_CASE_IMPL_REPORT.REPORT_ID
  is '报表ID';
comment on column AIAM.NA_CASE_IMPL_REPORT.ONLINE_PLAN_ID
  is '上线计划ID';
comment on column AIAM.NA_CASE_IMPL_REPORT.ONLINE_PLAN_NAME
  is '上线计划名称';
comment on column AIAM.NA_CASE_IMPL_REPORT.PLAN_DATE
  is '计划上线时间';
comment on column AIAM.NA_CASE_IMPL_REPORT.SYS_NAME
  is '系统名称/二级业务名称';
comment on column AIAM.NA_CASE_IMPL_REPORT.ENVIRONMENT
  is '环境';
comment on column AIAM.NA_CASE_IMPL_REPORT.CASE_TOTAL_COUNT
  is '用例总数';
comment on column AIAM.NA_CASE_IMPL_REPORT.MANUAL_CASE_COUNT
  is '手工用例数';
comment on column AIAM.NA_CASE_IMPL_REPORT.AUTO_CASE_COUNT
  is '自动化用例数';
comment on column AIAM.NA_CASE_IMPL_REPORT.FIR_SUC_RATE
  is '一次执行成功率';
comment on column AIAM.NA_CASE_IMPL_REPORT.FIR_EXEC_TIME
  is '第一次执行时间';
comment on column AIAM.NA_CASE_IMPL_REPORT.SEC_SUC_RATE
  is '二次执行成功率';
comment on column AIAM.NA_CASE_IMPL_REPORT.SEC_EXEC_TIME
  is '第二次执行时间';
comment on column AIAM.NA_CASE_IMPL_REPORT.THIRD_SUC_RATE
  is '三次执行成功率';
comment on column AIAM.NA_CASE_IMPL_REPORT.THIRD_EXEC_TIME
  is '第三次执行时间';
comment on column AIAM.NA_CASE_IMPL_REPORT.FLAG
  is '是否用例组1：是 2：否';
alter table AIAM.NA_CASE_IMPL_REPORT
  add constraint RE_PK primary key (REPORT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_CHANGE_PLAN_ONILE
prompt ===================================
prompt
create table AIAM.NA_CHANGE_PLAN_ONILE
(
  ONLINE_PLAN           NUMBER(12) not null,
  ONLINE_PLAN_NAME      VARCHAR2(200),
  PLAN_STATE            NUMBER(2),
  CREATE_OP_ID          VARCHAR2(200),
  CREATE_DATE           DATE,
  DONE_DATE             DATE,
  PLAN_DATE             DATE,
  RESULT                NUMBER(2),
  REMARK                VARCHAR2(2000),
  EXT_1                 VARCHAR2(20),
  EXT_2                 VARCHAR2(4000),
  EXT_3                 VARCHAR2(20),
  SIGN                  NUMBER(2),
  TYPES                 NUMBER(2),
  TIMELY                NUMBER(2),
  IS_FINISHED           NUMBER(2),
  AUTO_RUN_RESULT       NUMBER(2),
  FILE_UPLOAD_LAST_TIME DATE,
  CHANGE_PLAN_LASTTIME  DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 128K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_CHANGE_PLAN_ONILE.ONLINE_PLAN
  is '变更计划编号';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.ONLINE_PLAN_NAME
  is '变更计划名称';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.PLAN_STATE
  is '计划状态';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.CREATE_OP_ID
  is '创建人';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.CREATE_DATE
  is '创建时间';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.DONE_DATE
  is '完成时间';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.PLAN_DATE
  is '计划上线时间';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.RESULT
  is '上线结论';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.REMARK
  is '备注';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.EXT_2
  is '上线总结';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.SIGN
  is '计划是否废弃';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.TYPES
  is '类型';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.TIMELY
  is '上线申请是否及时';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.IS_FINISHED
  is '是否编译完成';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.AUTO_RUN_RESULT
  is '自动化执行结果';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.FILE_UPLOAD_LAST_TIME
  is '交付物截至时间';
alter table AIAM.NA_CHANGE_PLAN_ONILE
  add constraint PK_NA_CHANGE_PLAN_ONILE primary key (ONLINE_PLAN)
  disable;

prompt
prompt Creating table NA_FILE_UPLOAD
prompt =============================
prompt
create table AIAM.NA_FILE_UPLOAD
(
  ID               NUMBER not null,
  FILE_NAME        VARCHAR2(100),
  CREATE_TIME      DATE,
  LAST_UPLOAD_TIME DATE,
  UPLOAD_COUNT     NUMBER,
  DOWN_LOAD_TIME   DATE,
  PLAN_ID          NUMBER,
  EXT_1            VARCHAR2(100),
  EXT_2            VARCHAR2(100),
  EXT_3            VARCHAR2(100),
  FILE_TYPE        NUMBER,
  CREATE_ID        NUMBER
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_FILE_UPLOAD.FILE_NAME
  is '文件名称';
comment on column AIAM.NA_FILE_UPLOAD.CREATE_TIME
  is '上传时间';
comment on column AIAM.NA_FILE_UPLOAD.LAST_UPLOAD_TIME
  is '最后一次上传时间';
comment on column AIAM.NA_FILE_UPLOAD.UPLOAD_COUNT
  is '上传次数';
comment on column AIAM.NA_FILE_UPLOAD.DOWN_LOAD_TIME
  is '下载时间';
comment on column AIAM.NA_FILE_UPLOAD.FILE_TYPE
  is '文件类型：';
alter table AIAM.NA_FILE_UPLOAD
  add constraint Q_ID primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_IMAGE_UPLOAD
prompt ==============================
prompt
create table AIAM.NA_IMAGE_UPLOAD
(
  ID            NUMBER(10) not null,
  FILE_NAME     VARCHAR2(100) not null,
  IMG_SRC       VARCHAR2(100),
  TITLE         VARCHAR2(200),
  DESCRIPTION   VARCHAR2(500),
  LIKE_COUNT    NUMBER(10),
  COMMENT_COUNT NUMBER(10),
  IS_SHARED     VARCHAR2(1),
  CREATE_TIME   DATE,
  PLAN_ID       NUMBER(10),
  FILE_TYPE     NUMBER(10),
  CREATE_ID     NUMBER(10),
  EXT_1         VARCHAR2(100),
  EXT_2         VARCHAR2(100),
  EXT_3         VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_IMAGE_UPLOAD.ID
  is '主键ID';
comment on column AIAM.NA_IMAGE_UPLOAD.FILE_NAME
  is '图片名称';
comment on column AIAM.NA_IMAGE_UPLOAD.IMG_SRC
  is '图片路径';
comment on column AIAM.NA_IMAGE_UPLOAD.TITLE
  is '标题';
comment on column AIAM.NA_IMAGE_UPLOAD.DESCRIPTION
  is '描述';
comment on column AIAM.NA_IMAGE_UPLOAD.LIKE_COUNT
  is '喜欢数量';
comment on column AIAM.NA_IMAGE_UPLOAD.COMMENT_COUNT
  is '评论数量';
comment on column AIAM.NA_IMAGE_UPLOAD.IS_SHARED
  is '是否共享';
comment on column AIAM.NA_IMAGE_UPLOAD.CREATE_TIME
  is '创建时间';
comment on column AIAM.NA_IMAGE_UPLOAD.PLAN_ID
  is '关联ID';
comment on column AIAM.NA_IMAGE_UPLOAD.FILE_TYPE
  is '文件类型';
comment on column AIAM.NA_IMAGE_UPLOAD.CREATE_ID
  is '创建人ID';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_1
  is '扩展字段1';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_2
  is '扩展字段2';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_3
  is '扩展字段3';
alter table AIAM.NA_IMAGE_UPLOAD
  add constraint PK_NA_IMAGE_UPLOAD primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_INDEX_ALLOCATION
prompt ==================================
prompt
create table AIAM.NA_INDEX_ALLOCATION
(
  KPI_ID      NUMBER(14) not null,
  KPI_NAME    VARCHAR2(400),
  KPI_TYPE    NUMBER(2),
  KPI_SQL     VARCHAR2(4000),
  VALUE       NUMBER(14),
  ICON        VARCHAR2(400),
  BG_COLOR    VARCHAR2(400),
  IS_SHOW     NUMBER(2),
  CREATE_DATE DATE,
  DONE_DATE   DATE,
  OP_ID       NUMBER(14),
  EXT_1       VARCHAR2(1000),
  EXT_2       VARCHAR2(1000),
  EXT_3       VARCHAR2(1000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_INDEX_ALLOCATION.KPI_NAME
  is '指标名称';
comment on column AIAM.NA_INDEX_ALLOCATION.KPI_TYPE
  is '指标类型';
comment on column AIAM.NA_INDEX_ALLOCATION.VALUE
  is '指标值';
comment on column AIAM.NA_INDEX_ALLOCATION.ICON
  is '图标';
comment on column AIAM.NA_INDEX_ALLOCATION.BG_COLOR
  is '背景颜色';
comment on column AIAM.NA_INDEX_ALLOCATION.IS_SHOW
  is '是否显示';
alter table AIAM.NA_INDEX_ALLOCATION
  add constraint PK_KPI primary key (KPI_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_INDEX_ALLOCATION_BAK
prompt ======================================
prompt
create table AIAM.NA_INDEX_ALLOCATION_BAK
(
  KPI_ID      NUMBER(14) not null,
  KPI_NAME    VARCHAR2(400),
  KPI_TYPE    NUMBER(2),
  KPI_SQL     VARCHAR2(4000),
  VALUE       NUMBER(14),
  ICON        VARCHAR2(400),
  BG_COLOR    VARCHAR2(400),
  IS_SHOW     NUMBER(2),
  CREATE_DATE DATE,
  DONE_DATE   DATE,
  OP_ID       NUMBER(14),
  EXT_1       VARCHAR2(1000),
  EXT_2       VARCHAR2(1000),
  EXT_3       VARCHAR2(1000)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_ONLINE_PLAN_BUG
prompt =================================
prompt
create table AIAM.NA_ONLINE_PLAN_BUG
(
  BUG_ID       NUMBER(14) not null,
  ONLINE_PLANS NUMBER(14),
  BUG_TYPE     NUMBER(2),
  BUG_LEVEL    NUMBER(2),
  REQUIRE_NAME VARCHAR2(200),
  QUESTION     VARCHAR2(4000),
  REASONS      VARCHAR2(4000),
  METHODS      VARCHAR2(2000),
  DEAL         VARCHAR2(200),
  TYPE         VARCHAR2(200),
  CREATE_ID    NUMBER(14),
  CREATE_DATE  DATE,
  RESOVE       NUMBER(2),
  DONE_DATE    VARCHAR2(400),
  FOUND_DATE   VARCHAR2(400),
  EXT_1        VARCHAR2(200),
  EXT_2        VARCHAR2(200),
  EXT_3        VARCHAR2(200)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.NA_ONLINE_PLAN_BUG
  add primary key (BUG_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_PHOTO_UPLOAD
prompt ==============================
prompt
create table AIAM.NA_PHOTO_UPLOAD
(
  ID               NUMBER not null,
  FILE_NAME        VARCHAR2(100) not null,
  CREATE_TIME      DATE,
  LAST_UPLOAD_TIME DATE,
  UPLOAD_COUNT     NUMBER,
  DOWN_LOAD_TIME   DATE,
  PLAN_ID          NUMBER,
  FILE_TYPE        NUMBER,
  CREATE_ID        NUMBER,
  IS_SHARED        CHAR(1),
  EXT_1            VARCHAR2(100),
  EXT_2            VARCHAR2(100),
  EXT_3            VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.NA_PHOTO_UPLOAD
  add constraint PK_NA_PHOTO_UPLOAD primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_PROCESS_NODE_RECORD
prompt =====================================
prompt
create table AIAM.NA_PROCESS_NODE_RECORD
(
  PROCESS_ID   NUMBER not null,
  PROCESS_NAME VARCHAR2(200),
  TIME         DATE,
  TYPE         NUMBER,
  NODE         NUMBER,
  EXT_1        VARCHAR2(4000),
  EXT_2        VARCHAR2(4000),
  EXT_3        VARCHAR2(4000),
  PLAN_ID      NUMBER(14),
  PLAN_DATE    DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 192K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.NA_PROCESS_NODE_RECORD.PROCESS_ID
  is '流程ID';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PROCESS_NAME
  is '名字';
comment on column AIAM.NA_PROCESS_NODE_RECORD.TIME
  is '时间';
comment on column AIAM.NA_PROCESS_NODE_RECORD.TYPE
  is '类型 （1正处理.2.已处理 3.未处理）';
comment on column AIAM.NA_PROCESS_NODE_RECORD.NODE
  is '节点';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PLAN_ID
  is '计划Id';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PLAN_DATE
  is '上线时间';
alter table AIAM.NA_PROCESS_NODE_RECORD
  add constraint PK_NA_PROCESS_NODE_RECORD primary key (PROCESS_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table NA_STAFF_KPI_RELA
prompt ================================
prompt
create table AIAM.NA_STAFF_KPI_RELA
(
  REAL_ID     NUMBER(14) not null,
  STAFF_ID    NUMBER(14),
  KPI_ID      NUMBER(14),
  KPI_NAME    VARCHAR2(4000),
  CS_TYPE     NUMBER(14),
  STATE       NUMBER(2),
  EXT_1       VARCHAR2(1000),
  EXT_2       VARCHAR2(1000),
  EXT_3       VARCHAR2(1000),
  EXT_4       NUMBER(20),
  EXT_5       NUMBER(20),
  CREATE_DATE DATE,
  DONE_DATE   DATE,
  OP_ID       NUMBER(14)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.NA_STAFF_KPI_RELA
  add constraint PK_REAL primary key (REAL_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table OSB_SRRVICE
prompt ==========================
prompt
create table AIAM.OSB_SRRVICE
(
  SERVICE_KEY  VARCHAR2(100),
  SERVICE_DESC VARCHAR2(255),
  REMARK       VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table P_CSF_REPORT_BYMONTH
prompt ===================================
prompt
create table AIAM.P_CSF_REPORT_BYMONTH
(
  MONTH_DATE                  VARCHAR2(8),
  CENTER_CODE                 VARCHAR2(32),
  CENTER_NAME                 VARCHAR2(32),
  CSF_REGISTER_NUM            NUMBER(16),
  CSF_REGISTER_ADD            NUMBER(16),
  CSF_ACTIVITY_NUM            NUMBER(16),
  CSF_MONTHLY_ADJUSTMENT      NUMBER(32),
  LAST_CSF_MONTHLY_ADJUSTMENT NUMBER(32),
  ADJUSTMENT_RATE_CHANGE      VARCHAR2(32),
  CSF_AVG_TIME                VARCHAR2(32),
  LAST_CSF_AVG_TIME           VARCHAR2(32),
  AVGTIME_RATE_CHANGE         VARCHAR2(32),
  CSF_SUCC_RATE               VARCHAR2(32),
  LAST_CSF_SUCC_RATE          VARCHAR2(32),
  SUCC_RATE_CHAGE             VARCHAR2(32),
  INSERT_DATE                 DATE,
  ID                          NUMBER(8) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.P_CSF_REPORT_BYMONTH
  add constraint CONS_NAME_ID primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table P_TOP_CSF_REPORT_BYMONTH
prompt =======================================
prompt
create table AIAM.P_TOP_CSF_REPORT_BYMONTH
(
  ID                          NUMBER(8) not null,
  MONTH_DATE                  VARCHAR2(8),
  TOP_TYPE                    VARCHAR2(8),
  TOP_NO                      VARCHAR2(32),
  LAST_MONTH_TOP_NO           VARCHAR2(32),
  SERVICE_CODE                VARCHAR2(255),
  SERVICE_NAME                VARCHAR2(255),
  CENTER_NAME                 VARCHAR2(255),
  CSF_MONTHLY_ADJUSTMENT      NUMBER(32),
  LAST_CSF_MONTHLY_ADJUSTMENT NUMBER(32),
  ADJUSTMENT_RATE_CHANGE      VARCHAR2(32),
  CSF_AVG_TIME                NUMBER(8),
  LAST_CSF_AVG_TIME           NUMBER(8),
  AVGTIME_RATE_CHANGE         VARCHAR2(32),
  CSF_SUCC_RATE               VARCHAR2(32),
  LAST_CSF_SUCC_RATE          VARCHAR2(32),
  SUCC_RATE_CHAGE             VARCHAR2(32),
  INSERT_DATE                 DATE
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.P_TOP_CSF_REPORT_BYMONTH
  add constraint TOP_CSF_REPORT_ID primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table QUESTION_EVENT
prompt =============================
prompt
create table AIAM.QUESTION_EVENT
(
  ID         NUMBER(10) not null,
  NAME       VARCHAR2(100),
  START_TIME DATE,
  END_TIME   DATE,
  STATE      VARCHAR2(50),
  EXT_1      VARCHAR2(100),
  EXT_2      VARCHAR2(100),
  EXT_3      VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.QUESTION_EVENT
  add constraint PK_EVENT_ID primary key (ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table QUESTION_INFO
prompt ============================
prompt
create table AIAM.QUESTION_INFO
(
  QUES_ID           NUMBER(10) not null,
  QUES_TYPE         VARCHAR2(50) not null,
  FIRST_CATEGORY    VARCHAR2(255),
  SECOND_CATEGORY   VARCHAR2(255),
  THIRD_CATEGORY    VARCHAR2(255),
  DIFF_PROBLEM      VARCHAR2(255),
  ABSTRACTS         VARCHAR2(1000),
  OCCUR_ENVIRONMENT VARCHAR2(500),
  BELONG_PROJECT    VARCHAR2(500),
  ID_FIRST          NUMBER(10) not null,
  ID_SECOND         NUMBER(10) not null,
  ID_THIRD          NUMBER(10) not null,
  SYS_VERSION       VARCHAR2(500),
  PRIORITY          VARCHAR2(50),
  DEFECT_LEVEL      VARCHAR2(50),
  STATE             VARCHAR2(50),
  REQUEST_INFO      VARCHAR2(500),
  IDENTIFIED_INFO   VARCHAR2(500),
  SOLVED_INFO       VARCHAR2(500),
  CREATE_DATE       DATE,
  MODIFY_DATE       DATE,
  REPORTOR          VARCHAR2(100),
  APPOINTED_PERSON  VARCHAR2(100),
  EXT_1             VARCHAR2(255),
  EXT_2             VARCHAR2(255),
  EXT_3             VARCHAR2(255),
  IDENTIFIED_NAME   VARCHAR2(100),
  SOLVED_NAME       VARCHAR2(100)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.QUESTION_INFO.QUES_ID
  is '问题编号';
comment on column AIAM.QUESTION_INFO.QUES_TYPE
  is '问题类型
（1：系统巡检；
2：技术巡检；
3：疑难问题；）';
comment on column AIAM.QUESTION_INFO.FIRST_CATEGORY
  is '一级分类
（1：容量规划；
2：高可用；
3：分层；
4：柔性可用；
5：日志；
6：配置；
7：监控；
8：安全；）';
comment on column AIAM.QUESTION_INFO.SECOND_CATEGORY
  is '二级分类
（1：系统容量；
2：业务容量；
3：系统性能；
--以容量规划为例）';
comment on column AIAM.QUESTION_INFO.THIRD_CATEGORY
  is '三级分类
（1：数据库容量；
2：网络带宽；
3：TPCC；
4：存储；
--以系统容量为例）';
comment on column AIAM.QUESTION_INFO.DIFF_PROBLEM
  is '疑难问题';
comment on column AIAM.QUESTION_INFO.ABSTRACTS
  is '摘要';
comment on column AIAM.QUESTION_INFO.OCCUR_ENVIRONMENT
  is '发生环境';
comment on column AIAM.QUESTION_INFO.BELONG_PROJECT
  is '所属项目';
comment on column AIAM.QUESTION_INFO.ID_FIRST
  is '一级域编号';
comment on column AIAM.QUESTION_INFO.ID_SECOND
  is '二级子域编号';
comment on column AIAM.QUESTION_INFO.ID_THIRD
  is '三级系统编号';
comment on column AIAM.QUESTION_INFO.SYS_VERSION
  is '系统版本';
comment on column AIAM.QUESTION_INFO.PRIORITY
  is '优先级
（1：琐碎的trivial；
2：次要的minor；
3：主要的major：
4：危机的critical：
5：严重的blocker；）';
comment on column AIAM.QUESTION_INFO.DEFECT_LEVEL
  is '缺陷级别
（1：重大缺陷；
2：架构缺陷；
3：一般缺陷；
4：紧急问题；
5：一般问题；）';
comment on column AIAM.QUESTION_INFO.STATE
  is '流程状态
（1：已申报；
2：审核认定；
3：已分配；
4：已解决；
5：已关闭；）';
comment on column AIAM.QUESTION_INFO.REQUEST_INFO
  is '申报信息';
comment on column AIAM.QUESTION_INFO.IDENTIFIED_INFO
  is '认定信息';
comment on column AIAM.QUESTION_INFO.SOLVED_INFO
  is '解决信息';
comment on column AIAM.QUESTION_INFO.CREATE_DATE
  is '创建时间';
comment on column AIAM.QUESTION_INFO.MODIFY_DATE
  is '修改时间';
comment on column AIAM.QUESTION_INFO.REPORTOR
  is '报告人';
comment on column AIAM.QUESTION_INFO.APPOINTED_PERSON
  is '指派人';
comment on column AIAM.QUESTION_INFO.EXT_1
  is '扩展字段1';
comment on column AIAM.QUESTION_INFO.EXT_2
  is '扩展字段2';
comment on column AIAM.QUESTION_INFO.EXT_3
  is '扩展字段3';
comment on column AIAM.QUESTION_INFO.IDENTIFIED_NAME
  is '扩展字段4';
comment on column AIAM.QUESTION_INFO.SOLVED_NAME
  is '扩展字段5';
alter table AIAM.QUESTION_INFO
  add constraint PK_QUESTION_INFO primary key (QUES_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table QUES_CATEGORY
prompt ============================
prompt
create table AIAM.QUES_CATEGORY
(
  ROOT_ID NUMBER(20) not null,
  NAME    VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.QUES_CATEGORY
  add constraint PK_QUES_CATEGORY primary key (ROOT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ROOT
prompt ===================
prompt
create table AIAM.ROOT
(
  QUES_TYPE NUMBER(20) not null,
  NAME      VARCHAR2(10)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.ROOT
  add constraint PK_ROOT primary key (QUES_TYPE)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SECOND
prompt =====================
prompt
create table AIAM.SECOND
(
  SECOND_CATEGORY NUMBER(10) not null,
  NAME            VARCHAR2(50) not null,
  QUES_TYPE       NUMBER(10),
  FIRST_CATEGORY  NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.SECOND
  add constraint PK_SECOND primary key (SECOND_CATEGORY)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SECOND_CATEGORY
prompt ==============================
prompt
create table AIAM.SECOND_CATEGORY
(
  SECOND_ID NUMBER(10) not null,
  NAME      VARCHAR2(50) not null,
  ROOT_ID   NUMBER(10),
  FIRST_ID  NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.SECOND_CATEGORY
  add constraint PK_SECOND_CATEGORY primary key (SECOND_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SRVCALL_DAY
prompt ==========================
prompt
create table AIAM.SRVCALL_DAY
(
  SERVICEID     VARCHAR2(255),
  AVGDURATION   BINARY_DOUBLE,
  ACCESSTIMES   BINARY_DOUBLE,
  ERRORTIMES    BINARY_DOUBLE,
  TIMEPEROID    VARCHAR2(64),
  SERVICESTATUS CHAR(1),
  ERRMSG        VARCHAR2(256),
  MAXDURATION   NUMBER(20),
  MINDURATION   NUMBER(20),
  SUMDURATION   NUMBER(20),
  STATSKIND     VARCHAR2(64),
  STATSCODE     VARCHAR2(64)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_CONSTANT
prompt ===========================
prompt
create table AIAM.SYS_CONSTANT
(
  CONSTANT_ID   NUMBER(20) not null,
  CATEGORY_NAME VARCHAR2(256),
  CATEGORY      VARCHAR2(256) not null,
  SHOW          VARCHAR2(256),
  VALUE         NUMBER(11) not null,
  OTHER1        VARCHAR2(256),
  OTHER2        VARCHAR2(256),
  MEMO          VARCHAR2(1025),
  AUTHOR        VARCHAR2(100),
  REMARK        VARCHAR2(1025),
  ESCAPE_SHOW   VARCHAR2(100),
  ESCAPE_FIELD  VARCHAR2(100),
  FIRST_SPELL   VARCHAR2(100),
  FULL_SPELLING VARCHAR2(200),
  STATUS        NUMBER(10) default 0
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.SYS_CONSTANT.FIRST_SPELL
  is '首字母拼音（未填写的话，系统会自动添加，故此处填写特殊情况，比如多音字即可）';
comment on column AIAM.SYS_CONSTANT.FULL_SPELLING
  is '全拼（未填写的话，系统会自动添加，故此处填写特殊情况，比如多音字即可）';
comment on column AIAM.SYS_CONSTANT.STATUS
  is '0 可用 1不可用';
alter table AIAM.SYS_CONSTANT
  add primary key (CATEGORY, CONSTANT_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index AIAM.SYS_C_CATE_INX on AIAM.SYS_CONSTANT (CATEGORY)
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table AIAM.SYS_ROLE
(
  ROLE_ID     NUMBER(12) not null,
  CODE        VARCHAR2(40),
  NAME        VARCHAR2(60) not null,
  NOTES       VARCHAR2(100),
  STATE       NUMBER(2),
  DONE_CODE   NUMBER(38),
  CREATE_DATE DATE,
  DONE_DATE   DATE,
  VALID_DATE  DATE,
  EXPIRE_DATE DATE,
  OP_ID       NUMBER(12),
  ORG_ID      NUMBER(12)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column AIAM.SYS_ROLE.ROLE_ID
  is '????';
comment on column AIAM.SYS_ROLE.CODE
  is '??';
comment on column AIAM.SYS_ROLE.NAME
  is '??';
comment on column AIAM.SYS_ROLE.NOTES
  is '??';
comment on column AIAM.SYS_ROLE.STATE
  is '??
??';
comment on column AIAM.SYS_ROLE.DONE_CODE
  is '????????';
comment on column AIAM.SYS_ROLE.DONE_DATE
  is '???????';
comment on column AIAM.SYS_ROLE.VALID_DATE
  is '?????';
comment on column AIAM.SYS_ROLE.EXPIRE_DATE
  is '??????';
alter table AIAM.SYS_ROLE
  add constraint PK_ROLE primary key (ROLE_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
grant select, insert, update, delete on AIAM.SYS_ROLE to AIGA_NEW;

prompt
prompt Creating table TASK_LOG
prompt =======================
prompt
create table AIAM.TASK_LOG
(
  TASK_LOG_ID NUMBER(12) not null,
  CFG_TASK_ID NUMBER(12) not null,
  RESULTS     VARCHAR2(4000),
  PERCENT     NUMBER(2),
  START_DATE  DATE not null,
  FINISH_DATE DATE,
  STATE       CHAR(1) not null,
  REMARKS     VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table THIRD
prompt ====================
prompt
create table AIAM.THIRD
(
  THIRD_CATEGORY  NUMBER(10) not null,
  NAME            VARCHAR2(50) not null,
  QUES_TYPE       NUMBER(10),
  FIRST_CATEGORY  NUMBER(10),
  SECOND_CATEGORY NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.THIRD
  add constraint PK_THIRD primary key (THIRD_CATEGORY)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table THIRD_CATEGORY
prompt =============================
prompt
create table AIAM.THIRD_CATEGORY
(
  THIRD_ID  NUMBER(10) not null,
  NAME      VARCHAR2(50) not null,
  ROOT_ID   NUMBER(10),
  FIRST_ID  NUMBER(10),
  SECOND_ID NUMBER(10) not null
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table AIAM.THIRD_CATEGORY
  add constraint PK_THIRD_CATEGORY primary key (THIRD_ID)
  using index 
  tablespace TEST_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table YIYANG_INDEX_DATA
prompt ================================
prompt
create table AIAM.YIYANG_INDEX_DATA
(
  METRIC           VARCHAR2(255),
  CUID             VARCHAR2(255),
  POOL_ID          VARCHAR2(255),
  INSTANCE_ID      VARCHAR2(255),
  INSTANCE_NAME    VARCHAR2(255),
  POLICY_ID        VARCHAR2(255),
  CITY             VARCHAR2(255),
  DESCRIPTION_INFO VARCHAR2(255),
  SYSTEM_NAME      VARCHAR2(255),
  INTERFACE        VARCHAR2(255) not null,
  AGGREGATETAGS    VARCHAR2(2000),
  DATA_TIME        VARCHAR2(255),
  DATA_VALUE       VARCHAR2(255),
  CJ_TIME          VARCHAR2(255),
  EXT1             VARCHAR2(255),
  EXT2             VARCHAR2(255),
  EXT3             VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table YIYANG_INDEX_DATA_20180718
prompt =========================================
prompt
create table AIAM.YIYANG_INDEX_DATA_20180718
(
  METRIC           VARCHAR2(255),
  CUID             VARCHAR2(255),
  INSTANCE_ID      VARCHAR2(255),
  INSTANCE_NAME    VARCHAR2(255),
  POLICY_ID        VARCHAR2(255),
  CITY             VARCHAR2(255),
  DESCRIPTION_INFO VARCHAR2(255),
  SYSTEM_NAME      VARCHAR2(255),
  INTERFACE        VARCHAR2(255) not null,
  AGGREGATETAGS    VARCHAR2(2000),
  DATA_TIME        VARCHAR2(255),
  DATA_VALUE       VARCHAR2(255),
  DATA_TYPE        VARCHAR2(255),
  CJ_TIME          VARCHAR2(255),
  EXT1             VARCHAR2(255),
  EXT2             VARCHAR2(255),
  EXT3             VARCHAR2(255)
)
tablespace TEST_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence AIGA_AUTHOR$SEQ
prompt =================================
prompt
create sequence AIAM.AIGA_AUTHOR$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 981
increment by 1
cache 20;

prompt
prompt Creating sequence AIGA_FUNCTION$SEQ
prompt ===================================
prompt
create sequence AIAM.AIGA_FUNCTION$SEQ
minvalue 1
maxvalue 9999999999
start with 442
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence AIGA_ORGANIZE$SEQ
prompt ===================================
prompt
create sequence AIAM.AIGA_ORGANIZE$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 41
increment by 1
cache 10;

prompt
prompt Creating sequence AIGA_ROLE_FUNC$SEQ
prompt ====================================
prompt
create sequence AIAM.AIGA_ROLE_FUNC$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 9051
increment by 1
cache 10;

prompt
prompt Creating sequence AIGA_STAFF$SEQ
prompt ================================
prompt
create sequence AIAM.AIGA_STAFF$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 622
increment by 1
cache 20;

prompt
prompt Creating sequence AIGA_STAFF_ORG_RELAT$SEQ
prompt ==========================================
prompt
create sequence AIAM.AIGA_STAFF_ORG_RELAT$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 561
increment by 1
cache 20;

prompt
prompt Creating sequence AM_CORE_INDEX$SEQ
prompt ===================================
prompt
create sequence AIAM.AM_CORE_INDEX$SEQ
minvalue 1
maxvalue 999999999
start with 221
increment by 1
cache 20;

prompt
prompt Creating sequence ARCHITECTURE_GRADING$SEQ
prompt ==========================================
prompt
create sequence AIAM.ARCHITECTURE_GRADING$SEQ
minvalue 1
maxvalue 9999999999
start with 1159
increment by 1
nocache;

prompt
prompt Creating sequence ARCHITECTURE_STATIC_DATA$SEQ
prompt ==============================================
prompt
create sequence AIAM.ARCHITECTURE_STATIC_DATA$SEQ
minvalue 1
maxvalue 9999999999
start with 140
increment by 1
nocache;

prompt
prompt Creating sequence ARCHITECTURE_THIRD$SEQ
prompt ========================================
prompt
create sequence AIAM.ARCHITECTURE_THIRD$SEQ
minvalue 1
maxvalue 9999999999
start with 652
increment by 1
nocache;

prompt
prompt Creating sequence ARCHI_SYS_HEALTH_REPORT$SEQ
prompt =============================================
prompt
create sequence AIAM.ARCHI_SYS_HEALTH_REPORT$SEQ
minvalue 1
maxvalue 1000000000000000000000000000
start with 1
increment by 1
nocache;

prompt
prompt Creating sequence ARCHI_SYS_INDEX_RELA$SEQ
prompt ==========================================
prompt
create sequence AIAM.ARCHI_SYS_INDEX_RELA$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence ARCHI_TOP_LIST$SEQ
prompt ====================================
prompt
create sequence AIAM.ARCHI_TOP_LIST$SEQ
minvalue 1
maxvalue 9999999999
start with 161
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence ARCH_FUNCTION_RECORD$SEQ
prompt ==========================================
prompt
create sequence AIAM.ARCH_FUNCTION_RECORD$SEQ
minvalue 1
maxvalue 9999999999999999999999999
start with 9354
increment by 1
nocache;

prompt
prompt Creating sequence ARCH_NOTICE_TITLE$SEQ
prompt =======================================
prompt
create sequence AIAM.ARCH_NOTICE_TITLE$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence ARCH_STAFF_GRAD$SEQ
prompt =====================================
prompt
create sequence AIAM.ARCH_STAFF_GRAD$SEQ
minvalue 1
maxvalue 9999999999999
start with 12
increment by 1
nocache;

prompt
prompt Creating sequence ARCH_SYS_REPORT_DATA$SEQ
prompt ==========================================
prompt
create sequence AIAM.ARCH_SYS_REPORT_DATA$SEQ
minvalue 1
maxvalue 9999999999
start with 101
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence ARCH_SYS_REPORT_RESULT$SEQ
prompt ============================================
prompt
create sequence AIAM.ARCH_SYS_REPORT_RESULT$SEQ
minvalue 1
maxvalue 9999999999
start with 541
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence ARCH_WORK_PLAN$SEQ
prompt ====================================
prompt
create sequence AIAM.ARCH_WORK_PLAN$SEQ
minvalue 1
maxvalue 999999999
start with 107
increment by 1
cache 20;

prompt
prompt Creating sequence DB_SESSION_COUNT$SEQ
prompt ======================================
prompt
create sequence AIAM.DB_SESSION_COUNT$SEQ
minvalue 1
maxvalue 999999999
start with 4061
increment by 1
cache 20;

prompt
prompt Creating sequence INSPECT_RADAR_ERROR$SEQ
prompt =========================================
prompt
create sequence AIAM.INSPECT_RADAR_ERROR$SEQ
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache;

prompt
prompt Creating sequence INSPECT_RADAR_RESULT$SEQ
prompt ==========================================
prompt
create sequence AIAM.INSPECT_RADAR_RESULT$SEQ
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache;

prompt
prompt Creating sequence INSPECT_RADAR_RULE$SEQ
prompt ========================================
prompt
create sequence AIAM.INSPECT_RADAR_RULE$SEQ
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache;

prompt
prompt Creating sequence INSPECT_RADAR_TEMP$SEQ
prompt ========================================
prompt
create sequence AIAM.INSPECT_RADAR_TEMP$SEQ
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache;

prompt
prompt Creating sequence NA_FILE_UPLOAD$SEQ
prompt ====================================
prompt
create sequence AIAM.NA_FILE_UPLOAD$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 510
increment by 1
cache 10;

prompt
prompt Creating sequence NA_IMAGE_UPLOAD$SEQ
prompt =====================================
prompt
create sequence AIAM.NA_IMAGE_UPLOAD$SEQ
minvalue 1
maxvalue 99999999999
start with 5000000060
increment by 1
cache 20;

prompt
prompt Creating sequence NA_INDEX_ALLOCATION$SEQ
prompt =========================================
prompt
create sequence AIAM.NA_INDEX_ALLOCATION$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence NA_STAFF_KPI_RELA$SEQ
prompt =======================================
prompt
create sequence AIAM.NA_STAFF_KPI_RELA$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 170
increment by 1
cache 20;

prompt
prompt Creating sequence QUESTION_EVENT$SEQ
prompt ====================================
prompt
create sequence AIAM.QUESTION_EVENT$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1000000040
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_QUES_INFO
prompt ===============================
prompt
create sequence AIAM.SEQ_QUES_INFO
minvalue 4000000000
maxvalue 9999999999999999999999999999
start with 4000000798
increment by 1
cache 20;

prompt
prompt Creating sequence SYS_ROLE$SEQ
prompt ==============================
prompt
create sequence AIAM.SYS_ROLE$SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 150
increment by 1
cache 10;

prompt
prompt Creating sequence WORK_PLAN$SEQ
prompt ===============================
prompt
create sequence AIAM.WORK_PLAN$SEQ
minvalue 1
maxvalue 999999999
start with 78
increment by 1
cache 20;

prompt
prompt Creating function COUNT_NUM
prompt ===========================
prompt
create or replace function aiam.Count_Num
(i out number)
return number
as
Cnum number
begin
     i:=1;
     Cnum:=0;
     loop
     Cnum:=Cnum+i;
     i:=i+2;
     return Cnum;
     end loop
end Count_Num
declare
sum number
begin
    sum:=Count_Num(100);
    dbms_ooutput.put_line(to_char(sum))
end
/


spool off
