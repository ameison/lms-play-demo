# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table academy (
  id                            bigint not null,
  name                          varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_academy primary key (id)
);
create sequence academy_seq;

create table badge (
  id                            bigint not null,
  badge_group_id                bigint,
  name                          varchar(255),
  description                   varchar(255),
  url_image                     varchar(255),
  type                          varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_badge primary key (id)
);
create sequence badge_seq;

create table badge_group (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_badge_group primary key (id)
);
create sequence badge_group_seq;

create table banners (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  url_image                     varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_banners primary key (id)
);
create sequence banners_seq;

create table chapter (
  id                            bigint not null,
  name                          varchar(45),
  imagen                        varchar(200),
  course_id                     bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_chapter primary key (id)
);
create sequence chapter_seq;

create table content (
  id                            bigint not null,
  lesson_id                     bigint,
  name                          varchar(45),
  credit                        integer not null,
  order_content                 integer not null,
  type_content                  integer not null,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_content primary key (id)
);
create sequence content_seq;

create table content_download (
  id                            bigint not null,
  content_id                    bigint,
  title                         varchar(100),
  description                   TEXT,
  url_file                      varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_content_download_content_id unique (content_id),
  constraint pk_content_download primary key (id)
);
create sequence content_download_seq;

create table content_game (
  id                            bigint not null,
  content_id                    bigint,
  title                         varchar(100),
  description                   varchar(400),
  pos_ini                       varchar(10),
  orientation                   varchar(1),
  balls_x                       varchar(100),
  balls_y                       varchar(100),
  balls_rep                     varchar(100),
  code_example                  varchar(1200),
  cor_x_ini                     varchar(100),
  cor_y_ini                     varchar(100),
  cor_x_fin                     varchar(100),
  cor_y_fin                     varchar(100),
  pos_fin                       varchar(10),
  orientation_f                 varchar(1),
  balls_x_f                     varchar(100),
  balls_y_f                     varchar(100),
  balls_rep_f                   varchar(100),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_content_game_content_id unique (content_id),
  constraint pk_content_game primary key (id)
);
create sequence content_game_seq;

create table content_homework (
  id                            bigint not null,
  content_id                    bigint,
  title                         varchar(100),
  shortdescription              TEXT,
  longdescription               TEXT,
  url_homework                  varchar(255),
  instructor_id                 bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_content_homework_content_id unique (content_id),
  constraint uq_content_homework_instructor_id unique (instructor_id),
  constraint pk_content_homework primary key (id)
);
create sequence content_homework_seq;

create table content_homework_chat (
  id                            bigint not null,
  user_id                       bigint,
  contenthomework_id            bigint,
  message                       TEXT,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_content_homework_chat primary key (id)
);
create sequence content_homework_chat_seq;

create table content_quiz (
  id                            bigint not null,
  content_id                    bigint,
  question                      varchar(1000),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_content_quiz_content_id unique (content_id),
  constraint pk_content_quiz primary key (id)
);
create sequence content_quiz_seq;

create table content_quiz_answer (
  id                            bigint not null,
  content_quiz_id               bigint,
  answer                        varchar(1000),
  order_answer                  integer not null,
  is_correct_answer             boolean default false not null,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_content_quiz_answer primary key (id)
);
create sequence content_quiz_answer_seq;

create table content_video (
  id                            bigint not null,
  content_id                    bigint,
  isolation                     varchar(1),
  title                         varchar(100),
  description                   varchar(400),
  tocken                        varchar(400),
  time                          varchar(255),
  state                         boolean default false not null,
  url_video                     varchar(500),
  video_host                    varchar(1),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_content_video_isolation check ( isolation in ('O','C')),
  constraint ck_content_video_video_host check ( video_host in ('W','Y','O')),
  constraint uq_content_video_content_id unique (content_id),
  constraint pk_content_video primary key (id)
);
create sequence content_video_seq;

create table content_video_chat (
  id                            bigint not null,
  user_id                       bigint,
  contentvideo_id               bigint,
  message                       TEXT,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_content_video_chat primary key (id)
);
create sequence content_video_chat_seq;

create table content_video_hangout (
  id                            bigint not null,
  gid                           varchar(12),
  url_hangout                   TEXT,
  contentvideo_id               bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_content_video_hangout_gid check ( gid in ('245625370725')),
  constraint pk_content_video_hangout primary key (id)
);
create sequence content_video_hangout_seq;

