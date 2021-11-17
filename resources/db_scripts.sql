drop table if exists accounts;
drop table if exists bank_accounts;
drop table if exists app_users;

create table app_users (
    id varchar check (id <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    email varchar(255) unique not null check (email <> ''),
    username varchar(20) unique not null check (username <> ''),
    password varchar(255) not null check (password <> ''),

    constraint app_users_pk
    primary key (id)
);

create table bank_accounts (
    id varchar not null check (id <> ''),
    balance numeric(10,2), --not null check (balance <> '')
    acc_type varchar not null check (acc_type <> ''),
    date_created date default current_date,
    creator_id varchar not null check (creator_id <> ''),

    constraint accounts_pk
    primary key (id),

    constraint user_creator_fk
    foreign key (creator_id)
    references app_users
);


create table user_bank_accounts(
	user_id varchar not null check (id<>''),
	bank_account_id varchar not null check(bank_account_id<>''),
	
	constraint user_id_fk
	foreign key (user_id)
	references app_users,
	
	constraint bank_acc_fk
	foreign key (bank_account_id)
	references accounts
);

