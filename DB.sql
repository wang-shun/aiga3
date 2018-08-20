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
  is '��֯����';
comment on column AIAM.AIGA_ORGANIZE.CODE
  is '����';
comment on column AIAM.AIGA_ORGANIZE.ORG_ROLE_TYPE_ID
  is '��֯����';
comment on column AIAM.AIGA_ORGANIZE.DISTRICT_ID
  is '��������';
comment on column AIAM.AIGA_ORGANIZE.SHORT_NAME
  is '���';
comment on column AIAM.AIGA_ORGANIZE.ENGLISH_NAME
  is 'Ӣ������';
comment on column AIAM.AIGA_ORGANIZE.MEMBER_NUM
  is '����';
comment on column AIAM.AIGA_ORGANIZE.MANAGER_NAME
  is '����������';
comment on column AIAM.AIGA_ORGANIZE.EMAIL
  is 'EMAIL';
comment on column AIAM.AIGA_ORGANIZE.PHONE_ID
  is '��ϵ�绰';
comment on column AIAM.AIGA_ORGANIZE.FAX_ID
  is '����';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_NAME
  is '��ϵ������';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_CARD_TYPE
  is '��ϵ��֤������';
comment on column AIAM.AIGA_ORGANIZE.CONTACT_CARD_ID
  is '��ϵ��֤������';
comment on column AIAM.AIGA_ORGANIZE.POSTCODE
  is '��ϵ���ֻ�';
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
  is 'һ������';
comment on column AIAM.ARCHITECTURE_FIRST.NAME
  is 'һ��������
��1��ҵ��֧����
2���Ź���
3��BOMC��
4����������
5����ȫ��
6��������
7��������
8��������
9�������򣻣�';
comment on column AIAM.ARCHITECTURE_FIRST.SHORT_NAME
  is '���';
comment on column AIAM.ARCHITECTURE_FIRST.DESCRIPTION
  is '����';
comment on column AIAM.ARCHITECTURE_FIRST.CODE
  is '����
��1��ҵ��֧���� B��
2���Ź��� M��
3��BOMC�� C��
4���������� D��
5����ȫ�� S��
6�������� P��
7�������� O��
8�������� R��
9�������� N����';
comment on column AIAM.ARCHITECTURE_FIRST.BELONG_LEVEL
  is '�����ֲ�';
comment on column AIAM.ARCHITECTURE_FIRST.STATE
  is '����״̬
��1�����룻
2������ͨ����
3��������ͨ������';
comment on column AIAM.ARCHITECTURE_FIRST.APPLY_ID
  is '������';
comment on column AIAM.ARCHITECTURE_FIRST.APPLY_USER
  is '������';
comment on column AIAM.ARCHITECTURE_FIRST.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.ARCHITECTURE_FIRST.MODIFY_DATE
  is '�޸�ʱ��';
comment on column AIAM.ARCHITECTURE_FIRST.IDENTIFIED_INFO
  is '�϶���Ϣ';
comment on column AIAM.ARCHITECTURE_FIRST.FILE_INFO
  is '�鵵��Ϣ';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_1
  is '��չ�ֶ�1';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_2
  is '��չ�ֶ�2';
comment on column AIAM.ARCHITECTURE_FIRST.EXT_3
  is '��չ�ֶ�3';
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
  is '������';
comment on column AIAM.ARCHITECTURE_GRADING.IDENTIFIED_INFO
  is '�϶���Ϣ';
comment on column AIAM.ARCHITECTURE_GRADING.SYS_ID
  is 'ϵͳ���
';
comment on column AIAM.ARCHITECTURE_GRADING.NAME
  is '����
��1����������ϵͳ��
2���˻�����ϵͳ��
3���ںϼƷ�ϵͳ��
4��ʵʱ����ϵͳ��
5����������ϵͳ��
6����������ϵͳ��
7��ͳһ���ϵͳ��
--��ҵ��֧����Ϊ����';
comment on column AIAM.ARCHITECTURE_GRADING.SYSTEM_FUNCTION
  is 'ϵͳ����';
