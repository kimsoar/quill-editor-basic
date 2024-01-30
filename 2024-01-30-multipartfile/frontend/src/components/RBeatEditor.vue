<template>
    <div id="TextEditor">
      <quill-editor
        v-model="content"
        ref="myQuillEditor"
        :options="editorOption"
      />
    </div>
  </template>
  
  <script>
  import axios from 'axios';

  import "quill/dist/quill.snow.css";
  import { quillEditor } from "vue-quill-editor";
  import Quill from "quill";
  import ImageResize from "quill-image-resize-module";
  import ImageUploader from "quill-image-uploader";
  
  Quill.register("modules/imageUploader", ImageUploader);
  Quill.register("modules/imageResize", ImageResize);
  
  const baseOptions=[
    ["bold", "italic", "underline", "strike"],
    ["blockquote", "code-block"],

    [{ header: 1 }, { header: 2 }],
    [{ list: "ordered" }, { list: "bullet" }],
    [{ script: "sub" }, { script: "super" }],
    [{ indent: "-1" }, { indent: "+1" }],
    [{ direction: "rtl" }],

    //[{ 'size': ['small', false, 'large', 'huge'] }],
    [{ header: [1, 2, 3, 4, 5, 6, false] }],
    
    [{ color: [] }, { background: [] }],
    [{ font: [] }],
    [{ align: [] }],

    ["link", "image"],

    ["clean"],
  ];

// anti pattern
// 가능하면 function을 props로 넘기지 마시오.
const uploadCallbackDefault = (file) => {
    return new Promise((resolve, reject) => {
      const formData = new FormData();
      formData.append("file", file);
      axios.post("http://localhost:8080/file/uploadfileaaaa", formData, { headers: { 'Content-Type': 'multipart/form-data' } })
      .then(function (response) {
        console.log(response.data);
        resolve(response.data.fileDownloadUri);
      })
      .catch(function (error) {
        console.error(error);
        reject(error);
      });
    });
  };

  export default {
    name: "RBeatEditor",
    components: {
      quillEditor,
    },
    props: {
      placeholder: {
        type: String,
        default: ''
      },
      toolbarOptions: {
        type: Array,
        default: function() {
          return baseOptions;
        }
      },
      uploadProcess: {
        type: Function,
        default: uploadCallbackDefault,
      }
    },
    data() {
      return {
        content: "",
        editorOption: {
          debug: "warning",
          placeholder: this.placeholder,
          readOnly: true,
          theme: "snow",
          modules: {
            toolbar: this.toolbarOptions,
            imageResize: true,
            imageUploader: {
              upload: (file) => {
                  return new Promise(
                    (resolve, reject) => {
                      if(this.beforeUploadValidate(file)) {
                        resolve(file)
                      } else {
                        reject(file)
                      }
                    })
                  .then((file) => {
                    return this.uploadProcess(file)
                      .then((uri) => {
                        this.afterUpload(file);
                        console.log(uri)
                        return uri;
                      })
                  })
                }
            },
          },
        },
        showImageModal: true,
        formEntered: false,
        mounting: false,
    }},
    methods: {
      beforeUploadValidate(file) {
        console.log(file)
        // validate
        return true;
      },
      afterUpload(file) {
        console.log(file)
        // emit
      }
    },
  };
  </script>
  