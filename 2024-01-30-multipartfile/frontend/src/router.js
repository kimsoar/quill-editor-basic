import Vue from "vue";
import VueRouter from "vue-router";
import Home from "./components/Home.vue";
import EditorTest from "./components/EditorTest";

Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    { path: '/', component: Home },
    { path: '/editor', component: EditorTest },
  ],
});

export default router;