comment on column AIAM.ARCHITECTURE_GRADING.DESCRIPTION
  is '����';
comment on column AIAM.ARCHITECTURE_GRADING.CODE
  is '����';
comment on column AIAM.ARCHITECTURE_GRADING.ID_BELONG
  is '�����ϼ�����';
comment on column AIAM.ARCHITECTURE_GRADING.BELONG_LEVEL
  is '�����ֲ�
��1������Ӧ�ò�SaaS��
2������ҵ������BPaaS��
3��ͨ�÷�������UPaaS��
4��Ӧ�ü���ƽ̨����IPaaS��
5��ͨ�ü������ƽ̨����TPaaS��
6��������ʩ����:IaaS����';
comment on column AIAM.ARCHITECTURE_GRADING.DEPARTMENT
  is '���β���';
comment on column AIAM.ARCHITECTURE_GRADING.PROJECT_INFO
  is '��Ŀ������Ϣ';
comment on column AIAM.ARCHITECTURE_GRADING.DESIGN_INFO
  is '�滮�����Ϣ';
comment on column AIAM.ARCHITECTURE_GRADING.SYS_STATE
  is 'ϵͳ����״̬';
comment on column AIAM.ARCHITECTURE_GRADING.STATE
  is '����״̬
��1�����룻
2������ͨ����
3��������ͨ������';
comment on column AIAM.ARCHITECTURE_GRADING.RANK_INFO
  is 'ϵͳ�ȼ���Ϣ';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_USER
  is '������';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_TIME
  is '����ʱ��';
comment on column AIAM.ARCHITECTURE_GRADING.MODIFY_DATE
  is '�޸�ʱ��';
comment on column AIAM.ARCHITECTURE_GRADING.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_1
  is '��չ��Ϣ1   ϵͳ�㼶��1��һ���� 2���������� 3������ϵͳ��';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_2
  is '��չ��Ϣ2   ����״̬��ʱ�䣩';
comment on column AIAM.ARCHITECTURE_GRADING.EXT_3
  is '��չ��Ϣ3   ��app .pc��';
comment on column AIAM.ARCHITECTURE_GRADING.ONLYSYS_ID
  is 'ϵͳʶ����';
comment on column AIAM.ARCHITECTURE_GRADING.IDENTIFY_USER
  is '�϶���';
comment on column AIAM.ARCHITECTURE_GRADING.FILE_ID
  is '�ļ����';
comment on column AIAM.ARCHITECTURE_GRADING.DEVELOPER
  is '��������';
comment on column AIAM.ARCHITECTURE_GRADING.CLOUD_ORDER_ID
  is '�ƹ�ƽ̨����ҵ��ϵͳ�������';
comment on column AIAM.ARCHITECTURE_GRADING.APPLY_USER_INFO
  is '�����˸�����Ϣ';
comment on column AIAM.ARCHITECTURE_GRADING.PRINCIPAL
  is '������';
comment on column AIAM.ARCHITECTURE_GRADING.BACK_MESSAGE
  is '��������ظ�';
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
  is '����������';
comment on column AIAM.ARCHITECTURE_SECOND.NAME
  is '������������
��1��ҵ���������ģ�
2����Ӫ�������ģ�
3�������������ģ�
4�������������ģ�
5���������ģ�
6���˻����ģ�
7���Ʒ��������ģ�
--�Բ���ҵ��֧����Ϊ��
��';
comment on column AIAM.ARCHITECTURE_SECOND.SHORT_NAME
  is '���';
comment on column AIAM.ARCHITECTURE_SECOND.DESCRIPTION
  is '����';
comment on column AIAM.ARCHITECTURE_SECOND.CODE
  is '����
��1��ҵ���������� BSC��
2����Ӫ�������� OPM��
3�������������� HTL��
4�������������� ECC��
5���������� ORD��
6���˻����� ACC��
7���Ʒ��������� AMS��
--�Բ���ҵ��֧����Ϊ��
��';
comment on column AIAM.ARCHITECTURE_SECOND.ID_FIRST
  is '����һ������';
