<template>
  <div class="err-text">*<slot></slot></div>
</template>

<script>
export default {
  name: "ErrorText",
};
</script>

<style lang="scss" scoped>
.err-text {
  color: red;
}
</style>
