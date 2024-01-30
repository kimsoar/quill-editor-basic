<template>
  <div class="blog-post-creator rteditor">
    <quill-editor
      v-model="content"
      ref="myQuillEditor"
      :options="editorOption"
    />
    <div v-html="previewHtml"></div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import "quill/dist/quill.snow.css";
import { quillEditor } from "vue-quill-editor";
import Quill from "quill";
import ImageResize from "quill-image-resize-module";
import { ImageDrop } from "quill-image-drop-module";
import ImageUploader from "quill-image-uploader";

Quill.register("modules/imageUploader", ImageUploader);
Quill.register("modules/imageResize", ImageResize);
Quill.register("modules/imageDrop", ImageDrop);

export default {
  name: "HelloWorld",
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
            ["bold", "italic", "underline", "strike"], // toggled buttons
            ["blockquote", "code-block"],

            [{ header: 1 }, { header: 2 }], // custom button values
            [{ list: "ordered" }, { list: "bullet" }],
            [{ script: "sub" }, { script: "super" }], // superscript/subscript
            [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
            [{ direction: "rtl" }], // text direction

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ font: [] }],
            [{ align: [] }],
            ["link", "image"],
            ["clean"], // remove formatting button
          ],
        },
        imageDrop: true,
        imageResize: true,
        // imageResize: {
        //   // ...
        //   handleStyles: {
        //     backgroundColor: "black",
        //     border: "none",
        //     color: "#fff",
        //     // other camelCase styles for size display
        //   },
        // },
        imageUploader: {
          upload: (file) => {
            return new Promise((resolve, reject) => {
              const formData = new FormData();
              formData.append("file", file);

              const options = {
                method: "POST",
                body: formData,
              };

              let response = fetch("http://localhost:8080/file/uploadfile", options);
              console.log("######################");
              response
                .then(function (response) {
                  return response.json();
                })
                .then(function (data) {
                  console.log(data);
                  resolve(data.fileDownloadUri);
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
  watch: {
    content(val) {
      if (!this.mounting) {
        this.$store.commit(
          "setDelta",
          this.$refs.myQuillEditor.quill.getContents()
        );
      }
      this.$store.commit("setContent", val);
      this.mounting = false;

      this.previewHtml = this.$refs.myQuillEditor.quill.root.innerHTML;
    },
  },
  methods: {
    showPreview() {
      this.$router.replace("/preview");
    },
    del() {
      // TODO
      this.formEntered = true;
    },
    save() {
      // TODO
      this.formEntered = true;
    },
    publish() {
      // TODO
      this.formEntered = true;
    },
  },
  computed: {
    ...mapGetters(["delta", "contents"]),
  },
  mounted() {
    this.mounting = true;
    if (!this.content && this.contents) {
      this.content = this.contents;
    }
  },
};
</script>
<style>
.blog-post-creator {
  margin: 100px;
}
.rteditor {
  margin-left: 20px;
  margin-right: 20px;
}
.cat-input,
.title-input {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.post-inputs {
  display: grid;
  width: 90%;
  grid-gap: 20px;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  padding-bottom: 15px;
  padding-top: 15px;
  /* padding: 20px; */
}
.ql-editor {
  height: 72vh;
}
.inner-image-input {
  display: flex;
  align-items: center;
}
.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}
.post-actions-row {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}
.ivu-icon-ios-camera-outline {
  font-size: 16px;
}
.btn-delete {
  color: #2d8cf0 !important;
  border: 1px solid #2d8cf0 !important;
}
.btn-delete:hover {
  color: red !important;
  border: 1px solid red !important;
}
</style>
