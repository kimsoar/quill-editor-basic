<template>
  <div id="app">
    <editor-test />
  </div>
</template>

<script>
import EditorTest from "./components/EditorTest.vue";

export default {
  name: "App",
  components: {
    EditorTest,
  },
};
</script>

<style scoped>
h1 {
  margin-left: 20px;
  margin-top: 10px;
}
</style>