create table course (
  id                            bigint not null,
  code                          varchar(15),
  name                          varchar(50),
  description                   TEXT,
  url_image                     varchar(255),
  url_video                     varchar(255),
  course_free                   boolean,
  start_date                    timestamptz,
  course_dependency             varchar(255),
  academy_id                    bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_course_code unique (code),
  constraint pk_course primary key (id)
);
create sequence course_seq;

create table course_instructor (
  id                            bigint not null,
  instructor_id                 bigint,
  course_id                     bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_course_instructor primary key (id)
);
create sequence course_instructor_seq;

create table course_library (
  id                            bigint not null,
  course_id                     bigint,
  price                         float,
  state                         varchar(1),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_course_library_state check ( state in ('A','I')),
  constraint uq_course_library_course_id unique (course_id),
  constraint pk_course_library primary key (id)
);
create sequence course_library_seq;

create table schedule (
  id                            bigint not null,
  course_teacher_id             bigint,
  start_time                    varchar(255),
  finish_time                   varchar(255),
  state                         integer not null,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_schedule primary key (id)
);
create sequence schedule_seq;

create table events (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  address                       varchar(255),
  start_date                    timestamptz,
  end_date                      timestamptz,
  url_image                     varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_events primary key (id)
);
create sequence events_seq;

create table exercise (
  id                            bigint not null,
  user_id                       bigint,
  name                          varchar(45),
  description                   TEXT,
  level                         integer,
  time_limit                    varchar(255),
  source_limit                  varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_exercise_level check ( level in (1,2,3)),
  constraint pk_exercise primary key (id)
);
create sequence exercise_seq;

create table general (
  id                            bigint not null,
  abbreviation                  varchar(255),
  description                   varchar(255),
  code                          varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_general primary key (id)
);
create sequence general_seq;

create table general_detail (
  id                            bigint not null,
  general_id                    bigint,
  abbreviation                  varchar(255),
  description                   varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_general_detail primary key (id)
);
create sequence general_detail_seq;

create table homework_student (
  id                            bigint not null,
  homework_id                   bigint,
  student_id                    bigint,
  descripcion                   TEXT,
  url_complemento               varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_homework_student primary key (id)
);
create sequence homework_student_seq;

create table lesson (
  id                            bigint not null,
  name                          varchar(45),
  order_lesson                  integer not null,
  chapter_id                    bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_lesson primary key (id)
);
create sequence lesson_seq;

create table payment (
  id                            bigint not null,
  plan_id                       bigint,
  card_number                   varchar(20),
  card_cvc                      varchar(3),
  card_month                    bigint,
  card_year                     varchar(4),
  user_id                       bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_payment_user_id unique (user_id),
  constraint pk_payment primary key (id)
);
create sequence payment_seq;

create table resource (
  id                            bigint not null,
  course_id                     bigint,
  titulo                        varchar(100),
  description                   varchar(200),
  url_image                     varchar(500),
  url_content                   varchar(500),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_resource primary key (id)
);
create sequence resource_seq;

create table schedule_detail (
  id                            bigint not null,
  schedule_id                   bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_schedule_detail primary key (id)
);
create sequence schedule_detail_seq;

create table teacher_schedule (
  id_shedule                    bigint not null,
  date                          varchar(255),
  start_time                    varchar(255),
  end_time                      varchar(255),
  user_id                       bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint uq_teacher_schedule_user_id unique (user_id),
  constraint pk_teacher_schedule primary key (id_shedule)
);
create sequence teacher_schedule_seq;

create table users (
  id                            bigint not null,
  code                          bigint,
  username                      varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  about                         varchar(255),
  birthday                      timestamptz,
  email                         varchar(255),
  password                      varchar(255),
  url_image                     varchar(255),
  file_image                    bytea,
  class_code                    varchar(255),
  organization_name             varchar(100),
  organization_type             integer not null,
  country_id                    varchar(50),
  state                         varchar(1),
  telephone                     varchar(11),
  info_completed                boolean default false not null,
  rol                           varchar(1),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint ck_users_state check ( state in ('A','I')),
  constraint ck_users_rol check ( rol in ('S','T','A')),
  constraint uq_users_code unique (code),
  constraint uq_users_username unique (username),
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id)
);
create sequence user_seq;

