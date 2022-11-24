<template>
  <canvas width="800" height="600" id="canvas" style="border: 1px solid #ccc"></canvas>
  <div class="item-box">
    <div class="drawing-box">
      <button id="clear-canvas">clear</button>
      <button id="drawing-mode">펜 사용</button>
      <label class="width-label" for="drawing-line-width">굵기: </label>
      <span class="info">1</span>
      <input type="range" value="1" min="1" max="20" id="drawing-line-width">
      <label class="color-label" for="drawing-color">색깔: </label>
      <input type="color" value="#000000" id="drawing-color">
    </div>
    <div id="drawing-mode-options">
      <input type="text" id="text">
      <button id="itext">text</button>
      <button id="circle"><span class="material-symbols-outlined">circle</span></button>
      <button id="rect"><span class="material-symbols-outlined">check_box_outline_blank</span></button>
      <button id="triangle"><span class="material-symbols-outlined">change_history</span></button>
      <button id="delete"><span class="material-symbols-outlined">delete</span></button>
      <button id="save"><span class="material-symbols-outlined">save</span></button>
    </div>
  </div>

</template>

<script>
import { onMounted,ref } from 'vue'
import { fabric } from 'fabric'
import { useAccountStore } from "@/stores/account"
// import yorkie from 'yorkie-js-sdk'
export default {
  props:['openviduSessionId', 'mySessionId'],
  setup(props) {
    const init = () => {
      const color = ref('black')
      const sessionData = ref(props)
      const demeet = useAccountStore()
      // $ = id를 통해 적용할 태그를 설정
      let $ = function(id){return document.getElementById(id)}
      // 캔버스 생성
      let canvas = new fabric.Canvas('canvas',{
        backgroundColor: 'rgb(240,240,240)'
      })
      fabric.Object.prototype.transparentCorners = true
      // yorkie Client 설정
      // const client = new yorkie.Client('//localhost:8088', {
      //   syncLoopDuration: 0,
      //   reconnectStreamDelay: 1000
      // })
      // await client.activate()

      // // yorkie Document 연결
      // const doc = new yorkie.Document(props.mySessionId)
      // await client.attach(doc)

      // // yorkie document 설정
      // doc.update((root) => {
      //   if(!root['shapes']) {
      //     root['shapes'] = []
      //   }
      // }, 'create shape if not exists')

      // // 이벤트 연결 -> 변경된 부분을 새로 그리기.
      // doc.subscribe(() => {
      //   repaint(doc.getRoot().shapes)
      // })
      // await client.sync()

      // // 원격으로 변경된 도형들을 새로 그리는 함수
      // function repaint(shapes){
      //   // 새로 그리기 전 초기화
      //   canvas.clear()

      //   // shapes에 저장된 도형 그리기.
      //   for(const shape of shapes){
      //     // console.log(shape.object)
      //     fabric.loadSVGFromString(shape.object, (object) => {
      //       canvas.add(object[0])
      //     })
      //   }
      // }

      // repaint(doc.getRoot().shapes)


      // var isObjectMoving = false
      // canvas.on('object:moving', function () {
      //     isObjectMoving = true
      // });
      // var isObjectScaling = false
      // canvas.on('object:scaling', function() {
      //     isObjectScaling = true
      // });
      // var isObjectRotating = false
      // canvas.on('object:rotating', function() {
      //     isObjectRotating = true
      // })
      // canvas.on('mouse:up', function () {
      //     if (isObjectMoving || isObjectScaling || isObjectRotating ){
      //       isObjectMoving = false
      //       isObjectScaling = false
      //       isObjectRotating = false
      //       const object = canvas.getActiveObject()
            
            
      //       const ID = object.id
            
      //       console.log(ID + " changed")
      //       doc.update((root) => {
              
      //         root.shapes = root.shapes.filter((e) => e.id !== ID)
      //         root.shapes.push({
      //           'id' : ID,
      //           'object' : object.toSVG()
      //         })

      //         repaint(root.shapes)
      //       })
      //     } 
      // })
      // 선 그리기 이벤트
      // canvas.on('path:created', function(opt) {
      //   opt.path.id = getRandomId('path')
      //   const ID = opt.path.id
      //   console.log(opt.path);

      //   doc.update((root) => {
      //     //console.log(opt.path)
      //     const serialize = opt.path.toSVG();
      //     console.log(ID + " created")
      //     root.shapes.push({
      //       'id' : ID,
      //       'object' : serialize,
      //     });
      //     repaint(root.shapes);
      //   }, `update content by ${client.getID()}`)
      // })

      // function getRandomId(type){
      //   return type + Math.random().toString(32);
      // }
      // 드로잉모드 on/off element
      const drawingModeEl = $('drawing-mode'),
            // 도구상자 숨김/보이기 => 추후에 적용
            drawingOptionsEl = $('drawing-mode-options'),
            // 펜 색상 설정 element
            drawingColorEl = $('drawing-color'),
            // 펠 굵기 설정 element
            drawingLineWidthEl = $('drawing-line-width'),
            // 캔버스 초기화 element
            clearEl = $('clear-canvas'),
            // 원형 텍스트박스
            drawingCircleEl = $('circle'),
            // 사각형 텍스트 박스
            drawingRectEl = $('rect'),
            // 삼각형 텍스트 박스
            drawingTriangleEl = $('triangle'),
            // 텍스트 상자 버튼
            ITextEl = $('itext'),
            // 상자 안에 들어갈 텍스트 내용
            inputdata = $('text'),
            // 삭제 버튼
            deleteEl = $('delete'),
            //보기모드 및 백엔드로 보낼 데이터
            saveImage = $('save')

      // 캔버스 초기화
      clearEl.onclick = function() { 
        canvas.clear() 
        // doc.update((root) => {
        //   root.shapes = [];
        // })
        canvas.backgroundColor = 'rgb(240,240,240)'
      }
      // 클릭할때마다 드로잉모드 true/false
      drawingModeEl.onclick = function() {
        canvas.isDrawingMode = !canvas.isDrawingMode
        if (canvas.isDrawingMode) {
          drawingModeEl.innerText = '중지'
          drawingOptionsEl.style.display = 'none'
        }
        else {
          drawingModeEl.innerText = '펜 사용'
          drawingOptionsEl.style.display = ''
        }
      }
      // 펜 굵기 설정
      drawingLineWidthEl.onchange = function() {
        canvas.freeDrawingBrush.width = parseInt(this.value, 10) || 1
        this.previousSibling.innerText = this.value
      }
      
      // 펜 색상 설정
      drawingColorEl.onchange = function() {
        var brush = canvas.freeDrawingBrush
        brush.color = this.value
        color.value = this.value
        if (brush.getPatternSrc) {
          brush.source = brush.getPatternSrc.call(brush)
        }
      }

      // 원 텍스트 박스 설정
      drawingCircleEl.onclick = function () {
        // const ID = getRandomId('circle')
        let circle = new fabric.Circle({
          left: 150,
          top: 100,
          radius: 100,
          fill: color.value,
          scaleY: 0.5,
          originX: 'center',
          originY: 'center',
          //id: ID,
        })
        canvas.add(circle)
        // doc.update((root) => {
        //   const serialize = circle.toSVG();
        //   console.log(ID + " created")
        //   root.shapes.push({
        //     'id' : ID,
        //     'object' : serialize,
        //   });
        //   repaint(root.shapes);
        // }, `update content by ${client.getID()}`)
      }
      //사각형 텍스트 박스
      drawingRectEl.onclick = function() {
        // const ID = getRandomId('rect')
        let rect = new fabric.Rect({
          left: 150,
          top: 100,
          width: 200,
          height: 100,
          fill: color.value,
          originX: 'center',
          originY: 'center',
          // id: ID,
        })
        canvas.add(rect)
      //   doc.update((root) => {
      //     const serialize = rect.toSVG();
      //     console.log(ID + " created")
      //     root.shapes.push({
      //       'id' : ID,
      //       'object' : serialize,
      //     });
      //     repaint(root.shapes)
      //   }, `update content by ${client.getID()}`)
      }
      // 삼각형 텍스트 박스
      drawingTriangleEl.onclick = function() {
        // const ID = getRandomId('triangle')
        let triangle = new fabric.Triangle({
          left: 150,
          top: 100,
          width: 200,
          height: 100,
          fill: color.value,
          originX: 'center',
          originY: 'center',
          // id: ID
        })
        canvas.add(triangle)
        // doc.update((root) => {
        //   const serialize = triangle.toSVG();
        //   console.log(ID + " created")
        //   root.shapes.push({
        //     'id' : ID,
        //     'object' : serialize,
        //   });
        //   repaint(root.shapes)
        // }, `update content by ${client.getID()}`)
      }


      // 선택된 그림 삭제
      deleteEl.onclick = function () {
        canvas.remove(canvas.getActiveObject())
      }

      // 텍스트 박스 입력
      ITextEl.onclick = function () {
        //const ID = getRandomId('IText')
        let text = new fabric.IText(`${inputdata.value}`,{
          left: 100,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center',
          //id: ID,
        })
        canvas.add(text)
        inputdata.value = ''
        // doc.update((root) => {
        //   const serialize = text.toSVG();
        //   console.log(ID + " created")
        //   root.shapes.push({
        //     'id' : ID,
        //     'object' : serialize,
        //   });
        //   repaint(root.shapes)
        // }, `update content by ${client.getID()}`)
      }
      
      // 데이터 저장
      saveImage.onclick = () => {
        const dataURLtoFile = (dataurl, fileName) => {
  
          var arr = dataurl.split(','),
              mime = arr[0].match(/:(.*?);/)[1],
              bstr = atob(arr[1]), 
              n = bstr.length, 
              u8arr = new Uint8Array(n)
              
          while(n--){
              u8arr[n] = bstr.charCodeAt(n)
          }
          
          return new File([u8arr], fileName, {type:mime})
        }
      
      //Usage example:
        let file = dataURLtoFile(canvas.toDataURL({format: 'png', quality: 0.8}),'image.png')
        const imageData = ref({
          openviduSessionId: sessionData.value.openviduSessionId,
          multipartFile: file
        })
        // 백엔드로 갈 데이터 형식
        demeet.saveImage(imageData.value)
      }
    }
    onMounted(() => {
      init()
    })
  }
}

</script>
<style>
#text {
  width: 130px;
  height: 40px;
  border-radius: 10px;
}
.drawing-box button {
  margin: 4px;
  border-radius: 5px;
  background: #2097F7;
  color: white;
}
.drawing-box button span{
  margin-top: 4px;
}
.drawing-box button span:hover{
  transform: scale(1.4);
}

.color-label {
  margin: 4px;
  color: white;
}
.width-label {
  margin-top: 4px;
  color: white;
}
.info{
  color: white;
  margin: 8px;
}
#imageview svg{
  width: 300px;
  height: auto;
}
.item-box {
  margin: 8px;
  display: flex;
  flex-direction : column;
}
.drawing-box{
  display: flex;
  justify-content: flex-start;
  margin: 8px;
}
#drawing-mode-options {
  display: flex;
  justify-content: flex-start;
}
#drawing-mode-options button {
  margin: 4px;
  border-radius: 5px;
  background: #2097F7;
  color: white;
}
#drawing-mode-options button span:hover{
  transform: scale(1.4);
}

#drawing-mode-options button span{
  margin-top: 4px;
}

.confirm-text {
  margin: 25px;
  color: white;
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
.confirm-btn {
  display: flex;
  justify-content: center;
}
</style>
