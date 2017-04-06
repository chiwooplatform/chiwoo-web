drop table if exists COM_I18N_MESSAGE;

create table if not exists COM_I18N_MESSAGE (
    code      varchar(100) not null,
    locale    varchar(20)  not null,   
    message   varchar(255) not null,
    use_yn    smallint     not null default 1,
    upd_dtm   timestamp    default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
); 

alter table COM_I18N_MESSAGE add constraint PK_COM_I18N_MESSAGE primary key (code, locale);