comment on column AIAM.ARCHITECTURE_SECOND.BELONG_LEVEL
  is '�����ֲ�
��1������Ӧ�ò�SaaS��
2������ҵ������BPaaS��
3��ͨ�÷�������UPaaS��
4��Ӧ�ü���ƽ̨����IPaaS��
5��ͨ�ü������ƽ̨����TPaaS��
6��������ʩ����:IaaS����';
comment on column AIAM.ARCHITECTURE_SECOND.STATE
  is '����״̬
��1�����룻
2������ͨ����
3��������ͨ������
';
comment on column AIAM.ARCHITECTURE_SECOND.APPLY_ID
  is '������';
comment on column AIAM.ARCHITECTURE_SECOND.APPLY_USER
  is '������';
comment on column AIAM.ARCHITECTURE_SECOND.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.ARCHITECTURE_SECOND.MODIFY_DATE
  is '�޸�ʱ��';
comment on column AIAM.ARCHITECTURE_SECOND.IDENTIFIED_INFO
  is '�϶���Ϣ';
comment on column AIAM.ARCHITECTURE_SECOND.FILE_INFO
  is '�鵵��Ϣ';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_1
  is '��չ�ֶ�1';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_2
  is '��չ�ֶ�2';
comment on column AIAM.ARCHITECTURE_SECOND.EXT_3
  is '��չ�ֶ�3';
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
  is '����ϵͳ���
';
comment on column AIAM.ARCHITECTURE_THIRD.SYSTEM_FUNCTION
  is 'ϵͳ����';
comment on column AIAM.ARCHITECTURE_THIRD.DESCRIPTION
  is '����';
comment on column AIAM.ARCHITECTURE_THIRD.CODE
  is '����';
comment on column AIAM.ARCHITECTURE_THIRD.BELONG_LEVEL
  is '�����ֲ�
��1������Ӧ�ò�SaaS��
2������ҵ������BPaaS��
3��ͨ�÷�������UPaaS��
4��Ӧ�ü���ƽ̨����IPaaS��
5��ͨ�ü������ƽ̨����TPaaS��
6��������ʩ����:IaaS����';
comment on column AIAM.ARCHITECTURE_THIRD.DEPARTMENT
  is '���β���';
comment on column AIAM.ARCHITECTURE_THIRD.PROJECT_INFO
  is '��Ŀ������Ϣ';
comment on column AIAM.ARCHITECTURE_THIRD.DESIGN_INFO
  is '�滮�����Ϣ';
comment on column AIAM.ARCHITECTURE_THIRD.STATE
  is '����״̬
��1�����룻
2������ͨ����
3��������ͨ������';
comment on column AIAM.ARCHITECTURE_THIRD.APPLY_ID
  is '������';
comment on column AIAM.ARCHITECTURE_THIRD.APPLY_USER
  is '������';
comment on column AIAM.ARCHITECTURE_THIRD.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.ARCHITECTURE_THIRD.MODIFY_DATE
  is '�޸�ʱ��';
comment on column AIAM.ARCHITECTURE_THIRD.IDENTIFIED_INFO
  is '�϶���Ϣ';
comment on column AIAM.ARCHITECTURE_THIRD.FILE_INFO
  is '�鵵��Ϣ';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_1
  is '��չ��Ϣ1';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_2
  is '��չ��Ϣ2';
comment on column AIAM.ARCHITECTURE_THIRD.EXT_3
  is '��չ��Ϣ3';
comment on column AIAM.ARCHITECTURE_THIRD.DEVELOPER
  is '��������';
comment on column AIAM.ARCHITECTURE_THIRD.CLOUD_ORDER_ID
  is '�ƹ�ƽ̨����ҵ��ϵͳ�������';
comment on column AIAM.ARCHITECTURE_THIRD.PRINCIPAL
  is '������';
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
  is '���';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_SEQ
  is '����';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_NAME
  is '����';
comment on column AIAM.ARCHI_TOP_LIST.INDEX_GROUP
  is '����';
