<template>
  <div class="modal" v-if="isBool">
    <div class="overlay"></div>
    <div class="modal-card">
      <h3 class="confirm-text">{{ confirmText }}</h3>
      <div class="confirm-btn">
        <button @click="changeBool(true)" id="check">확인</button>
        <button @click="changeBool(false)" id="check">취소</button>
      </div>
    </div>
  </div>
<div class="img-container">
  <h1>저장된 이미지</h1>
  <div class="window" v-if="isData">
    <div class="container">
      <img v-for="image in demeet.imageList"
      :key="image.dipid"
      :image="image"
      @dblclick="downloadDelete(image)" 
      :src="`${image.url}`" 
      alt="">
    </div>
  </div>
  <div class="image-btn" v-if="isImage">
    <button><a type="button" @click="isBool=true, confirmText='정말 삭제하시겠습니까?'">delete</a></button>
    <button><a type="button" @click="isImage=false">cancel</a></button>
  </div>
  <div class="window2" v-if="!isData">
    <div class="content">
      <div class="slides">
        <img class="slide_item" src="@/assets/DEMEET.jpg" alt="">
        <img class="slide_item" src="@/assets/meeting-g8077ce66b_640.jpg" alt="">
        <img class="slide_item" src="@/assets/idea-g694f6ff46_640.jpg" alt="">
        <img class="slide_item" src="@/assets/video-call-gb0f0e6a82_640.jpg" alt="">
        <img class="slide_item" src="@/assets/lamp-g86bfbec92_640.jpg" alt="">
      </div>
    </div>
  </div>
  <div class="button-container" v-if="isData && !isImage">
    <button class="prev">previous</button>
    <button class="next">next</button>
  </div>
</div>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from "@/stores/account"
import { useRoute } from 'vue-router'
import axios from "axios"
export default {
  async setup() {
    const isBool = ref(false)
    const confirmText = ref('')
    const isData = ref(false),
          demeet = useAccountStore(),
          route = useRoute()  ,
          project_pk = ref(route.params.pid),
          isImage = ref(false),
          isDelete = ref(false),
          selectImage = ref(null)

    await axios({
        url: process.env.VUE_APP_API_URL + "projects/drawing/" + project_pk.value,
        method: 'get',
        headers: demeet.authHeader,
      })
        .then(res => {
          demeet.imageList = res.data.drawingPathList})

    const sliderOn = () => {
    const slides = document.querySelector('.slides'); // 슬라이드뼈대 감지
    const item = slides.getElementsByClassName('slide_item') // 슬라이드 아이템 획득
    const firstEle = item[0] // 첫번째 슬라이드 아이템
    firstEle.classList.add('ontheSlide') //첫번째 슬라이드 아이템에 ontheSlide 클래스 추가
      
    setInterval(sliderGo, 4000)
    
    function sliderGo () {
        
    const currentItem = document.querySelector('.ontheSlide'); // 현재 활성화된 슬라이드 아이템 감지

    currentItem.classList.remove('ontheSlide') //현재 활성화된 슬라이드 아이템 비활성화

      if (!currentItem.nextElementSibling) { // 만약 마지막 슬라이드 아이템이라면
          item[0].classList.add('ontheSlide') //첫번째 아이템을 활성화
      }else { // 그 외의 경우
        currentItem.nextElementSibling.classList.add('ontheSlide') //다음 엘리먼트를 활성화
      }   
    }
      
    }
    
    const init = () => {
      const container = document.querySelector(".container")
      const prevBtn = document.querySelector(".prev")
      const nextBtn = document.querySelector(".next");
      (function addEvent(){
        prevBtn.addEventListener('click', translateContainer.bind(this, 1))
        nextBtn.addEventListener('click', translateContainer.bind(this, -1))
      })()

      function translateContainer(direction){
        const selectedBtn = (direction === 1) ? 'prev' : 'next'
        container.style.transitionDuration = '500ms'
        container.style.transform = `translateX(0%)`
        container.ontransitionend = () => reorganizeEl(selectedBtn)
      }

      function reorganizeEl(selectedBtn) {
        container.removeAttribute('style'); (selectedBtn === 'prev') ? container.insertBefore(container.lastElementChild, container.firstElementChild): container.appendChild(container.firstElementChild)
      }
    }
    const carousel = ref(null)
    if (demeet.imageList.length === 0) {
      carousel.value = sliderOn
      isData.value = false
    }else {
      carousel.value = init
      isData.value = true
    }    
    const downloadDelete = (setImageData) => {
      isImage.value = true
      selectImage.value = setImageData.dipid
    }
    const deleteImage = () => {
      demeet.deleteImage(selectImage.value)
    }
    const changeBool = (res) => {
      if(res){
        deleteImage()
        isBool.value = false
      }else {
        isBool.value = false
      }
    }
    return {
      demeet,
      isData,
      isBool,
      isImage,
      carousel,
      isDelete,
      confirmText,
      downloadDelete,
      deleteImage,
      changeBool
    }
  },
  mounted() {
    this.carousel()
  }
}
</script>

