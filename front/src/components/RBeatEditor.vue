<template>
    <div class="rteditor">
      <quill-editor
        v-model="content"
        ref="myQuillEditor"
        :options="editorOption"
      />
    </div>
  </template>
  
  <script>
  import "quill/dist/quill.snow.css";
  import { quillEditor } from "vue-quill-editor";
  import Quill from "quill";
  import ImageResize from "quill-image-resize-module";
  import ImageUploader from "quill-image-uploader";
  
  Quill.register("modules/imageUploader", ImageUploader);
  Quill.register("modules/imageResize", ImageResize);
  
  
  export default {
    name: "RBeatEditor",
    components: {
      quillEditor,
    },
    data: () => ({
      previewHtml: "",
      content: "",
      editorOption: {
        debug: "warning",
        placeholder: "Type your post...",
        readOnly: true,
        theme: "snow",
        modules: {
          toolbar: {
            container: [
              ["bold", "italic", "underline", "strike"],
              ["blockquote", "code-block"],
              [{ header: 1 }, { header: 2 }],
              [{ list: "ordered" }, { list: "bullet" }],
              [{ script: "sub" }, { script: "super" }],
              [{ indent: "-1" }, { indent: "+1" }],
              [{ direction: "rtl" }],
              [{ header: [1, 2, 3, 4, 5, 6, false] }],
              [{ color: [] }, { background: [] }],
              [{ font: [] }],
              [{ align: [] }],
              ["link", "image"],
              ["clean"],
            ],
          },
          imageResize: true,
          imageUploader: {
            upload: (file) => {
              console.log(file)
              return new Promise((resolve, reject) => {
                const formData = new FormData();
                formData.append("file", file);
                const options = {
                  method: "POST",
                  body: formData,
                };
                // let response = fetch("http://localhost:8080/upload/board", options);
                let response = fetch("http://localhost:8080/file/uploadfile", options);
                response
                  .then(function (response) {
                    return response.json();
                  })
                  .then(function (data) {
                    console.log(data);
                    //resolve(data.imageAccessUrl);
                  })
                  .catch((error) => {
                    reject(error);
                  });
              });
            },
          },
        },
      },
      showImageModal: false,
      formEntered: false,
      mounting: false,
    }),
    methods: {
      
    },
  };
  </script>

  <style>
  </style>
  