create table user_badges (
  id                            bigint not null,
  user_id                       bigint,
  badge_id                      bigint,
  type                          varchar(255),
  register_date                 timestamptz,
  modified_date                 timestamptz,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_user_badges primary key (id)
);
create sequence user_badges_seq;

create table user_course (
  id                            bigint not null,
  user_id                       bigint,
  course_id                     bigint,
  blocked                       boolean,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_user_course primary key (id)
);
create sequence user_course_seq;

create table user_progress (
  user_id                       bigint not null,
  content_id                    bigint not null,
  content_type                  integer,
  state                         varchar(255),
  execute_date                  timestamptz,
  code_game                     varchar(255),
  quiz_answer_id                bigint,
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_user_progress primary key (user_id,content_id)
);

create table videos (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  url_video                     varchar(255),
  created_date                  timestamptz not null,
  updated_date                  timestamptz not null,
  constraint pk_videos primary key (id)
);
create sequence videos_seq;

create index ix_badge_badge_group_id on badge (badge_group_id);
alter table badge add constraint fk_badge_badge_group_id foreign key (badge_group_id) references badge_group (id) on delete restrict on update restrict;

create index ix_chapter_course_id on chapter (course_id);
alter table chapter add constraint fk_chapter_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_content_lesson_id on content (lesson_id);
alter table content add constraint fk_content_lesson_id foreign key (lesson_id) references lesson (id) on delete restrict on update restrict;

alter table content_download add constraint fk_content_download_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;

alter table content_game add constraint fk_content_game_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;

alter table content_homework add constraint fk_content_homework_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;

alter table content_homework add constraint fk_content_homework_instructor_id foreign key (instructor_id) references users (id) on delete restrict on update restrict;

create index ix_content_homework_chat_user_id on content_homework_chat (user_id);
alter table content_homework_chat add constraint fk_content_homework_chat_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_content_homework_chat_contenthomework_id on content_homework_chat (contenthomework_id);
alter table content_homework_chat add constraint fk_content_homework_chat_contenthomework_id foreign key (contenthomework_id) references content_homework (id) on delete restrict on update restrict;

alter table content_quiz add constraint fk_content_quiz_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;

create index ix_content_quiz_answer_content_quiz_id on content_quiz_answer (content_quiz_id);
alter table content_quiz_answer add constraint fk_content_quiz_answer_content_quiz_id foreign key (content_quiz_id) references content_quiz (id) on delete restrict on update restrict;

alter table content_video add constraint fk_content_video_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;

create index ix_content_video_chat_user_id on content_video_chat (user_id);
alter table content_video_chat add constraint fk_content_video_chat_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_content_video_chat_contentvideo_id on content_video_chat (contentvideo_id);
alter table content_video_chat add constraint fk_content_video_chat_contentvideo_id foreign key (contentvideo_id) references content_video (id) on delete restrict on update restrict;

create index ix_content_video_hangout_contentvideo_id on content_video_hangout (contentvideo_id);
alter table content_video_hangout add constraint fk_content_video_hangout_contentvideo_id foreign key (contentvideo_id) references content_video (id) on delete restrict on update restrict;

create index ix_course_academy_id on course (academy_id);
alter table course add constraint fk_course_academy_id foreign key (academy_id) references academy (id) on delete restrict on update restrict;

create index ix_course_instructor_instructor_id on course_instructor (instructor_id);
alter table course_instructor add constraint fk_course_instructor_instructor_id foreign key (instructor_id) references users (id) on delete restrict on update restrict;

create index ix_course_instructor_course_id on course_instructor (course_id);
alter table course_instructor add constraint fk_course_instructor_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table course_library add constraint fk_course_library_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_schedule_course_teacher_id on schedule (course_teacher_id);
alter table schedule add constraint fk_schedule_course_teacher_id foreign key (course_teacher_id) references course_instructor (id) on delete restrict on update restrict;

create index ix_exercise_user_id on exercise (user_id);
alter table exercise add constraint fk_exercise_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_general_detail_general_id on general_detail (general_id);
alter table general_detail add constraint fk_general_detail_general_id foreign key (general_id) references general (id) on delete restrict on update restrict;

