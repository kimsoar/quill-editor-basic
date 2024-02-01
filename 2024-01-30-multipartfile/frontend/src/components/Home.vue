<template>
    <div id="app">
      <img v-for="image in images" :src="image" alt="aa" :key="image" width="100" />
      <img :src="imgSrc" alt="aa" width="100" />
      <div>
        <button @click="onClickUploadFile">upload File</button>
      </div>
      <div class="editor">
        <RBeatEditor placeholder="입력 해주세요"/>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import RBeatEditor from './RBeatEditor.vue';

  export default {
    name: 'HomePage',
    components: {
        RBeatEditor
    },
    data() {
        return {
          images: [],
          imgName: '200',
          imgSrc: undefined,
        }
    },
    methods: {
      changeImg(){
                if(this.imgName === '200') {
                    this.imgName = '300';
                }else {
                    this.imgName = '200';
                }
            },
      getImageSelectElement(multiple) {
            let elem = document.createElement('input');
            elem.id = 'image';
            elem.type = 'file';
            elem.multiple = multiple;
            return elem;
        },
      onClickUploadFile() {
        const context = this;

            let elem = this.getImageSelectElement(false);
            elem.click();
            elem.onchange = function() {
                const formData = new FormData();
                formData.append('file', this.files[0]);
                axios.post('http://localhost:8011/api/file/uploadFile', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                .then((response) => {
                  console.log(response.data);
                  const image = response.data.fileDownloadUri;
                  context.imgSrc = image;
                  console.log(context.images);
                  context.images.push(image);
                })
                .catch((error) => {
                  console.error(error);
                });
            }
      }
    }
  }
  </script>
  
  <style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;

  }

  .editor {
     max-height: 300px; 
    }

</style>
  