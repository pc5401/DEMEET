# 이번 주 학습 내용

0713
프로젝트 관련 주제 찾고
깃 다함께 테스트

0714
주제 세부화하고 주제 관련 조사(crdt관련해 markdown editor나 그림판 연동이 가능한지 확인 등)
노션 정리

## 1. 주제 관련 조사

아래 툴들은 모두 동시편집을 지원한다. ⇒ 동시편집을 지원하는 백엔드로 yorkie 사용 (직접 구현은 손이 너무 많이 듬)

1. 명세서 작성을 위한 툴을 제공한다
   - 명세서 폼
     - Markdown (simplemde, vue example)
       - 직접 구현 할 것인가 혹은 존재하는 라이브러리를 사용 할 것인가?
       - https://rlxuc0ppd.toastcdn.net/presentation/[NHN FORWARD 2020]12K만큼 사랑하는 오픈 소스 에디터 개발 이야기_류선임.pdf 실제로 마크다운 에디터를 활용하여 개발한 사람의 발표자료, 개발할 때 참고하면 좋을 듯.
       - https://github.com/nhn/tui.editor/tree/master/apps/vue-editor : 이미 구현된 사례, 직접 사용하기엔 편해보임
       - https://commonmark.org 명세 및 직접 구현에 도움이 되는 사이트
       - https://github.github.com/gfm/ 깃허브 스타일의 마크다운에 관련된 사이트
   - ERD
     - 직접 드로잉 기능 구현
   - 순서도
     - 직접 드로잉 기능 구현

## 2. 스켈레톤 코드 리뷰

### 2.1 Sub PJT 1 로그인 과정

#### 결론

---

userService.getUserByUserId(userId)를 통해 내 db에 유저가 있는지 확인하는 과정을 거치는데 이때 유저아이디가 db에 없으면 어떻게되는지 확인해서 보완해줄 필요 있음.

위 과정에서 jpa, springSecurity, jwt, 등을 자세히 봐줘야 할 필요성을 느낌.



### 2.2 Sub PJT 1 회원가입 과정

#### 결론

---

회원가입부분에서 이해 안되는 부분 발견

1. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않는다는 말이 이해되지않음
2. userRepository.save(user)의 save가 어디서오는것인지, 어떻게 작동되는지 알수없음

위를 위해선 jpa를 우선 공부해야할듯함