comment on column AIAM.ARCHI_TOP_LIST.SETT_MONTH
  is '�ɼ�ʱ��';
comment on column AIAM.ARCHI_TOP_LIST.KEY_1
  is '��1';
comment on column AIAM.ARCHI_TOP_LIST.KEY_2
  is '��2';
comment on column AIAM.ARCHI_TOP_LIST.KEY_3
  is '��3';
comment on column AIAM.ARCHI_TOP_LIST.RESULT_VALUE
  is 'ֵ';
comment on column AIAM.ARCHI_TOP_LIST.IMG_SRC
  is 'ͷ��·��';
comment on column AIAM.ARCHI_TOP_LIST.STAR_NUM
  is '��������';
comment on column AIAM.ARCHI_TOP_LIST.CREATE_DATE
  is '����ʱ��';
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
  is '�ܹ�����-���ݿ�����ָ��';
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
  is '�ܹ�����-�¶�ָ��';

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
  is '��ҵ��ϵͳ����״̬Ѳ�챨��ָ���ѯSQL���ñ��������ñ��汨������������Ĳ�ѯSQL';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.DATA_CODE
  is 'ԭʼ��������ID�������ñ��еĲ���ID��Ӧ';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.DATA_NAME
  is 'ԭʼ������������';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.QRY_SQL
  is '������Ӧ��ѯSQL';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.REMARK
  is '��ע';
comment on column AIAM.ARCH_SYS_QRY_SQL_CFG.MODIFY_DATE
  is '�����޸�ʱ��';
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
  is '��ҵ��ϵͳ����״̬Ѳ�챨���������ñ��������ñ������ģ���չʾ˳���������ݣ�������ȡ��Ӧ��ϵ';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CFG_ID
  is 'Ѳ�챨����������ID';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_SHOW_ORDER
  is '������ģ��չʾ˳����';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_MODULE_CODE
  is '������ģ�����';
comment on column AIAM.ARCH_SYS_REPORT_CFG.MODULE_SHOW_ORDER
  is '��ģ���ڲ�չʾ˳����';
comment on column AIAM.ARCH_SYS_REPORT_CFG.MODULE_TYPE
  is '��ģ���ڲ��������ͣ�����⣬���ģ��ɼ�˵����';
comment on column AIAM.ARCH_SYS_REPORT_CFG.STATE
  is '������Ч��״̬,UΪ��Ч��XΪ������';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_1
  is 'Ѳ�챨�����������ֶ�1';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_2
  is 'Ѳ�챨�����������ֶ�2';
comment on column AIAM.ARCH_SYS_REPORT_CFG.REPORT_CONTENT_CFG_3
  is 'Ѳ�챨�����������ֶ�3';
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
  is '��ҵ��ϵͳ����״̬Ѳ��ԭʼ���ݽ����ͨ���ɼ����㽫��������ֵ�����������У����ñ�ͨ�������еı���ID�����ӱ����ȡ�������';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_ID
  is 'Ѳ�챨��ԭʼ������������������';
comment on column AIAM.ARCH_SYS_REPORT_DATA.CJ_DATE
  is '����ɼ�չʾ����';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_CODE
  is 'ԭʼ��������ID�������ñ��еĲ���ID��Ӧ';
comment on column AIAM.ARCH_SYS_REPORT_DATA.DATA_VALUE
  is 'ԭʼ�������ݽ��ֵ';
comment on column AIAM.ARCH_SYS_REPORT_DATA.INSERT_DATE
  is '���ݴ���ʱ��';
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
  is '��������';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.CJ_DATE
  is '����ɼ�չʾ����';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_SHOW_ORDER
  is '������ģ��չʾ˳����';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_MODULE_CODE
  is '������ģ�����';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.MODULE_SHOW_ORDER
  is '��ģ���ڲ�չʾ˳����';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.MODULE_TYPE
  is '��ģ���ڲ��������ͣ�����⣬���ģ��ɼ�˵����';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_1
  is 'Ѳ�챨��������1';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_2
  is 'Ѳ�챨��������2';
