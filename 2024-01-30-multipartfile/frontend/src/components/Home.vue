<template>
    <div id="app">
      <img alt="Vue logo" src="../assets/logo.png">
      <RBeatEditor placeholder="입력 해주세요" :uploadProcess="upload"/>
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
            toolbar: [
                ["bold", "italic", "underline", "strike"],
                ["blockquote", "code-block"],

                [{ header: 1 }, { header: 2 }],
                [{ list: "ordered" }, { list: "bullet" }],
                [{ script: "sub" }, { script: "super" }],
                [{ indent: "-1" }, { indent: "+1" }],
                [{ direction: "rtl" }],
            ]
        }
    },
    methods: {
        upload(file) {
                return new Promise((resolve, reject) => {
                  // if 조건 이미지가 아니면 메세지 박스
                  // show message box
            
                  console.log('hehehehehe!!!!!!')

                  const formData = new FormData();
                  formData.append("file", file);
                  axios.post("http://localhost:8080/file/uploadfile", formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                  .then(function (response) {
                    console.log(response.data);
                    resolve(response.data.fileDownloadUri);
                  })
                  .catch(function (error) {
                    console.error(error);
                    reject(error);
                  });
                });
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
  </style>
  