create index ix_homework_student_homework_id on homework_student (homework_id);
alter table homework_student add constraint fk_homework_student_homework_id foreign key (homework_id) references content_homework (id) on delete restrict on update restrict;

create index ix_homework_student_student_id on homework_student (student_id);
alter table homework_student add constraint fk_homework_student_student_id foreign key (student_id) references users (id) on delete restrict on update restrict;

create index ix_lesson_chapter_id on lesson (chapter_id);
alter table lesson add constraint fk_lesson_chapter_id foreign key (chapter_id) references chapter (id) on delete restrict on update restrict;

alter table payment add constraint fk_payment_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_resource_course_id on resource (course_id);
alter table resource add constraint fk_resource_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_schedule_detail_schedule_id on schedule_detail (schedule_id);
alter table schedule_detail add constraint fk_schedule_detail_schedule_id foreign key (schedule_id) references schedule (id) on delete restrict on update restrict;

alter table teacher_schedule add constraint fk_teacher_schedule_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_user_badges_user_id on user_badges (user_id);
alter table user_badges add constraint fk_user_badges_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_user_badges_badge_id on user_badges (badge_id);
alter table user_badges add constraint fk_user_badges_badge_id foreign key (badge_id) references badge (id) on delete restrict on update restrict;

create index ix_user_course_user_id on user_course (user_id);
alter table user_course add constraint fk_user_course_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_user_course_course_id on user_course (course_id);
alter table user_course add constraint fk_user_course_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_user_progress_user_id on user_progress (user_id);
alter table user_progress add constraint fk_user_progress_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_user_progress_content_id on user_progress (content_id);
alter table user_progress add constraint fk_user_progress_content_id foreign key (content_id) references content (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists badge drop constraint if exists fk_badge_badge_group_id;
drop index if exists ix_badge_badge_group_id;

alter table if exists chapter drop constraint if exists fk_chapter_course_id;
drop index if exists ix_chapter_course_id;

alter table if exists content drop constraint if exists fk_content_lesson_id;
drop index if exists ix_content_lesson_id;

alter table if exists content_download drop constraint if exists fk_content_download_content_id;

alter table if exists content_game drop constraint if exists fk_content_game_content_id;

alter table if exists content_homework drop constraint if exists fk_content_homework_content_id;

alter table if exists content_homework drop constraint if exists fk_content_homework_instructor_id;

alter table if exists content_homework_chat drop constraint if exists fk_content_homework_chat_user_id;
drop index if exists ix_content_homework_chat_user_id;

alter table if exists content_homework_chat drop constraint if exists fk_content_homework_chat_contenthomework_id;
drop index if exists ix_content_homework_chat_contenthomework_id;

alter table if exists content_quiz drop constraint if exists fk_content_quiz_content_id;

alter table if exists content_quiz_answer drop constraint if exists fk_content_quiz_answer_content_quiz_id;
drop index if exists ix_content_quiz_answer_content_quiz_id;

alter table if exists content_video drop constraint if exists fk_content_video_content_id;

alter table if exists content_video_chat drop constraint if exists fk_content_video_chat_user_id;
drop index if exists ix_content_video_chat_user_id;

alter table if exists content_video_chat drop constraint if exists fk_content_video_chat_contentvideo_id;
drop index if exists ix_content_video_chat_contentvideo_id;

alter table if exists content_video_hangout drop constraint if exists fk_content_video_hangout_contentvideo_id;
drop index if exists ix_content_video_hangout_contentvideo_id;

alter table if exists course drop constraint if exists fk_course_academy_id;
drop index if exists ix_course_academy_id;

alter table if exists course_instructor drop constraint if exists fk_course_instructor_instructor_id;
drop index if exists ix_course_instructor_instructor_id;

alter table if exists course_instructor drop constraint if exists fk_course_instructor_course_id;
drop index if exists ix_course_instructor_course_id;

alter table if exists course_library drop constraint if exists fk_course_library_course_id;

alter table if exists schedule drop constraint if exists fk_schedule_course_teacher_id;
drop index if exists ix_schedule_course_teacher_id;

alter table if exists exercise drop constraint if exists fk_exercise_user_id;
drop index if exists ix_exercise_user_id;

alter table if exists general_detail drop constraint if exists fk_general_detail_general_id;
drop index if exists ix_general_detail_general_id;

alter table if exists homework_student drop constraint if exists fk_homework_student_homework_id;
drop index if exists ix_homework_student_homework_id;

alter table if exists homework_student drop constraint if exists fk_homework_student_student_id;
drop index if exists ix_homework_student_student_id;

alter table if exists lesson drop constraint if exists fk_lesson_chapter_id;
drop index if exists ix_lesson_chapter_id;

alter table if exists payment drop constraint if exists fk_payment_user_id;

alter table if exists resource drop constraint if exists fk_resource_course_id;
drop index if exists ix_resource_course_id;

alter table if exists schedule_detail drop constraint if exists fk_schedule_detail_schedule_id;
drop index if exists ix_schedule_detail_schedule_id;

alter table if exists teacher_schedule drop constraint if exists fk_teacher_schedule_user_id;

alter table if exists user_badges drop constraint if exists fk_user_badges_user_id;
drop index if exists ix_user_badges_user_id;

alter table if exists user_badges drop constraint if exists fk_user_badges_badge_id;
drop index if exists ix_user_badges_badge_id;

alter table if exists user_course drop constraint if exists fk_user_course_user_id;
drop index if exists ix_user_course_user_id;

alter table if exists user_course drop constraint if exists fk_user_course_course_id;
drop index if exists ix_user_course_course_id;

alter table if exists user_progress drop constraint if exists fk_user_progress_user_id;
drop index if exists ix_user_progress_user_id;

alter table if exists user_progress drop constraint if exists fk_user_progress_content_id;
drop index if exists ix_user_progress_content_id;

drop table if exists academy cascade;
drop sequence if exists academy_seq;

drop table if exists badge cascade;
drop sequence if exists badge_seq;

drop table if exists badge_group cascade;
drop sequence if exists badge_group_seq;

drop table if exists banners cascade;
drop sequence if exists banners_seq;

drop table if exists chapter cascade;
drop sequence if exists chapter_seq;

drop table if exists content cascade;
drop sequence if exists content_seq;

drop table if exists content_download cascade;
drop sequence if exists content_download_seq;

drop table if exists content_game cascade;
drop sequence if exists content_game_seq;

drop table if exists content_homework cascade;
drop sequence if exists content_homework_seq;

drop table if exists content_homework_chat cascade;
drop sequence if exists content_homework_chat_seq;

drop table if exists content_quiz cascade;
drop sequence if exists content_quiz_seq;

drop table if exists content_quiz_answer cascade;
drop sequence if exists content_quiz_answer_seq;

drop table if exists content_video cascade;
drop sequence if exists content_video_seq;

drop table if exists content_video_chat cascade;
drop sequence if exists content_video_chat_seq;

drop table if exists content_video_hangout cascade;
drop sequence if exists content_video_hangout_seq;

drop table if exists course cascade;
drop sequence if exists course_seq;

drop table if exists course_instructor cascade;
drop sequence if exists course_instructor_seq;

drop table if exists course_library cascade;
drop sequence if exists course_library_seq;

drop table if exists schedule cascade;
drop sequence if exists schedule_seq;

drop table if exists events cascade;
drop sequence if exists events_seq;

drop table if exists exercise cascade;
drop sequence if exists exercise_seq;

drop table if exists general cascade;
drop sequence if exists general_seq;

drop table if exists general_detail cascade;
drop sequence if exists general_detail_seq;

drop table if exists homework_student cascade;
drop sequence if exists homework_student_seq;

drop table if exists lesson cascade;
drop sequence if exists lesson_seq;

drop table if exists payment cascade;
drop sequence if exists payment_seq;

drop table if exists resource cascade;
drop sequence if exists resource_seq;

drop table if exists schedule_detail cascade;
drop sequence if exists schedule_detail_seq;

drop table if exists teacher_schedule cascade;
drop sequence if exists teacher_schedule_seq;

drop table if exists users cascade;
drop sequence if exists user_seq;

drop table if exists user_badges cascade;
drop sequence if exists user_badges_seq;

drop table if exists user_course cascade;
drop sequence if exists user_course_seq;

drop table if exists user_progress cascade;

drop table if exists videos cascade;
drop sequence if exists videos_seq;