<style scoped>
@media (max-width: 1024px){
  .img-container{
		display: flex;
    flex-direction: column;
    align-items: center;
  }
}

.button-container {
  margin-left: 28px;
}
.img-container {
  display: flex;
  flex-direction: column;
}
body {
  width: 2000px;
  height: 500px;
}

h1 {
  font-size: 50px;
  margin-top: 50px;
  margin-left: 28px;
  color: white;
}
.image-btn {
  margin-left: 28px;
}
.image-btn button {
  height: 30px;
  width: 80px;
  background: radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
  margin: 20px;
}
.image-btn button:hover {
  transform: scale(1.2);
}
.window {
  overflow: hidden;  /*check out container's movement : command + */
  border: 4px solid; 
  width: 500px;
  height: 300px;
  margin-left: 28px;
}
.window2 {
  overflow: hidden;  /*check out container's movement : command + */
  border: 4px solid; 
  width: 500px;
  height: 300px;
  margin-left: 28px;
  margin-bottom: 100px;
}

.container img {
  width: 500px;
  height: 300px;
}

button {
  height: 30px;
  width: 80px;
  background: radial-gradient(95% 60% at 50% 75%, #d6d600 0%, #f6e58d 100%);
  border: 1px solid #fdf754;
  box-shadow: 0px 8px 20px -8px #fbff11, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
  margin: 20px;
}

button:hover {
  transform: scale(1.2);
}

.content{ /*컨텐츠 넓이지정*/
  position:relative;
  width:500px;
}
.slides { /*슬라이드 아이템이 나올 뼈대 지정*/
  width:500px;
  height:300px;
  position:relative;

}
.slide_item { /*슬라이드 아이템을 absolute로 겹쳐놓고 투명하게 하기*/
  position:absolute;
  width:500px;
  height:300px;
  left: 0%;
  opacity:0;
   transition:all 0.3s;
}
.ontheSlide { /*현재 아이템에 붙여줄 클래스*/
  opacity:1;
  transition:all 0.3s;
}
.confirm-btn {
  margin-bottom: 8px;
  margin-right: 8px;
}
#check {
  background: radial-gradient(95% 60% at 50% 75%, #005FD6 0%, #209BFF 100%);
  border: 1px solid #54A1FD;
  box-shadow: 0px 8px 20px -8px #1187FF, inset 0px 1px 8px -4px #FFFFFF;
  border-radius: 12px;
  color: white;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  letter-spacing: .02em;
  transition: all .2s ease;
  -webkit-tap-highlight-color: rgba(255,255,255,0);
  margin: 20px;
  width: 80px;
  height: 30px;
}

#check:hover {
  transform: scale(1.2);
}

slot {
  margin: auto;
}
.modal{
  display: flex;
}
.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: #C4C4C4;
}
.modal-card {
  background: #2b2b2b !important;
  border-radius: 5px;
  position: relative;
  width: 400px;
  margin: auto;
  margin-top: 30px;
  background-color: #111315;
  min-height: 100px;
  z-index: 10;
  opacity: 1;
}
</style>
