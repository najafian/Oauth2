create table if not exists authority
(
    authority_id bigint       not null AUTO_INCREMENT
        primary key,
    name         varchar(255) null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists client_detail
(
    id                            bigint       not null AUTO_INCREMENT
        primary key,
    client_id                     varchar(255) null,
    client_secret                 varchar(255) null,
    grant_type                    varchar(255) null,
    resource_id                   varchar(255) null,
    access_token_validity_seconds int          null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists client_authority
(
    id           bigint not null,
    authority_id bigint not null,
    primary key (id, authority_id),
    constraint FK6t30tyj9e84cwn9djdo15uylf
        foreign key (authority_id) references authority (authority_id),
    constraint FKjq1de23n4na43ety2k7q7t3tm
        foreign key (id) references client_detail (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists hibernate_sequence
(
    next_val bigint null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists role
(
    role_id   bigint       not null AUTO_INCREMENT
        primary key,
    role_name varchar(255) null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





create table if not exists scope
(
    scope_id   bigint       not null AUTO_INCREMENT
        primary key,
    scope_name varchar(255) null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists client_scopes
(
    client_detail_id bigint not null,
    scope_id         bigint not null,
    primary key (client_detail_id, scope_id),
    constraint FKb64bxc67vc626i6q5mjgabtiv
        foreign key (scope_id) references scope (scope_id),
    constraint FKfyj4qqqi8bw961n5w5e8jvdvr
        foreign key (client_detail_id) references client_detail (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists redirect
(
    redirect_id bigint  not null AUTO_INCREMENT
        primary key,
    url   varchar(255) null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists client_redirect
(
    client_detail_id bigint not null,
    redirect_id         bigint not null,
    primary key (client_detail_id, redirect_id),
    constraint FKb64bxc67vc626i6q5mjgjhytr
        foreign key (redirect_id) references redirect (redirect_id),
    constraint FKfyj4qqqi8bw961n5w5e8gfyrd
        foreign key (client_detail_id) references client_detail (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table if not exists user_account
(
    user_id         bigint       not null AUTO_INCREMENT
        primary key,
    app_id          bigint       null,
    company_id      bigint       null,
    created         datetime(6)  null,
    deleted         datetime(6)  null,
    email           varchar(255) null,
    expiration_date datetime(6)  null,
    enabled         bit          null,
    locked          bit          null,
    last_name       varchar(255) null,
    name            varchar(255) null,
    password        varchar(255) null,
    user_name       varchar(255) null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FK7ojmv1m1vrxfl3kvt5bi5ur73
        foreign key (user_id) references user_account (user_id),
    constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id) references role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;