comment on column AIAM.ARCH_SYS_REPORT_RESULT.REPORT_CONTENT_3
  is 'Ѳ�챨��������3';
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
  is '��������ID';
comment on column AIAM.CFG_TASK.TASK_NAME
  is '��������';
comment on column AIAM.CFG_TASK.CFG_TASK_TYPE_CODE
  is '������������';
comment on column AIAM.CFG_TASK.BUSINESS_CLASS
  is '�ӿڵ�ҵ��ʵ����';
comment on column AIAM.CFG_TASK.TASK_METHOD
  is '����ִ�з�ʽ(F,Ϊ�̶�ʱ��;C,Ϊcron����ִ��;I,Ϊ����ִ��)';
comment on column AIAM.CFG_TASK.TASK_EXPR
  is '�������ñ��ʽ';
comment on column AIAM.CFG_TASK.SORT_ID
  is '�������ȼ���(Խ�����ȼ���Խ��) ';
comment on column AIAM.CFG_TASK.STAFF_ID
  is '���������Ա��';
comment on column AIAM.CFG_TASK.CREATE_DATE
  is '���񴴽�ʱ��';
comment on column AIAM.CFG_TASK.STATE_DATE
  is '�������õ�״̬����ʱ��';
comment on column AIAM.CFG_TASK.STATE
  is '״̬(U,Ϊ����;E,Ϊɾ��;F,Ϊִ�����;X,Ϊ�쳣ִ�����)';
comment on column AIAM.CFG_TASK.REMARKS
  is '��ע';
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
  is '�����ţ����У�';
comment on column AIAM.INSPECT_RADAR_ERROR.RULE_ID
  is '������';
comment on column AIAM.INSPECT_RADAR_ERROR.SYS_ID
  is 'ϵͳ���';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_NAME
  is 'ָ������';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_INFO
  is 'ָ����Ϣ˵��';
comment on column AIAM.INSPECT_RADAR_ERROR.PARANT_NAME
  is '�����˴����';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_MARK
  is 'ָ��÷�';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_VALUE
  is 'ָ����ֵ';
comment on column AIAM.INSPECT_RADAR_ERROR.INDEX_TIME
  is 'ָ�����ʱ��';
comment on column AIAM.INSPECT_RADAR_ERROR.CREATE_TIME
  is '����ʱ��';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT1
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT2
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_ERROR.EXT3
  is '��չ�ֶ�';

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
  is '�����ţ����У�';
comment on column AIAM.INSPECT_RADAR_RESULT.SYS_ID
  is 'ϵͳ���';
comment on column AIAM.INSPECT_RADAR_RESULT.TOTAL_MARK
  is '�ܵ÷�';
comment on column AIAM.INSPECT_RADAR_RESULT.AQ_MARK
  is '��ȫ';
comment on column AIAM.INSPECT_RADAR_RESULT.RL_MARK
  is '����';
comment on column AIAM.INSPECT_RADAR_RESULT.JK_MARK
  is '���';
comment on column AIAM.INSPECT_RADAR_RESULT.GKY_MARK
  is '�߿���';
comment on column AIAM.INSPECT_RADAR_RESULT.RXKY_MARK
  is '���Կ���';
comment on column AIAM.INSPECT_RADAR_RESULT.PZ_MARK
  is '����';
comment on column AIAM.INSPECT_RADAR_RESULT.RZ_MARK
  is '��־';
comment on column AIAM.INSPECT_RADAR_RESULT.FC_MARK
  is '�ֲ�';
comment on column AIAM.INSPECT_RADAR_RESULT.CREATE_TIME
  is '����ʱ��';
comment on column AIAM.INSPECT_RADAR_RESULT.SPONSOR
  is '������';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT1
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT2
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_RESULT.EXT3
  is '��չ�ֶ�';

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
  is '�����ţ����У�';
comment on column AIAM.INSPECT_RADAR_RULE.TEMP_ID
  is 'ģ����';
