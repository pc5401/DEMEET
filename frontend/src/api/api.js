const HOST = process.env.VUE_APP_API_URL

const ACCOUNTS = 'users/'
const PROJECTS = 'projects/'
const DRAWING = 'drawing/'
const CONFERENCE = 'api-sessions/'

export default {
  accounts: {
    signup_userlist_signout: () => HOST + ACCOUNTS, //get, post, delete
    login: () => HOST + ACCOUNTS + 'login/', //post
    currentUserInfo: () => HOST + ACCOUNTS + 'me/' , // get
    checkemail: email => HOST + ACCOUNTS + `${email}/`, //get
    nickname_update: () => HOST + ACCOUNTS + 'nickname/', //patch
    password_update: () => HOST + ACCOUNTS + 'password/', //patch
    profileimage_update_delete: () => HOST + ACCOUNTS + 'profile/', //post, delete
    find_password: () => HOST + ACCOUNTS + 'password/forget/',
  },
  projects: {
    projects_create_update: () => HOST + PROJECTS, //get, patch
    projects_list: () => HOST + PROJECTS + 'activate/joined/',
    project_detail_delete: pid => HOST + PROJECTS + `${pid}/`, //get, delete
    image_list: pid => HOST + PROJECTS + DRAWING + `${pid}/`, // get
    image_delete: dipid => HOST + PROJECTS + DRAWING + `${dipid}/`, //delete
    image_save: () => HOST + PROJECTS + DRAWING, //post
    add_delete_user: () => HOST + PROJECTS + 'user/', //post, delete
  },
  conferences: {
    conference: () => HOST + CONFERENCE,
  }
}
