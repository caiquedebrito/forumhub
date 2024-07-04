create table topics (
    id bigint not null auto_increment,
    title varchar(100) not null unique,
    message varchar(255) not null unique,
    author varchar(100) not null,
    course varchar(100) not null,
    state varchar(10),
    created_at datetime not null,

    primary key (id)
)