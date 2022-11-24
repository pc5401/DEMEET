import { defineStore } from "pinia";
import axios from 'axios'
import api from "@/api/api"
import router from "@/router"

export const useAccountStore = defineStore("account", {
  state: () => ({
    token: localStorage.getItem('token') || '' ,
    profile: {},
    userList: [],
    project: {},
    projects: [],
    endProjects: [],
    imageList: [],
    search: '',
    authError: null,
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Bearer ${state.token}`}),

  },
  actions: {
    //token값 저장
    saveToken(token) {
      this.token = token
      localStorage.setItem('token', token)
    },
    //token값 삭제
    removeToken() {
      this.token = ''
      localStorage.setItem('token', '')
    },
    // 유저 프로필
    fetchProfile() {
      axios({
        url: api.accounts.currentUserInfo(),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.profile = res.data.user
          this.endProjects = res.data.user.deActivateProjects.reverse()
        })
    },

    // 로그인 (이메일,패스워드)
    login(credentials) {
      axios({
        url: api.accounts.login(),
        method: 'post',
        data: credentials
      })
        .then(res => {
          const token = res.data.accessToken
          this.saveToken(token)        
          router.push({ name: 'MainView' })
        })
        .catch(err => {
          console.error(err.response)
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '이메일 혹은 비밀번호가 잘못되었습니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
        })
    },

    // 로그아웃
    logout() {
      this.removeToken()
      router.push({ name: 'LoginView'})
    },

    // 비밀번호 수정
    changePassword(namedata) {
      axios({
        url: api.accounts.password_update(),
        method: 'patch',
        data: namedata,
        headers: this.authHeader,
      })
        .then(() => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '비밀번호가 변경되었습니다. \n 다시 로그인 해주세요.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
            router.push({ name: 'LoginView'})
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
          this.removeToken()
        })
        .catch(err => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '현재 비밀번호가 다릅니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
          console.error(err.response)
        })
    },

    // 회원가입
    signup(signdata) {
      axios({
        url: api.accounts.checkemail(signdata.email),
        method: 'get',
      })
        .then(() => {
          axios({
            url: api.accounts.signup_userlist_signout(),
            method: 'post',
            data: signdata
          })
            .then(() => {
              router.push({ name: 'LoginView' })
            })
            .catch(err => {
              if (err.response.data.statusCode===400){
                const modal = document.createElement('div')
                const overlay = document.createElement('div')
                const modalCard = document.createElement('div')
                const h3 = document.createElement('h3')
                const button = document.createElement('button')
                const body = document.querySelector('body')
                modal.style.display = 'flex'
                modal.style.width = '100%'
                modal.style.height = '100%'
                modal.style.position = 'fixed'
                modal.style.left = 0
                modal.style.top = 0
                overlay.style.width = '100%'
                overlay.style.height = '100%'
                overlay.style.position = 'fixed'
                overlay.style.left = 0
                overlay.style.top = 0
                overlay.style.opacity = 0.5
                overlay.style.backgroundColor = '#C4C4C4'
                modalCard.style.background = '#2b2b2b'
                modalCard.style.borderRadius = '5px'
                modalCard.style.position = 'relative'
                modalCard.style.width = '400px'
                modalCard.style.margin = 'auto'
                modalCard.style.marginTop = '30px'
                modalCard.style.backgroundColor = '#111315'
                modalCard.style.minHeight = '100px'
                modalCard.style.zIndex = 10
                modalCard.style.opacity = 1
                h3.innerText = '이메일이 존재하지 않습니다.'
                h3.style.color = 'white'
                h3.style.textAlign = 'center'
                button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
                button.style.border = '1px solid #54A1FD'
                button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
                button.style.borderRadius = '12px'
                button.style.color = 'white'
                button.style.fontSize = '16px'
                button.style.lineHeight = '22px'
                button.style.letterSpacing = '.02em'
                button.style.transition = 'all .2s ease'
                button.style.width = '60px'
                button.style.height = '30px'
                button.style.textAlign = 'center'
                button.innerText = '확인'
                button.style.marginLeft = '340px'
                button.style.marginTop = '7px'
                button.addEventListener('click',function () {
                  modal.style.display = 'none'
                })
                modalCard.append(h3,button)
                modal.append(overlay,modalCard)
                body.append(modal)
              }
              
            })
            
        })
        .catch(err => {
          console.error(err.response)
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '중복된 메일입니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
        })

    },

    // 회원 탈퇴
    signout() {
      axios({
        url: api.accounts.signup_userlist_signout(),
        method: 'delete',
        headers: this.authHeader
      })
        .then(() => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '회원 탈퇴되었습니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
            router.push({ name: 'LoginView'})
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
          this.removeToken()
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    // 유저 닉네임 변경
    changeName(namedata) {
      axios({
        url: api.accounts.nickname_update(),
        method: 'patch',
        data: { nickname: namedata },
        headers: this.authHeader
      })
       .then(res => {
        this.profile = res.data
        router.go({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },
    // 유저 프로필 이미지 변경
    changeImage( image ) {
      const formData = new FormData()
      formData.append("multipartFile", image)
      axios({
        url: api.accounts.profileimage_update_delete(),
        method: 'post',
        data: formData,
        headers: this.authHeader
      })
       .then(() => {

        router.go({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },

    // 유저 프로필 삭제
    profileDeleteImage() {
      axios({
        url: api.accounts.profileimage_update_delete(),
        method: 'delete',
        headers: this.authHeader
      })
       .then(() => {
        router.go({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },

    //유저 목록조회
    fetchUserList() {
      axios({
        url: api.accounts.signup_userlist_signout(),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.userList = res.data.userList
        })
        .catch(err => {
          console.error(err)
        })
      
    },

    // 비밀번호 찾기
    findPassword(userData) {
      axios({
        url: api.accounts.find_password(),
        method: 'patch',
        data: userData,
      })
        .then(() => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '임시 비밀번호가 이메일로\n전송되었습니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
            router.push({name : 'LoginView'})
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
        })
        .catch(err => {
          console.error(err)
        })
      
    },
    
    
//////////////////////////////////////////////////////////////project
    // 프로젝트 상세 조회
    fetchProject(project_pk) {
      axios({
        url: api.projects.project_detail_delete(project_pk),
        method: 'get',
        headers: this.authHeader,
      }) 
        .then(res => {
          this.project = res.data.project
        })
        .catch(err => console.error(err.response))
    }, 

    // 유저가 속한 프로젝트 조회
    fetchProjects() {
      axios({
        url: api.projects.projects_list(),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.projects = res.data.activateProjects.reverse()
        })
        .catch(err => console.error(err.response))
    },
    // 프로젝트 생성
    createProject(projectData) {
      axios({
        url: api.projects.projects_create_update(),
        method: 'post',
        data: projectData,
        headers: this.authHeader,
      })
        .then(res => {
          this.project = res.data
          router.go({name: 'MainView'})
        })
        .catch(err => console.error(err.response))
    },

    // 유저 초대
    addUser(idData) {
      axios({
        url: api.projects.add_delete_user(),
        method: 'post',
        data: idData,
        headers: this.authHeader,
      })
        .then(() => {
          router.go({name: 'DetailView'})
        })
        .catch(err => console.error(err.response))
    },
    //유저 추방
    removeUser(idData) {
      axios({
        url: api.projects.add_delete_user(),
        method: 'delete',
        data: idData,
        headers: this.authHeader,
      })
        .then(() => {
          router.go({name:'MainView'})
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 데이터 수정 
    updateProject(projectData) {
      axios({
        url: api.projects.projects_create_update(),
        method: 'patch',
        data: projectData,
        headers: this.authHeader,
      })
        .then(() => {
          if (projectData.deactivate) {
            router.push({name:'MainView'})
          }else {
            router.go({name:'DetailView', parmas: {project_pk: projectData.pid}})
          }
        })
        .catch(err => console.error(err.response))
    },
    // 프로젝트 삭제
    deleteProject(project_pk) {
      axios({
        url: api.projects.project_detail_delete(project_pk),
        method: 'delete',
        headers: this.authHeader,
      })
        .then(() => {
          router.push({name:'ProfileView'})
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 이미지 리스트
    fetchImage(project_pk) {
      axios({
        url: api.projects.image_list(project_pk),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.imageList = res.data.drawingPathList
          
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 이미지 삭제
    deleteImage(dipid) {
      axios({
        url: api.projects.image_delete(dipid),
        method: 'delete',
        headers: this.authHeader,
      })
        .then(() => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '삭제되었습니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
            router.go({ name: 'DetailView' })
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 이미지 저장
    saveImage(imageData) {
      console.log(imageData.multipartFile)
      const formSaveData = new FormData()
      formSaveData.append("openviduSessionId", imageData.openviduSessionId)
      formSaveData.append("multipartFile", imageData.multipartFile)
      axios({
        url: api.projects.image_save(),
        method: 'post',
        data: formSaveData,
        headers: this.authHeader,
      })
        .then(() => {
          const modal = document.createElement('div')
          const overlay = document.createElement('div')
          const modalCard = document.createElement('div')
          const h3 = document.createElement('h3')
          const button = document.createElement('button')
          const body = document.querySelector('body')
          modal.style.display = 'flex'
          modal.style.width = '100%'
          modal.style.height = '100%'
          modal.style.position = 'fixed'
          modal.style.left = 0
          modal.style.top = 0
          overlay.style.width = '100%'
          overlay.style.height = '100%'
          overlay.style.position = 'fixed'
          overlay.style.left = 0
          overlay.style.top = 0
          overlay.style.opacity = 0.5
          overlay.style.backgroundColor = '#C4C4C4'
          modalCard.style.background = '#2b2b2b'
          modalCard.style.borderRadius = '5px'
          modalCard.style.position = 'relative'
          modalCard.style.width = '400px'
          modalCard.style.margin = 'auto'
          modalCard.style.marginTop = '30px'
          modalCard.style.backgroundColor = '#111315'
          modalCard.style.minHeight = '100px'
          modalCard.style.zIndex = 10
          modalCard.style.opacity = 1
          h3.innerText = '저장되었습니다.'
          h3.style.color = 'white'
          h3.style.textAlign = 'center'
          button.style.background = 'radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%)'
          button.style.border = '1px solid #54A1FD'
          button.style.boxShadow = '0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF'
          button.style.borderRadius = '12px'
          button.style.color = 'white'
          button.style.fontSize = '16px'
          button.style.lineHeight = '22px'
          button.style.letterSpacing = '.02em'
          button.style.transition = 'all .2s ease'
          button.style.width = '60px'
          button.style.height = '30px'
          button.style.textAlign = 'center'
          button.innerText = '확인'
          button.style.marginLeft = '332px'
          button.style.marginBottom = '8px'
          button.addEventListener('click',function () {
            modal.style.display = 'none'
          })
          modalCard.append(h3,button)
          modal.append(overlay,modalCard)
          body.append(modal)
        })
        .catch(err => console.error(err.response))
    }
  }
})