# 7.13

주제 정하기 회의 및 자료조사 진행

git branch사용 연습



노션정리

https://www.notion.so/wondaeme/7-13-74cb011a08964d17b933fdc324677148



# 7.14

프론트 코딩 컨벤션 회의

mvp, mlp 및 심화 회의

깃과 지라 사용에 관한 규칙 회의

기술 구현에 대한 자료조사



노션정리

https://www.notion.so/wondaeme/4ef259976b504f50930d71846b6f70db



# 7.15

간단한 프로젝트 흐름 작성

컴포넌트 구조 작성

컴포너트 구조 작성을 하며 있었던 아이디어 및 변경사항 회의

피그마 공부



# 7.18

1차 팀원평가

피그마를 이용한 와이어 프레임 작성

api 문서 작성 및 회의

jira정리 및 구체화



# 7.19

스토리보드 작성

프론트 담당 인원들 회의

컨퍼런스 담당으로 지정

컨퍼런스 2인 코드 짜는 순서 및 일정 회의

피그마에서 화면 구성 수정



# 7.20

component 폴더 구조 회의

---

##### 명세서 기반 스켈레톤 코드 정리

### 공용 웹 소켓 모듈

##### sockjs

https://github.com/sockjs/sockjs-client

##### websocket

https://github.com/jmesnil.stomp-websocket

##### STOMP.js

http://jmesnil.net/stomp-websocket/doc/

----------------------------------------------------

### WebRTC

##### Kurento

https://doc-kurento.readthedocs.io/en/latest/user/installation.html

java 예제 - https://github.com/Kurento/kurento-tutorial-java

javascript 예제 - https://github.com/Kurento/kurento-tutorial-js

### GroupCall 구현 및 통합

참고자료
https://doc-kurento.readthedocs.io/en/stable/user/tutorials.html#webrtc-many-to-many-video-call-group-call
https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-group-call

-----

element plus

https://element-plus.org/en-US/



# 7.21

gojs - ERD Tool

https://gojs.net/latest/api/

https://github.com/NorthwoodsSoftware/GoJS

frontend 스켈레톤 코드 분석 및 방향성 회의



drawing canvas

https://www.webrtc-experiment.com/Canvas-Designer/



# 7.25

chat.js 및 server.js 분리

채팅창 정상작동 확인

vue에 연동작업 및 백과 연결할 때 수정할 내용 주석에 작성

---

openvidu 이용을 위한 ubuntu 및 docker설치

openvidu를 이용하여 웹캠 화면 확인

openvidu에 있는 내용 vue에 연동하기 위한 방법 찾기



# 7.26

openvidu와 vue를 이용해 화상회의를 만든 프로젝트의 코드를 분석

openvidu와 vue를 연결하는법 공부



# 7.27

openvidu와 예시코드를 비교 및 분석하기

openvidu 기능정리

# 비디오관련

```
OvVideo.vue, UserVideo.vue
파일은 메인과 서브비디오만 나누고 그대로 사용
```

튜토리얼파일의 app.vue에 있는 화상회의 관련 내용 -> 통합 화상회의 화면에 몰아놓기



# 7.28

openvidu-custom의 angular코드를 실행 후 화상회의 안에 들어있는 기능들 확인

위의 코드를 기반으로 각 기능별의 코드를 확인하며 vue에서 어떤역할을 하는 지에 대해 공부

ts -> vue로 변환하는법



# 7.29

angular -> vue코드변환에 모듈에 담긴 코드를 수정하기 어려움 따라서 기능별 각자 구현으로 변경

chat.js 및 server.js를 기존의 프로젝트에 적용시키고 작동확인
