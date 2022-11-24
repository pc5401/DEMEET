## 주제 정하기 회의

---

- 개발자를 위한 화상 채팅 서비스
    - 프로젝트 관리
    - 화상 미팅
- 개발 전체를 위한 서비스는 힘들다
    - 개발 이전을 위한 서비스 → 명세서 작성을 위한 툴을 제공한다.
    - 개발 진행 중을 위한 서비스 → 회의 녹화/녹음/기록을 보조해주는 기능
    - 개발 완료 이후 유지보수 과정을 위한 서비스
- 코드 리뷰를 위한 서비스 → merge conflict를 해결하기 위해 → 깃과 연동해보기
    - 현업에서는 어떻게 하나?
- 현업에서의 유지보수 과정
    - 현업에서는 어떻게 하나?

---

1. 개발 이전을 위한 서비스
    
    아래 툴들은 모두 동시편집을 지원한다.
    
    1. 명세서 작성을 위한 툴을 제공한다
        - 명세서 폼
            - Markdown ([simplemde](https://simplemde.com/), [vue example](https://vuejs.org/examples/#markdown))
                - [https://rlxuc0ppd.toastcdn.net/presentation/[NHN FORWARD 2020]12K만큼 사랑하는 오픈 소스 에디터 개발 이야기_류선임.pdf](https://rlxuc0ppd.toastcdn.net/presentation/%5BNHN%20FORWARD%202020%5D12K%EB%A7%8C%ED%81%BC%20%EC%82%AC%EB%9E%91%ED%95%98%EB%8A%94%20%EC%98%A4%ED%94%88%20%EC%86%8C%EC%8A%A4%20%EC%97%90%EB%94%94%ED%84%B0%20%EA%B0%9C%EB%B0%9C%20%EC%9D%B4%EC%95%BC%EA%B8%B0_%EB%A5%98%EC%84%A0%EC%9E%84.pdf)
                실제로 마크다운 에디터를 활용하여 개발한 사람의 발표자료, 개발할 때 참고하면 좋을 듯.
                - [https://github.com/nhn/tui.editor/tree/master/apps/vue-editor](https://github.com/nhn/tui.editor/tree/master/apps/vue-editor) : 
                이미 구현된 사례, 직접 사용하기엔 편해보임
                - [https://commonmark.org](https://commonmark.org/)
                명세 및 직접 구현에 도움이 되는 사이트
                - [https://github.github.com/gfm/](https://github.github.com/gfm/)
                깃허브 스타일의 마크다운에 관련된 사이트
        - ERD
            - 직접 드로잉 기능 구현
        - 순서도
            - 직접 드로잉 기능 구현
    2. 아이디어 구상을 위한 툴
        - 마인드맵
            - 드로잉 기능
            - 기본 :화이트보드 구현(가능)
            - 심화 : 코드가 보이는 반투명 보드(고민 필요)
            - → 보통 canvas tag 기반으로 javascripts 이용해서 구현
            - vue 라이브러리 : [https://www.npmjs.com/package/vue-drawing-canvas](https://www.npmjs.com/package/vue-drawing-canvas)
                - 예시 [https://codesandbox.io/s/vue-3-drawing-canvas-demo-ihmmz?file=/src/App.vue](https://codesandbox.io/s/vue-3-drawing-canvas-demo-ihmmz?file=/src/App.vue)
2. 개발 진행 중을 위한 서비스
    1. 회의 녹화/녹음/기록을 보조해주는 기능 (공통기능으로 넣을지 추후 논의)
        - 회의 녹화 / 녹음 : 서버에 저장하는 것이 아닌, 미팅 방장이 다운로드 받을 수 있게 함
            - [https://docs.openvidu.io/en/stable/advanced-features/recording/#how-to-record-sessions](https://docs.openvidu.io/en/stable/advanced-features/recording/#how-to-record-sessions)
            - [https://docs.openvidu.io/en/stable/tutorials/openvidu-recording-node/](https://docs.openvidu.io/en/stable/tutorials/openvidu-recording-node/)
            - 
        - 회의 기록 : 클로바 노트처럼 회의 대화 / 채팅을 text로 받을 수 있게 함.
            - [https://docs.openvidu.io/en/stable/cheatsheet/send-messages/](https://docs.openvidu.io/en/stable/cheatsheet/send-messages/)
            - [https://docs.openvidu.io/en/stable/components/openvidu-custom-chat-panel/](https://docs.openvidu.io/en/stable/components/openvidu-custom-chat-panel/)
        - 투표 기능 : 서버단에서 구현해야한다.
    2. MR 과정 중 생기는 코드 리뷰를 화상회의를 통해 면대면으로 하게 하는 과정
        - MR 요청시 깃 내 인원의 전체 회의 : 깃랩 연동, 깃헙 연동 (추후)
        - 머지 성공 시 프로젝트 인원들에게 알림 보냄
        - 머지 실패해도 프로젝트 인원들에게 알림 보냄
        - 회의 예약 기능에 머지 신청 결과를 아이콘으로 표시하기
        
        ---
        
        - 깃랩 기준으로 머지 요청만 받을 수 있음.
    3. 타이머 기능 → 정해진 시간 이내에 일 하기
3. 개발 완료 이후 유지보수 과정을 위한 서비스


--------
# 7/14

[스타일 가이드 | Vue.js](https://v3.ko.vuejs.org/style-guide/)

# 주제 : 프로젝트 기획을 위한 웹 미팅 서비스

## MVP 정하기 (기본기능)

---

- 회원 관리 기능
    - 회원가입
        - 회원 가입에 넣을 요소 : 이메일, 비밀번호
        - 가입시 정보를 암호화해서 넘겨줄 것인가
    - 로그인
        - 기본 로그인 방식 : jwt를 사용한 방식?
- webRTC 관련 기능
    - 화상회의
        - 카메라/마이크 on/off
        - 화면 공유 기능
        - 마이크/스피커 테스트 기능
            - 마이크/스피커 볼륨조절
        - 화면 전환 기능(자동이든 수동이든)
- 실시간 채팅 기능
    - Spring 사용시 SockJS 를 이용하는 것이 일반적
    - node.js 사용시 [Socket.IO](http://Socket.IO) 를 이용하는 것이 일반적
    - 또는 OpenVidu의 자체 채팅 기능 사용
- 마인드맵
    - 드로잉기능(febric.js)

## MLP 정하기 (추가기능)

---

- 회원 관리 기능
    - 비밀번호 찾기 기능
        - 이메일 인증을 통한 비밀번호 변경 기회 제공
- webRTC 관련 기능
    - 녹화/ 녹음 기능
    - 포인터를 통한 지시 기능(발표자 + 시청자)
    - 원격 제어 기능
- 실시간 채팅 기능
    - 채팅 기록을 openvidu에서 따로 저장할 수 있도록 구현
    - [Socket.I](http://Socket.IO)O 기반 채팅
- 개발 이전을 위한 서비스
    
    아래 툴들은 모두 동시편집을 지원한다.
    ⇒ 동시편집을 지원하는 백엔드로 yorkie 사용 (직접 구현은 손이 너무 많이 듬)
    
    1. 명세서 작성을 위한 툴을 제공한다
        - 명세서 폼
            - Markdown ([simplemde](https://simplemde.com/), [vue example](https://vuejs.org/examples/#markdown))
                - 직접 구현 할 것인가 혹은 존재하는 라이브러리를 사용 할 것인가?
                - [https://rlxuc0ppd.toastcdn.net/presentation/[NHN FORWARD 2020]12K만큼 사랑하는 오픈 소스 에디터 개발 이야기_류선임.pdf](https://rlxuc0ppd.toastcdn.net/presentation/%5BNHN%20FORWARD%202020%5D12K%EB%A7%8C%ED%81%BC%20%EC%82%AC%EB%9E%91%ED%95%98%EB%8A%94%20%EC%98%A4%ED%94%88%20%EC%86%8C%EC%8A%A4%20%EC%97%90%EB%94%94%ED%84%B0%20%EA%B0%9C%EB%B0%9C%20%EC%9D%B4%EC%95%BC%EA%B8%B0_%EB%A5%98%EC%84%A0%EC%9E%84.pdf)
                실제로 마크다운 에디터를 활용하여 개발한 사람의 발표자료, 개발할 때 참고하면 좋을 듯.
                - [https://github.com/nhn/tui.editor/tree/master/apps/vue-editor](https://github.com/nhn/tui.editor/tree/master/apps/vue-editor) : 
                이미 구현된 사례, 직접 사용하기엔 편해보임
                - [https://commonmark.org](https://commonmark.org/)
                명세 및 직접 구현에 도움이 되는 사이트
                - [https://github.github.com/gfm/](https://github.github.com/gfm/)
                깃허브 스타일의 마크다운에 관련된 사이트
        - ERD
            - 직접 드로잉 기능 구현
        - 순서도
            - 직접 드로잉 기능 구현
    2. 아이디어 구상을 위한 툴
        - 마인드맵
            - 드로잉 기능
            - 기본 : 화이트보드 구현(가능)
            - 심화 : 코드가 보이는 반투명 보드(고민 필요)
            - → 보통 canvas tag 기반으로 javascripts 이용해서 구현
            - vue 라이브러리 : [https://www.npmjs.com/package/vue-drawing-canvas](https://www.npmjs.com/package/vue-drawing-canvas)
                - 예시 [https://codesandbox.io/s/vue-3-drawing-canvas-demo-ihmmz?file=/src/App.vue](https://codesandbox.io/s/vue-3-drawing-canvas-demo-ihmmz?file=/src/App.vue)

## 심화기능

---

- 개발 진행 중을 위한 서비스
    1. 회의 녹화/녹음/기록을 보조해주는 기능 (공통기능으로 넣을지 추후 논의)
        - 회의 기록 : 클로바 노트처럼 회의 대화 / 채팅을 text로 받을 수 있게 함.
            - [https://docs.openvidu.io/en/stable/cheatsheet/send-messages/](https://docs.openvidu.io/en/stable/cheatsheet/send-messages/)
            - [https://docs.openvidu.io/en/stable/components/openvidu-custom-chat-panel/](https://docs.openvidu.io/en/stable/components/openvidu-custom-chat-panel/)
    2. MR 과정 중 생기는 코드 리뷰를 화상회의를 통해 면대면으로 하게 하는 과정
        - MR 요청시 깃 내 인원의 전체 회의 : 깃랩 연동, 깃헙 연동 (추후)
        - 머지 성공 시 프로젝트 인원들에게 알림 보냄
        - 머지 실패해도 프로젝트 인원들에게 알림 보냄
        - 회의 예약 기능에 머지 신청 결과를 아이콘으로 표시하기
        
        ---
        
        - 깃랩 기준으로 머지 요청만 받을 수 있음.
- 드래그 앤 드롭으로 파일 업로드
- 채팅창에 특정명령어와 함께 검색어 입력후 전송시 구글 검색 결과 페이지로 넘어가도록 하기
- 투표 기능
- 타이머 기능 → 정해진 시간 이내에 일 하기
- 이모티콘 추가

---

## 코딩 컨벤션

---

## Front

---

컴포넌트명 : 파스칼케이스

ex) ListItem.vue

<ListItem />

pk 변수명 : 스네이크구조

ex) user_pk

컴포넌트 불러올때

ex) import ListItem from ‘@/component/ListItem’

함수명 : 카멜케이스

ex)setData

vue 공식

[스타일 가이드 | Vue.js](https://v3.ko.vuejs.org/style-guide/)

## Back

---

## 기타

---

### 깃 관련 규칙

1. 커밋 메시지
    
    [커밋 유형] 스토리 번호 : 수정한 파일(여러개 일 경우 반점으로 구분) 혹은 폴더 이름 작성
    
    `[DOCS] [S07P11B309-9](https://jira.ssafy.com/browse/S07P11B309-9) : README.md`
    
    `[DOCS] ETC : foo.txt, foo1.js`
    
    1. 커밋은 영어로 작성한다
    2. 기타 내용은 스토리 번호를 ETC로 적는다
    
    <aside>
    ❓  **커밋 유형 종류**
    
    - FEAT : 새로운 기능의 추가
    - FIX: 버그 수정
    - DOCS: 문서 수정
    - STYLE: 스타일 관련 기능(코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
    - REFACTOR: 코드 리펙토링
    - TEST: 테스트 코트, 리펙토링 테스트 코드 추가
    - CHORE: 빌드 업무 수정, 패키지 매니저 수정(ex .gitignore 수정 같은 경우)
    </aside>
    
2. 백엔드 브랜치 권한자는 임웅균, 프론트 브랜치 권한자는 장종훈이 한다.
3. 머지 요청 메시지
    1. 머지 요청은 기능 단위로 구분한다.
    2. 머지 요청 이름은 기능 이름으로 한다.
    3. 머지 상세 내용은 브랜치의 커밋 메시지를 참고한다.

### REST API 관련

   

---
### JPA(Java Persistence API)

- 자바의 ORM 표준 스펙을 정의
- JPA의 스펙은 자바의 객체와 데이터베이스를 어떻게 매핑하고 동작해야 하는지를 정의 함.

### Hibernate

- ORM framework 중 하나
- JPA의 실제 구현체 중 하나이며, 현재 가장 많이 사용 됨.

### 설정 순서

1. maven에 추가
2. META-INF/persistence.xml 추가 (내용은 영상 참고)

### Persistence Context

- JPA가 관리하는 엔티티 객체의 집합
- 엔티티 객체가 Persistence Context에 들어오게 되면 JPA는 엔티티 객체의 매핑 정보를 가지고 DB에 반영
- 엔티티가 PC에 들어오게 되면, 그 객체를 Persistence Object라고 부름.
- PC는 세션 단위로 생명 주기를 가지고 있음
- PC에 접근하기 위해 Entity Manager를 사용
- Entity Manager는 하나의 세션으로 보고 아래와 같은 방식으로 동작을 구성함
    - ㄴㅁㅇㄹㅁㄹ
    - ㅁㄹㅁㅇㄹ
- JPA 어노테이션을 활용하여 엔티티 클래스를 정의함.
    - 23424
    - ㅁㄹㅇㄹ


------
## 2022-07-18
1. 와이어프레임 수정
2. API 문서 작성

-------
## 2022-07-19
오늘 한 일

1. 스토리 보드 보완 및 개선
2. ERD 구상 및 초안 작성
3. 프론트 기능 별 역할 배분
---

## 2022-07-20

오늘 한 일
1. erd 수정
2. api 문서 작성
3. FE 역할 배분

---

## 2022-07-25

오늘 한 일
1. OpenVidu 튜토리얼 진행 및 실행 성공
2. AWS 서버 세팅 및 docker + nginx 설치
3. 현재까지 진행된 프로젝트를 docker를 통해 image 만드는 시도 중

---

## 2022-07-26

오늘 한 일
1. 프로젝트 도커라이즈 계속 진행 -> 따로 JAR 파일까지는 성공
2. 각종 강의 및 특강 시청

---

## 2022-07-29

오늘 한 일
1. 발표자료 작성