comment on column AIAM.INSPECT_RADAR_RULE.SYS_ID
  is 'ϵͳ���';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_VALUE
  is 'ָ��ɼ���ֵ';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_TIME
  is 'ָ��ɼ�ʱ��';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_SQL
  is 'ָ��ɼ�sql';
comment on column AIAM.INSPECT_RADAR_RULE.INDEX_MARK
  is 'ָ��÷�';
comment on column AIAM.INSPECT_RADAR_RULE.STATE
  is '״̬';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_TYPE
  is '��������; ���桢��������';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_FUNCTION
  is '�жϷ���';
comment on column AIAM.INSPECT_RADAR_RULE.RULE_VALUE
  is '�����ж�ֵ';
comment on column AIAM.INSPECT_RADAR_RULE.CREATE_TIME
  is '����ʱ��';
comment on column AIAM.INSPECT_RADAR_RULE.EXT1
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_RULE.EXT2
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_RULE.EXT3
  is '��չ�ֶ�';

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
  is 'ģ���ţ����У�';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_NAME
  is 'ָ������';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_INFO
  is 'ָ����Ϣ˵��';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_TYPE
  is 'ָ�����ͣ��˴���Ҫ����˴��������';
comment on column AIAM.INSPECT_RADAR_TEMP.PARENT_ID
  is 'ָ���Ӧ�˴�������';
comment on column AIAM.INSPECT_RADAR_TEMP.INDEX_WEIGHT
  is 'ָ��Ȩ��';
comment on column AIAM.INSPECT_RADAR_TEMP.STATE
  is '״̬';
comment on column AIAM.INSPECT_RADAR_TEMP.CREATE_TIME
  is '����ʱ��';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT1
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT2
  is '��չ�ֶ�';
comment on column AIAM.INSPECT_RADAR_TEMP.EXT3
  is '��չ�ֶ�';

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
  is '����ID';
comment on column AIAM.NA_CASE_IMPL_REPORT.ONLINE_PLAN_ID
  is '���߼ƻ�ID';
comment on column AIAM.NA_CASE_IMPL_REPORT.ONLINE_PLAN_NAME
  is '���߼ƻ�����';
comment on column AIAM.NA_CASE_IMPL_REPORT.PLAN_DATE
  is '�ƻ�����ʱ��';
comment on column AIAM.NA_CASE_IMPL_REPORT.SYS_NAME
  is 'ϵͳ����/����ҵ������';
comment on column AIAM.NA_CASE_IMPL_REPORT.ENVIRONMENT
  is '����';
comment on column AIAM.NA_CASE_IMPL_REPORT.CASE_TOTAL_COUNT
  is '��������';
comment on column AIAM.NA_CASE_IMPL_REPORT.MANUAL_CASE_COUNT
  is '�ֹ�������';
comment on column AIAM.NA_CASE_IMPL_REPORT.AUTO_CASE_COUNT
  is '�Զ���������';
comment on column AIAM.NA_CASE_IMPL_REPORT.FIR_SUC_RATE
  is 'һ��ִ�гɹ���';
comment on column AIAM.NA_CASE_IMPL_REPORT.FIR_EXEC_TIME
  is '��һ��ִ��ʱ��';
comment on column AIAM.NA_CASE_IMPL_REPORT.SEC_SUC_RATE
  is '����ִ�гɹ���';
comment on column AIAM.NA_CASE_IMPL_REPORT.SEC_EXEC_TIME
  is '�ڶ���ִ��ʱ��';
comment on column AIAM.NA_CASE_IMPL_REPORT.THIRD_SUC_RATE
  is '����ִ�гɹ���';
comment on column AIAM.NA_CASE_IMPL_REPORT.THIRD_EXEC_TIME
  is '������ִ��ʱ��';
