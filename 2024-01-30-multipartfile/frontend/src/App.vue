<!-- App.vue -->
<template>
  <div id="app">
    <nav>
      <router-link to="/"><div>홈</div></router-link>
      <router-link to="/editor"><div>에디터 테스트</div></router-link>
      <router-link to="/rbEditor"><div>RB 테스트</div></router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>


<script>

export default {
  name: "App",
};
</script>

<style scoped>
h1 {
  margin-left: 20px;
  margin-top: 10px;
}

nav {
  background-color: #eee;
  padding: 10px;
  margin-bottom: 20px;
}

router-link {
  font-size: x-large;
  margin: 50px;
  color: #333;
}

router-link:hover {
  color: #ff5722;
  cursor: pointer;
}
</style>
