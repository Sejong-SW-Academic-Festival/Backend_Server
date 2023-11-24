
insert into category(category_type, name) VALUES
    ("ACADEMIC", "학사일정"),
    ("COLLEGE", "인문과학대학"),
    ("COLLEGE", "사회과학대학"),
    ("COLLEGE", "경영경제대학"),
    ("COLLEGE", "호텔관광대학"),
    ("COLLEGE", "자연과학대학"),
    ("COLLEGE", "생명과학대학"),
    ("COLLEGE", "전자정보공학대학"),
    ("COLLEGE", "소프트웨어융합대학"),   -- 9
    ("COLLEGE", "공과대학"),
    ("COLLEGE", "예채능대학"),
    ("DEPARTMENT", "컴퓨터공학과"),
    ("DEPARTMENT", "소프트웨어학과"),  -- 13
    ("DEPARTMENT", "정보보호학과"),
    ("DEPARTMENT", "지능기전공학과"),
    ("DEPARTMENT", "전자정보통신공학과"),
    ("DEPARTMENT", "반도체시스템공학과"),
    ("DEPARTMENT", "건축공학과"),
    ("DEPARTMENT", "건축학과"),
    ("DEPARTMENT", "건설환경공학과"),
    ("DEPARTMENT", "기계공학과"),
    ("DEPARTMENT", "양자원자력공학과"),
    ("DO_DREAM", "두드림"),        -- 23
    ("CLUB", "동아리"),     -- 24
    ("PERSONAL", "개인일정")
;

insert into schedule(created_at, updated_at, category_id, name, description, location, start_date, end_date, user_email) VALUES
    (now(), now(), 1, "성적조회기간(중간)", "2023/2학기", " ", '2023-10-27 00:00:00', '2023-11-06 23:59:59', " "),
    (now(), now(), 1, "전과신청기간", "2023/2학기", " ", '2023-11-30 00:00:00', '2023-12-13 23:59:59', " "),
    (now(), now(), 1, "2학기 강의평가", " ", " ", '2023-12-08 00:00:00', '2023-12-26 00:00:00', " "),
    (now(), now(), 1, "동계 계절학기 수강신청", " ", " ", '2023-12-04 00:00:00', '2023-12-06 00:00:00', " "),
    (now(), now(), 1, "2학기 기말고사 및 수업결손 보충", "2023/2학기", " ", '2023-12-15 00:00:00', '2023-12-21 00:00:00', " "),
    (now(), now(), 1, "1학기 복학, 휴학 신청", " ", " ", '2024-01-22 00:00:00', '2024-01-28 23:59:59', " "),
    (now(), now(), 1, "1학기 수강신청", " ", " ", '2024-02-13 00:00:00', '2024-02-16 23:59:59', " "),
    (now(), now(), 1, "1학기 등록", " ", " ", '2024-02-19 00:00:00', '2024-02-23 23:59:59', " "),
    (now(), now(), 1, "입학식", " ", " ", '2024-02-26 00:00:00', '2024-02-26 23:59:59', " "),
    (now(), now(), 1, "종강일", "2023/2학기", " ", '2023-12-21 00:00:00', '2023-12-21 23:59:59', " "),
    (now(), now(), 9, "소프트웨어융합대학 학생회 투표 기간", " ", "대양AI센터", '2023-11-27 10:30:00', '2023-11-30 18:00:00', " "),
    (now(), now(), 9, "소프트웨어융합대학 공청회", "학생회 선거 공청회", "대양AI센터 B205호", '2023-11-24 18:30:00', '2023-11-24 20:00:00', " "),
    (now(), now(), 9, "소프트웨어융합대학 학술제 전시회", " ", "대양AI센터 B1 복도", '2023-11-20 12:00:00', '2023-11-21 18:00:00', " "),
    (now(), now(), 12, "제 10회 컴퓨터공학과 학술제", " ", " ", '2023-12-15 00:00:00', '2023-12-22 23:59:59', " "),
    (now(), now(), 13, "소프트웨어학과 학술제", " ", "대양AI센터 B107호", '2023-11-27 18:00:00', '2023-11-27 20:00:00', " "),
    (now(), now(), 13, "소프트웨어학과 야식행사", "버거킹", "대양AI센터", '2023-08-23 00:00:00', '2023-08-25 00:00:00', " "),
    (now(), now(), 23, "2023-2 학술정보원 이용 교육-신입생3", " ", " ", '2023-11-28 14:00:00', '2023-11-28 15:00:00', " "),
    (now(), now(), 23, "2023-2 학술정보원 이용 교육-재학생5", " ", " ", '2023-11-29 14:00:00', '2023-11-29 15:00:00', " "),
    (now(), now(), 23, "2023-2 학습 컨설팅", "전체 학년", " ", '2023-11-09 00:00:00', '2023-12-11 23:45:59', " "),
    (now(), now(), 23, "2023년 IT 창업 기획 컨퍼런스", "취창업지원프로그램", "광개토관 208호", '2023-11-20 00:00:00', '2023-11-23 00:00:00', " "),
    (now(), now(), 23, "2023년 하계 창업캠프", "취창업지원프로그램", " ", '2023-08-23 00:00:00', '2023-08-25 00:00:00', " "),
    (now(), now(), 23, "2023년 창업 컨설팅(1:1 멘토링)", "취창업지원프로그램", " ", '2023-03-20 10:00:00', '2024-01-05 17:00:00', " "),
    (now(), now(), 23, "세종 나눔 튜터링 4기 모집", " ", " ", '2023-11-23 00:00:00', '2023-12-22 23:59:59', " "),
    (now(), now(), 23, "2023년 2학기 창업지원장학금", "취창업지원프로그램", " ", '2023-09-25 10:00:00', '2024-01-05 17:00:00', " "),
    (now(), now(), 24, "피로그래밍 20기 모집", "리크루팅", " ", '2023-11-21 00:00:00', '2023-12-02 00:00:00', " "),
    (now(), now(), 24, "SKBS 방송제", "방송제", "학생회관 지하 1층", '2023-11-17 00:00:00', '2023-11-17 23:59:59', " "),
    (now(), now(), 24, "세종극회 67회 워크샵 공연", " ", "학생회관 지하 1층 만남 소극장", '2023-12-02 14:00:00', '2023-12-02 18:00:00', " ")
;
