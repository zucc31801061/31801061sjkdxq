/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/5 21:37:40                            */
/*==============================================================*/


drop table if exists add_info;

drop table if exists control;

drop table if exists dd_info;

drop table if exists gly_info;

drop table if exists mj_method;

drop table if exists own;

drop table if exists qs_info;

drop table if exists sj_info;

drop table if exists sp_dd;

drop table if exists sp_info;

drop table if exists sp_kind;

drop table if exists sp_pj;

drop table if exists user_info;

drop table if exists yh_info;

drop table if exists yh_use;

/*==============================================================*/
/* Table: add_info                                              */
/*==============================================================*/
create table add_info
(
   add_no               varchar(5) not null,
   use_user_no          varchar(5),
   sheng                varchar(5),
   shi                  varchar(5),
   qu                   varchar(5),
   address              varchar(50),
   user_name            varchar(20),
   user_phnum           varchar(20),
   user_no              varchar(5),
   primary key (add_no)
);

/*==============================================================*/
/* Table: control                                               */
/*==============================================================*/
create table control
(
   dd_no                varchar(5) not null,
   yg_no                varchar(20) not null,
   primary key (dd_no, yg_no)
);

/*==============================================================*/
/* Table: dd_info                                               */
/*==============================================================*/
create table dd_info
(
   sp_no                varchar(5) not null,
   dd_no                varchar(5) not null,
   num                  int,
   price                float(15),
   discount             float(15),
   primary key (sp_no, dd_no)
);

/*==============================================================*/
/* Table: gly_info                                              */
/*==============================================================*/
create table gly_info
(
   yg_no                varchar(20) not null,
   yg_name              varchar(20),
   yg_mm                varchar(20),
   primary key (yg_no)
);

/*==============================================================*/
/* Table: mj_method                                             */
/*==============================================================*/
create table mj_method
(
   mj_no                varchar(5) not null,
   sj_no                varchar(5),
   mj_money             float(15),
   mj_yh                float(15),
   mj_dj                bool,
   primary key (mj_no)
);

/*==============================================================*/
/* Table: own                                                   */
/*==============================================================*/
create table own
(
   yh_no                varchar(5) not null,
   user_no              varchar(5) not null,
   money                float(15),
   num                  int,
   endtime              date,
   primary key (yh_no, user_no)
);

/*==============================================================*/
/* Table: qs_info                                               */
/*==============================================================*/
create table qs_info
(
   qs_no                varchar(5) not null,
   dd_no                varchar(5) not null,
   qs_name              varchar(20),
   qs_date              date,
   qs_id                varchar(20),
   money                float(15),
   time                 datetime,
   pj                   varchar(500),
   primary key (qs_no)
);

/*==============================================================*/
/* Table: sj_info                                               */
/*==============================================================*/
create table sj_info
(
   sj_name              varchar(20),
   sj_star              int,
   sj_avgxf             float(15),
   sj_sumxl             float(15),
   sj_no                varchar(5) not null,
   primary key (sj_no)
);

/*==============================================================*/
/* Table: sp_dd                                                 */
/*==============================================================*/
create table sp_dd
(
   dd_startmoney        float(15),
   dd_endmoney          float(15),
   dd_starttime         datetime,
   dd_endtime           datetime,
   dd_zt                varchar(20),
   sj_no                varchar(5),
   add_no               varchar(5),
   mj_no                varchar(5),
   user_no              varchar(5),
   dd_no                varchar(5) not null,
   qs__qs_no            varchar(5),
   use_user_no          varchar(5),
   qs_no                varchar(5),
   yh_no                varchar(5),
   primary key (dd_no)
);

/*==============================================================*/
/* Table: sp_info                                               */
/*==============================================================*/
create table sp_info
(
   sp_no                varchar(5) not null,
   sp__fl_no            varchar(5),
   sj_no                varchar(5),
   sp_name              varchar(20),
   sp_money             float(15),
   sp_yh                float(15),
   fl_no                varchar(5),
   primary key (sp_no)
);