comment on column AIAM.NA_CASE_IMPL_REPORT.FLAG
  is '�Ƿ�������1���� 2����';
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
  is '����ƻ����';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.ONLINE_PLAN_NAME
  is '����ƻ�����';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.PLAN_STATE
  is '�ƻ�״̬';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.CREATE_OP_ID
  is '������';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.DONE_DATE
  is '���ʱ��';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.PLAN_DATE
  is '�ƻ�����ʱ��';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.RESULT
  is '���߽���';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.REMARK
  is '��ע';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.EXT_2
  is '�����ܽ�';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.SIGN
  is '�ƻ��Ƿ����';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.TYPES
  is '����';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.TIMELY
  is '���������Ƿ�ʱ';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.IS_FINISHED
  is '�Ƿ�������';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.AUTO_RUN_RESULT
  is '�Զ���ִ�н��';
comment on column AIAM.NA_CHANGE_PLAN_ONILE.FILE_UPLOAD_LAST_TIME
  is '���������ʱ��';
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
  is '�ļ�����';
comment on column AIAM.NA_FILE_UPLOAD.CREATE_TIME
  is '�ϴ�ʱ��';
comment on column AIAM.NA_FILE_UPLOAD.LAST_UPLOAD_TIME
  is '���һ���ϴ�ʱ��';
comment on column AIAM.NA_FILE_UPLOAD.UPLOAD_COUNT
  is '�ϴ�����';
comment on column AIAM.NA_FILE_UPLOAD.DOWN_LOAD_TIME
  is '����ʱ��';
comment on column AIAM.NA_FILE_UPLOAD.FILE_TYPE
  is '�ļ����ͣ�';
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
  is '����ID';
comment on column AIAM.NA_IMAGE_UPLOAD.FILE_NAME
  is 'ͼƬ����';
comment on column AIAM.NA_IMAGE_UPLOAD.IMG_SRC
  is 'ͼƬ·��';
comment on column AIAM.NA_IMAGE_UPLOAD.TITLE
  is '����';
comment on column AIAM.NA_IMAGE_UPLOAD.DESCRIPTION
  is '����';
comment on column AIAM.NA_IMAGE_UPLOAD.LIKE_COUNT
  is 'ϲ������';
comment on column AIAM.NA_IMAGE_UPLOAD.COMMENT_COUNT
  is '��������';
comment on column AIAM.NA_IMAGE_UPLOAD.IS_SHARED
  is '�Ƿ���';
comment on column AIAM.NA_IMAGE_UPLOAD.CREATE_TIME
  is '����ʱ��';
comment on column AIAM.NA_IMAGE_UPLOAD.PLAN_ID
  is '����ID';
comment on column AIAM.NA_IMAGE_UPLOAD.FILE_TYPE
  is '�ļ�����';
comment on column AIAM.NA_IMAGE_UPLOAD.CREATE_ID
  is '������ID';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_1
  is '��չ�ֶ�1';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_2
  is '��չ�ֶ�2';
comment on column AIAM.NA_IMAGE_UPLOAD.EXT_3
  is '��չ�ֶ�3';
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
  is 'ָ������';
comment on column AIAM.NA_INDEX_ALLOCATION.KPI_TYPE
  is 'ָ������';
comment on column AIAM.NA_INDEX_ALLOCATION.VALUE
  is 'ָ��ֵ';
comment on column AIAM.NA_INDEX_ALLOCATION.ICON
  is 'ͼ��';
comment on column AIAM.NA_INDEX_ALLOCATION.BG_COLOR
  is '������ɫ';
comment on column AIAM.NA_INDEX_ALLOCATION.IS_SHOW
  is '�Ƿ���ʾ';
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
  is '����ID';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PROCESS_NAME
  is '����';
comment on column AIAM.NA_PROCESS_NODE_RECORD.TIME
  is 'ʱ��';
comment on column AIAM.NA_PROCESS_NODE_RECORD.TYPE
  is '���� ��1������.2.�Ѵ��� 3.δ����';
comment on column AIAM.NA_PROCESS_NODE_RECORD.NODE
  is '�ڵ�';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PLAN_ID
  is '�ƻ�Id';
comment on column AIAM.NA_PROCESS_NODE_RECORD.PLAN_DATE
  is '����ʱ��';
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
  is '������';
comment on column AIAM.QUESTION_INFO.QUES_TYPE
  is '��������
