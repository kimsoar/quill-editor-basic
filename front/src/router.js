import Vue from "vue";
import VueRouter from "vue-router";
import BlogPostCreator from "./components/BlogPostCreator";
import BlogPostPreview from "./components/BlogPostPreview";
import EditorTest from "./components/EditorTest";

Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    { path: "/create_post", component: BlogPostCreator },
    { path: "/preview", component: BlogPostPreview },
    { path: "/test", component: EditorTest },
  ],
});

export default router;
