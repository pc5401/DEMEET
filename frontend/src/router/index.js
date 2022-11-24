import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/main/MainView'
import DetailView from '@/views/main/DetailView'
import ConferenceView from '@/views/conference/ConferenceView'
import ProfileView from '@/views/account/ProfileView'
import LoginView from '@/views/account/LoginView'
import SignupView from '@/views/account/SignupView'
import FindPassword from '@/views/account/FindPassword'

const routes = [
  {
    path: '/',
    name: 'MainView',
    component: MainView
  },
  {
    path: '/project/:pid',
    name: 'DetailView',
    component: DetailView
  },
  {
    path: '/:pid/conference/:sessionId',
    name: 'ConferenceView',
    component: ConferenceView
  },
  {
    path: '/profile/me',
    name: 'ProfileView',
    component: ProfileView
  },
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/signup',
    name: 'SignupView',
    component: SignupView
  },
  {
    path: '/findpw',
    name: 'FindPassword',
    component: FindPassword
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
// 로우터 가드
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  // 토근이 존재할때 로그인화면으로 가려고하면 메인으로 이동
  if(to.name === 'LoginView') {
    if(token) {
      next({ name:'MainView' })
    }
  }
  // 토근이 존재할때 회원가입화면으로 가려고하면 메인으로 이동
  else if (to.name === 'SignupView') {
    if(token) {
      next({ name:'LoginView' })
    }
  }
  // 메인화면에서 토큰이 존재하지 않으면 로그인화면으로 이동
  else if (to.name === 'MainView') {
    if(!token) {
      next({ name:'LoginView' })
    }
  }
  // 상세페이지에서 토큰이 존재하지 않으면 로그인화면으로 이동
  else if (to.name === 'DetailView') {
    if(!token) {
      next({ name:'LoginView' })
    }
  }
  // 프로필화면에서 토큰이 존재하지 않으면 로그인화면으로 이동
  else if (to.name === 'ProfileView') {
    if(!token) {
      next({ name:'LoginView' })
    }
  }
  next()
})

export default router