��1��ϵͳѲ�죻
2������Ѳ�죻
3���������⣻��';
comment on column AIAM.QUESTION_INFO.FIRST_CATEGORY
  is 'һ������
��1�������滮��
2���߿��ã�
3���ֲ㣻
4�����Կ��ã�
5����־��
6�����ã�
7����أ�
8����ȫ����';
comment on column AIAM.QUESTION_INFO.SECOND_CATEGORY
  is '��������
��1��ϵͳ������
2��ҵ��������
3��ϵͳ���ܣ�
--�������滮Ϊ����';
comment on column AIAM.QUESTION_INFO.THIRD_CATEGORY
  is '��������
��1�����ݿ�������
2���������
3��TPCC��
4���洢��
--��ϵͳ����Ϊ����';
comment on column AIAM.QUESTION_INFO.DIFF_PROBLEM
  is '��������';
comment on column AIAM.QUESTION_INFO.ABSTRACTS
  is 'ժҪ';
comment on column AIAM.QUESTION_INFO.OCCUR_ENVIRONMENT
  is '��������';
comment on column AIAM.QUESTION_INFO.BELONG_PROJECT
  is '������Ŀ';
comment on column AIAM.QUESTION_INFO.ID_FIRST
  is 'һ������';
comment on column AIAM.QUESTION_INFO.ID_SECOND
  is '����������';
comment on column AIAM.QUESTION_INFO.ID_THIRD
  is '����ϵͳ���';
comment on column AIAM.QUESTION_INFO.SYS_VERSION
  is 'ϵͳ�汾';
comment on column AIAM.QUESTION_INFO.PRIORITY
  is '���ȼ�
��1�������trivial��
2����Ҫ��minor��
3����Ҫ��major��
4��Σ����critical��
5�����ص�blocker����';
comment on column AIAM.QUESTION_INFO.DEFECT_LEVEL
  is 'ȱ�ݼ���
��1���ش�ȱ�ݣ�
2���ܹ�ȱ�ݣ�
3��һ��ȱ�ݣ�
4���������⣻
5��һ�����⣻��';
comment on column AIAM.QUESTION_INFO.STATE
  is '����״̬
��1�����걨��
2������϶���
3���ѷ��䣻
4���ѽ����
5���ѹرգ���';
comment on column AIAM.QUESTION_INFO.REQUEST_INFO
  is '�걨��Ϣ';
comment on column AIAM.QUESTION_INFO.IDENTIFIED_INFO
  is '�϶���Ϣ';
comment on column AIAM.QUESTION_INFO.SOLVED_INFO
  is '�����Ϣ';
comment on column AIAM.QUESTION_INFO.CREATE_DATE
  is '����ʱ��';
comment on column AIAM.QUESTION_INFO.MODIFY_DATE
  is '�޸�ʱ��';
comment on column AIAM.QUESTION_INFO.REPORTOR
  is '������';
comment on column AIAM.QUESTION_INFO.APPOINTED_PERSON
  is 'ָ����';
comment on column AIAM.QUESTION_INFO.EXT_1
  is '��չ�ֶ�1';
comment on column AIAM.QUESTION_INFO.EXT_2
  is '��չ�ֶ�2';
comment on column AIAM.QUESTION_INFO.EXT_3
  is '��չ�ֶ�3';
comment on column AIAM.QUESTION_INFO.IDENTIFIED_NAME
  is '��չ�ֶ�4';
comment on column AIAM.QUESTION_INFO.SOLVED_NAME
  is '��չ�ֶ�5';
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
  is '����ĸƴ����δ��д�Ļ���ϵͳ���Զ���ӣ��ʴ˴���д�����������������ּ��ɣ�';
comment on column AIAM.SYS_CONSTANT.FULL_SPELLING
  is 'ȫƴ��δ��д�Ļ���ϵͳ���Զ���ӣ��ʴ˴���д�����������������ּ��ɣ�';
comment on column AIAM.SYS_CONSTANT.STATUS
  is '0 ���� 1������';
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