/*==============================================================*/
/* Table: sp_kind                                               */
/*==============================================================*/
create table sp_kind
(
   fl_no                varchar(5) not null,
   fl_name              varchar(20),
   num                  int,
   primary key (fl_no)
);

/*==============================================================*/
/* Table: sp_pj                                                 */
/*==============================================================*/
create table sp_pj
(
   pj_nr                varchar(500),
   pj_date              datetime,
   pj_star              int,
   pj_photo             bool,
   sp_no                varchar(5),
   sj_no                varchar(5),
   user_no              varchar(5),
   dd                   varchar(5) not null,
   sp__sp_no            varchar(5),
   primary key (dd)
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   user_name            varchar(20),
   user_sex             varchar(20),
   user_mm              varchar(20),
   user_phnum           varchar(20),
   user_email           varchar(20),
   user_city            varchar(20),
   user_starttime       date,
   vip                  bool,
   vip_enddate          date,
   user_no              varchar(5) not null,
   primary key (user_no)
);

/*==============================================================*/
/* Table: yh_info                                               */
/*==============================================================*/
create table yh_info
(
   yh_money             int,
   jd_num               int,
   yh_startdate         date,
   yh_enddate           date,
   yh_no                varchar(5) not null,
   sj_no                varchar(5),
   dd_no                varchar(5) not null,
   already              int,
   primary key (yh_no)
);

/*==============================================================*/
/* Table: yh_use                                                */
/*==============================================================*/
create table yh_use
(
   yh_no                varchar(5) not null,
   dd_no                varchar(5) not null,
   primary key (yh_no, dd_no)
);

alter table add_info add constraint FK_add_use foreign key (use_user_no)
      references user_info (user_no) on delete restrict on update restrict;

alter table control add constraint FK_control foreign key (dd_no)
      references sp_dd (dd_no) on delete restrict on update restrict;

alter table control add constraint FK_control2 foreign key (yg_no)
      references gly_info (yg_no) on delete restrict on update restrict;

alter table dd_info add constraint FK_dd_info foreign key (sp_no)
      references sp_info (sp_no) on delete restrict on update restrict;

alter table dd_info add constraint FK_dd_info2 foreign key (dd_no)
      references sp_dd (dd_no) on delete restrict on update restrict;

alter table mj_method add constraint FK_mj_provide foreign key (sj_no)
      references sj_info (sj_no) on delete restrict on update restrict;

alter table own add constraint FK_own2 foreign key (yh_no)
      references yh_info (yh_no) on delete restrict on update restrict;

alter table own add constraint FK_own3 foreign key (user_no)
      references user_info (user_no) on delete restrict on update restrict;

alter table qs_info add constraint FK_makemoney foreign key (dd_no)
      references sp_dd (dd_no) on delete restrict on update restrict;

alter table sp_dd add constraint FK_order foreign key (use_user_no)
      references user_info (user_no) on delete restrict on update restrict;

alter table sp_dd add constraint FK_take foreign key (qs__qs_no)
      references qs_info (qs_no) on delete restrict on update restrict;

alter table sp_info add constraint FK_belong foreign key (sp__fl_no)
      references sp_kind (fl_no) on delete restrict on update restrict;

alter table sp_info add constraint FK_produce foreign key (sj_no)
      references sj_info (sj_no) on delete restrict on update restrict;

alter table sp_pj add constraint FK_own foreign key (sp__sp_no)
      references sp_info (sp_no) on delete restrict on update restrict;

alter table yh_info add constraint FK_sum foreign key (dd_no)
      references sp_dd (dd_no) on delete restrict on update restrict;

alter table yh_info add constraint FK_yh_provide foreign key (sj_no)
      references sj_info (sj_no) on delete restrict on update restrict;

alter table yh_use add constraint FK_yh_use foreign key (yh_no)
      references yh_info (yh_no) on delete restrict on update restrict;

alter table yh_use add constraint FK_yh_use2 foreign key (dd_no)
      references sp_dd (dd_no) on delete restrict on update restrict;

